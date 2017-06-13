package com.example.eltory.rejectcall;

import android.content.Intent;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eltory on 2017-03-18.
 */
public class SetTime extends AppCompatActivity {

    // TODO : 시간 반복 설정이랑 객체마다 SMS 등 세부 셋팅 지정
    ListView listView;
    ArrayList<TimeObj> timeObjList;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_time_set);
        listView = (ListView) findViewById(R.id.time_list_view);
        adapter = new ListViewAdapter();

        if (timeObjList != null)
            for (TimeObj t : timeObjList) {
                adapter.addItem(t);
            }
        listView.setAdapter(adapter);

        FloatingActionButton addTime = (FloatingActionButton) findViewById(R.id.addTime);
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetTime.this, SetTimeObject.class));
            }
        });
    }

    public void addList() {
        timeObjList = TimeObjectManager.getInstance().getTimeObjs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addList();
        if (timeObjList != null)
            for (TimeObj t : timeObjList) {
                adapter.addItem(t);
            }
        adapter.notifyDataSetChanged();
    }
}
