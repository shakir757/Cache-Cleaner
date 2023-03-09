package com.cache.cleaner.start.app.fragments;

import static android.content.ContentValues.TAG;
import static android.content.Context.BATTERY_SERVICE;
import static android.content.Context.POWER_SERVICE;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cache.cleaner.start.app.AdsManager;
import com.cache.cleaner.start.app.CacheStatus;
import com.cache.cleaner.start.app.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;


public class BatteryFragment extends Fragment {

    int brightness;
    LocationManager locationManager;
    boolean GpsStatus;
    final CacheStatus cacheStatus =  CacheStatus.getInstance();
    int seconds = 0;
    AdsManager adsManager;
    InterstitialAd mInterstitialAd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        adsManager = new AdsManager(getContext());
        loadInterstitial();
        return inflater.inflate(R.layout.fragment_battery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnBattery = view.findViewById(R.id.button_battery);
        TextView tvPercents = view.findViewById(R.id.text_view_percents_battery); // Percents loading
        ProgressBar progressBar = view.findViewById(R.id.progress_circular_bar_battery); // Progress bar of cache
        GifImageView gifLoad = view.findViewById(R.id.gif_load_battery); // Gif animation, enable at loading
        gifLoad.setVisibility(View.INVISIBLE);
        brightness = Settings.System.getInt(getContext().getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,0);

        Boolean status = cacheStatus.get_cache_status();

        if(status){
            //activating the animation
            gifLoad.setVisibility(View.VISIBLE);
            new CountDownTimer(15150, 150) {
                public void onTick(long millisUntilFinished) {
                    progressBar.setProgress(seconds);
                    tvPercents.setText(seconds + "%");
                    seconds++;
                }

                public void onFinish() {
                    gifLoad.setVisibility(View.INVISIBLE);
                    seconds = 0;
                    progressBar.setProgress(0);
                    tvPercents.setText("0 %");
                    Toast.makeText(getContext(),"Done! ",Toast.LENGTH_SHORT).show();

                }
            }.start();
            cacheStatus.set_false(); // меняем cashStatus
            //turn on advertising
            if (mInterstitialAd != null) {
                mInterstitialAd.show(getActivity());
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
                loadInterstitial();
            }
            //calling the battery saving function
            if (Objects.equals(cacheStatus.get_function(), "SAVE")) {
                saveBattery();
            }
            if (Objects.equals(cacheStatus.get_function(), "SAVETWENTYPERCENT")) {
                saveTwentyPercent();
            }
            if (Objects.equals(cacheStatus.get_function(), "OPTIMIZATIONBATTERY")) {
                optimizationBattry();
            }
        }

    }

    public void saveTwentyPercent(){
        disableBT();
        exta_save();
    }

    public void optimizationBattry(){
        reducingUsage();
        turnGPSOff();
    }

    public void saveBattery() {
        light();
        disableBT();
        turnGPSOff();
    }

    public void reducingUsage(){
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.cancel();
        Settings.System.putInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Settings.System.putInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 50); // 50% brightness

    }

    public void exta_save(){
        Settings.Global.putInt(getContext().getContentResolver(), Settings.Global.ANIMATOR_DURATION_SCALE, 0);
        Settings.Global.putInt(getContext().getContentResolver(), Settings.Global.TRANSITION_ANIMATION_SCALE, 0);
    }

    private void loadInterstitial() {
        InterstitialAd.load(getContext(), "ca-app-pub-9807331593671712/5093620362", new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d("error_load", loadAdError.toString());
                        Toast.makeText(getContext(), "Error battery", Toast.LENGTH_SHORT).show();
                        mInterstitialAd = null;
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