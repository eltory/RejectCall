package com.example.eltory.rejectcall;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, Splash.class));
        setContentView(R.layout.pref_custom);
        mContext = this;

        RecyclerView rc = (RecyclerView) findViewById(R.id.scv);
        LinearLayoutManager mManager = new LinearLayoutManager(this);
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(mManager);

        /* ListViewAdapter adapter = new Adapter();
        adapter.addItem("자동응답거부 설정하기", "전화수신을 자동으로 거부합니다.", CallingService.chk[0]);
        adapter.addItem("거부 문자메세지 발송 설정하기", "수신거부에 관한 문자메세지를 보냅니다.", CallingService.chk[1]);
        adapter.addItem("요일 및 시간대 설정하기", "원하는 요일 및 시간대에만 수신거부를 설정합니다.", CallingService.chk[2]);
        adapter.addItem("예외 번호 설정하기", "자동응답거부모드에도 수신할 번호를 설정합니다.", CallingService.chk[3]);
        adapter.addItem("설정창 테스트", "테스트 문구입니당~", false);
        adapter.addItem("알람매니저 반복설정 테스트", "테스트 문구입니당~", false);
        adapter.addItem("모르는 번호 수신거부","수신거부용",false);
        */
        //adapter
        // rc.setAdapter(adapter);
        /*findViewById(R.id.goOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Option.class));
            }
        });*/

        Intent serviceIntent = new Intent(this, CallingService.class);
        serviceIntent.putExtra(CallingService.EXTRA_CALL_NUMBER, 111111111);
        startService(serviceIntent);

        Permissions permissions = new Permissions();
        permissions.permissionCheck(this);
    }
}