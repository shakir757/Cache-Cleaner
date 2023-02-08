package com.cache.cleaner.start.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cache.cleaner.start.app.R;

public class CacheCategoriesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cache_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnCleanCache = view.findViewById(R.id.button_clean_cache);
        Button btnCleanFiles = view.findViewById(R.id.button_clean_files);
        Button btnCleanHiddenFiles = view.findViewById(R.id.button_clean_hidden_files);
        ImageView goBack = view.findViewById(R.id.button_back_to_cache);
    }
}
