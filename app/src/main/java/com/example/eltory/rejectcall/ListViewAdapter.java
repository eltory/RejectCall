package com.example.eltory.rejectcall;

import android.app.PendingIntent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by eltory on 2017-03-10.
 */
public class ListViewAdapter extends BaseAdapter {

    private ArrayList<TimeObj> optionSettingList;
    SimpleDateFormat simpleFormat = new SimpleDateFormat("a hh:mm");

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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.select_time_set, parent, false);
        }

        desc = (TextView) convertView.findViewById(R.id.descStr);
        title = (TextView) convertView.findViewById(R.id.titleStr);
        optionSwitch = (Switch) convertView.findViewById(R.id.setSwitch);

        final TimeObj optionSettingItem = optionSettingList.get(position);

        if (optionSettingItem != null) {

            title.setText(simpleFormat.format(new Date(optionSettingItem.getStartTime())) + " ~ " + simpleFormat.format(new Date(optionSettingItem.getEndTime())));
            desc.setText(optionSettingItem.getWeekSet());
            optionSwitch.setChecked(false);

            if(optionSettingItem.isEnd())
                title.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
            Log.d("이즈앤드",String.valueOf(optionSettingItem.isEnd()));
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