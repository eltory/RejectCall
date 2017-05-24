package com.example.eltory.rejectcall;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.Preference;
import android.provider.Settings;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by eltor on 2017-05-06.
 */
public class TimeSetBroadcastReceiver extends BroadcastReceiver {

    private int week;

    @Override
    public void onReceive(Context context, Intent intent) {
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
        ((CallingService) CallingService.context).setAutoRejection(true);
        Log.d("셋업", String.valueOf(CallingService.chk[0]));

        week = intent.getIntExtra("days", 0);
        //Intent intent_ = new Intent(context, PopUpTest.class);
        //intent_.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //context.startActivity(intent_);
        // if(week == 0)
        //   return;
    }
}
