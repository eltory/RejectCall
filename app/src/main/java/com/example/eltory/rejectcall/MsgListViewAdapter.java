package com.example.eltory.rejectcall;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eltory on 2017-03-10.
 */
public class MsgListViewAdapter extends BaseAdapter {

    private ArrayList<MessageSettingItem> msgSettingList = new ArrayList<>();

    // 화면에 표시될 View(Layout 이 inflate 된)으로부터 위젯에 대한 참조 획득
    @BindView(R.id.msgStr)
    TextView msgStr;
    @BindView(R.id.msgSelect)
    CheckBox msgSelect;

    // ListViewAdapter 생성자
    public MsgListViewAdapter() {

    }

    // Adapter 에 사용되는 데이터의 개수를 리턴 : 필수 구현
    @Override
    public int getCount() {
        return msgSettingList.size();
    }

    // position 에 위치한 데이터를 화면에 출력하는데 사용될 View 를 리턴 : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        // "select_message" layout inflate 해서 convertView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.select_message, parent, false);
        }

        msgStr = (TextView) convertView.findViewById(R.id.msgStr);
        msgSelect = (CheckBox) convertView.findViewById(R.id.msgSelect);
        msgStr.setSelected(true);

        // Data Set(OptionSettingItem)에서 position 에 위치한 데이터 참조 획득
        final MessageSettingItem messageSettingItem = msgSettingList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        if (messageSettingItem != null) {
            msgStr.setText(messageSettingItem.getMessageStr());
            msgSelect.setChecked(messageSettingItem.getChkBox());
            msgSelect.setClickable(false);
        }
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID 를 리턴 : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return msgSettingList.get(position);
    }

    // 아이템 데이터 추가
    public void addItem(String msg, boolean chk) {
        MessageSettingItem item = new MessageSettingItem();

        item.setMessageStr(msg);
        item.setChkBox(chk);

        msgSettingList.add(item);
    }

    // 아이템 데이터 삭제
    public void removeItem(int idx) {
        msgSettingList.remove(idx);
    }

    // 메세지 가져오기
    public String getMessage(int idx) {
        return msgSettingList.get(idx).getMessageStr();
    }
}
