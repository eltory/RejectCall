package com.example.eltory.rejectcall;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.ArraySet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eltory.rejectcall.ContactsManager;
import com.example.eltory.rejectcall.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by eltory on 2017-03-18.
 */
public class SetExceptNumber extends AppCompatActivity {

    private static Set<String> exceptNumberSet = new HashSet<>();
    private ArrayList numberList;
    private ArrayList person;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_exception_num);

        ListView lv = (ListView) findViewById(R.id.contact_list);
        numberList = ContactsManager.getInstance().getContactsList(this);
    }

    public boolean isSavedContacts(String num) {
        if (numberList.contains(num))
            return true;
        return false;
    }

    public void putExc() {
        exceptNumberSet.addAll(numberList);
        SharedPreferences pref = getSharedPreferences("ExcNum", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet("ExceptedNum", exceptNumberSet);
    }
}
               /*if (cursor.getString(1) != null) {
                    if (person.size() == 0) {
                        bean = new ContactBean();
                        bean.setName(cursor.getString(0));
                        bean.setNumber(cursor.getString(1));
                        person.add(bean);
                    }
                } else {
                    if (!person.get(person.size() - 1).getNumber().equals(cursor.getString(1))) {
                        bean = new ContactBean();
                        bean.setName(cursor.getString(0));
                        bean.setNumber(cursor.getString(1));
                        person.add(bean);
                    }
                }*/