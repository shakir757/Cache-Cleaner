package com.cache.cleaner.start.app.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cache.cleaner.start.app.R;

import java.util.Date;


public class BatteryFragment extends Fragment {

    int Brightness;

    public BatteryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_battery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button battery = view.findViewById(R.id.battery_btn);
        Brightness = Settings.System.getInt(getContext().getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,0
        );

        battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                light();
                disableBT();
            }
        });
    }

    public void light(){
        WindowManager.LayoutParams layout = getActivity().getWindow().getAttributes();
        layout.screenBrightness = 30/100F;
        getActivity().getWindow().setAttributes(layout);
    }

    @SuppressLint("MissingPermission")
    public void disableBT(){
        final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
        bAdapter.disable();
    }

}