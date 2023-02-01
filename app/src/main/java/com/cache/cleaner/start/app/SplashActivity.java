package com.cache.cleaner.start.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_splash);

        ConstraintLayout splashBg = findViewById(R.id.splash_bg);
        TextView tvHeader = findViewById(R.id.text_view_header);
        GifImageView gifLoad = findViewById(R.id.gif_load_splash);
        CardView cardView = findViewById(R.id.card_view_splash);

        splashBg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_bg));
        gifLoad.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_bg));
        tvHeader.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up_tv));
        cardView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down_cv));

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()  {
            @Override
            public void run() {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
            }
        }, 3000);
    }
}