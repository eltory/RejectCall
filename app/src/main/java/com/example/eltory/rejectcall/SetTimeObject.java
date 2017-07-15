package com.example.eltory.rejectcall;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.Calendar;


/**
 * Created by eltory on 2017-04-23.
 */
public class SetTimeObject extends Font {

    private AlarmManager alarmManager;
    private PendingIntent pIntent;
    private Intent intent;
    private ToggleButton toggleSun, toggleMon, toggleTue, toggleWed, toggleThu, toggleFri, toggleSat;
    private int weekSet = 0;
    private Calendar cal;
    private long startTime;
    private long endTime;
    private int[] requestCode;
    private boolean isRepeat = false;
    TimePicker start;
    TimePicker end;
    Switch repeatSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time_obj);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(this, TimeSetBroadcastReceiver.class);
        repeatSwitch = (Switch) findViewById(R.id.isRepeat);
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

        repeatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRepeat = b;
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setAlarm()) {
                    finish();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public boolean setAlarm() {
        if ((toggleSun.isChecked() || toggleMon.isChecked() || toggleTue.isChecked() ||
                toggleWed.isChecked() || toggleThu.isChecked() || toggleFri.isChecked() || toggleSat.isChecked()) == false) {
            Toast.makeText(SetTimeObject.this, "요일을 선택해 주세요!", Toast.LENGTH_SHORT).show();
            return false;
        }
        cal = Calendar.getInstance();

        requestCode = new int[]{(int) System.currentTimeMillis(), (int) System.currentTimeMillis() + 1};
        Log.d("시작", String.valueOf(requestCode[0]));
        Log.d("종료", String.valueOf(requestCode[1]));

        // 시작
        setTime(cal, start);
        startTime = cal.getTimeInMillis();
        if (startTime == endTime) {
            Toast.makeText(SetTimeObject.this, "시작시간과 종료시간이 같습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        onRegisterAlarm(requestCode[0], true);

        // 종료
        setTime(cal, end);
        endTime = cal.getTimeInMillis();

        onRegisterAlarm(requestCode[1], false);
        TimeObjectManager.getInstance().addTimeObj(makeTimeObj());
        return true;
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
        intent.putExtra("isRepeat", isRepeat);
        intent.putExtra("week", weekSet);
        intent.putExtra("isOn", isOn);
        intent.putExtra("time", requestCode);
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

    /*  Set start & End time  */
    public void setTime(Calendar cal, TimePicker timePicker) {
        int atHour, atMinute;

        // SDK 버전에 따른 time picker 시간 가져오기
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

    // TimeObject 생성
    public TimeObj makeTimeObj() {
        TimeObj timeObj = new TimeObj();
        timeObj.setStartTime(startTime);
        timeObj.setEndTime(endTime);
        timeObj.setWeekSet(weekSet);
        timeObj.setRequestCodeSet(requestCode);
        timeObj.setRepeat(repeatSwitch.isChecked());
        return timeObj;
    }

    public void setDay(int day, boolean set) {
        if (set) {
            weekSet |= (1 << day);
        } else {
            weekSet &= ~(1 << day);
        }
    }
}
