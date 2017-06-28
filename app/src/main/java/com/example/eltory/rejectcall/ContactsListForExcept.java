package com.example.eltory.rejectcall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by eltory on 2017-06-26.
 */
public class ContactsListForExcept extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        ListView listView = (ListView) findViewById(R.id.contact_list);
        final ContactAdapter adapter = new ContactAdapter();
        adapter.addItem(ContactsManager.getInstance().getContactsList(this));
        listView.setAdapter(adapter);
        Button btn = (Button) findViewById(R.id.add_list);
        final Intent returnIntent = new Intent();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnIntent.putExtra("list", adapter.getList());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
