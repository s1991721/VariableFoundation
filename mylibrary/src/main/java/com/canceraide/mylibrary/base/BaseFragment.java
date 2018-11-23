package com.canceraide.mylibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by mr.lin on 2018/11/7
 * <p>
 * Fragment基类
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().injectManager(this);
    }

    public <M extends BaseManager> M getManager(Class<M> cls) {
        return BaseApplication.getInstance().getManager(cls);
    }
}
