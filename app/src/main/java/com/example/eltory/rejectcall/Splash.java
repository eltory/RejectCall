package com.example.eltory.rejectcall;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by eltory on 2017-03-10.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }
}
