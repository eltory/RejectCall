package com.example.eltory.rejectcall;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

    public static String msgString;
    public final Context context = this;
    private int checkedPosition;
    private SharedPreferences pref_msg;
    private Set<String> msgSet;
    private Set<String> inSet;
    private SharedPreferences.Editor editor;
    private List toSort;
    private MsgListViewAdapter adapter;
    private MessageSettingItem itemAtPosition;

    @BindView(R.id.addText)
    EditText message;
    @BindView(R.id.addMessage)
    Button addBtn;
    @BindView(R.id.msgLV)
    ListView msgListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_message);
        ButterKnife.bind(this);
        adapter = new MsgListViewAdapter();

        // 저장된 메세지 pref 가져오기
        pref_msg = getSharedPreferences("Message", MODE_PRIVATE);
        editor = pref_msg.edit();
        checkedPosition = pref_msg.getInt("CheckedPosition", 0);
        msgString = pref_msg.getString("msgString",CallingService.SEND_MSG);
        msgSet = pref_msg.getStringSet("MsgSet", new HashSet<String>());
        inSet = new HashSet<>(msgSet);
        toSort = new ArrayList(msgSet);

        // 메세지셋 정렬
        if (toSort.size() != 0)
            Collections.sort(toSort);

        // 리스트뷰에 뿌리기
        int i = 0;
        itemAtPosition = (MessageSettingItem) msgListView.getItemAtPosition(i);

        if (msgString.equals(CallingService.SEND_MSG)) {
            adapter.addItem(CallingService.SEND_MSG, true);
            checkedPosition = 0;
        } else
            adapter.addItem(CallingService.SEND_MSG, false);

        for (Object s : toSort) {
            if (msgString.equals(s.toString())) {
                adapter.addItem(s.toString(), true);
                checkedPosition = i + 1;
            } else
                adapter.addItem(s.toString(), false);
            i++;
        }
        msgListView.setAdapter(adapter);

        // 메세지 추가하기
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.getText().toString().equals("")) {
                    Toast.makeText(context, "메세지를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (inSet.size() <= 5) {
                        inSet.add(message.getText().toString());
                        editor.putStringSet("MsgSet", inSet).commit();
                        adapter.addItem(message.getText().toString(), false);
                        adapter.notifyDataSetChanged();
                        message.setText("");
                    } else {
                        Toast.makeText(SetMessage.this, "메세지는 최대 7개 까지 저장 가능합니다.", Toast.LENGTH_LONG).show();
                    }
                    // setSendMSG();
                    //putMSG();
                }
            }
        });

        // 해당 메세지문구 체크
        msgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MessageSettingItem itemAtPosition = (MessageSettingItem) msgListView.getItemAtPosition(i);
                MessageSettingItem checkedItem = (MessageSettingItem) msgListView.getItemAtPosition(checkedPosition);
                checkedItem.setChkBox(false);
                itemAtPosition.setChkBox(true);
                checkedPosition = i;
                editor.putInt("CheckedPosition", checkedPosition).commit();
                editor.putString("msgString",itemAtPosition.getMessageStr()).commit();
                msgString = itemAtPosition.getMessageStr();
                editor.putString("MSG", msgString).commit();
                adapter.notifyDataSetChanged();
            }
        });

        // 해당 메세지문구 삭제
        msgListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int idx = i;

                if (idx != 0) {
                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);
                    alert.setTitle("메세지 삭제");
                    alert.setMessage("메세지를 삭제하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // 설정 된 메세지 포지션과 삭제할 메세지 포지션이 같을 경우 기본 메세지로 설정
                                    if (idx == checkedPosition) {
                                        MessageSettingItem itemAtPosition = (MessageSettingItem) msgListView.getItemAtPosition(0);
                                        itemAtPosition.setChkBox(true);
                                        msgString = itemAtPosition.getMessageStr();
                                        editor.putString("msgString",CallingService.SEND_MSG);
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
    }

    public void setSendMSG(int idx) {

    }

    public void putMSG() {
        SharedPreferences pref = getSharedPreferences("MsgString", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("MSG", message.getText().toString());
        editor.commit();
    }
}