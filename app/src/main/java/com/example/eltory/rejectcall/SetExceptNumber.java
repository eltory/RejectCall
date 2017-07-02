package com.example.eltory.rejectcall;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.telephony.PhoneNumberUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

    private ArrayList<ContactItem> list;
    private ListContactObj contactObjs;
    private ComplexPreferences complexPreferences;

    String phnum;
    ContactAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_exception_num);
        listView = (ListView) findViewById(R.id.excepted_list);
        adapter = new ContactAdapter();
        adapter.addItem(getContactObjs().getList());
        listView.setAdapter(adapter);
        final EditText ed = (EditText) findViewById(R.id.addPNum);
        Button btn2 = (Button) findViewById(R.id.find);
        Button btn = (Button) findViewById(R.id.addNum);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i;
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(SetExceptNumber.this);
                alert.setTitle("예외번호 삭제");
                alert.setMessage("현재번호를 예외번호에서 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getContactObjs().removeItem(((ContactItem) adapter.getItem(index)).getPhoneNumber());
                                setContactObjs();
                                adapter.addItem(getContactObjs().getList());
                                adapter.notifyDataSetChanged();
                            }

                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                android.app.AlertDialog alertDialog = alert.create();
                alertDialog.show();
                return true;
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SetExceptNumber.this, ContactsListForExcept.class), 1);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phnum = String.format(PhoneNumberUtils.formatNumber(ed.getText().toString()));
                if(ContactsManager.getInstance().isSavedContacts(phnum)){

                }
                if (getContactObjs().isSavedObj(phnum)) {
                    Toast.makeText(SetExceptNumber.this, "이미 저장 된 번호입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContactItem person = new ContactItem();
                person.setPhoneNumber(phnum);
                person.setName("알수없음");
                getContactObjs().addContact(person);
                setContactObjs();
                adapter.addItem(getContactObjs().getList());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                getContactObjs().addContactList((ArrayList<ContactItem>) data.getSerializableExtra("list"));
                setContactObjs();
                adapter.addItem(getContactObjs().getList());
                adapter.notifyDataSetChanged();
            }
        }
    }

    /*  Save the data object by using GSON for serializing an object to JSON  */
    public void setContactObjs() {
        complexPreferences.putObject("exceptedList", contactObjs);
        complexPreferences.commit();
    }

    /*  Return the person object list  */
    public ListContactObj getContactObjs() {
        this.complexPreferences = ComplexPreferences.getComplexPreferences(context, "mExceptList", Context.MODE_PRIVATE);
        this.contactObjs = complexPreferences.getObject("exceptedList", ListContactObj.class);

        if (contactObjs == null) {
            list = new ArrayList<>();
            this.contactObjs = new ListContactObj();
            this.contactObjs.setList(list);
            setContactObjs();
        }
        return this.contactObjs;
    }
}