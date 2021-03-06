package com.jef.variablefoundation.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jef.variablefoundation.utils.Logger;
import com.jef.variablefoundation.utils.ToastUtils;

/**
 * Created by mr.lin on 2018/11/7
 * <p>
 * Activity基类
 */
public class BaseActivity extends FragmentActivity {

    protected Logger logger = new Logger(this);
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().injectManager(this);
    }

    public static int dpToPx(Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5);
    }

    public static int dpToPx(int dp) {
        return (int) (BaseApplication.getInstance().getResources().getDisplayMetrics().density * dp + 0.5);
    }

    public void startActivity(Class<? extends BaseActivity> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public <M extends BaseManager> M getManager(Class<M> cls) {
        return BaseApplication.getInstance().getManager(cls);
    }

    public void showToast(int resId) {
        showToast(getString(resId));
    }

    public void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    public void showLoadingDialog() {
        showLoadingDialog("", false);
    }

    public void showLoadingDialog(String msg) {
        showLoadingDialog(msg, false);
    }

    public void showLoadingDialog(int resId) {
        showLoadingDialog(getString(resId), false);
    }

    public void showLoadingDialog(String msg, boolean cancelable) {
        if (isFinishing()) {
            return;
        }

        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadingDialog.setIndeterminate(false);
            loadingDialog.setCancelable(cancelable);
        }
        loadingDialog.setMessage(msg);
        loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
        dismissLoadingDialog();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideInput(view, ev)) {
                hideSoftInput();
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 是否该隐藏键盘
     *
     * @param v     点击时传进来的视图
     * @param event 点击事件
     * @return 是否该隐藏键盘
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void showSoftInput(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = this.getCurrentFocus();
        if (currentFocus == null)
            return;
        IBinder windowToken = currentFocus.getWindowToken();
        imm.hideSoftInputFromWindow(windowToken, 0);
    }

    public void onBackClick(View view) {
        finish();
    }
}
