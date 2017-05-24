package com.example.eltory.rejectcall;

/**
 * Created by eltory on 2017-05-09.
 */
public class TimeObj {

    private int daySet = 0;
    private long startTime;
    private long endTime;
    private boolean repeat = false;

    TimeObj(){

    }

    public void setDaySet(int daySet) {

    }

    public int getDaySet() {
        return daySet;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    public long getEndTime() {
        return endTime;
    }
    public void setRepeat(boolean b){
        this.repeat = b;
    }
    public boolean getRepeat(){
        return this.repeat;
    }
    public boolean isRepeat() {
        return repeat;
    }
}
