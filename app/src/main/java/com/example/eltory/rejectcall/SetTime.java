package com.example.eltory.rejectcall;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;


import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import butterknife.BindView;

/**
 * Created by eltory on 2017-03-18.
 */
public class SetTime extends AppCompatActivity {

    // TODO : 시간 반복 설정이랑 객체마다 SMS 등 세부 셋팅 지정
    private AlarmManager alarmManager;
    private Intent intent;
    private static int daysOfWeek = 0;
    private boolean[] daySet;
    private boolean isRepeat = false;
    int h, m;
    @BindView(R.id.day)
    Button btn;

    @BindView(R.id.days)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time_set);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        daySet = new boolean[7];
        final TextView tv = (TextView) findViewById(R.id.day);

        CheckBox c1 = (CheckBox) findViewById(R.id.d1);
        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                daySet[0] = b;
            }
        });
        CheckBox c2 = (CheckBox) findViewById(R.id.d2);
        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                daySet[1] = b;
            }
        });
        CheckBox c3 = (CheckBox) findViewById(R.id.d3);
        c3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                daySet[2] = b;
            }
        });
        CheckBox c4 = (CheckBox) findViewById(R.id.d4);
        c4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                daySet[3] = b;
            }
        });
        CheckBox c5 = (CheckBox) findViewById(R.id.d5);
        c5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                daySet[4] = b;
            }
        });
        CheckBox c6 = (CheckBox) findViewById(R.id.d6);
        c6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                daySet[5] = b;
            }
        });
        CheckBox c7 = (CheckBox) findViewById(R.id.d7);
        c7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                daySet[6] = b;
            }
        });
        CheckBox c8 = (CheckBox) findViewById(R.id.checkBox);
        c8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRepeat = b;
            }
        });
        initDaySet();
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker2);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    h = timePicker.getHour();
                    m = timePicker.getMinute();
                } else {
                    h = timePicker.getCurrentHour();
                    m = timePicker.getCurrentMinute();
                }
            }
        });


        Button but = (Button) findViewById(R.id.setday);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                for (boolean b : daySet) {
                    setDay(i, b);
                    i++;
                }
                tv.setText(String.valueOf(Integer.toBinaryString(daysOfWeek)));
            }
        });
    }

    private void onRegistAlarm(Context context) {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(this, TimeSetBroadcastReceiver.class);
        intent.putExtra("days", daysOfWeek);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 20);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);

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

    private void onUnregistAlarm(Context context) {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(this, TimeSetBroadcastReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.cancel(pIntent);
    }

    public void initDaySet() {
        for (boolean b : daySet) {
            b = false;
        }
    }

    public void repeatAlarm() {
        // if(repeat.isChecked())
        // return true;

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
