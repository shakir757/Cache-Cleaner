package com.cache.cleaner.start.app.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cache.cleaner.start.app.AdsManager;
import com.cache.cleaner.start.app.R;
import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

public class SpeedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_speed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                if (inter != null) {
                    inter.show(getActivity());
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
                gifLoad.setVisibility(View.VISIBLE);
            }
        });
    }
}

