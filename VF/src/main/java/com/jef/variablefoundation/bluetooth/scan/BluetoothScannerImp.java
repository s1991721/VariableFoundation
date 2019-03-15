package com.jef.variablefoundation.bluetooth.scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jef.variablefoundation.bluetooth.bean.Device;

/**
 * Created by mr.lin on 2019/3/15
 * Ble API是在 Android4.3 API18加入的
 * Base Ble Scanner
 */
public class BluetoothScannerImp extends BluetoothScanner {

    private BluetoothAdapter mBluetoothAdapter;
    private DeviceFindListener mDeviceFindListener;

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (device == null || TextUtils.isEmpty(device.getName())) {
                return;
            }
            Device device1 = new Device(device);
            mDeviceFindListener.onDeviceFind(device1);
        }
    };

    public BluetoothScannerImp() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public void scan(@NonNull DeviceFindListener deviceFindListener) {
        mDeviceFindListener = deviceFindListener;
        mBluetoothAdapter.startLeScan(leScanCallback);
    }

    @Override
    public void stopScan() {
        mBluetoothAdapter.stopLeScan(leScanCallback);
    }

}
