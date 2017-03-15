package com.app.sc.helicopter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;


public class SplashActivity extends FragmentActivity {

    private final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashlayout);


        Runnable r = new Runnable(){
            @Override
            public void run() {
                start();
            }
        };



        long spDelay = 2000; //

        Handler h = new Handler();
        h.postDelayed(r, spDelay);
    }

    private void start(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
