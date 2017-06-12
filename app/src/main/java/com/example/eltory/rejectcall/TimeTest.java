package com.example.eltory.rejectcall;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private PendingIntent pIntent;
    private Intent intent;
    private static int daysOfWeek = 0;
    private boolean[] daySet;
    Calendar cal;
    EditText ed;
    String n;

    @BindView(R.id.day)
    Button btn;
    @BindView(R.id.days)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time);
        ButterKnife.bind(this);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(this, TimeSetBroadcastReceiver.class);
        ed = (EditText) findViewById(R.id.editText);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n = ed.getText().toString();

                cal = Calendar.getInstance();
                //
                setTime(cal, Integer.parseInt(n));
                onRegisterAlarm(0, true);
                setTime(cal, Integer.parseInt(n) + 1);
                onRegisterAlarm(1, false);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUnregisterAlarm();
            }
        });
    }

    /*  Register an alarm for setting options */
    private void onRegisterAlarm(int requestCode, Boolean isOn) {
        intent.putExtra("isOn", isOn);
        pIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // SDK 버전별로 정확한 시간에 작동시키기
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
        } else {
            if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
            }
        }
    }

    /*  Unregister an alarm  */
    private void onUnregisterAlarm() {
        pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.cancel(pIntent);
    }

    /*  Set start & finish time  */
    public void setTime(Calendar cal, int atTime) {
        cal.set(Calendar.MINUTE, atTime);
        cal.set(Calendar.SECOND, 0);
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


