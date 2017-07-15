package com.example.eltory.rejectcall;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by eltory on 2017-05-18.
 */

public class ContactsManager {

    private static ContactsManager contactsManager = null;
    private ListUnanswered unansweredLists;
    private ArrayList<ContactItem> person = null;
    private ArrayList<ContactItem> list;
    private ComplexPreferences complexPreferences;
    private ListContactObj contactObjs;
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
                ContactItem contactItem = new ContactItem();
                contactItem.setName(cursor.getString(0));
                contactItem.setPhoneNumber(cursor.getString(1));
                contactItem.setCheck(false);
                person.add(contactItem);
            } while (cursor.moveToNext());
            contactObjs.setList(person);
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
        Log.d("여기", "진입");
        if (unansweredLists.getList() == null) {
            unansweredLists.setList(new ArrayList<Unanswered>());
        }
        try {
            cursor = context.getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    new String[]{
                            CallLog.Calls.TYPE,
                            CallLog.Calls.CACHED_NAME,
                            CallLog.Calls.NUMBER,
                            CallLog.Calls.DATE
                    },
                    null,
                    null,
                    CallLog.Calls.DATE + " DESC ");
            cursor.moveToFirst();
            if (cursor != null && cursor.getCount() > 0) {
                do {
                    int type = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));
                    if (type == 3 || type == 5) {
                        String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                        String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                        long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));

                        if (currTime < date) {
                            if (unansweredLists.getUnanswered(number) == null) {
                                missedCall = new Unanswered();
                                missedCall.setName(name);
                                missedCall.setPhoneNum(number);
                                missedCall.setCalledTime(date);
                                missedCall.setNumOfCalled(1);
                                unansweredLists.getList().add(missedCall);
                            } else {
                                unansweredLists.getUnanswered(number).setNumOfCalled(unansweredLists.getUnanswered(number).getNumOfCalled() + 1);
                            }
                        }
                    }
                }
                while (cursor.moveToNext());
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void setMissedCall(Context context) {
        missedList(context);
    }

    public ArrayList<Unanswered> getMissedList(Context context) {
        return unansweredLists.getList();
    }

    public void initLists() {
        this.unansweredLists.setList(null);
    }

    public void setCurrTime(Context context) {
        SharedPreferences pref_time = context.getSharedPreferences("time", Context.MODE_PRIVATE);
        this.currTime = pref_time.getLong("currTime", System.currentTimeMillis());
    }

    public boolean isSavedContacts(Context context, String phoneNumber) {
        Iterator iterator = getContactsList(context).iterator();

        while (iterator.hasNext()) {
            ContactItem person = (ContactItem) iterator.next();

            if (person.getPhoneNumber().equals(phoneNumber))
                return true;
        }
        return false;
    }

    public boolean isHeadNumber(Context context, String phoneNumber) {
        SharedPreferences pref_head_num = context.getSharedPreferences("HeadNum", context.MODE_PRIVATE);
        Set<String> headNumSet = pref_head_num.getStringSet("headNumSet", null);

        if (headNumSet != null) {
            
            Iterator iterator = headNumSet.iterator();

            while (iterator.hasNext()) {
                if (phoneNumber.startsWith((String) iterator.next()))
                    return true;
            }
        }
        return false;
    }

    public boolean hasUnansweredList() {
        if (unansweredLists.getList() != null)
            return unansweredLists.getList().size() > 0;
        return false;
    }

    public boolean isExceptedList(Context context, String phoneNumber) {
        return getContactObjs(context).isSavedObj(phoneNumber);
    }

    /*  Return the person object list  */
    public ListContactObj getContactObjs(Context context) {
        this.complexPreferences = ComplexPreferences.getComplexPreferences(context, "mExceptList", Context.MODE_PRIVATE);
        this.contactObjs = complexPreferences.getObject("exceptedList", ListContactObj.class);

        if (contactObjs == null) {
            list = new ArrayList<>();
            this.contactObjs = new ListContactObj();
            this.contactObjs.setList(list);
        }
        return this.contactObjs;
    }
}