package com.example.eltory.rejectcall;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

/**
 * Created by eltor on 2017-05-30.
 */
public class OptionPrefActivity extends PreferenceFragment {

    private Intent it;
    SwitchPreference autoReject;
    SwitchPreference setDetail;
    SwitchPreference block;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.option_pref);

        /*  Set auto reject  */
        autoReject = (SwitchPreference) findPreference("autoReject");
        autoReject.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                it = new Intent(getActivity(), CallingService.class);
                it.putExtra("setOption", "ok");
                getActivity().startService(it);
                return true;
            }
        });

        /*  Set detail options  */
        setDetail = (SwitchPreference) findPreference("setting");
        setDetail.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                it = new Intent(getActivity(), CallingService.class);
                it.putExtra("setOption", "ok");
                getActivity().startService(it);
                return true;
            }
        });


       /* block = (SwitchPreference) findPreference("autoBlock");
        block.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                it = new Intent(getActivity(), CallingService.class);
                it.putExtra("setOption", "ok");
                getActivity().startService(it);
                return true;
            }
        });*/

        /*  Set each entrance */
        MySwitchPreference autoMessage = (MySwitchPreference) findPreference("autoMessage");
        autoMessage.setNum(1);
        MySwitchPreference autoTime = (MySwitchPreference) findPreference("autoTime");
        autoTime.setNum(2);
        MySwitchPreference exceptNumber = (MySwitchPreference) findPreference("exceptNumber");
        exceptNumber.setNum(3);
       MySwitchPreference autoBlock = (MySwitchPreference) findPreference("autoBlock");
        autoBlock.setNum(4);
    }

    public void setCheck() {
        autoReject.setChecked(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("autoReject", false));
    }

    @Override
    public void onResume() {
        super.onResume();
        setCheck();
    }
}
