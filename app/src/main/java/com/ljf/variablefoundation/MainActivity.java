package com.ljf.variablefoundation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ljf.variablefoundation.http.OnResponseListener;
import com.ljf.variablefoundation.image.ImageLoader;

/**
 * Created by mr.lin on 2018/11/7
 */
public class MainActivity extends Activity {
    Button httpBt;
    Button imageBt;
    ImageView imageV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpBt = findViewById(R.id.httpBt);
        httpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginRequester(new OnResponseListener<LoginInfo>() {
                    @Override
                    public void onResponse(int code, LoginInfo data) {
                        httpBt.setText(data.getName() + "\n" + data.getPwd());
                    }
                }).request();
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
    }
}
