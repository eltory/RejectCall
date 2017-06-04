package com.example.eltory.rejectcall;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;

/**
 * Created by eltor on 2017-05-30.
 */
public class OptionPrefActivity extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.option_pref);
// TODO : 설정창 프래그먼트로
        SwitchPreference sm = (SwitchPreference) findPreference("autoReject");
        sm.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                Intent it = new Intent(getActivity(), CallingService_Fragment.class);

                it.putExtra("setOption", "gg");
                getActivity().startService(it);
                return true;
            }
        });
    }
}
