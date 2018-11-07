package com.ljf.variablefoundation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ljf.variablefoundation.http.OnResponseListener;

/**
 * Created by mr.lin on 2018/11/7
 */
public class MainActivity extends Activity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginRequester(new OnResponseListener<LoginInfo>() {
                    @Override
                    public void onResponse(int code, LoginInfo data) {
                        button.setText(data.getName() + "\n" + data.getPwd());
                    }
                }).request();
            }
        });
    }
}
