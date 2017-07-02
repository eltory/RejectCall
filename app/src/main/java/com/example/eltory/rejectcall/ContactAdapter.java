package com.example.eltory.rejectcall;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by eltor on 2017-06-26.
 */
public class ContactAdapter extends BaseAdapter {

    private ArrayList<ContactItem> contactItemList;
    private ArrayList<ContactItem> objList;

    ContactAdapter() {
        contactItemList = new ArrayList<>();
        objList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        if (contactItemList.size() > 0)
            return contactItemList.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return contactItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final int position = i;
        final Context context = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.select_option, viewGroup, false);
        }

        TextView name = (TextView) view.findViewById(R.id.titleStr);
        TextView phoneNumber = (TextView) view.findViewById(R.id.descStr);
        final CheckBox check = (CheckBox) view.findViewById(R.id.setSwitch);

        final ContactItem contactItem = this.contactItemList.get(position);

        if (contactItem != null) {
            name.setText(contactItem.getName());
            phoneNumber.setText(contactItem.getPhoneNumber());
            check.setChecked(contactItem.getCheck());
        }
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check.isChecked()) {
                    contactItemList.get(position).setCheck(true);
                    if (!ContactsManager.getInstance().isExceptedList(context, contactItemList.get(position).getPhoneNumber()))
                        objList.add(contactItem);
                } else {
                    contactItemList.get(position).setCheck(false);
                    objList.remove(contactItem);
                }
            }
        });
        return view;
    }

    public void addItem(ArrayList<ContactItem> contactItemList) {
        this.contactItemList = contactItemList;
    }

    public void addAnItem(ContactItem contactItem) {
        this.contactItemList.add(contactItem);
    }

    public ArrayList<ContactItem> getList() {
        return this.objList;
    }
}
