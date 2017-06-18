package com.example.eltory.rejectcall;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by eltor on 2017-06-17.
 */
public class ListUnanswered {
    ArrayList<Unanswered> list = null;

    public void setList(ArrayList<Unanswered> list) {
        this.list = list;
    }

    public ArrayList<Unanswered> getList() {
        return list;
    }

    public Unanswered getUnanswered(String phoneNum) {
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            Unanswered unanswered = (Unanswered) iterator.next();

            if (unanswered.getPhoneNum().equals(phoneNum))
                return unanswered;
        }
        return null;
    }
}
