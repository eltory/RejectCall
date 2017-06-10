package com.example.eltory.rejectcall;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

/**
 * Created by eltory on 2017-03-07.
 * <p/>
 * Description : Being operated on background, BroadcastReceiver is for incoming of any call
 * When the call comes into the phone, the BR is awaken for delivering data which have
 * incoming phone number and current state to a CallingService.
 */

public class IncomingCallBroadcastReceiver extends BroadcastReceiver {

    private static String mState = null;
    private String incomingNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        // BR 2번이상 실행 되는거 방지
        if (state.equals(mState)) {
            return;
        } else {
            mState = state;
        }
        incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        // TODO : ARC 상태진입 시간 이후부터 설정하기
        ContactsManager.getInstance().getMissedList(context);

        // 현재 상태가 전화걸려오는 상태일때
        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            final String phone_number = PhoneNumberUtils.formatNumber(incomingNumber);
            Intent serviceIntent = new Intent(context, CallingService_Fragment.class);
            serviceIntent.putExtra("incomingNum", phone_number);
            context.startService(serviceIntent);
        }
    }
}
