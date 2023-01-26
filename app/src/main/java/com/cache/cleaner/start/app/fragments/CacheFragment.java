package com.cache.cleaner.start.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cache.cleaner.start.app.R;

import pl.droidsonroids.gif.GifImageView;

public class CacheFragment extends Fragment {

    public CacheFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cache, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvPercents = view.findViewById(R.id.text_view_percents); // Percents loading
        TextView tvPoints = view.findViewById(R.id.text_view_balance_points); // Points of user
        Button btnStartCleanCache = view.findViewById(R.id.button_start_clean_cache); // Start Clean Cache
        Button btnGetPoints = view.findViewById(R.id.button_get_points); // Get start ads for user
        ProgressBar progressBar = view.findViewById(R.id.progress_circular_bar); // Progress bar of cache
        GifImageView gifLoad = view.findViewById(R.id.gif_load); // Gif animation, enable at loading
    }
}