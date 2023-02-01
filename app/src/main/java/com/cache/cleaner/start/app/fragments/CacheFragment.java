package com.cache.cleaner.start.app.fragments;

import android.Manifest;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cache.cleaner.start.app.FileListActivity;
import com.cache.cleaner.start.app.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class CacheFragment extends Fragment{
    private PackageManager packageManager = null;
    private List applist = null;
    File[] filesAndFolders;




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

        String path = Environment.getExternalStorageDirectory().getPath()+"/Download";
        File root = new File(path);
        File[] filesAndFolders = root.listFiles();
        btnStartCleanCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()){
                    deleteFiles(filesAndFolders);
                }else{
                    requestPermission();
                }
            }
        });
    }
    public void deleteFiles(File[] filesAndFolders){
        int len = filesAndFolders.length;
        for(int position = 0; position < len; position++){
            Log.e("file: ", String.valueOf(filesAndFolders[position]));
            boolean deleted = filesAndFolders[position].delete();
            if(deleted){
                Toast.makeText(getContext(),"DELETED ",Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else
            return false;
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(getContext(),"Storage permission is requires,please allow from settings",Toast.LENGTH_SHORT).show();
        }else
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
    }


}