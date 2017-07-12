package com.example.eltory.rejectcall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Font{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         /*  Calling Service start  */
        Intent serviceIntent = new Intent(this, CallingService.class);
        startService(serviceIntent);

         /*  All Permissions check  */
        Permissions permissions = new Permissions();
        permissions.permissionCheck(this);

        startActivity(new Intent(this, Splash.class));
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*  AdMob ad banner  */
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.info) {
                    startActivity(new Intent(MainActivity.this, AppInfo.class));
                }
                return false;
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.content,
                new OptionPrefActivity()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

}