package com.example.eltory.rejectcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by lsh on 2017. 7. 14..
 */

public class OnBootedBroadcastReceiver extends BroadcastReceiver {

    private ListTimeObj timeObjs;
    protected SetAlarm setAlarm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            timeObjs = TimeObjectManager.getInstance().getTimeObjs(context);
            Log.d("부팅", "진입");
            Log.d("부팅타임매니저", String.valueOf(TimeObjectManager.getInstance()));
            for (Object o : timeObjs.getList()) {
                TimeObj timeObj = (TimeObj) o;
                setAlarm = new SetAlarm();
                if (!timeObj.isEnd()) {
                    Log.d("시작시간",String.valueOf(timeObj.getStartTime()));
                    Log.d("종료시간",String.valueOf(timeObj.getEndTime()));
                    Log.d("위크셋",String.valueOf(timeObj.getWeek()));
                    Log.d("RC_1",String.valueOf(timeObj.getRequestCodeSet()[0]));
                    Log.d("RC_2",String.valueOf(timeObj.getRequestCodeSet()[1]));
                    setAlarm.onRegisterAlarm(context, timeObj, true, 0,0);
                    setAlarm.onRegisterAlarm(context, timeObj, false, 1,0);
                }
            }
        }
    }
}
