package com.cache.cleaner.start.app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdsManager {

    private Context ctx;
    private InterstitialAd mInterstitialAd;

    public  AdsManager(Context ctx){
        this.ctx = ctx;
        MobileAds.initialize(ctx, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    public InterstitialAd createInterstitialAd(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(ctx,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });
        return mInterstitialAd;
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
