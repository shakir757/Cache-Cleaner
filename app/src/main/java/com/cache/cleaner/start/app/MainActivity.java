package com.cache.cleaner.start.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.cache.cleaner.start.app.fragments.BatteryFragment;
import com.cache.cleaner.start.app.fragments.CacheFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.cache);

    }
    CacheFragment cacheFragment = new CacheFragment();
    BatteryFragment batteryFragment = new BatteryFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cache:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, cacheFragment).commit();
                return true;

            case R.id.battery:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, batteryFragment).commit();
                return true;


        }
        return false;
    }
}