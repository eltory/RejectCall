package com.example.eltory.rejectcall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by eltor on 2017-05-18.
 */
public class UnansweredListAdapter extends BaseAdapter {

    private ArrayList<Unanswered> unansweredList;
    protected Context context;
    SimpleDateFormat simpleFormat = new SimpleDateFormat("HH:mm");
    TextView nameOrNum;
    TextView calledTime;

    // ListViewAdapter 생성자
    public UnansweredListAdapter() {

    }

    // Adapter 에 사용되는 데이터의 개수를 리턴 : 필수 구현
    @Override
    public int getCount() {
        if (unansweredList != null)
            return unansweredList.size();
        return 0;
    }

    // position 에 위치한 데이터를 화면에 출력하는데 사용될 View 를 리턴 : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.popup_list, parent, false);
        }

        nameOrNum = (TextView) convertView.findViewById(R.id.name);
        calledTime = (TextView) convertView.findViewById(R.id.calledTime);
        Button btn = (Button) convertView.findViewById(R.id.go_to_call);

        // Data Set(OptionSettingItem)에서 position 에 위치한 데이터 참조 획득
        final Unanswered unanswered = unansweredList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        if (unanswered != null) {
            if (unanswered.getName() != null)
                nameOrNum.setText(unanswered.getName() + " (" + unanswered.getNumOfCalled() + ")");
            else
                nameOrNum.setText(unanswered.getPhoneNum() + " (" + unanswered.getNumOfCalled() + ")");
            calledTime.setText(simpleFormat.format(new Date(unanswered.getCalledTime())));

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + unanswered.getPhoneNum())));
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
        return unansweredList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수, 개발자가 원하는대로 작성 가능
    public void addItem() {
        this.unansweredList = ContactsManager.getInstance().getMissedList(context);
    }
    public void init(){
        this.unansweredList = null;
    }
}
