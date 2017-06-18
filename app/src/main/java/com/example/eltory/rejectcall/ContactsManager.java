package com.example.eltory.rejectcall;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eltory on 2017-05-18.
 */


public class ContactsManager {

    private static ContactsManager contactsManager = null;
    private ListUnanswered unansweredLists;
    private ArrayList person = null;
    private Unanswered missedCall;
    private Cursor cursor;
    private long currTime;

    private ContactsManager() {
        unansweredLists = new ListUnanswered();
    }

    /*  Singleton Instance  */
    public static ContactsManager getInstance() {
        if (contactsManager == null) {
            contactsManager = new ContactsManager();
        }
        return contactsManager;
    }

    /*  Get ContactList from Database  */
    private void contactsList(Context context) {
        cursor = null;
        person = new ArrayList();

        try {
            // Cursor 이용 연락처 정보 가져오기
            cursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, // uri 를 설정해서 폰 주소록 가져올 준비
                    new String[]{  // 주소록 담을 배열 이름, 번호
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    },
                    null,
                    null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME); // 주소록 정렬

            cursor.moveToFirst();  // Cursor 에 담긴 연락처 정보들 person <List> 에 담기
            do {
                person.add(cursor.getString(1));
            } while (cursor.moveToNext());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /*  Return the List to the class which called getList()  */
    public ArrayList getContactsList(Context context) {
        contactsList(context);
        return person;
    }

    /*  Get the RejectedList from Database  */
    private void missedList(Context context) {
        cursor = null;

        if (unansweredLists.getList() == null) {
            unansweredLists.setList(new ArrayList<Unanswered>());
        }
        // TODO : missed call list 가져오기
        try {
            cursor = context.getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    new String[]{
                            CallLog.Calls.CACHED_NAME,
                            CallLog.Calls.NUMBER,
                            CallLog.Calls.DATE
                    },
                    CallLog.Calls.TYPE + " = ? AND " + CallLog.Calls.NEW + " = ?",
                    new String[]{
                            Integer.toString(CallLog.Calls.REJECTED_TYPE),
                            "1"
                    },
                    CallLog.Calls.DATE + " DESC ");

            cursor.moveToFirst();
            if (cursor != null && cursor.getCount() > 0) {

                Log.d("진입----", "cursor.getCount() :" + cursor.getCount());

                do {
                    String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));

                    Log.d("진입----", "cursor :" + name);
                    Log.d("진입----", "cursor :" + number);
                    Log.d("진입----", "현재시간 :" + String.valueOf(currTime));
                    Log.d("진입----", "커서시간 :" + String.valueOf(date));
                    if (currTime < date) {
                        if (unansweredLists.getUnanswered(number) == null) {
                            Log.d("첨", "진입");
                            missedCall = new Unanswered();
                            missedCall.setName(name);
                            missedCall.setPhoneNum(number);
                            missedCall.setCalledTime(date);
                            missedCall.setNumOfCalled(missedCall.getNumOfCalled() + 1);
                            unansweredLists.getList().add(missedCall);
                        } else {
                            unansweredLists.getUnanswered(number).setCalledTime(date);
                            unansweredLists.getUnanswered(number).setNumOfCalled(unansweredLists.getUnanswered(number).getNumOfCalled() + 1);
                            Log.d("진입----", "오브시간" + String.valueOf(date));
                        }
                    }
                }
                while (cursor.moveToNext());
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void setMissedCall(Context context) {
        missedList(context);
    }

    public ArrayList<Unanswered> getMissedList(Context context) {
        //missedList(context);
        return unansweredLists.getList();
    }

    public void initLists() {
        this.unansweredLists.setList(null);
    }

    public void setCurrTime() {
        this.currTime = System.currentTimeMillis();
    }

    public boolean hasUnansweredList() {
        return unansweredLists.getList().size() > 0;
    }
}