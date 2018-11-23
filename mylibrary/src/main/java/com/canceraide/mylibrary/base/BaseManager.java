package com.canceraide.mylibrary.base;

import com.canceraide.mylibrary.utils.Logger;

/**
 * Created by mr.lin on 2018/11/13
 * 管理基类
 */
public abstract class BaseManager {

    protected Logger logger = new Logger(this);

    protected abstract void onManagerCreate();

    protected void onAllManagerCreate() {
    }

    protected <M extends BaseManager> M getManager(Class<M> manager) {
        return getApplication().getManager(manager);
    }

    protected BaseApplication getApplication() {
        return BaseApplication.getInstance();
    }

}
