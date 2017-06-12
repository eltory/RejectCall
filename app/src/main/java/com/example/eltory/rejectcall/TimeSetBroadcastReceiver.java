package com.example.eltory.rejectcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by eltor on 2017-05-06.
 */
public class TimeSetBroadcastReceiver extends BroadcastReceiver {

    private int week;
    private Boolean isOn;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Intent sIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("리시버 진입","ㅇㅇ");
        // TODO : 부팅 후 부재중 리스트 리시버 만들기 위함
        week = intent.getIntExtra("days", 0);
        isOn = intent.getBooleanExtra("isOn", false);
        Log.d("리시버 진입",String.valueOf(isOn));
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = pref.edit();
        sIntent = new Intent(context, CallingService.class);

        if (isOn) {
            editor.putBoolean("autoReject", true).commit();
            Log.d("이즈온","true");
        } else {
            editor.putBoolean("autoReject", false).commit();
            Log.d("이즈온","false");
        }
        sIntent.putExtra("setOption", "ok");
        context.startService(sIntent);
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