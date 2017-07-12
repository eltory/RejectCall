package com.example.eltory.rejectcall;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by eltor on 2017-06-12.
 */
public class SetBlock extends AppCompatActivity {

    SharedPreferences pref_head_num;
    SharedPreferences.Editor editor;
    EditText editHeadNumber;
    Set<String> headNums;
    Set<String> inSet;
    ArrayAdapter adapter;
    String[] arr;
    ListView listView;
    Button addHeadNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_block);

        pref_head_num = getSharedPreferences("HeadNum", MODE_PRIVATE);
        editor = pref_head_num.edit();
        headNums = pref_head_num.getStringSet("headNumSet", new HashSet<String>());

        editHeadNumber = (EditText) findViewById(R.id.addHNum);
        addHeadNum = (Button) findViewById(R.id.addHeadNum);
        listView = (ListView) findViewById(R.id.head_number_list);
        inSet = new HashSet<>(headNums);

        setListView();

        // 리스트뷰에서 제거
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i;

                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(SetBlock.this);
                alert.setTitle("설정번호 삭제");
                alert.setMessage("설정번호를 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                inSet.remove(index);
                                setListView();
                                editor.putStringSet("headNumSet", inSet).commit();
                            }

                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                android.app.AlertDialog alertDialog = alert.create();
                alertDialog.show();

                return false;
            }
        });
        addHeadNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inSet.add(editHeadNumber.getText().toString());
                setListView();
                editHeadNumber.setText("");
                editor.putStringSet("headNumSet", inSet).commit();
            }
        });
    }

    /*  Set the listview in realtime  */
    public void setListView() {
        arr = inSet.toArray(new String[inSet.size()]);
        adapter = new ArrayAdapter(SetBlock.this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
