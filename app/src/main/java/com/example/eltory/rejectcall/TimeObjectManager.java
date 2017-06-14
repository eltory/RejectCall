package com.example.eltory.rejectcall;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by eltor on 2017-06-13.
 */
public class TimeObjectManager {

    private static TimeObjectManager timeObjectManager = null;
    private ArrayList<TimeObj> list;
    private ListTimeObj timeObjs;
    private ComplexPreferences complexPreferences;
    private Context context = null;

    private TimeObjectManager() {
        timeObjs = new ListTimeObj();
    }

    /*  Singleton Instance  */
    public static TimeObjectManager getInstance() {
        if (timeObjectManager == null) {
            timeObjectManager = new TimeObjectManager();
        }
        return timeObjectManager;
    }

    /*  Add a time object into the list  */
    public void addTimeObj(TimeObj timeObj) {
        this.timeObjs.getList().add(timeObj);
        setTimeObjs();
    }

    /*  Remove the time object from the list  */
    public void removeTimeObj(TimeObj timeObj) {
        if (this.timeObjs.getList().contains(timeObj))
            this.timeObjs.getList().remove(timeObj);
        setTimeObjs();
    }

    /*  Save the data object by using GSON for serializing an object to JSON  */
    public void setTimeObjs() {
        complexPreferences.putObject("timeList", timeObjs);
        complexPreferences.commit();
    }

    /*  Return the time object list  */
    public ListTimeObj getTimeObjs(Context context) {
        this.context = context;
        this.complexPreferences = ComplexPreferences.getComplexPreferences(context, "mTimeList", Context.MODE_PRIVATE);
        this.timeObjs = complexPreferences.getObject("timeList", ListTimeObj.class);

        if (timeObjs == null) {
            list = new ArrayList<>();
            timeObjs = new ListTimeObj();
            timeObjs.setList(list);
            setTimeObjs();
        }
        return this.timeObjs;
    }
}
