package com.example.eltory.rejectcall;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * Created by eltory on 2017-05-18.
 */
public class UnansweredListPopUpService extends Service {
    private BroadcastReceiver broadcastReceiver;

    // TODO : 부재중목록 있을때 팝업 리시버 만들기, 그 후 해제
    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        broadcastReceiver = new UnansweredCallBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //startActivity(new Intent(context, PopUpTest.class));
       if(intent == null){
           IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
           filter.addAction(Intent.ACTION_SCREEN_OFF);
           broadcastReceiver = new UnansweredCallBroadcastReceiver();
           registerReceiver(broadcastReceiver, filter);
       }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}