package com.jef.variablefoundation.bluetooth.bean;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;

import com.jef.variablefoundation.utils.Logger;

/**
 * Created by mr.lin on 2019/3/13
 * 蓝牙设备
 */
public class Device implements DeviceOperation {

    protected Logger logger = new Logger(this);

    protected BluetoothDevice mBluetoothDevice;

    private String name = "";

    public Device(BluetoothDevice bluetoothDevice) {
        this.mBluetoothDevice = bluetoothDevice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return TextUtils.isEmpty(mBluetoothDevice.getName()) ? name : mBluetoothDevice.getName();
    }

    public String getAddress() {
        return mBluetoothDevice.getAddress();
    }

    public BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevice;
    }

    @Override
    public void connect(Context context, DeviceChangeListener deviceChangeListener) {
    }

    @Override
    public void onRead(byte[] bytes) {
    }

    @Override
    public void write(String order) {
    }

    @Override
    public String toString() {
        return "Device{" +
                "logger=" + logger +
                ", mBluetoothDevice=" + mBluetoothDevice +
                ", name='" + name + '\'' +
                '}';
    }
}
