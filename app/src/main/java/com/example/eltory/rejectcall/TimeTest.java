package com.example.eltory.rejectcall;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eltory on 2017-05-10.
 */
public class TimeTest extends AppCompatActivity {

    private AlarmManager alarmManager;
    private Intent intent;
    private static int daysOfWeek = 0;
    public boolean[] daySet;

    @BindView(R.id.day)
    Button btn;

    @BindView(R.id.days)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        ButterKnife.bind(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegistOneAlarm(TimeTest.this);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUnregistOneAlarm(TimeTest.this);
            }
        });
        //alarmManager.setRepeating();
        //setDay(1, true);
    }

    private void onRegistOneAlarm(Context context) {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(this, TimeSetBroadcastReceiver.class);
        intent.putExtra("days", daysOfWeek);

        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        EditText ed = (EditText)findViewById(R.id.editText);
        String n = ed.getText().toString();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, Integer.parseInt(n));
        cal.set(Calendar.SECOND, 0);
        long interval = 1000 * 60;

        //if (cal.getTimeInMillis() < System.currentTimeMillis()) cal.add(Calendar.DAY_OF_YEAR, 1);

        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
        } else {
            if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
            }
        }

        //alarmManager.setExactAndAllowWhileIdle();
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), interval, pIntent);
    }

    private void onUnregistOneAlarm(Context context) {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(this, TimeSetBroadcastReceiver.class);
        intent.putExtra("days", daysOfWeek);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.cancel(pIntent);
    }

    public void initDaySet() {
        for (boolean b : daySet) {
            b = false;
        }
    }

    public void repeatAlarm() {

    }

    public void setDay(int day, boolean set) {
        if (set) {
            daysOfWeek |= (1 << day);
        } else {
            daysOfWeek &= ~(1 << day);
        }
    }

    public boolean getDay(int day) {
        int temp = daysOfWeek;

        if (((temp >> day) & 1) == 1) {
            return true;                    // 선택한 요일이 '1'이면 true 반환
        } else {
            return false;                   // 선택한 요일이 '0'이면 false 반환
        }
    }
}


