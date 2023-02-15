package com.cache.cleaner.start.app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cache.cleaner.start.app.CacheStatus;
import com.cache.cleaner.start.app.MainActivity;
import com.cache.cleaner.start.app.R;

public class BatteryCategoriesFragment extends Fragment {

    final CacheStatus cacheStatus = CacheStatus.getInstance();

    SharedPreferences mSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_battery_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnPowerSaving = view.findViewById(R.id.button_power_saving);
        Button btnExtendBattery = view.findViewById(R.id.button_extend_battery);
        Button btnBatteryOptimization = view.findViewById(R.id.button_battery_optimization);
        ImageView goBack = view.findViewById(R.id.button_back_to_battery);

        mSettings = getActivity().getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        btnPowerSaving.setOnClickListener(new View.OnClickListener() {
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
                    cacheStatus.set_function("SAVE");
                    ((MainActivity)getActivity()).onClickButtonBattery(view);
                } else {
                    Toast.makeText(view.getContext(), "Not enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnExtendBattery.setOnClickListener(new View.OnClickListener() {
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
                    ((MainActivity)getActivity()).onClickButtonBattery(view);
                } else {
                    Toast.makeText(view.getContext(), "Not enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBatteryOptimization.setOnClickListener(new View.OnClickListener() {
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
                    ((MainActivity)getActivity()).onClickButtonBattery(view);
                } else {
                    Toast.makeText(view.getContext(), "Not enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
