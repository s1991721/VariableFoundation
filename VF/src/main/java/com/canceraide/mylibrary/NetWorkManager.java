package com.canceraide.mylibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.canceraide.mylibrary.base.BaseApplication;
import com.canceraide.mylibrary.base.BaseManager;
import com.canceraide.mylibrary.utils.async.AsyncTaskVF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.lin on 2018/12/5
 * 网络管理
 */
public class NetWorkManager extends BaseManager {

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkNetState();
        }
    };

    public static final int NETWORK_NONE = 0;
    public static final int NETWORK_UNKNOWN = 1;
    public static final int NETWORK_WIFI = 2;
    public static final int NETWORK_MOBILE = 3;

    private int currentType = NETWORK_NONE;
    private boolean isConnected = false;

    private List<OnNetStateChangeListener> onNetStateChangeListenerList;

    //暂不使用6.0的方法
    @Override
    protected void onManagerCreate() {
        isConnected = (getNetState() != NETWORK_NONE);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getApplication().registerReceiver(broadcastReceiver, intentFilter);
    }

    private int getNetState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (!networkInfo.isConnected()) {
                return NETWORK_NONE;
            }

            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_WIFI;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return NETWORK_MOBILE;
            } else {
                return NETWORK_UNKNOWN;
            }
        }
        return NETWORK_NONE;
    }

    private void checkNetState() {
        AsyncTaskVF<Integer> asyncTaskVF = new AsyncTaskVF<Integer>() {
            @Override
            public Integer runOnBackGround() {
                return getNetState();
            }

            @Override
            public void runOnUIThread(Integer integer) {
                if (integer != currentType) {
                    currentType = integer;
                    if (currentType == NETWORK_NONE) {
                        isConnected = false;
                    } else {
                        isConnected = true;
                    }
                    notifyNetStateChange(currentType);
                }
            }
        };
        asyncTaskVF.execute();
    }

    public boolean isConnected() {
        return isConnected;
    }

    public int currentNetworkType() {
        return currentType;
    }

    private void notifyNetStateChange(int type) {
        if (onNetStateChangeListenerList != null) {
            for (OnNetStateChangeListener onNetStateChangeListener : onNetStateChangeListenerList) {
                onNetStateChangeListener.onStateChange(type);
            }
        }
    }

    public void addOnNetStateChangeListener(OnNetStateChangeListener onNetStateChangeListener) {
        if (onNetStateChangeListenerList == null) {
            onNetStateChangeListenerList = new ArrayList<>();
        }
        onNetStateChangeListenerList.add(onNetStateChangeListener);
    }

    public void removeOnNetStateChangeListener(OnNetStateChangeListener onNetStateChangeListener) {
        if (onNetStateChangeListenerList == null) {
            return;
        }
        onNetStateChangeListenerList.remove(onNetStateChangeListener);
    }

    interface OnNetStateChangeListener {
        void onStateChange(int newState);
    }
}
