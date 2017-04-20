package com.example.soumyaagarwal.quickdeal;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;
    int height,width;
    ImageView icon,title;
    RelativeLayout.LayoutParams params_icon,params_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        icon= (ImageView) findViewById(R.id.icon);
        params_icon = new RelativeLayout.LayoutParams(width*30/100,height*30/100);
        params_icon.topMargin = height*27/100;
        params_icon.leftMargin = width*35/100;
        icon.setLayoutParams(params_icon);

        title= (ImageView) findViewById(R.id.title);
        params_title = new RelativeLayout.LayoutParams(width*48/100,height*10/100);
        params_title.topMargin = height*54/100;
        params_title.leftMargin = width*26/100;
        title.setLayoutParams(params_title);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(Splash.this,Screen1.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
