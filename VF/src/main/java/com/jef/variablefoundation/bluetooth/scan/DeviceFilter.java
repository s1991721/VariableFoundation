package com.jef.variablefoundation.bluetooth.scan;

import com.jef.variablefoundation.bluetooth.bean.Device;

/**
 * Created by mr.lin on 2019/3/13
 */
public interface DeviceFilter {

    boolean filter(Device device);

}
