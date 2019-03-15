package com.jef.variablefoundation.bluetooth.bean;

import android.bluetooth.BluetoothDevice;

/**
 * Created by mr.lin on 2019/3/13
 * 蓝牙设备
 */
public class Device {

    BluetoothDevice bluetoothDevice;

    public Device(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getName() {
        return bluetoothDevice.getName();
    }

    public String getAddress() {
        return bluetoothDevice.getAddress();
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    @Override
    public String toString() {
        return getName()+"---"+getAddress();
    }
}
