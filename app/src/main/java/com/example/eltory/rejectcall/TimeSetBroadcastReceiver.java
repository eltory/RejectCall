package com.example.eltory.rejectcall;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by eltor on 2017-05-06.
 */
@TargetApi(16)
public class TimeSetBroadcastReceiver extends BroadcastReceiver {

    private int week;
    private int requestCode_s;
    private boolean isOn;
    private boolean isRepeat = false;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Intent sIntent;
    private Notification notification;
    private NotificationManager nm;
    private AlarmManager alarmManager;
    private ListTimeObj timeObjs;
    private SetAlarm setAlarm;

    @Override
    public void onReceive(Context context, Intent intent) {

        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
        week = intent.getIntExtra("week", 0);
        isOn = intent.getBooleanExtra("isOn", false);
        sIntent = new Intent(context, CallingService.class);
        nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Calendar cal = Calendar.getInstance();

        // 오늘의 요일이 설정되어 있지 않으면 리턴
        if (!getDay(cal.get(Calendar.DAY_OF_WEEK) - 1))
            return;

        // start 알람
        if (isOn) {
            requestCode_s = intent.getIntExtra("time", 0);
            Log.d("start 알람", String.valueOf(TimeObjectManager.getInstance()));
            String startEndTime = TimeObjectManager.getInstance().getStartEndTime(context, requestCode_s);
            String[] timeSet = startEndTime.split("/");
            editor.putBoolean("autoReject", true).commit();

            notification = new Notification.Builder(context)
                    .setAutoCancel(false)
                    .setContentTitle("시작시간  " + timeSet[0] + " ~ 종료시간  " + timeSet[1])
                    .setContentText("자동수신 거부 중입니다.")
                    .setSmallIcon(getNotificationIcon())
                    .build();
            notification.flags = Notification.FLAG_NO_CLEAR;
            nm.notify(111, notification);
        }
        // end 알람
        else {
            isRepeat = intent.getBooleanExtra("isRepeat", false);
            setAlarm = new SetAlarm();
            if (isRepeat) {
                Log.d("반복", "진입");
                long triggerTime = 3600 * 24 * 1000;
                setAlarm.onRegisterAlarm(context, TimeObjectManager.getInstance().getTimeObjs(context).getTimeObj(intent.getIntExtra("time", 0)), true, 0, triggerTime);
                setAlarm.onRegisterAlarm(context, TimeObjectManager.getInstance().getTimeObjs(context).getTimeObj(intent.getIntExtra("time", 0)), false, 1, triggerTime);
            } else {
                Log.d("미반복", "진입");
                TimeObjectManager.getInstance().getTimeObjs(context).getTimeObj(intent.getIntExtra("time", 0)).setEnd(true);
                TimeObjectManager.getInstance().setTimeObjs();
                setAlarm.onUnregisterAlarm(context, intent.getIntExtra("time", 0));
            }
            editor.putBoolean("autoReject", false).commit();
            nm.cancel(111);
        }
        // start & end 에 따른 옵션 설정
        sIntent.putExtra("setOption", "ok");
        context.startService(sIntent);
    }

    /*  Notification icon setting  */
    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.banana : R.drawable.banana;
    }

    /*  Get the day's bit of week  */
    public boolean getDay(int day) {
        int temp = week;

        if (((temp >> day) & 1) == 1) {
            return true;
        } else {
            return false;
        }
    }
}