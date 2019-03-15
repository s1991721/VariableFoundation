package com.jef.variablefoundation.bluetooth.listener;

/**
 * Created by mr.lin on 2019/3/13
 * 初始化回调
 */
public interface InitListener {

    int INIT_OK = 0;

    int NO_BLE_FEATURE = -1;
    int ADAPTER_DISABLE = -2;
    int LOCATION_PERMISSION_DISABLE = -3;
    int LOCATION_DISABLE = -4;

    void initResult(int code);

}
