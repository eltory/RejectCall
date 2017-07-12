package com.example.eltory.rejectcall;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lsh on 2017. 7. 11..
 */

public class ExceptedListAdapter extends BaseAdapter {

    private ArrayList<ContactItem> contactItemList;
    private ArrayList<ContactItem> objList;
    private ArrayList<ContactItem> filteredItemList ;

    ExceptedListAdapter() {
        contactItemList  = new ArrayList<>();
        objList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return filteredItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredItemList.get(i);
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
            view = inflater.inflate(R.layout.excepted_list, viewGroup, false);
        }

        TextView name = (TextView) view.findViewById(R.id.titleStr);
        TextView phoneNumber = (TextView) view.findViewById(R.id.descStr);
        final ContactItem contactItem = this.filteredItemList.get(position);

        if (contactItem != null) {
            name.setText(contactItem.getName());
            phoneNumber.setText(contactItem.getPhoneNumber());
        }

        return view;
    }

    public void addItem(ArrayList<ContactItem> contactItemList) {
        this.filteredItemList = contactItemList;
    }

    public void addAnItem(ContactItem contactItem) {
        this.filteredItemList.add(contactItem);
    }

    public ArrayList<ContactItem> getList() {
        return this.objList;
    }

}
