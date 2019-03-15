package com.jef.variablefoundation.bluetooth.bean;

import android.support.annotation.NonNull;

/**
 * Created by mr.lin on 2019/3/15
 * device 状态回调
 */
public interface DeviceChangeListener<D extends Device> {
    void onConnected(@NonNull D device);

    void onConnectError();

    void onRead(byte[] data);

    void onWrite();
}
