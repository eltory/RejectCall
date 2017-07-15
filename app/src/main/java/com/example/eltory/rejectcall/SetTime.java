package com.example.eltory.rejectcall;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eltory on 2017-03-18.
 */
public class SetTime extends Font {

    // TODO : 시간 반복 설정이랑 객체마다 SMS 등 세부 셋팅 지정 -> 버전업데이트
    // TODO : 시간 리스트 화면 디자인
    private ListView listView;
    private ListViewAdapter adapter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time_set);

        textView = (TextView) findViewById(R.id.not_time_list);
        listView = (ListView) findViewById(R.id.time_list_view);
        adapter = new ListViewAdapter(this);
        listView.setAdapter(adapter);
        addList();

        // 알람 삭제 가능
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                final View v = view;
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(SetTime.this);
                alert.setTitle("시간설정 삭제");
                alert.setMessage("해당 시간을 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onUnregisterAlarm(((TimeObj) adapter.getItem(position)).getRequestCodeSet()[0]);
                                onUnregisterAlarm(((TimeObj) adapter.getItem(position)).getRequestCodeSet()[1]);
                                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                if (nm != null)
                                    nm.cancel(111);
                                SharedPreferences pref_option = PreferenceManager.getDefaultSharedPreferences(SetTime.this);
                                SharedPreferences.Editor edit = pref_option.edit();
                                edit.putBoolean("autoReject", false).commit();
                                adapter.removeItem(position);
                                adapter.notifyDataSetChanged();
                                if (adapter.getCount() == 0) {
                                    textView.setVisibility(View.VISIBLE);
                                }
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                android.app.AlertDialog alertDialog = alert.create();
                alertDialog.show();

                // onClick 방지
                return true;
            }
        });

        // 알람 추가버튼
        FloatingActionButton addTime = (FloatingActionButton) findViewById(R.id.addTime);
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetTime.this, SetTimeObject.class));
            }
        });
    }

    /*  Unregister an alarm  */
    private void onUnregisterAlarm(int requestCode) {
        Intent intent = new Intent(this, TimeSetBroadcastReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pIntent);
    }

    public void addList() {
        adapter.addItem(this);
        if (adapter.getCount() > 0) {
            textView.setVisibility(View.INVISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addList();
    }
}
