package com.example.eltory.rejectcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.eltory.rejectcall.PopUpTest;

/**
 * Created by eltory on 2017-05-09.
 */
public class UnansweredCallBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == Intent.ACTION_USER_PRESENT) {
            Log.d("화면켜짐", "실행");
            ContactsManager.getInstance().setMissedCall(context);
            if (ContactsManager.getInstance().hasUnansweredList()) {
                Log.d("팝업진입", "실행");
                Intent i = new Intent(context, PopUpTest.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(i);
            }
        }if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            Log.d("화면꺼짐","실행");
            Log.d("현재시간 전", String.valueOf(System.currentTimeMillis()));
            ContactsManager.getInstance().setCurrTime();
            Log.d("현재시간 후", String.valueOf(System.currentTimeMillis()));
        }
    }
}

