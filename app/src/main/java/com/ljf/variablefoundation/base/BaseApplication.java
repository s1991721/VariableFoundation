package com.ljf.variablefoundation.base;

import android.app.ActivityManager;
import android.app.Application;

import com.ljf.variablefoundation.BuildConfig;
import com.ljf.variablefoundation.UserManager;
import com.ljf.variablefoundation.exception.ExceptionCaughtAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mr.lin on 2018/11/13
 * Application基类
 */
public class BaseApplication extends Application {


    private static BaseApplication application;

    public static BaseApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (getCurrentProcessName().equals(getPackageName())) {
            application = this;
            initManager();
            initLog();
        }
    }

    //获取当前进程
    private String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
            if (pid == runningAppProcessInfo.pid) {
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }

    private HashMap<String, BaseManager> managers = new HashMap<>();

    private void initManager() {
        List<BaseManager> managerList = new ArrayList<>();
        registerManager(managerList);
        for (BaseManager manager : managerList) {
            injectManager(manager);
            manager.onManagerCreate();
            managers.put(manager.getClass().getName(), manager);
        }

        for (Map.Entry<String, BaseManager> entry : managers.entrySet()) {
            entry.getValue().onAllManagerCreate();
        }

    }

    //注册manager
    private void registerManager(List managers) {
        managers.add(new UserManager());
    }

    //注解manager
    public void injectManager(Object object) {
        Class<?> cls = object.getClass();

        while (cls != BaseManager.class && cls != Object.class) {

            Field[] declaredFields = cls.getDeclaredFields();

            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    int modifiers = field.getModifiers();
                    if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
                        continue;
                    }

                    if (!field.isAnnotationPresent(Manager.class)) {
                        continue;
                    }

                    Class<?> type = field.getType();
                    if (!BaseManager.class.isAssignableFrom(type)) {
                        throw new RuntimeException("@Manager 注解只能应用到BaseManager的子类");
                    }

                    BaseManager baseManager = getManager((Class<? extends BaseManager>) type);

                    if (baseManager == null) {
                        throw new RuntimeException(type.getSimpleName() + " 管理类还未初始化！");
                    }

                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }

                    try {
                        field.set(object, baseManager);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            cls = cls.getSuperclass();
        }

    }

    public <M extends BaseManager> M getManager(Class<M> cls) {
        return (M) managers.get(cls.getName());
    }

    private void initLog() {
        if (BuildConfig.DEBUG) {
            Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
            ExceptionCaughtAdapter adapter = new ExceptionCaughtAdapter(handler);
            Thread.setDefaultUncaughtExceptionHandler(adapter);
        }
    }

}
