package com.example.eltory.rejectcall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eltor on 2017-05-05.
 */
public class pref_fragment extends PreferenceFragment {
    Context ct;
    // @BindView(R.id.setReject)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_setting);

        SwitchPreference setReject = (SwitchPreference) findPreference("autoReject");
        SwitchPreference setMSG = (SwitchPreference) findPreference("autoMessage");
        setMSG.setSummary("자동거부 되어야 함");
        setMSG.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent it = new Intent(SettingTest.context, SetMessage.class);
                startActivity(it);
                return false;
            }
        });

        setMSG.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                return true;
            }
        });
    }
}
