package com.example.eltory.rejectcall;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by eltory on 2017-03-10.
 */
public class ListViewAdapter extends BaseAdapter {

    private ArrayList<TimeObj> optionSettingList;

    // 화면에 표시될 View(Layout 이 inflate 된)으로부터 위젯에 대한 참조 획득
    @BindView(R.id.descStr)
    TextView desc;
    @BindView(R.id.titleStr)
    TextView title;
    @BindView(R.id.setSwitch)
    Switch optionSwitch;

    // ListViewAdapter 생성자
    public ListViewAdapter(Context context) {
        addItem(context);
    }

    @Override
    public int getCount() {
        return optionSettingList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        // "select_option" layout inflate 해서 convertView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.select_option, parent, false);
        }

        desc = (TextView) convertView.findViewById(R.id.descStr);
        title = (TextView) convertView.findViewById(R.id.titleStr);
        optionSwitch = (Switch) convertView.findViewById(R.id.setSwitch);

        final TimeObj optionSettingItem = optionSettingList.get(position);

        // TODO : 시간리스트뷰 정리하기
        if (optionSettingItem != null) {

            title.setText(optionSettingItem.getStartTime() + "/" + optionSettingItem.getEndTime());
            desc.setText(optionSettingItem.getWeekSet());
            optionSwitch.setChecked(false);
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return optionSettingList.get(position);
    }

    public void addItem(Context context) {
        this.optionSettingList = TimeObjectManager.getInstance().getTimeObjs(context).getList();
    }

    public void removeItem(int position) {
        TimeObjectManager.getInstance().removeTimeObj((TimeObj) getItem(position));
    }
}
/*
* 노티 푸쉬아람 만들기 */