package com.urskart;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class SplashActivity extends AppCompatActivity {
    Animation animation;
    CardView cardview;
    AppCompatImageView img_splash;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        cardview=findViewById(R.id.cardview);
        img_splash=findViewById(R.id.img_splash);
        img_splash.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.splash_background));

       /* animation= AnimationUtils.loadAnimation(this,R.anim.zoom);
        cardview.setAnimation(animation);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }
        },2500);
    }
}
