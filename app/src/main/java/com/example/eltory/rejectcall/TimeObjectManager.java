package com.example.eltory.rejectcall;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eltor on 2017-06-13.
 */
public class TimeObjectManager {

    private static TimeObjectManager timeObjectManager = null;
    private ArrayList<TimeObj> list;
    private ListTimeObj timeObjs;
    private ComplexPreferences complexPreferences;
    private Context context;
    SimpleDateFormat simpleFormat = new SimpleDateFormat("a hh:mm");

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

    /*  Return the time from start to end  */
    public String getStartEndTime(Context context, int requestCode) {
        return simpleFormat.format(new Date(findTimeObj(context, requestCode).getStartTime())) + "/" + simpleFormat.format(new Date(findTimeObj(context, requestCode).getEndTime()));
    }

    /*  Distinguish from the list by request code  */
    public TimeObj findTimeObj(Context context, int requestCode) {
        getTimeObjs(context);
        return this.timeObjs.getTimeObj(requestCode);
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
            this.timeObjs = new ListTimeObj();
            this.timeObjs.setList(list);
            setTimeObjs();
        }
        return this.timeObjs;
    }
}
