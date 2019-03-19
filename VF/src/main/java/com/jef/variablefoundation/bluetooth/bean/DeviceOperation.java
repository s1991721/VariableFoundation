package com.jef.variablefoundation.bluetooth.bean;

import android.content.Context;

/**
 * Created by mr.lin on 2019/3/15
 * Device 操作
 */
public interface DeviceOperation {
    void connect(Context context, DeviceChangeListener deviceChangeListener);

    void onRead(byte[] bytes);

    void write(String order);
}
