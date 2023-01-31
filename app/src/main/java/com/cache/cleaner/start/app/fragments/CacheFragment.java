package com.cache.cleaner.start.app.fragments;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Context.ACTIVITY_SERVICE;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cache.cleaner.start.app.FileListActivity;
import com.cache.cleaner.start.app.PermissionUtils;
import com.cache.cleaner.start.app.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class CacheFragment extends Fragment{
    private PackageManager packageManager = null;
    private static final int PERMISSION_STORAGE = 101;

    Context context;



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


        List<PackageInfo> packList = getContext().getPackageManager().getInstalledPackages(0);
        ArrayList<String> packages = new ArrayList<String>();

        if (PermissionUtils.hasPermissions(getContext())) return;
        PermissionUtils.requestPermissions(getActivity(), PERMISSION_STORAGE);

        String path = Environment.getExternalStorageDirectory().getPath()+"/Download";
        File root = new File(path);
        File[] filesAndFolders = root.listFiles();
        Toast.makeText(context, ""+filesAndFolders.toString(), Toast.LENGTH_SHORT).show();

        btnStartCleanCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()){
                    Intent intent = new Intent(getContext(), FileListActivity.class);
                    String path = Environment.getExternalStorageDirectory().getPath()+"/Download";
                    intent.putExtra("path",path);
                    startActivity(intent);
//                    deleteFiles(filesAndFolders);
                }else{
                    requestPermission();

                }
            }
        });
    }

    public void  deleteFiles(File[] filesAndFolders) {
        int len = filesAndFolders.length;
        for(int position = 0; position <= len; position++){
            File selectedFile = filesAndFolders[position];
            Toast.makeText(context, selectedFile.getName().toString(), Toast.LENGTH_SHORT).show();
//            selectedFile.delete();
        }
    }


    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else
            return false;
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//            Toast.makeText(getContext().this,"Storage permission is requires,please allow from settings",Toast.LENGTH_SHORT).show();
        }else
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PERMISSION_STORAGE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (PermissionUtils.hasPermissions(getContext())) {
                    Toast.makeText(getContext(), "Разрешение получено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Разрешение не получено", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Разрешение получено", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Разрешение не получено", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}