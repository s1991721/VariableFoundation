package com.jef.variablefoundation.bluetooth.bean;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;

import com.jef.variablefoundation.utils.Logger;

/**
 * Created by mr.lin on 2019/3/13
 * 蓝牙设备
 */
public class Device implements DeviceOperation {

    protected Logger logger = new Logger(this);

    protected BluetoothDevice mBluetoothDevice;

    public Device(BluetoothDevice bluetoothDevice) {
        this.mBluetoothDevice = bluetoothDevice;
    }

    public String getName() {
        return mBluetoothDevice.getName();
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
    public void read(BluetoothGattCharacteristic characteristic) {
    }

    @Override
    public void write() {
    }

}
