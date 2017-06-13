package com.example.eltory.rejectcall;

import java.util.ArrayList;

/**
 * Created by eltor on 2017-06-13.
 */
public class TimeObjectManager {

    private static TimeObjectManager timeObjectManager = null;
    private TimeObj timeObj;
    private ArrayList<TimeObj> timeObjs;

    private TimeObjectManager() {
        timeObjs = new ArrayList<>();
    }

    /*  Singleton Instance  */
    public static TimeObjectManager getInstance() {
        if (timeObjectManager == null) {
            timeObjectManager = new TimeObjectManager();
        }
        return timeObjectManager;
    }

    /*  Add a time object  */
    public void addTimeObj(TimeObj timeObj) {
        this.timeObjs.add(timeObj);
    }

    /*  Remove the time object from the list  */
    public void removeTimeObj(TimeObj timeObj) {
        if (this.timeObjs.contains(timeObj))
            this.timeObjs.remove(timeObj);
    }

    /*  Return the time object list  */
    public ArrayList<TimeObj> getTimeObjs() {
        return this.timeObjs;
    }
}
