package com.canceraide.mylibrary.user;

import android.text.TextUtils;

import com.canceraide.mylibrary.base.BaseApplication;
import com.canceraide.mylibrary.base.BaseManager;
import com.canceraide.mylibrary.db.cache.SettingManager;
import com.canceraide.mylibrary.net.http.OnResponseListener;
import com.canceraide.mylibrary.user.bean.Feedback;
import com.canceraide.mylibrary.user.bean.MyInfo;
import com.canceraide.mylibrary.user.bean.UploadImgInfo;
import com.canceraide.mylibrary.user.bean.UserInfo;
import com.canceraide.mylibrary.user.requester.CheckAccountRequester;
import com.canceraide.mylibrary.user.requester.EditMyInfoRequester;
import com.canceraide.mylibrary.user.requester.LoginRequester;
import com.canceraide.mylibrary.user.requester.LogoutRequester;
import com.canceraide.mylibrary.user.requester.ModifyPasswordRequester;
import com.canceraide.mylibrary.user.requester.MyInfoRequester;
import com.canceraide.mylibrary.user.requester.RegisterRequester;
import com.canceraide.mylibrary.user.requester.ResetPasswordRequester;
import com.canceraide.mylibrary.user.requester.SaveFeedbackRequester;
import com.canceraide.mylibrary.user.requester.UploadFeedbackImgRequester;
import com.canceraide.mylibrary.user.requester.UploadHeadImgRequester;
import com.canceraide.mylibrary.user.requester.VerifyCodeRequester;
import com.canceraide.mylibrary.utils.json.JsonHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.lin on 2018/11/13
 * 用户管理
 */
public class UserManager extends BaseManager {

    private List<OnUserStateChangeListener> onUserStateChangeListeners = new ArrayList<>();

    private UserInfo mUserInfo;

    @Override
    protected void onManagerCreate() {
        initUser();
    }

    private void initUser() {
        mUserInfo = getUserInfo();
    }

    //唯一标识
    public String getUID() {
        return isLoggedIn() ? mUserInfo.getAccountId() : "";
    }

    public UserInfo getUser() {
        return mUserInfo;
    }

    public boolean isLoggedIn() {
        return mUserInfo != null;
    }

    //检查账号
    public void checkAccount(final OnResponseListener onResponseListener, String account) {
        new CheckAccountRequester(new OnResponseListener() {
            @Override
            public void onResponse(Object data) {
                onResponseListener.onResponse(data);
            }

            @Override
            public void onError(int code, String msg) {
                onResponseListener.onError(code, msg);
            }
        }, account).request();
    }

    //获取验证码
    public void getVerifyCode(final OnResponseListener onResponseListener, String mobile, String type) {
        new VerifyCodeRequester(new OnResponseListener() {
            @Override
            public void onResponse(Object data) {
                onResponseListener.onResponse(data);
            }

            @Override
            public void onError(int code, String msg) {
                onResponseListener.onError(code, msg);
            }
        }, mobile, type).request();
    }

    //注册
    public void register(final OnResponseListener onResponseListener, String account, String phone, String password, String verifyCode) {
        new RegisterRequester(new OnResponseListener() {
            @Override
            public void onResponse(Object data) {
                onResponseListener.onResponse(data);
            }

            @Override
            public void onError(int code, String msg) {
                onResponseListener.onError(code, msg);
            }
        }, account, phone, password, verifyCode).request();
    }

    //登录
    public void logIn(final OnResponseListener<UserInfo> onResponseListener, String account, String password) {
        new LoginRequester(new OnResponseListener<UserInfo>() {
            @Override
            public void onResponse(UserInfo data) {
                saveUserInfo(data);
                onResponseListener.onResponse(data);
                notifyUserLogin(data);
                if (data.isHasPrimaryCondition()){
                    BaseApplication.getInstance().triggerHome();
                }else {
                    BaseApplication.getInstance().triggerComplete();
                }
            }

            @Override
            public void onError(int code, String msg) {
                onResponseListener.onError(code, msg);
            }
        }, account, password).request();
    }

    //登出
    public void logOut() {
        new LogoutRequester(new OnResponseListener<UserInfo>() {
            @Override
            public void onResponse(UserInfo data) {
            }

            @Override
            public void onError(int code, String msg) {
            }
        }).request();
        saveUserInfo(null);
        notifyUserLogout();
    }

    //获取我的个人信息
    public void getMyInfo(final OnResponseListener<MyInfo> onResponseListener) {
        new MyInfoRequester(new OnResponseListener<MyInfo>() {
            @Override
            public void onResponse(MyInfo data) {
                onResponseListener.onResponse(data);
            }

            @Override
            public void onError(int code, String msg) {
                onResponseListener.onError(code, msg);
            }
        }).request();
    }

    //更新我的个人信息
    public void updateMyInfo(final OnResponseListener onResponseListener, final MyInfo myInfo) {
        new EditMyInfoRequester(new OnResponseListener() {
            @Override
            public void onResponse(Object data) {
                onResponseListener.onResponse(data);
            }

            @Override
            public void onError(int code, String msg) {
                onResponseListener.onError(code, msg);
            }
        }, myInfo).request();
    }

    //更新个人图像
    public void uplaodMyHeadImg(final OnResponseListener<UploadImgInfo> onResponseListener, String headImgUrl) {
        new UploadHeadImgRequester(new OnResponseListener<UploadImgInfo>() {

            @Override
            public void onResponse(UploadImgInfo data) {
                onResponseListener.onResponse(data);
            }

            @Override
            public void onError(int code, String msg) {
                onResponseListener.onError(code, msg);
            }
        }, headImgUrl).request();
    }

    //上传意见反馈图片
    public void uploadImg(final OnResponseListener<List<UploadImgInfo>> onResponseListener, final List<String> imgUrl) {
        new UploadFeedbackImgRequester(new OnResponseListener<List<UploadImgInfo>>() {
            @Override
            public void onResponse(List<UploadImgInfo> data) {
                onResponseListener.onResponse(data);
            }

            @Override
            public void onError(int code, String msg) {
                onResponseListener.onError(code, msg);
            }
        }, imgUrl).request();
    }

    //保存意见反馈
    public void saveFeedback(final OnResponseListener onResponseListener, Feedback feedback) {
        new SaveFeedbackRequester(new OnResponseListener() {
            @Override
            public void onResponse(Object data) {
                onResponseListener.onResponse(data);
            }

            @Override
            public void onError(int code, String msg) {
                onResponseListener.onError(code, msg);
            }
        }, feedback).request();
    }

    //用户信息本地化
    public void saveUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
        if (userInfo == null) {
            getManager(SettingManager.class).setGlobalSetting(SettingManager.GLOBAL_KEY_USER_INFO, "");
        } else {
            getManager(SettingManager.class).setGlobalSetting(SettingManager.GLOBAL_KEY_USER_INFO, JsonHelper.toJSONObject(userInfo).toString());
        }
    }

    private UserInfo getUserInfo() {
        String json = getManager(SettingManager.class).getGlobalSetting(SettingManager.GLOBAL_KEY_USER_INFO, "");
        if (TextUtils.isEmpty(json)) {
            return null;
        } else {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return JsonHelper.toObject(jsonObject, UserInfo.class);
        }
    }

    //重置密码
    public void resetPsw(final OnResponseListener onResponseListener, String phone, String password, String verifyCode) {
        new ResetPasswordRequester(new OnResponseListener() {
            @Override
            public void onResponse(Object data) {
                onResponseListener.onResponse(data);
            }

            @Override
            public void onError(int code, String msg) {
                onResponseListener.onError(code, msg);
            }
        }, password, phone, verifyCode).request();
    }

    //修改密码
    public void modifyPwd(final OnResponseListener onResponseListener, String oldPwd, String newPwd) {
        new ModifyPasswordRequester(new OnResponseListener() {
            @Override
            public void onResponse(Object data) {
                onResponseListener.onResponse(data);
            }

            @Override
            public void onError(int code, String msg) {
                onResponseListener.onError(code, msg);
            }
        }, oldPwd, newPwd).request();
    }

    //Observer

    public interface OnUserStateChangeListener {
        void onLogin(UserInfo userInfo);

        void onLogout();
    }

    public static final int STATE_ONLINE = 0;
    public static final int STATE_OFFLINE = 1;

    public void addOnUserStateChangeListener(OnUserStateChangeListener onUserStateChangeListener) {
        onUserStateChangeListeners.add(onUserStateChangeListener);
    }

    public void removeOnUserStateChangeListener(OnUserStateChangeListener onUserStateChangeListener) {
        onUserStateChangeListeners.remove(onUserStateChangeListener);
    }

    private void notifyUserLogin(UserInfo userInfo) {
        for (OnUserStateChangeListener listener : onUserStateChangeListeners) {
            listener.onLogin(userInfo);
        }
    }

    private void notifyUserLogout() {
        for (OnUserStateChangeListener listener : onUserStateChangeListeners) {
            listener.onLogout();
        }
    }

}
