package com.example.eltory.rejectcall;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eltory on 2017-03-18.
 */

public class SetMessage extends AppCompatActivity {

    public static final String SEND_MSG = "지금은 전화를 받을 수 없습니다.";
    private String msgString;
    private int checkedPosition;
    private SharedPreferences pref_msg;
    private SharedPreferences.Editor editor;
    private Set<String> msgSet;
    private Set<String> inSet;
    private List toSort;
    private MsgListViewAdapter adapter;
    private MessageSettingItem itemAtPosition;

    @BindView(R.id.addText)
    EditText message;
    @BindView(R.id.addMessage)
    Button addBtn;
    @BindView(R.id.msgLV)
    ListView msgListView;
    @BindView(R.id.set_msg_except)
    Switch msgExcept;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_message);
        ButterKnife.bind(this);
        adapter = new MsgListViewAdapter();

        // 저장된 메세지 pref 가져오기
        pref_msg = getSharedPreferences("Message", MODE_PRIVATE);
        editor = pref_msg.edit();
        checkedPosition = pref_msg.getInt("CheckedPosition", 0);
        msgString = pref_msg.getString("msgString", SEND_MSG);
        msgExcept.setChecked(pref_msg.getBoolean("exceptUnregist", false));
        msgSet = pref_msg.getStringSet("MsgSet", new HashSet<String>());
        inSet = new HashSet<>(msgSet);
        toSort = new ArrayList(msgSet);

        // 메세지셋 정렬
        if (toSort.size() != 0)
            Collections.sort(toSort);

        // 리스트뷰에 뿌리기
        int i = 0;

        /*  Set a default message  */
        if (msgString.equals(SEND_MSG)) {
            adapter.addItem(SEND_MSG, true);
            checkedPosition = 0;
        } else
            adapter.addItem(SEND_MSG, false);

        /*  Set Optional Messages  */
        for (Object s : toSort) {
            if (msgString.equals(s.toString())) {
                adapter.addItem(s.toString(), true);
                checkedPosition = i + 1;
            } else
                adapter.addItem(s.toString(), false);
            i++;
        }
        msgListView.setAdapter(adapter);

        /*  Insert messages into the pref_msg  */
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.getText().toString().equals("")) {
                    Toast.makeText(SetMessage.this, "메세지를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 메세지 갯수 제한 --> PRO ver. 에서 늘리기
                    if (inSet.size() <= 5) {
                        inSet.add(message.getText().toString());
                        editor.putStringSet("MsgSet", inSet).commit();
                        adapter.addItem(message.getText().toString(), false);
                        adapter.notifyDataSetChanged();
                        message.setText("");
                    } else {
                        Toast.makeText(SetMessage.this, "메세지는 최대 7개 까지 저장 가능합니다.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        /*  Checking and Saving the current state  */
        msgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemAtPosition = (MessageSettingItem) msgListView.getItemAtPosition(i);
                MessageSettingItem checkedItem = (MessageSettingItem) msgListView.getItemAtPosition(checkedPosition);
                checkedItem.setChkBox(false);
                itemAtPosition.setChkBox(true);
                checkedPosition = i;
                editor.putInt("CheckedPosition", checkedPosition).commit();
                editor.putString("msgString", itemAtPosition.getMessageStr()).commit();
                msgString = itemAtPosition.getMessageStr();
                editor.putString("MSG", msgString).commit();
                adapter.notifyDataSetChanged();
            }
        });

        /*  Delete the message from the pref_msg  */
        msgListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int idx = i;

                // Default 메세지 제외
                if (idx != 0) {
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(SetMessage.this);
                    alert.setTitle("메세지 삭제");
                    alert.setMessage("메세지를 삭제하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // 설정 된 메세지 포지션과 삭제할 메세지 포지션이 같을 경우 기본 메세지로 설정
                                    if (idx == checkedPosition) {
                                        itemAtPosition = (MessageSettingItem) msgListView.getItemAtPosition(0);
                                        itemAtPosition.setChkBox(true);
                                        msgString = itemAtPosition.getMessageStr();
                                        editor.putString("msgString", SEND_MSG);
                                        checkedPosition = 0;
                                    }
                                    // 설정 된 메세지 포지션보다 삭제할 메세지 포지션이 위에일 경우
                                    else if (idx < checkedPosition) {
                                        checkedPosition--;
                                    }
                                    editor.putInt("CheckedPosition", checkedPosition).commit();
                                    inSet.remove(adapter.getMessage(idx));
                                    editor.putStringSet("MsgSet", inSet).commit();
                                    editor.putString("MSG", msgString).commit();
                                    adapter.removeItem(idx);
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
                }
                // onClick 방지
                return true;
            }
        });

        msgExcept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("exceptUnregist", b).commit();
                Intent it = new Intent(SetMessage.this, CallingService.class);
                it.putExtra("setOption", "ok");
                startService(it);
            }
        });
    }
}