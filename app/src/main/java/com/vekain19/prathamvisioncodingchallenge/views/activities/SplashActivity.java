package com.vekain19.prathamvisioncodingchallenge.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.vekain19.prathamvisioncodingchallenge.R;

public class SplashActivity extends Activity {

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        FrameLayout l=findViewById(R.id.root);
        l.clearAnimation();
        l.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = findViewById(R.id.logo_img);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 1200) {
                        sleep(100);
                        waited += 100;
                    }

                    //for checking login
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    if (pref.getString("first_run", "0").equalsIgnoreCase("1")) {
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    }else {
                        Intent intent = new Intent(SplashActivity.this, UserAuthentication.class);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    }
                 } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashActivity.this.finish();
                }}
        };
        splashTread.start();
    }
}
