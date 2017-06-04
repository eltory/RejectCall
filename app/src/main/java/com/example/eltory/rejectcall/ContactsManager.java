package com.example.eltory.rejectcall;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by eltory on 2017-05-18.
 */


public class ContactsManager {

    private static ContactsManager contactsManager = null;
    private ArrayList person = new ArrayList();  // Contact List in database


    private ContactsManager() {
    }

    /*  Singleton Instance  */
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
            // Cursor 이용 연락처 정보 가져오기
            cursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, // uri 를 설정해서 폰 주소록 가져올 준비
                    new String[]{  // 주소록 담을 배열 이름, 번호
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER},
                    null,
                    null,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME); // 주소록 정렬

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

    /*  Get the RejectedList from Database  */
    public void getMissedContacts(Context context, String phoneNum) {
        Cursor cursor = null;
        // TODO : missed call list 가져오기
        try {
            Log.d("진입", "1");
            cursor = context.getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    new String[]{
                            CallLog.Calls.CACHED_NAME,
                            CallLog.Calls.NUMBER,
                            CallLog.Calls.DATE},
                    CallLog.Calls.TYPE + " = ? AND " + CallLog.Calls.NEW + " = ?",
                    new String[]{
                            Integer.toString(CallLog.Calls.REJECTED_TYPE),
                            "1"},
                    CallLog.Calls.DATE + " DESC ");

            cursor.moveToFirst();
            Log.d("진입----", "cursor.getCount() :" + cursor.getCount());

            if (cursor != null && cursor.getCount() > 0) {
                SimpleDateFormat simpleFormat = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                String date = simpleFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE))));
                Log.d("진입----", "cursor :" + cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
                Log.d("진입----", "cursor :" + cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
                Log.d("진입----", "cursor :" + date);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (cursor != null && cursor.getCount() > 0) {
            Log.d("진입", "2");
            cursor.moveToFirst();
            int type = cursor.getInt(0);
            Calendar cal = Calendar.getInstance();
        }
    }
}