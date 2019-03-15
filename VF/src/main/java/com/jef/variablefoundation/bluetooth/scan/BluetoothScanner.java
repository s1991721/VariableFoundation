package com.jef.variablefoundation.bluetooth.scan;

import android.os.Build;
import android.support.annotation.NonNull;

import com.jef.variablefoundation.bluetooth.bean.Device;

/**
 * Created by mr.lin on 2019/3/13
 */
public abstract class BluetoothScanner {

    public static BluetoothScanner getScanner() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new BluetoothScanner21();
        }

        return new BluetoothScannerImp();
    }

    public abstract void scan(@NonNull DeviceFindListener deviceFindListener);

    public abstract void stopScan();

    public interface DeviceFindListener {
        void onDeviceFind(Device device);
    }

}
