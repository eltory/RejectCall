package com.example.eltory.rejectcall;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

/**
 * Created by eltor on 2017-05-30.
 */
public class OptionPrefActivity extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.option_pref);
// TODO : 설정창 프래그먼트로
    }
}
