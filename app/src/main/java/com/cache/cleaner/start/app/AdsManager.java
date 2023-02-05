package com.cache.cleaner.start.app;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class AdsManager {

    private Context ctx;

    public  AdsManager(Context ctx){
        this.ctx = ctx;
        MobileAds.initialize(ctx, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    public void createAds(AdView adview){
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
//                Toast.makeText(ctx, ""+loadAdError.getCode(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
//                Toast.makeText(ctx, "ads loaded", Toast.LENGTH_SHORT).show();
            }
        });
        adview.loadAd(adRequest);


    }




}
