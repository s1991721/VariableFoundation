package com.jef.variablefoundation.bluetooth.device;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jef.variablefoundation.bluetooth.bean.Device;
import com.jef.variablefoundation.bluetooth.bean.DeviceChangeListener;

/**
 * Created by mr.lin on 2019/3/15
 * 脉搏波
 */
public final class MaiBoBo extends Device {

    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCharacteristic characteristicRead;
    private BluetoothGattCharacteristic characteristicWrite;
    private DeviceChangeListener deviceChangeListener;

    public MaiBoBo(Device device) {
        super(device.getBluetoothDevice());
    }

    @Override
    public void connect(@NonNull Context context, @NonNull final DeviceChangeListener deviceConnectListener) {
        mBluetoothGatt = mBluetoothDevice.connectGatt(context, false, new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                logger.i("onConnectionStateChange");
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    mBluetoothGatt.discoverServices();
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                logger.i("onServicesDiscovered");
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
                            logger.i("onServicesDiscovered OK");
                            mBluetoothGatt.setCharacteristicNotification(characteristicRead, true);
                            deviceConnectListener.onConnected(MaiBoBo.this);
                        } else {
                            deviceConnectListener.onConnectError();
                        }
                    }
                }
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                logger.i("onCharacteristicChanged");
                read(characteristic);
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                logger.i("onCharacteristicWrite");
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                logger.i("onCharacteristicRead");
            }
        });
    }

    @Override
    public void read(BluetoothGattCharacteristic characteristic) {
        deviceChangeListener.onRead(characteristic.getValue());
    }

    @Override
    public void write() {
        deviceChangeListener.onWrite();
    }

}
