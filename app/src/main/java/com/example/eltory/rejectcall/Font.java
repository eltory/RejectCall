package com.example.eltory.rejectcall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by lsh on 2017. 7. 10..
 */

public class Font extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typekit.getInstance().addNormal(Typekit.createFromAsset(this, "NanumGothic.otf")).addBold(Typekit.createFromAsset(this, "NanumGothicBold.otf"));

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
