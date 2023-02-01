package com.cache.cleaner.start.app.fragments;

import static android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
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

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;


public class BatteryFragment extends Fragment {

    int Brightness;
    LocationManager locationManager ;
    boolean GpsStatus ;

    public BatteryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_battery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button battery = view.findViewById(R.id.button_battery);
        Brightness = Settings.System.getInt(getContext().getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,0
        );


        battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                light();
                disableBT();
                turnGPSOff();
            }
        });
    }

    public void light(){
        WindowManager.LayoutParams layout = getActivity().getWindow().getAttributes();
        layout.screenBrightness = 30/100F;
        getActivity().getWindow().setAttributes(layout);
    }

    private void turnGPSOff(){
        locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(GpsStatus == true) {
            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent1);
        }
    }

    @SuppressLint("MissingPermission")
    public void disableBT(){
        final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
        bAdapter.disable();
    }

}