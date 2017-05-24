package com.example.eltory.rejectcall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;

/**
 * Created by eltory on 2017-04-23.
 */
public class AppInfo extends AppCompatActivity{

    //@BindView()
    public static String version = "1.0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_inform);


    }
}
