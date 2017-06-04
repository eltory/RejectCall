package com.example.eltory.rejectcall;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by eltory on 2017-05-18.
 */

public class ContactsManager {

    private static ContactsManager contactsManager = null;
    private ArrayList person = new ArrayList();  // Contact List in database

    private ContactsManager() {
    }

    /*  Singleton pattern  */
    public static ContactsManager getInstance() {
        if (contactsManager == null) {
            contactsManager = new ContactsManager();
        }
        return contactsManager;
    }

    /*  Get ContactList from Database  */
    public void getContactsList(Context context) {
        Cursor cursor = null;

        try {
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;  // uri 를 설정해서 폰 주소록 가져올 준비
            String[] projection = new String[]{  // 주소록 담을 배열 이름, 번호
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };
            String ord = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;  // 주소록 정렬
            cursor = context.getContentResolver().query(uri, projection, null, null, ord);  // Cursor 이용 연락처 정보 가져오기

            cursor.moveToFirst();  // Cursor 에 담긴 연락처 정보들 person <List> 에 담기
            do {
                person.add(cursor.getString(0) + "/" + cursor.getString(1));
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
    public ArrayList getList(Context context) {
        getContactsList(context);
        return person;
    }

    /*  Get the MissedCallList from Database  */
    public void getMissedContacts(Context context, String phoneNum) {
        Cursor cursor = null;
        // TODO : missed call list 가져오기
        try {
            Log.d("진입", "1");
            cursor = context.getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    null,
                    CallLog.Calls.TYPE + " = ? AND " + CallLog.Calls.NEW + " = ?",
                    new String[]{Integer.toString(CallLog.Calls.MISSED_TYPE), "1"},
                    CallLog.Calls.DATE + " DESC ");/*
                    new String[]{CallLog.Calls.TYPE},
                    CallLog.Calls.NUMBER + " = ? AND " + CallLog.Calls.NEW + " = ?",
                    new String[]{phoneNum, "1"},
                    CallLog.Calls.DATE + " DESC ");*/
            Log.d("진입----", "cursor.getCount() :" + cursor.getCount());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (cursor != null && cursor.getCount() > 0) {
            Log.d("진입", "2");
            cursor.moveToFirst();
            int type = cursor.getInt(0);
            Calendar cal = Calendar.getInstance();

            if (type == CallLog.Calls.MISSED_TYPE) {//&& CallingService.curTime < cal.getTimeInMillis()) {
                Log.d("부재중", String.valueOf(cursor.getCount()));
            }
        }
    }
}