package com.jef.variablefoundation.bluetooth.device;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jef.variablefoundation.bluetooth.bean.Device;
import com.jef.variablefoundation.bluetooth.bean.DeviceChangeListener;
import com.jef.variablefoundation.utils.DigitUtils;
import com.jef.variablefoundation.utils.async.AsyncTaskVF;

import static com.jef.variablefoundation.bluetooth.device.MaiBoBoResult.RESULT_TYPE_FINAL;
import static com.jef.variablefoundation.bluetooth.device.MaiBoBoResult.RESULT_TYPE_LINKED;
import static com.jef.variablefoundation.bluetooth.device.MaiBoBoResult.RESULT_TYPE_PROCESSING;

/**
 * Created by mr.lin on 2019/3/15
 * 脉搏波
 */
public final class MaiBoBo extends Device {

    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCharacteristic characteristicRead;
    private BluetoothGattCharacteristic characteristicWrite;
    private DeviceChangeListener deviceChangeListener;
    private MaiBoboCallBack mMaiBoboCallBack;

    public MaiBoBo(Device device) {
        super(device.getBluetoothDevice());
    }

    public void setMaiBoboCallBack(MaiBoBo.MaiBoboCallBack maiBoboCallBack) {
        mMaiBoboCallBack = maiBoboCallBack;
    }

    @Override
    public void connect(@NonNull Context context, @NonNull final DeviceChangeListener deviceChangeListener) {
        this.deviceChangeListener = deviceChangeListener;
        mBluetoothGatt = mBluetoothDevice.connectGatt(context, false, new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                logger.i("onConnectionStateChange");
                if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothGatt.STATE_CONNECTED) {//连接成功才发现服务
                    mBluetoothGatt.discoverServices();
                } else {
                    deviceChangeListener.onConnectError();
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
                    }
                    if (characteristicRead != null && characteristicWrite != null) {
                        logger.i("onServicesDiscovered OK");
                        mBluetoothGatt.setCharacteristicNotification(characteristicRead, true);
                        deviceChangeListener.onConnected(MaiBoBo.this);
                    } else {
                        deviceChangeListener.onConnectError();
                    }
                }
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                logger.i("onCharacteristicChanged");
                onRead(characteristic.getValue());
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
    public void onRead(byte[] bytes) {
        parser(bytes);
    }

    @Override
    public void write(String order) {
        byte[] bytes = DigitUtils.hex2byte(order.getBytes());
        characteristicWrite.setValue(bytes);
        mBluetoothGatt.writeCharacteristic(characteristicWrite);
        deviceChangeListener.onWrite(order);
    }

    @Override
    public void disConnect() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
    }

    private void parser(final byte[] bytes) {
        new AsyncTaskVF<MaiBoBoResult>() {
            @Override
            public MaiBoBoResult runOnBackGround() {

                MaiBoBoResult result = new MaiBoBoResult();

                if (DigitUtils.byteToHex(bytes).startsWith("aa800203010100")) {
                    result.setType(RESULT_TYPE_LINKED);
                    return result;
                }

                if (DigitUtils.byteToHex(bytes).startsWith("aa800208010500000000")) {
                    result.setType(RESULT_TYPE_PROCESSING);

                    int mmHg = (0xFF & bytes[10]);
                    result.setMmHg(mmHg);

                    return result;
                }
                if (DigitUtils.byteToHex(bytes).startsWith("aa80020f0106")) {
                    result.setType(RESULT_TYPE_FINAL);

                    int sys = (0xFF & bytes[14]);
                    int dia = (0xFF & bytes[16]);
                    int pul = (0xFF & bytes[18]);
                    result.setSYS(sys);
                    result.setDIA(dia);
                    result.setPUL(pul);

                    return result;
                }

                return null;
            }

            @Override
            public void runOnUIThread(MaiBoBoResult maiBoBoResult) {
                deviceChangeListener.onRead(DigitUtils.byteToHex(bytes));
                if (mMaiBoboCallBack != null && maiBoBoResult != null) {
                    switch (maiBoBoResult.getType()) {
                        case RESULT_TYPE_LINKED:
                            mMaiBoboCallBack.onLinked();
                            break;
                        case RESULT_TYPE_PROCESSING:
                            mMaiBoboCallBack.onMeasuring(maiBoBoResult);
                            break;
                        case RESULT_TYPE_FINAL:
                            mMaiBoboCallBack.onFinalResult(maiBoBoResult);
                            break;
                    }
                }

            }
        }.execute();

    }

    //业务回调
    public interface MaiBoboCallBack {
        void onLinked();

        void onMeasuring(MaiBoBoResult maiBoBoResult);

        void onFinalResult(MaiBoBoResult maiBoBoResult);
    }

    //设备操作***********************************************************

    //连接
    public void link() {
        write("cc80020301010001");
    }

    //开始测量
    public void startMeasure() {
        write("cc80020301020002");
    }

    //关机
    public void shutdown() {
        write("cc80020301040004");
        disConnect();
    }

}
