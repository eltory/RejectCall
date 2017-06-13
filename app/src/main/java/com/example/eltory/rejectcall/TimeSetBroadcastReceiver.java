package com.example.eltory.rejectcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by eltor on 2017-05-06.
 */
public class TimeSetBroadcastReceiver extends BroadcastReceiver {

    private int week;
    private boolean isOn;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Intent sIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO : 부팅 후 부재중 리스트 리시버 만들기
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
        week = intent.getIntExtra("week", 0);
        isOn = intent.getBooleanExtra("isOn", false);
        sIntent = new Intent(context, CallingService.class);
        Calendar cal = Calendar.getInstance();

        // 오늘의 요일이 설정되어 있지 않으면 리턴
        if (!getDay(cal.get(Calendar.DAY_OF_WEEK) - 1))
            return;

        if (isOn) {
            editor.putBoolean("autoReject", true).commit();
        } else {
            editor.putBoolean("autoReject", false).commit();
        }

        sIntent.putExtra("setOption", "ok");
        context.startService(sIntent);
    }

    public boolean getDay(int day) {
        int temp = week;

        if (((temp >> day) & 1) == 1) {
            return true;                    // 선택한 요일이 '1'이면 true 반환
        } else {
            return false;                   // 선택한 요일이 '0'이면 false 반환
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