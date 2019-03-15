package com.jef.variablefoundation.bluetooth.scan;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jef.variablefoundation.bluetooth.bean.Device;

/**
 * Created by mr.lin on 2019/3/15
 * Android 5.0 API 21修改了扫描方式
 * LOLLIPOP Ble Scanner
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BluetoothScanner21 extends BluetoothScanner {

    private BluetoothLeScanner scanner;
    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            if (device == null
                    || TextUtils.isEmpty(device.getAddress())
                    || TextUtils.isEmpty(device.getName())) {
                return;
            }
            Device device1 = new Device(device);

            deviceFindListener.onDeviceFind(device1);
        }

    };

    private DeviceFindListener deviceFindListener;

    public BluetoothScanner21() {
        scanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
    }

    @Override
    public void scan(@NonNull DeviceFindListener deviceFindListener) {
        this.deviceFindListener = deviceFindListener;
        scanner.startScan(null, new ScanSettings.Builder().build(), scanCallback);
    }

    @Override
    public void stopScan() {
        scanner.stopScan(scanCallback);
    }
}
