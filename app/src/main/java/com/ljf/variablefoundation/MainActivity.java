package com.ljf.variablefoundation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jef.variablefoundation.base.BaseActivity;
import com.jef.variablefoundation.image.ImageLoader;
import com.jef.variablefoundation.net.http.OnResponseListener;
import com.jef.variablefoundation.user.bean.UserInfo;
import com.jef.variablefoundation.user.requester.LoginRequester;
import com.jef.variablefoundation.utils.PermissionUtils;

/**
 * Created by mr.lin on 2018/11/7
 */
public class MainActivity extends BaseActivity {
    Button httpBt;
    Button imageBt;
    ImageView imageV;
    Button showDialogBt;
    Button dismissDialogBt;
    Button exceptionBt;
    Button bluetoothBt;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            dismissLoadingDialog();
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        httpBt = findViewById(R.id.httpBt);
        httpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginRequester(new OnResponseListener<UserInfo>() {
                    @Override
                    public void onResponse(UserInfo data) {

                    }

                    @Override
                    public void onError(int code, String msg) {

                    }
                }, "", "").request();
            }
        });

        imageBt = findViewById(R.id.imageBt);
        imageV = findViewById(R.id.imageV);
        imageBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.load(imageV, "https://www.baidu.com/img/bd_logo2.png").setErrorResId(R.mipmap.ic_launcher).setCircle(true).apply();
            }
        });

        showDialogBt = findViewById(R.id.showDialogBt);
        showDialogBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog("dialog", false);
                handler.sendEmptyMessageDelayed(8, 5000);
            }
        });

        dismissDialogBt = findViewById(R.id.dismissDialogBt);
        dismissDialogBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissLoadingDialog();
            }
        });

        exceptionBt = findViewById(R.id.exceptionBt);
        exceptionBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = 10 / 0;
            }
        });

        PermissionUtils.requestAllPermission(this);

        bluetoothBt = findViewById(R.id.bluetoothBt);
        bluetoothBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(BluetoothActivity.class);
            }
        });
    }


//    TabItem 的使用
//    private View.OnClickListener onTabItemClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            clearTabItemsState();
//            showFragment((Class<BaseFragment>) v.getTag());
//        }
//    };
//
//    private String currentFragmentTag;
//
//    private void init() {
//        homeTab.setOnClickListener(onTabItemClickListener);
//        findTab.setOnClickListener(onTabItemClickListener);
//        hotTab.setOnClickListener(onTabItemClickListener);
//        mineTab.setOnClickListener(onTabItemClickListener);
//    }
//
//    private void clearTabItemsState() {
//        homeTab.setSelect(false);
//        findTab.setSelect(false);
//        hotTab.setSelect(false);
//        mineTab.setSelect(false);
//    }
//
//    private void showFragment(Class<BaseFragment> fragmentClass) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        BaseFragment hideFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(currentFragmentTag);
//        if (hideFragment != null) {
//            transaction.hide(hideFragment);
//        }
//
//        BaseFragment showFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(fragmentClass.getSimpleName());
//
//        if (showFragment == null) {
//            try {
//                showFragment = fragmentClass.newInstance();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            }
//            transaction.add(R.id.frame_layout, showFragment, fragmentClass.getSimpleName());
//        } else {
//            transaction.show(showFragment);
//        }
//        transaction.commit();
//        currentFragmentTag = fragmentClass.getSimpleName();
//
//    }
}
