package com.canceraide.mylibrary;

import com.canceraide.mylibrary.base.BaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.lin on 2018/11/13
 * 用户管理
 */
public class UserManager extends BaseManager {

    private List<OnUserStateChangeListener> onUserStateChangeListeners = new ArrayList<>();

    @Override
    protected void onManagerCreate() {

    }

    public void logIn() {

    }

    public void logOut() {

    }

    public String getUID() {
        return "";
    }

    public boolean isLogedIn() {
        return true;
    }

    //Observer

    public interface OnUserStateChangeListener {
        void onStateChange(int state);
    }

    public static final int STATE_ONLINE = 0;
    public static final int STATE_OFFLINE = 1;

    public void addOnUserStateChangeListener(OnUserStateChangeListener onUserStateChangeListener) {
        onUserStateChangeListeners.add(onUserStateChangeListener);
    }

    public void removeOnUserStateChangeListener(OnUserStateChangeListener onUserStateChangeListener) {
        onUserStateChangeListeners.remove(onUserStateChangeListener);
    }

    private void notifyUserStateChange(int state) {
        for (OnUserStateChangeListener listener : onUserStateChangeListeners) {
            listener.onStateChange(state);
        }
    }

}
