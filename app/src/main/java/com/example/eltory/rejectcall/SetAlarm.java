package com.example.eltory.rejectcall;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by lsh on 2017. 7. 14..
 */

public class SetAlarm {

    private ArrayList<TimeObj> timeObjs;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Intent intent;
    private long time;

    public void onRegisterAlarm(Context context, TimeObj timeObj, Boolean isOn, int i, long triggerTime) {
        intent = new Intent(context, TimeSetBroadcastReceiver.class);
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        intent.putExtra("isRepeat", timeObj.getRepeat());
        intent.putExtra("week", timeObj.getWeek());
        intent.putExtra("isOn", isOn);
        intent.putExtra("time", timeObj.getRequestCodeSet()[i]);
        pendingIntent = PendingIntent.getBroadcast(context, timeObj.getRequestCodeSet()[i], intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (i == 0) {
            time = timeObj.getStartTime() + triggerTime;
        } else {
            time = timeObj.getEndTime() + triggerTime;
        }
        // SDK 버전별로 정확한 시간에 작동시키기
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        } else {
            if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            }
        }
    }

    /*  Unregister an alarm  */
    public void onUnregisterAlarm(Context context, int requestCode) {
        Intent intent = new Intent(context, TimeSetBroadcastReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pIntent);
    }
}
