package com.cache.cleaner.start.app.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cache.cleaner.start.app.CacheStatus;
import com.cache.cleaner.start.app.MainActivity;
import com.cache.cleaner.start.app.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CacheCategoriesFragment extends Fragment {

    final CacheStatus cacheStatus = CacheStatus.getInstance();

    SharedPreferences mSettings;

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

        mSettings = getActivity().getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        String[] paths = {Environment.getExternalStorageDirectory().getPath()+"/Telegram/Telegram Images", Environment.getExternalStorageDirectory().getPath()+"/Telegram/Telegram Video", Environment.getExternalStorageDirectory().getPath()+"/Telegram/Telegram File", Environment.getExternalStorageDirectory().getPath()+"/Telegram/Telegram Documents"};
        String path = Environment.getExternalStorageDirectory().getPath()+"/Download";
        File root = new File(path);
        File[] filesAndFolders = root.listFiles();

        btnCleanCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int points = 0;
                points = mSettings.getInt("points", 0);

                if (points >= 10) {
                    points -= 10;
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("points", points);
                    editor.apply();

                    ((MainActivity) getActivity()).refreshPoints();

                    cacheStatus.set_true();
                    cacheStatus.set_function("CLEAR");
                    ((MainActivity)getActivity()).onClickButtonCache(view);
                } else {
                    Toast.makeText(view.getContext(), "Not enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCleanFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int points = 0;
                points = mSettings.getInt("points", 0);

                if (points >= 10) {
                    points -= 10;
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("points", points);
                    editor.apply();

                    ((MainActivity) getActivity()).refreshPoints();

                    cacheStatus.set_true();
                    cacheStatus.set_function("NONE");
                    ((MainActivity)getActivity()).onClickButtonCache(view);
                } else {
                    Toast.makeText(view.getContext(), "Not enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCleanHiddenFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int points = 0;
                points = mSettings.getInt("points", 0);

                if (points >= 10) {
                    points -= 10;
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("points", points);
                    editor.apply();

                    ((MainActivity) getActivity()).refreshPoints();

                    cacheStatus.set_true();
                    cacheStatus.set_function("NONE");
                    ((MainActivity)getActivity()).onClickButtonCache(view);
                } else {
                    Toast.makeText(view.getContext(), "Not enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
