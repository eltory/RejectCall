package com.example.eltory.rejectcall;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by eltory on 2017-05-01.
 */
public class SettingTest extends AppCompatActivity {
    public static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //startActivity(new Intent(this, pref_fragment.class));
        getFragmentManager().beginTransaction().replace(android.R.id.content,
         new OptionPrefActivity()).commit();
    }
}
