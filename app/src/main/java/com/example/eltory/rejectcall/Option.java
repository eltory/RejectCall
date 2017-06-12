package com.example.eltory.rejectcall;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * Created by eltory on 2017-03-10.
 */
public class Option extends AppCompatActivity {

    public static Context mContext;
    private SharedPreferences pref_option;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        mContext = this;

        pref_option = getSharedPreferences("SetOption", MODE_PRIVATE);
        // 앱 로컬에 저장된 설정값 변수들 불러오기
        CallingService.chk[0] = pref_option.getBoolean("AutoReject", false);
        CallingService.chk[1] = pref_option.getBoolean("AutoMessage", false);
        CallingService.chk[2] = pref_option.getBoolean("SetTime", false);
        CallingService.chk[3] = pref_option.getBoolean("Exception", false);

        ListView listView = (ListView) findViewById(R.id.mLV);
        ListViewAdapter adapter = new ListViewAdapter();

        adapter.addItem("자동응답거부 설정하기", "전화수신을 자동으로 거부합니다.", CallingService.chk[0]);
        adapter.addItem("거부 문자메세지 발송 설정하기", "수신거부에 관한 문자메세지를 보냅니다.", CallingService.chk[1]);
        adapter.addItem("요일 및 시간대 설정하기", "원하는 요일 및 시간대에만 수신거부를 설정합니다.", CallingService.chk[2]);
        adapter.addItem("예외 번호 설정하기", "자동응답거부모드에도 수신할 번호를 설정합니다.", CallingService.chk[3]);
        adapter.addItem("설정창 테스트", "테스트 문구입니당~", false);
        adapter.addItem("알람매니저 반복설정 테스트", "테스트 문구입니당~", false);
        adapter.addItem("모르는 번호 수신거부","수신거부용",false);

        listView.setAdapter(adapter);

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // 각 설정마다 세부설정 들어가기
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it;

                switch (i) {
                    case 1:
                        it = new Intent(Option.this, SetMessage.class);
                        startActivity(it);
                        break;
                    case 2:
                        it = new Intent(Option.this, SetTime.class);
                        startActivity(it);
                        break;
                    case 3:
                        it = new Intent(Option.this, SetExceptNumber.class);
                        startActivity(it);
                        //getFragmentManager().beginTransaction().replace(android.R.id.content,
                        //      new SettingTest()).commit();
                        break;
                    case 4:
                        it = new Intent(Option.this, SettingTest.class);
                        startActivity(it);
                        break;
                    case 5:
                        it = new Intent(Option.this, TimeTest.class);
                        startActivity(it);
                        break;
                }
            }
        });
    }

    /*
     * 액티비티가 저장 될 시기 지정


    // 뒤로가기 버튼 눌렀을때 데이터 저장
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onSave();
    }

    // 액티비티 활동 정지시 자동 저장
    @Override
    protected void onStop() {
        super.onStop();
        onSave();
    }

    // Preference 를 이용해 설정값 데이터 저장
    public void onSave() {

        // Preference 에 메세지 설정값 저장
        SharedPreferences.Editor editor = pref_option.edit();

        editor.putBoolean("AutoReject", CallingService.chk[0]);
        editor.putBoolean("AutoMessage", CallingService.chk[1]);
        editor.putBoolean("SetTime", CallingService.chk[2]);
        editor.putBoolean("Exception", CallingService.chk[3]);
        editor.commit();
    }*/
}
