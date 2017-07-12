package com.example.eltory.rejectcall;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eltory on 2017-03-18.
 */
public class SetTime extends Font {

    // TODO : 시간 반복 설정이랑 객체마다 SMS 등 세부 셋팅 지정
    private ListView listView;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time_set);

        listView = (ListView) findViewById(R.id.time_list_view);
        adapter = new ListViewAdapter(this);
        listView.setAdapter(adapter);
        addList();

        // 알람 삭제 가능
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;

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
                                adapter.removeItem(position);
                                adapter.notifyDataSetChanged();
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

    // 추가하기
    public void addList() {
        adapter.addItem(this);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addList();
    }
}
