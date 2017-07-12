package com.example.eltory.rejectcall;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.example.eltory.rejectcall.ContactsManager;

import java.lang.reflect.Method;
import java.util.jar.Manifest;

/**
 * Created by eltory on 2017-03-07.
 */
public class CallingService extends Service {

    private final String EXTRA_CALL_NUMBER = "incomingNum";
    private final String EXTRA_SET_OPTIONS = "setOption";
    private String incomingNumber = "없음";
    private TelephonyManager tm;
    private ITelephony telephonyService;
    private SharedPreferences pref_option;
    private SharedPreferences pref_msg;
    private SharedPreferences pref_except;
    private boolean[] checkedOptions = new boolean[7];

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("실행...", "Calling 서비스 실행중");

        UnansweredCallBroadcastReceiver un = new UnansweredCallBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(un, intentFilter);
        setOptions();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("진입...", "Calling 서비스 진입");
        setExtra(intent);
        Log.d("수신 전화번호", incomingNumber);

        if (!TextUtils.isEmpty(incomingNumber)) {
            // TODO : String null 값이나 "" 일때수정하기
        }
        return START_REDELIVER_INTENT;
    }

    /*  Extra intent for onStartCommand of Service  */
    private void setExtra(Intent intent) {
        if (intent == null)
            return;

        // 자동수신거부를 위한 외부번호 인텐트
        if (intent.getStringExtra(EXTRA_CALL_NUMBER) != null) {
            incomingNumber = intent.getStringExtra(EXTRA_CALL_NUMBER);
            setOptions();

            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            // 모르는번호 자동거절
            if (this.checkedOptions[5] && !ContactsManager.getInstance().isHeadNumber(getApplicationContext(), incomingNumber))
                setBlock();

            // 수신거절 설정조건문 -> 자동응답거부 설정여부
            if (this.checkedOptions[0]) {
                if (this.checkedOptions[1] && this.checkedOptions[4]) {
                    if (ContactsManager.getInstance().isExceptedList(getApplicationContext(), incomingNumber))
                        return;
                }
                setBlock();
                setMSG();
            }
        }

        // 설정변화시 서비스에 바로 적용
        if (intent.getStringExtra(EXTRA_SET_OPTIONS) != null)
            setOptions();
    }

    /*  Get the options of Setting  */
    public void setOptions() {
        pref_option = PreferenceManager.getDefaultSharedPreferences(this);
        pref_msg = getSharedPreferences("Message", MODE_PRIVATE);
        this.checkedOptions[0] = pref_option.getBoolean("autoReject", false);
        this.checkedOptions[1] = pref_option.getBoolean("setting", false);
        this.checkedOptions[2] = pref_option.getBoolean("autoMessage", false);
        this.checkedOptions[3] = pref_option.getBoolean("autoTime", false);
        this.checkedOptions[4] = pref_option.getBoolean("exceptNumber", false);
        this.checkedOptions[5] = pref_option.getBoolean("autoBlock", false);
        this.checkedOptions[6] = pref_msg.getBoolean("exceptUnregist", false);
    }

    /*  Auto Call Rejection (The following, I'll call ACR or ACB)  */
    private void setBlock() {
        try {
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(tm);
            telephonyService.endCall();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMSG() {
        // 메세지 자동 발송조건문 -> 세부설정 & 자동문자 발송 설정여부
        if (this.checkedOptions[1] && this.checkedOptions[2]) {
            // 모르는 번호 문자보내기 금지
            if (this.checkedOptions[6]) {
                if (ContactsManager.getInstance().isSavedContacts(getApplicationContext(), incomingNumber))
                    return;
            }
            // TODO : 상황에 맞는 메세지 가져오기, 모르는번호 메세지 처리하기
            sendSMS(incomingNumber, pref_msg.getString("MSG", SetMessage.SEND_MSG));
        }
    }

    /*  Send SMS  */
    public void sendSMS(String phoneNumber, String msg) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        // SMS 보낼때
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "알림 문자 메세지 전송완료", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, msg, sentPI, deliveredPI);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("종료...", "Calling 서비스 종료");
    }
}
