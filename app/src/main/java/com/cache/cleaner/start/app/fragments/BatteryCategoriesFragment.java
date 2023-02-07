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

public class BatteryCategoriesFragment extends Fragment {
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
    }
}
