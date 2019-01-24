package com.canceraide.mylibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.canceraide.mylibrary.utils.Logger;

/**
 * Created by mr.lin on 2018/11/7
 * <p>
 * Fragment基类
 */
public class BaseFragment extends Fragment {

    protected Logger logger = new Logger(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().injectManager(this);
    }

    public <M extends BaseManager> M getManager(Class<M> cls) {
        return BaseApplication.getInstance().getManager(cls);
    }

    public void showToast(int resId) {
        ((BaseActivity) getActivity()).showToast(resId);
    }

    public void showToast(String msg) {
        ((BaseActivity) getActivity()).showToast(msg);
    }

    public void showLoadingDialog() {
        ((BaseActivity) getActivity()).showLoadingDialog();
    }

    public void showLoadingDialog(String msg) {
        ((BaseActivity) getActivity()).showLoadingDialog(msg);
    }

    public void showLoadingDialog(int resId) {
        ((BaseActivity) getActivity()).showLoadingDialog(resId);
    }

    public void showLoadingDialog(String msg, boolean cancelable) {
        ((BaseActivity) getActivity()).showLoadingDialog(msg, cancelable);
    }

    public void dismissLoadingDialog() {
        ((BaseActivity) getActivity()).dismissLoadingDialog();
    }
}
