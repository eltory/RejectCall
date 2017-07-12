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

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO : 부팅 후 부재중 리스트 리시버 만들기
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

        if (isOn) {
            requestCode_s = intent.getIntExtra("time", 0);

            String startEndTime = TimeObjectManager.getInstance().getStartEndTime(requestCode_s);
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
        } else {
            isRepeat = intent.getBooleanExtra("isRepeat", false);
            if (isRepeat) {
                onRegisterAlarmByItem(context, TimeObjectManager.getInstance().getTimeObjs(context).getTimeObj(intent.getIntExtra("time", 0)));
            }
            editor.putBoolean("autoReject", false).commit();
            nm.cancel(111);
        }
        sIntent.putExtra("setOption", "ok");
        context.startService(sIntent);
    }

    /*  Register an alarm for setting options */
    private void onRegisterAlarmByItem(Context context, TimeObj timeObj) {
        alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent s_intent = new Intent(context, TimeSetBroadcastReceiver.class);
        Intent e_intent = new Intent(context, TimeSetBroadcastReceiver.class);
        s_intent.putExtra("week", timeObj.getWeekSet());
        s_intent.putExtra("isOn", "isOn");
        s_intent.putExtra("time", timeObj.getRequestCodeSet()[0]);
        e_intent.putExtra("week", timeObj.getWeekSet());
        e_intent.putExtra("isOn", "isOff");
        e_intent.putExtra("isRepeat", true);
        s_intent.putExtra("time", timeObj.getRequestCodeSet()[1]);

        PendingIntent s_pIntent = PendingIntent.getBroadcast(context, timeObj.getRequestCodeSet()[0], s_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent e_pIntent = PendingIntent.getBroadcast(context, timeObj.getRequestCodeSet()[1], e_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // SDK 버전별로 정확한 시간에 작동시키기
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeObj.getStartTime(), s_pIntent);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeObj.getEndTime(), e_pIntent);
        } else {
            if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeObj.getStartTime(), s_pIntent);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeObj.getEndTime(), e_pIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeObj.getStartTime(), s_pIntent);
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeObj.getEndTime(), e_pIntent);
            }
        }
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
/*
        // 부팅 후 BR
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            활성
            ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
            PackageManager pm = context.getPackageManager();
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

            비활성
            ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
            PackageManager pm = context.getPackageManager();
            pm.setComponentEnabledSetting(receiver,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP)
        }*/