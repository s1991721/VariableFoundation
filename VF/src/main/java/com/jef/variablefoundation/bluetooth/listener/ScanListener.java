package com.jef.variablefoundation.bluetooth.listener;

import android.support.annotation.NonNull;

import com.jef.variablefoundation.bluetooth.bean.Device;

import java.util.List;

/**
 * Created by mr.lin on 2019/3/13
 * 扫描回调
 */
public interface ScanListener {

    int MISS_INIT = -1;//没有init
    int TIME_OUT = -2;//扫描超时

    //已去重
    void scanResult(@NonNull List<Device> devices);

    void scanError(int code);

}
