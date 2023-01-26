package com.cache.cleaner.start.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_splash);

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()  {
            @Override
            public void run() {
                startActivity(intent);}
        }, 3000);
    }
}