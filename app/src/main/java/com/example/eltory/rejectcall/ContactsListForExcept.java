package com.example.eltory.rejectcall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by eltory on 2017-06-26.
 */
public class ContactsListForExcept extends AppCompatActivity {

    protected ListView listView;
    private ContactAdapter adapter;
    private Button addToList;
    private Intent returnIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);

        listView = (ListView) findViewById(R.id.contact_list);
        addToList = (Button) findViewById(R.id.add_list);
        adapter = new ContactAdapter();
        adapter.addItem(ContactsManager.getInstance().getContactsList(this));
        adapter.isEnter(1);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactItem contactItem = (ContactItem) listView.getItemAtPosition(position);

                if (!contactItem.getCheck()) {
                    contactItem.setCheck(true);
                    if (!ContactsManager.getInstance().isExceptedList(ContactsListForExcept.this, contactItem.getPhoneNumber()))
                        adapter.addToList(contactItem);
                } else {
                    contactItem.setCheck(false);
                    adapter.removeFromList(contactItem);
                }
                adapter.notifyDataSetChanged();
            }

        });
        listView.setAdapter(adapter);

        EditText searchName = (EditText) findViewById(R.id.search_contact);
        searchName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString();
                if (filterText.length() > 0) {
                    listView.setFilterText(filterText);
                } else {
                    listView.clearTextFilter();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        returnIntent = new Intent();

        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnIntent.putExtra("list", adapter.getList());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
