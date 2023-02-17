package com.cache.cleaner.start.app.fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cache.cleaner.start.app.AdsManager;
import com.cache.cleaner.start.app.MainActivity;
import com.cache.cleaner.start.app.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import pl.droidsonroids.gif.GifImageView;

public class SpeedFragment extends Fragment {

    int seconds = 0;
    AdsManager adsManager;
    private InterstitialAd mInterstitialAd;

    SharedPreferences mSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_speed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        adsManager = new AdsManager(getContext());
        mSettings = getActivity().getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        loadInterstitial();

        Button btnSpeed = view.findViewById(R.id.button_speed);
        TextView tvPercents = view.findViewById(R.id.text_view_percents_speed); // Percents loading
        ProgressBar progressBar = view.findViewById(R.id.progress_circular_bar_speed); // Progress bar of cache
        GifImageView gifLoad = view.findViewById(R.id.gif_load_speed); // Gif animation, enable at loading
        gifLoad.setVisibility(View.INVISIBLE);
        AdsManager adsManager = new AdsManager(getContext());
        final InterstitialAd inter = adsManager.createInterstitialAd();

        btnSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int points = 0;
                points = mSettings.getInt("points", 0);

                if (points >= 10){
                    points -= 10;
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("points", points);
                    editor.apply();

                    ((MainActivity)getActivity()).refreshPoints();

                    btnSpeed.setClickable(false);
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

                            btnSpeed.setClickable(true);
                        }
                    }.start();

                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(getActivity());
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                loadInterstitial();
                            }
                        });
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        loadInterstitial();
                    }
                } else {
                    Toast.makeText(view.getContext(), "Not enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadInterstitial() {
        InterstitialAd.load(getContext(), "ca-app-pub-8657317529631499/1700655940", new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });
    }
}

