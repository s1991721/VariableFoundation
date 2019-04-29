package com.jef.variablefoundation.exception;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.jef.variablefoundation.BuildConfig;
import com.jef.variablefoundation.R;
import com.jef.variablefoundation.base.BaseActivity;
import com.jef.variablefoundation.base.BaseApplication;
import com.jef.variablefoundation.views.ActionBar;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by mr.lin on 2018/11/13
 * 异常展示页
 */
public class ExceptionActivity extends BaseActivity {

    private TextView exceptionTv;
    private ActionBar actionBar;

    public static void showException(Throwable throwable) {
        BaseApplication application = BaseApplication.getInstance();
        if (application != null && BuildConfig.DEBUG) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            throwable.printStackTrace(new PrintStream(byteArrayOutputStream));
            String msg = byteArrayOutputStream.toString();

            Intent intent = new Intent(application, com.jef.variablefoundation.exception.ExceptionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("msg", msg);
            application.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception);
        actionBar = findViewById(R.id.action_bar);
        actionBar.setFunction(ActionBar.FUNCTION_TITLE);
        actionBar.setTitle("崩溃日志");
        exceptionTv = findViewById(R.id.exceptionTv);
        handleIntent(getIntent(), false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent, true);
    }

    private void handleIntent(Intent intent, boolean isNew) {
        String msg = intent.getStringExtra("msg");
        if (!TextUtils.isEmpty(msg)) {
            if (isNew) {
                exceptionTv.append("\n\n\n\n");
            }
            exceptionTv.append(msg);
        }
    }

}
