package com.ljf.variablefoundation.bluetooth;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jef.variablefoundation.bluetooth.bean.Device;
import com.ljf.variablefoundation.R;

import java.util.List;

/**
 * Created by mr.lin on 2019/3/15
 */
public class BluetoothAdapter extends RecyclerView.Adapter<BluetoothAdapter.ViewHolder> {

    private Context context;
    private List<Device> devices;
    private View.OnClickListener onClickListener;

    public BluetoothAdapter(Context context, List<Device> devices) {
        this.context = context;
        this.devices = devices;
    }

    public void setItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_bluetooth_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Device device = devices.get(i);
        viewHolder.nameTv.setText(device.getName());
        viewHolder.macTv.setText(device.getAddress());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.itemView.setBackgroundColor(Color.GRAY);
                viewHolder.itemView.setTag(i);
                onClickListener.onClick(viewHolder.itemView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv;
        TextView macTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
            macTv = itemView.findViewById(R.id.macTv);
        }
    }
}
