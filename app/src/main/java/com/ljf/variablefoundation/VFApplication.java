package com.ljf.variablefoundation;

import com.jef.variablefoundation.base.BaseApplication;
import com.jef.variablefoundation.base.BaseManager;
import com.jef.variablefoundation.bluetooth.BluetoothManager;

import java.util.List;

/**
 * Created by mr.lin on 2019/1/24
 */
public class VFApplication extends BaseApplication {
    @Override
    protected void registerManager(List<BaseManager> managers) {
        managers.add(new BluetoothManager());
    }

    @Override
    protected void init() {

    }

    @Override
    public void triggerLogin() {

    }

    @Override
    public void triggerHome() {

    }

    @Override
    public void triggerComplete() {

    }
}
