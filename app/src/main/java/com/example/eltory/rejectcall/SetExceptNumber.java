package com.example.eltory.rejectcall;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private ListView lv;
    String phnum;
    ContactAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_exception_num);
        listView = (ListView) findViewById(R.id.excepted_list);
        adapter = new ContactAdapter();
        final EditText ed = (EditText) findViewById(R.id.addPNum);
        Button btn2 = (Button) findViewById(R.id.find);
        Button btn = (Button) findViewById(R.id.addNum);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SetExceptNumber.this, ContactsListForExcept.class), 1);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phnum = ed.getText().toString();
                if(isSavedContacts(phnum))
                    adapter.addAnItem();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                adapter.addItem((ArrayList<ContactItem>) data.getSerializableExtra("list"));
                if (adapter != null)
                    listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public boolean isSavedContacts(String num) {
        return (numberList.contains(num));
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