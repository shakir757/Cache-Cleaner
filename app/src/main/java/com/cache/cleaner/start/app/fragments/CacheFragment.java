package com.cache.cleaner.start.app.fragments;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class CacheFragment extends Fragment{
    private PackageManager packageManager = null;
    private List applist = null;
    private File filePath = new File(Environment.getExternalStorageDirectory()+"/");



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
        ProgressBar progressBar = view.findViewById(R.id.progress_circular_bar_cache); // Progress bar of cache
        GifImageView gifLoad = view.findViewById(R.id.gif_load_cache); // Gif animation, enable at loading


        List<PackageInfo> packList = getContext().getPackageManager().getInstalledPackages(0);
        ArrayList<String> packages = new ArrayList<String>();



        for (int i=0; i < packList.size(); i++)
        {
            PackageInfo packInfo = packList.get(i);
            if ((packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            {
                String pack = packInfo.applicationInfo.packageName;
                packages.add(pack);
                String appName = packInfo.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
                Log.e("AppN" + Integer.toString(i), packages.toString());
            }
        }

        btnStartCleanCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }



//    public static void deleteCache(Context context) {
//        try {
//            File dir = context.getCacheDir();
//            deleteDir(dir);
//        } catch (Exception e) { e.printStackTrace();}
//    }
//
//    public static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//            return dir.delete();
//        } else if(dir!= null && dir.isFile()) {
//            return dir.delete();
//        } else {
//            return false;
//        }
//    }

}