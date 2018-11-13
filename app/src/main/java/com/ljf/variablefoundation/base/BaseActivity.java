package com.ljf.variablefoundation.base;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Created by mr.lin on 2018/11/7
 * <p>
 * Activity基类
 */
public class BaseActivity extends FragmentActivity {

    ProgressDialog loadingDialog;

    public void showLoadindDialog() {
        showLoadindDialog("", false);
    }

    public void showLoadindDialog(String msg) {
        showLoadindDialog(msg, false);
    }

    public void showLoadindDialog(String msg, boolean cancelable) {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
        }
        loadingDialog.setMessage(msg);
        loadingDialog.setCanceledOnTouchOutside(cancelable);
        loadingDialog.show();
    }

    public void dismissLoadindDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    public void showToast(int resId) {
        showToast(getString(resId));
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
