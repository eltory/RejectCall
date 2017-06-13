package com.example.eltory.rejectcall;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.example.eltory.rejectcall.R;

import java.util.Calendar;

import butterknife.BindView;

/**
 * Created by eltory on 2017-04-23.
 */
public class SetDetail extends AppCompatActivity {

    private AlarmManager alarmManager;
    private PendingIntent pIntent;
    private Intent intent;
    private ToggleButton toggleSun, toggleMon, toggleTue, toggleWed, toggleThu, toggleFri, toggleSat;
    private int weekSet = 0;
    private Calendar cal;
    TimePicker start;
    TimePicker end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time_obj);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(this, TimeSetBroadcastReceiver.class);

        toggleSun = (ToggleButton) findViewById(R.id.sun);
        toggleMon = (ToggleButton) findViewById(R.id.mon);
        toggleTue = (ToggleButton) findViewById(R.id.tue);
        toggleWed = (ToggleButton) findViewById(R.id.wed);
        toggleThu = (ToggleButton) findViewById(R.id.thu);
        toggleFri = (ToggleButton) findViewById(R.id.fri);
        toggleSat = (ToggleButton) findViewById(R.id.sat);
        start = (TimePicker) findViewById(R.id.start_time_picker);
        end = (TimePicker) findViewById(R.id.end_time_picker);

        Button confirm = (Button) findViewById(R.id.confirm);
        Button cancel = (Button) findViewById(R.id.cancel);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal = Calendar.getInstance();
                // 시작
                setTime(cal, start);
                onRegisterAlarm(0, true);
                // 종료
                setTime(cal, end);
                onRegisterAlarm(1, false);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /*  Register an alarm for setting options */
    private void onRegisterAlarm(int requestCode, Boolean isOn) {
        boolean[] daySet = {
                toggleSun.isChecked(), toggleMon.isChecked(), toggleTue.isChecked(),
                toggleWed.isChecked(), toggleThu.isChecked(), toggleFri.isChecked(), toggleSat.isChecked()
        };
        int i = 0;
        for (boolean b : daySet) {
            setDay(i, b);
            i++;
        }
        intent.putExtra("week",weekSet);
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

    /*  Set start & End time  */
    public void setTime(Calendar cal, TimePicker timePicker) {
        int atHour, atMinute;

        if (Build.VERSION.SDK_INT >= 23) {
            atHour = timePicker.getHour();
            atMinute = timePicker.getMinute();
        } else {
            atHour = timePicker.getCurrentHour();
            atMinute = timePicker.getCurrentMinute();
        }
        cal.set(Calendar.HOUR_OF_DAY, atHour);
        cal.set(Calendar.MINUTE, atMinute);
        cal.set(Calendar.SECOND, 0);
    }

    public void repeatAlarm() {

    }

    public void setDay(int day, boolean set) {
        if (set) {
            weekSet |= (1 << day);
        } else {
            weekSet &= ~(1 << day);
        }
    }
}
