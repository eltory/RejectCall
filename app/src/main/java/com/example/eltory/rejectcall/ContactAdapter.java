package com.example.eltory.rejectcall;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by eltor on 2017-06-26.
 */
public class ContactAdapter extends BaseAdapter implements Filterable {

    private ArrayList<ContactItem> contactItemList;
    private ArrayList<ContactItem> objList;
    private ArrayList<ContactItem> filteredItemList;
    Filter listFilter;
    boolean isEntering = false;

    ContactAdapter() {
        contactItemList = new ArrayList<>();
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
    public View getView(int i, View view, final ViewGroup viewGroup) {

        final int position = i;
        final Context context = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.select_option, viewGroup, false);
        }

        TextView name = (TextView) view.findViewById(R.id.titleStr);
        TextView phoneNumber = (TextView) view.findViewById(R.id.descStr);
        final CheckBox check = (CheckBox) view.findViewById(R.id.setSwitch);

        final ContactItem contactItem = this.filteredItemList.get(position);

        if (contactItem != null) {
            name.setText(contactItem.getName());
            phoneNumber.setText(contactItem.getPhoneNumber());
            check.setChecked(contactItem.getCheck());
        }
        if (isEntering == false)
            check.setVisibility(View.INVISIBLE);
        else {
            check.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void addToList(ContactItem contactItem){
        objList.add(contactItem);
    }
    public void removeFromList(ContactItem contactItem){
        objList.remove(contactItem);
    }

    public void setVisibleCheck(View view
    ) {

        view.animate()
                .translationY(view.getHeight())
                .alpha(0.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        //view.setVisibility(View.GONE);
                    }
                });
    }

    public void isEnter(int i) {
        if (i == 1)
            isEntering = true;
        else
            isEntering = false;
    }

    public void addItem(ArrayList<ContactItem> contactItemList) {
        this.filteredItemList = contactItemList;
        this.contactItemList = filteredItemList;
    }

    public void addAnItem(ContactItem contactItem) {
        this.filteredItemList.add(contactItem);
    }

    public ArrayList<ContactItem> getList() {
        return this.objList;
    }


    /*  리스트뷰 검색필터 구현부분  */
    @Override
    public Filter getFilter() {
        if (listFilter == null) {
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    /*  실제 필터기능 이름 및 번호 검색  */
    private class ListFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = contactItemList;
                results.count = contactItemList.size();
                Log.d("필터중", "필터내용 없음");
            } else {
                ArrayList<ContactItem> itemList = new ArrayList<>();
                for (ContactItem item : contactItemList) {
                    if (item.getName().toUpperCase().contains(constraint.toString().toUpperCase())
                            || item.getPhoneNumber().contains(constraint.toString())) {
                        itemList.add(item);
                        Log.d("필터중", "필터내용 " + constraint.toString());
                    }
                }
                results.values = itemList;
                results.count = itemList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItemList = (ArrayList<ContactItem>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
