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
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by eltory on 2017-03-07.
 */
public class CallingService extends Service {

    public static boolean[] chk = new boolean[4];
    public static final String EXTRA_CALL_NUMBER = "call_number";
    public static String SEND_MSG = "지금은 전화를 받을 수 없습니다.";
    private String call_number = "없음";
    public static Context context;
    public static boolean hasList = false;
    public static UnansweredList un;
    public static long curTime;
    private boolean wakeUp;
    public static ArrayList<UnansweredList> unansweredLists;
    private ITelephony telephonyService;
    private Set<String> excNum;
    private TelephonyManager tm;
    private SharedPreferences pref_option;
    private SharedPreferences pref_msg;
    private SharedPreferences pref_except;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("실행...","Calling 서비스 실행중");
        context = this;
        unansweredLists = new ArrayList<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setExtra(intent);

        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        pref_option = getSharedPreferences("SetOption", MODE_PRIVATE);
        pref_msg = getSharedPreferences("Message", MODE_PRIVATE);
        pref_except = getSharedPreferences("ExcNum", MODE_PRIVATE);

        setOptions();

        //excNum = pref_except.getStringSet("ExceptedNum", null);
        hasList = true;
        Log.d("수신 전화번호", call_number);

        // 수신거절 설정조건문 -> 자동응답거부 설정여부
        if ( this.chk[0]) {
            // 예외 번호 설정조건문 -> 예외번호 설정여부
            //if (CallingService.chk[3]) {
            {
                //if (CallingService.chk[1])
                setBlock();
            }
        }

        if (!TextUtils.isEmpty(call_number)) {

        }
        return START_REDELIVER_INTENT;
    }

    // 설정초기화
    public void setOptions(){
        this.chk[0] = pref_option.getBoolean("AutoReject", false);
        this.chk[1] = pref_option.getBoolean("AutoMessage", false);
        this.chk[2] = pref_option.getBoolean("SetTime", false);
        this.chk[3] = pref_option.getBoolean("Exception", false);
    }

    public void setAutoRejection(boolean b) {
        SharedPreferences.Editor editor = pref_option.edit();
        editor.putBoolean("AutoReject", b).commit();
    }

    // 자동으로 수신거절
    private void setBlock() {
        try {
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(tm);
            telephonyService.endCall();

            int h, mm, s;
            Calendar cal = Calendar.getInstance();
            h = cal.get(Calendar.HOUR_OF_DAY);
            mm = cal.get(Calendar.MINUTE);
            s = cal.get(Calendar.SECOND);
            cal.set(Calendar.HOUR_OF_DAY,cal.get(Calendar.HOUR_OF_DAY));
            cal.set(Calendar.MINUTE,cal.get(Calendar.MINUTE));
            cal.set(Calendar.SECOND,cal.get(Calendar.SECOND));
            curTime = cal.getTimeInMillis();

            String arg;
            if (h < 12) {
                if (h == 0)
                    arg = "오전 12";
                else
                    arg = "오전 " + h;
            } else {
                if (h == 12)
                    arg = "오후 12";
                else
                    arg = "오후 " + h;
            }
            String time = arg + ":" + String.valueOf(mm);

            un = new UnansweredList();
            un.setPhoneNum(call_number);
            if (!unansweredLists.contains(un)) {
                unansweredLists.add(un);
            }
            Log.d("사이즈", String.valueOf(unansweredLists.size()));
            // 메세지 자동 발송조건문 -> 자동문자 발송 설정여부
            if ( this.chk[1]) {
                sendSMS(call_number, pref_msg.getString("MSG", null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setExtra(Intent intent) {
        if (intent == null) {
            return;
        }
        if (intent.getStringExtra(EXTRA_CALL_NUMBER) != null)
            call_number = intent.getStringExtra(EXTRA_CALL_NUMBER);
    }

    // 메세지 보내기
    public void sendSMS(String phoneNumber, String msg) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        // SMS 보내질때
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

    public void setWakeUp(boolean set) {
        this.wakeUp = set;
    }

    public boolean getWakeUp() {
        return this.wakeUp;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("실행...", "콜링 서비스 종료");
    }
}