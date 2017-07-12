package com.example.eltory.rejectcall;

import android.app.Activity;

import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;

/**
 * Created by eltory on 2017-05-09.
 */
public class PopUpTest extends Activity {

    @BindView(R.id.tv_content)
    TextView tvCont;
    private UnansweredListAdapter adapter;
    protected ListView tv;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.popup_unanswered_list);

         /*  AdMob ad banner  */
        AdView mAdView = (AdView) findViewById(R.id.adView_);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setVisibility(View.VISIBLE);
        mAdView.loadAd(adRequest);

        tv = (ListView) findViewById(R.id.tv_content);
        cancel = (Button) findViewById(R.id.close_popup);

        adapter = new UnansweredListAdapter();
        adapter.addItem();
        tv.setAdapter(adapter);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactsManager.getInstance().initLists();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.addItem();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        ContactsManager.getInstance().initLists();
    }

    @Override
    public void onBackPressed() {

    }
}