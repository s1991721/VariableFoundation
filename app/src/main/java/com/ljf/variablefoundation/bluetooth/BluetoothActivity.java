package com.ljf.variablefoundation.bluetooth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jef.variablefoundation.base.BaseActivity;
import com.jef.variablefoundation.bluetooth.BluetoothManager;
import com.jef.variablefoundation.bluetooth.bean.Device;
import com.jef.variablefoundation.bluetooth.listener.InitListener;
import com.jef.variablefoundation.bluetooth.listener.ScanListener;
import com.ljf.variablefoundation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mr.lin on 2019/3/14
 * 蓝牙
 */
public class BluetoothActivity extends BaseActivity {

    private TextView textView;
    private Button initBt;
    private Button scanBt;
    private Button linkBt;
    private Button sendBt;
    private RecyclerView recyclerView;
    private BluetoothManager mBluetoothManager;

    private List<Device> mDevices;
    private BluetoothAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        textView = findViewById(R.id.textView);

        initBt = findViewById(R.id.initBt);
        scanBt = findViewById(R.id.scanBt);
        linkBt = findViewById(R.id.linkBt);
        sendBt = findViewById(R.id.sendBt);

        recyclerView = findViewById(R.id.recyclerView);

        mBluetoothManager = getManager(BluetoothManager.class);

        mDevices = new ArrayList<>();
        mAdapter = new BluetoothAdapter(this, mDevices);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBluetoothManager.init(new InitListener() {
                    @Override
                    public void initResult(int code) {
                        switch (code) {
                            case INIT_OK:
                                textView.setText("初始化成功");
                                break;
                            case NO_BLE_FEATURE:
                                textView.setText("不支持BLE");
                                break;
                            case ADAPTER_DISABLE:
                                textView.setText("蓝牙未打开");
                                mBluetoothManager.openBluetooth();
                                break;
                            case LOCATION_PERMISSION_DISABLE:
                                textView.setText("无定位权限");
                                break;
                            case LOCATION_DISABLE:
                                textView.setText("定位没打开");
                                break;
                        }
                    }
                });
            }
        });
        scanBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("扫描中。。。");
                mBluetoothManager.scan(new ScanListener() {
                    @Override
                    public void scanResult(@NonNull List<Device> devices) {
                        mDevices.clear();
                        mDevices.addAll(devices);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void scanError(int code) {

                    }

                });
            }
        });
        mAdapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBluetoothManager.connect(mDevices.get((Integer) v.getTag()));
            }
        });
        linkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
