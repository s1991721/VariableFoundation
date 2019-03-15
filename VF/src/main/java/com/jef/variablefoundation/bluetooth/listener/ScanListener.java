package com.jef.variablefoundation.bluetooth.listener;

import android.support.annotation.Nullable;

import com.jef.variablefoundation.bluetooth.bean.Device;

import java.util.List;

/**
 * Created by mr.lin on 2019/3/13
 * 扫描回调
 */
public interface ScanListener {

    void scanResult(@Nullable List<Device> devices);

}
