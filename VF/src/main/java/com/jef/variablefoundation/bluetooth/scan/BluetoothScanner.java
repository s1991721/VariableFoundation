package com.jef.variablefoundation.bluetooth.scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jef.variablefoundation.bluetooth.bean.Device;

/**
 * Created by mr.lin on 2019/3/13
 */
public class BluetoothScanner {

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

    public BluetoothScanner() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void scan(@NonNull DeviceFindListener deviceFindListener) {
        mDeviceFindListener = deviceFindListener;
        mBluetoothAdapter.startLeScan(leScanCallback);
    }

    public void stopScan() {
        mBluetoothAdapter.stopLeScan(leScanCallback);
    }

    public interface DeviceFindListener {
        void onDeviceFind(Device device);
    }

}
