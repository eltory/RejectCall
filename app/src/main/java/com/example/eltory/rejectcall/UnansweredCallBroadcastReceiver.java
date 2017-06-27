package com.example.eltory.rejectcall;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.CallLog;
import android.util.Log;

import com.example.eltory.rejectcall.PopUpTest;

/**
 * Created by eltory on 2017-05-09.
 */
public class UnansweredCallBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ContactsManager.getInstance().setCurrTime(context);
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            Log.d("화면켜짐", "실행");
            SharedPreferences pref_time = context.getSharedPreferences("time", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref_time.edit();
            editor.putLong("currTime", System.currentTimeMillis()).commit();

            ContactsManager.getInstance().setMissedCall(context);
            if (ContactsManager.getInstance().hasUnansweredList()) {
                Intent i = new Intent(context, PopUpTest.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(i);
            }
        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d("화면꺼짐", "실행");
            ContactsManager.getInstance().initLists();
        }
    }
}

