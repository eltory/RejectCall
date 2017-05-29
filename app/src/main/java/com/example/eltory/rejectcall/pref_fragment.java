package com.example.eltory.rejectcall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

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
        //setContentView();
        //PreferenceScreen ps =
        SwitchPreference setReject = (SwitchPreference) findPreference("autoReject");
        SwitchPreference setMSG = (SwitchPreference) findPreference("autoMessage");
       // setMSG.setSummary("자동거부 되어야 함");
        //setMSG.setDependency();
        //setMSG.setWidgetLayoutResource(R.layout.select_option);



        //setMSG.setLayoutResource(R.layout.select_option);
       // ListView listView = (ListView) findViewById(R.id.list);
        ListViewAdapter adapter = new ListViewAdapter();

        adapter.addItem("자동응답거부 설정하기", "전화수신을 자동으로 거부합니다.", CallingService.chk[0]);
        adapter.addItem("거부 문자메세지 발송 설정하기", "수신거부에 관한 문자메세지를 보냅니다.", CallingService.chk[1]);
        adapter.addItem("요일 및 시간대 설정하기", "원하는 요일 및 시간대에만 수신거부를 설정합니다.", CallingService.chk[2]);
        adapter.addItem("예외 번호 설정하기", "자동응답거부모드에도 수신할 번호를 설정합니다.", CallingService.chk[3]);
        adapter.addItem("설정창 테스트", "테스트 문구입니당~", false);
        adapter.addItem("알람매니저 반복설정 테스트", "테스트 문구입니당~", false);
        adapter.addItem("모르는 번호 수신거부","수신거부용",false);

        //listView.setAdapter(adapter);
        //TextView title = (TextView)findViewById(R.id.)
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
