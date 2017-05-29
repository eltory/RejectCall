package com.example.eltory.rejectcall;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;

/**
 * Created by eltory on 2017-04-23.
 */
public class SetDetail extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_);
        addPreferencesFromResource(R.xml.custom_pref);

        Button backButton = (Button) findViewById(R.id.backPrefBurron);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               // Intent myIntent = new Intent(getBaseContext(),
                       // BTFirstTab.class);
                //startActivity(myIntent);
            }
        });

    }
}
