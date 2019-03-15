package com.jef.variablefoundation.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.jef.variablefoundation.base.BaseManager;
import com.jef.variablefoundation.bluetooth.bean.Device;
import com.jef.variablefoundation.bluetooth.listener.InitListener;
import com.jef.variablefoundation.bluetooth.listener.ResultListener;
import com.jef.variablefoundation.bluetooth.listener.ScanListener;
import com.jef.variablefoundation.bluetooth.scan.BluetoothScanner;
import com.jef.variablefoundation.bluetooth.scan.DeviceFilter;
import com.jef.variablefoundation.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mr.lin on 2019/3/14
 * 蓝牙管理
 */
public class BluetoothManager extends BaseManager {

    @Override
    protected void onManagerCreate() {
    }

    private boolean isInitSuccess;
    private boolean isScanning;

    //初始化************************************************************

    public void init(@NonNull InitListener initListener) {

        if (!getApplication().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {//设备不支持ble
            initListener.initResult(InitListener.NO_BLE_FEATURE);
            return;
        }

        if (!isBluetoothEnable()) {//蓝牙没打开
            initListener.initResult(InitListener.ADAPTER_DISABLE);
            return;
        }

        if (Build.VERSION.SDK_INT >= 23) {//6.0版本

            if (!PermissionUtils.isPermissionGranded(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION)) {//定位权限
                initListener.initResult(InitListener.LOCATION_PERMISSION_DISABLE);
                return;
            }

            if (!isLocalServiceEnable()) {//定位是否开启
                initListener.initResult(InitListener.LOCATION_DISABLE);
                return;
            }

        }

        isInitSuccess = true;
        initListener.initResult(InitListener.INIT_OK);
    }

    //蓝牙是否开启
    private boolean isBluetoothEnable() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    //定位是否开启
    private boolean isLocalServiceEnable() {
        if (Build.VERSION.SDK_INT < 23) {
            LocationManager locationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
            boolean gpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean netEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            return gpsEnable || netEnable;
        } else if (Build.VERSION.SDK_INT < 28) {
            int mode = Settings.Secure.getInt(getApplication().getContentResolver(), Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
            return mode != Settings.Secure.LOCATION_MODE_OFF;
        } else {//9.0新方法
            LocationManager locationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
            return locationManager.isLocationEnabled();
        }
    }

    //打开蓝牙
    public boolean openBluetooth() {
        return BluetoothAdapter.getDefaultAdapter().enable();
    }

    //扫描**************************************************************

    private BluetoothScanner mBluetoothScanner;
    private Map<String, Device> mScannedDevices = new HashMap<>();
    private ScanListener mScanListener;

    public void scan(@NonNull ScanListener scanListener) {
        scan(scanListener, null);
    }

    public void scan(@NonNull final ScanListener scanListener, @Nullable final DeviceFilter deviceFilter) {

        if (!isInitSuccess) {
            scanListener.scanError(ScanListener.MISS_INIT);
            return;
        }
        mScanListener = scanListener;

        if (mBluetoothScanner == null) {
            mBluetoothScanner = BluetoothScanner.getScanner();
        }

        mBluetoothScanner.scan(new BluetoothScanner.DeviceFindListener() {
            @Override
            public void onDeviceFind(Device device) {
                Log.i("BluetoothManager", device.getAddress());
                if (!mScannedDevices.containsKey(device.getAddress())) {

                    Log.i("BluetoothManager", "save");
                    if (deviceFilter != null) {
                        if (deviceFilter.filter(device)) {
                            mScannedDevices.put(device.getAddress(), device);
                            notifyScanResult();
                        }
                    } else {
                        mScannedDevices.put(device.getAddress(), device);
                        notifyScanResult();
                    }
                }
            }
        });
        isScanning = true;
    }

    public void stopScan() {
        mBluetoothScanner.stopScan();
        isScanning = false;
    }

    private void notifyScanResult() {
        List<Device> devices = new ArrayList<>(mScannedDevices.size());
        for (String mac : mScannedDevices.keySet()) {
            devices.add(mScannedDevices.get(mac));
        }
        mScanListener.scanResult(devices);
    }

    //连接*************************************************************

    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCharacteristic characteristicRead;
    private BluetoothGattCharacteristic characteristicWrite;

    public void connect(Device device) {
        if (isScanning) {
            stopScan();
        }
        mBluetoothGatt = device.getBluetoothDevice().connectGatt(getApplication(), false, new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                Log.i("BluetoothManager", "onConnectionStateChange");
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    mBluetoothGatt.discoverServices();
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                Log.i("BluetoothManager", "onServicesDiscovered");
                for (BluetoothGattService s : gatt.getServices()) {
                    int charaSize = s.getCharacteristics().size();
                    if (charaSize < 2) continue;
                    characteristicRead = null;
                    characteristicWrite = null;
                    for (int i = 0; i < charaSize; i++) {
                        BluetoothGattCharacteristic c = s.getCharacteristics().get(i);
                        if (c.getDescriptors() != null && c.getDescriptors().size() != 0) {
                            if (characteristicWrite == null && c.getProperties() == BluetoothGattCharacteristic.PROPERTY_WRITE) {
                                characteristicWrite = c;
                            } else if (characteristicRead == null && c.getProperties() == BluetoothGattCharacteristic.PROPERTY_NOTIFY) {
                                characteristicRead = c;
                            }
                        }
                        if (characteristicRead != null && characteristicWrite != null) {
                            Log.i("BluetoothManager", "onServicesDiscovered OK");
                            mBluetoothGatt.setCharacteristicNotification(characteristicRead, true);
                        }
                    }
                }
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                Log.i("BluetoothManager", "onCharacteristicChanged");
                read(characteristic);
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                Log.i("BluetoothManager", "onCharacteristicWrite");
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                Log.i("BluetoothManager", "onCharacteristicRead");
            }
        });
    }

    //控制*************************************************************

    //发送指令
    @WorkerThread
    // TODO: 2019/3/13
    public void send(byte[] order) {
        Log.i("BluetoothManager", "send");
        characteristicWrite.setValue(order);
        mBluetoothGatt.writeCharacteristic(characteristicWrite);
    }

    //获取数据***********************************************************
    public ResultListener mResultListener;

    //接收指令
    @WorkerThread
    // TODO: 2019/3/13
    public void read(BluetoothGattCharacteristic characteristic) {
//        Log.i("BluetoothManager", "read:\n" + Util.bytes2hex(characteristic.getValue()));
        mResultListener.onResult(characteristic.getValue());
    }
}
