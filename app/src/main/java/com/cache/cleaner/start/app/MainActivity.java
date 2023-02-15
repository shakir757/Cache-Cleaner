package com.cache.cleaner.start.app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cache.cleaner.start.app.fragments.BatteryCategoriesFragment;
import com.cache.cleaner.start.app.fragments.BatteryFragment;
import com.cache.cleaner.start.app.fragments.CacheCategoriesFragment;
import com.cache.cleaner.start.app.fragments.CacheFragment;
import com.cache.cleaner.start.app.fragments.SpeedFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    Button get_points_btn;
    TextView tvPoints;
    AdView adview;
    AdRequest adRequest;
    InterstitialAd mInterstitialAd;

    private int points = 0;
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        loadInterstitial();
        loadAndShowInterstitialStart();

        // google ads banner
        adview = findViewById(R.id.adView);
        AdsManager adsManager = new AdsManager(this);
        adsManager.createAds(adview);

        // google ads Interstitial

        // initializing elements
        tvPoints = findViewById(R.id.text_view_balance_points); // Points of user
        get_points_btn = findViewById(R.id.button_get_points);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.cache);

        points = mSettings.getInt("points", 0);
        tvPoints.setText("Balance: " + points + " points");

        get_points_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                points = mSettings.getInt("points", 0);
                points += 10;
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putInt("points", points);
                editor.apply();

                tvPoints.setText("Balance: " + points + " points");

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            loadInterstitial();
                        }
                    });

                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }

            }
        });
    }

    CacheFragment cacheFragment = new CacheFragment();
    BatteryFragment batteryFragment = new BatteryFragment();
    SpeedFragment speedFragment = new SpeedFragment();
    BatteryCategoriesFragment batteryCategoriesFragment = new BatteryCategoriesFragment();
    CacheCategoriesFragment cacheCategoriesFragment = new CacheCategoriesFragment();


    private void loadInterstitial() {
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d("load_err", loadAdError.toString());
                        Toast.makeText(MainActivity.this, "Error getPoints!", Toast.LENGTH_SHORT).show();
                        mInterstitialAd = null;
                    }
        });
    }

    private void loadAndShowInterstitialStart() {
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        interstitialAd.show(MainActivity.this);
                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                loadInterstitial();
                            }
                        });
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d("load_err", loadAdError.toString());
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        mInterstitialAd = null;
                    }
                });
    }


    public void onClickButtonCacheCategories(View view){
        set_Visibility(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, cacheCategoriesFragment).commit();
    }

    public void onClickButtonBatteryCategories(View view){
        set_Visibility(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, batteryCategoriesFragment).commit();
    }

    public void onClickButtonCache(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, cacheFragment).commit();
        set_Visibility(true);
    }

    public void onClickButtonBattery(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, batteryFragment).commit();
        set_Visibility(true);

    }

    public void set_Visibility(Boolean mode){
        if (mode){
            get_points_btn.setVisibility(View.VISIBLE);
            tvPoints.setVisibility(View.VISIBLE);
        }
        else{
            get_points_btn.setVisibility(View.INVISIBLE);
            tvPoints.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cache:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, cacheFragment).commit();
                return true;
            case R.id.battery:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, batteryFragment).commit();
                return true;
            case R.id.speed:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, speedFragment).commit();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {}

    public void refreshPoints(){
        tvPoints.setText("Balance: " + mSettings.getInt("points", 0) + " points");
    }
}