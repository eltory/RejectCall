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

    private ArrayList<TimeObj> optionSettingList = new ArrayList<>();

    // 화면에 표시될 View(Layout 이 inflate 된)으로부터 위젯에 대한 참조 획득
    @BindView(R.id.descStr)
    TextView desc;
    @BindView(R.id.titleStr)
    TextView title;
    @BindView(R.id.setSwitch)
    Switch optionSwitch;

    // ListViewAdapter 생성자
    public ListViewAdapter() {

    }

    // Adapter 에 사용되는 데이터의 개수를 리턴 : 필수 구현
    @Override
    public int getCount() {
        return optionSettingList.size();
    }

    // position 에 위치한 데이터를 화면에 출력하는데 사용될 View 를 리턴 : 필수 구현
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

        // Data Set(OptionSettingItem)에서 position 에 위치한 데이터 참조 획득
        final TimeObj optionSettingItem = optionSettingList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        if (optionSettingItem != null) {
            title.setText(optionSettingItem.getStartTime()+"/"+optionSettingItem.getEndTime());
            desc.setText(optionSettingItem.getWeekSet());
            optionSwitch.setChecked(false);

            optionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    //CallingService.chk[pos] = b;
                }
            });
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
        return optionSettingList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수, 개발자가 원하는대로 작성 가능
    public void addItem(TimeObj timeObj) {
        if (!optionSettingList.contains(timeObj)) {
            optionSettingList.add(timeObj);
        }
    }
}
