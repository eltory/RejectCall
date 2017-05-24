package com.example.eltory.rejectcall;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by eltor on 2017-05-18.
 */
public class UnansweredListAdapter extends BaseAdapter {

    private ArrayList<UnansweredList> unansweredList = new ArrayList<>();

    // 화면에 표시될 View(Layout 이 inflate 된)으로부터 위젯에 대한 참조 획득
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phoneNum)
    TextView phoneNum;
    @BindView(R.id.calledNum)
    TextView calledNum;


    // ListViewAdapter 생성자
    public UnansweredListAdapter() {

    }

    // Adapter 에 사용되는 데이터의 개수를 리턴 : 필수 구현
    @Override
    public int getCount() {
        return unansweredList.size();
    }

    // position 에 위치한 데이터를 화면에 출력하는데 사용될 View 를 리턴 : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        // "select_option" layout inflate 해서 convertView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.popup_list, parent, false);
        }

        name = (TextView) convertView.findViewById(R.id.name);
        phoneNum = (TextView) convertView.findViewById(R.id.phoneNum);
        calledNum = (TextView) convertView.findViewById(R.id.calledNum);

        // Data Set(OptionSettingItem)에서 position 에 위치한 데이터 참조 획득
        final UnansweredList unanswered = unansweredList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        if (unanswered != null) {
            name.setText(unanswered.getName());
            phoneNum.setText(unanswered.getPhoneNum());
            calledNum.setText(unanswered.getCalledTime());
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
        return unansweredList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수, 개발자가 원하는대로 작성 가능
    public void addItem() {

        if (CallingService.unansweredLists != null) {
            for (UnansweredList u : CallingService.unansweredLists) {
                UnansweredList item = new UnansweredList();

                item.setNumOfCalled(u.getNumOfCalled());
                item.setPhoneNum(u.getPhoneNum());
                item.setCalledTime(u.getCalledTime());

                unansweredList.add(item);
            }
        }
    }
}
