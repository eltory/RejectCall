package com.example.eltory.rejectcall;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by eltory on 2017-06-14.
 */
public class ListTimeObj {
    ArrayList<TimeObj> list;

    public void setList(ArrayList<TimeObj> list) {
        this.list = list;
    }

    public ArrayList<TimeObj> getList() {
        return list;
    }

    public TimeObj getTimeObj(int requestCodeSet) {
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            TimeObj timeObj = (TimeObj) iterator.next();

            if (timeObj.getRequestCodeSet()[0] == requestCodeSet || timeObj.getRequestCodeSet()[1] == requestCodeSet)
                return timeObj;
        }
        return null;
    }
}
