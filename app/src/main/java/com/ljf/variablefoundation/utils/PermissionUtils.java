package com.ljf.variablefoundation.utils;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.ljf.variablefoundation.base.BaseActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by mr.lin on 2018/11/19
 * 权限工具
 */
public class PermissionUtils {

    public static String[] permissions = new String[]{

    };

    public static void requestAllPermission(BaseActivity activity) {
        requestPermission(activity, permissions);
    }

    @SuppressLint("CheckResult")
    public static void requestPermission(BaseActivity activity, String... permissions) {
        new RxPermissions(activity)
                .requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (!permission.granted) {
                            //todo 权限未全部打开，影响部分功能
                        }
                    }
                });
    }

    public static boolean isPermissionGranded(BaseActivity activity, String permission) {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(activity, permission);
    }

    @SuppressLint("CheckResult")
    public static void requestPermission(BaseActivity activity, String permission) {
        new RxPermissions(activity)
                .requestEach(permission)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (!permission.shouldShowRequestPermissionRationale) {
                            //todo 提示跳转设置页
                        } else if (!permission.granted) {
                            //todo 权限未打开，影响功能使用
                        }
                    }
                });
    }

}
