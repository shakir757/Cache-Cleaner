package com.cache.cleaner.start.app.fragments;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.cache.cleaner.start.app.R;
import java.util.List;
import pl.droidsonroids.gif.GifImageView;

public class CacheFragment extends Fragment{
    private PackageManager packageManager = null;
    private List applist = null;


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

//        final PackageManager pm = getContext().getPackageManager();
//
//        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
//
//        for (ApplicationInfo packageInfo : packages) {
//            Log.d("package", "Installed package :" + packageInfo.packageName);
//            Log.d("dir", "Source dir : " + packageInfo.sourceDir);
//            Log.d("launch act", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
//        }

        List<PackageInfo> packList = getContext().getPackageManager().getInstalledPackages(0);
        for (int i=0; i < packList.size(); i++)
        {
            PackageInfo packInfo = packList.get(i);
            if (  (packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            {
                String appPath = packInfo.applicationInfo.packageName;
                String appName = packInfo.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
                Log.d("App N" + Integer.toString(i), appName);
                Log.d("App Path" + Integer.toString(i), appPath);
            }
        }

        btnStartCleanCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}