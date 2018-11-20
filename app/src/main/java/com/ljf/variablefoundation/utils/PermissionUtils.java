package com.ljf.variablefoundation.utils;

import android.Manifest;
import android.annotation.SuppressLint;

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
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE
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
//        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(activity, permission);
        return new RxPermissions(activity).isGranted(permission);
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
