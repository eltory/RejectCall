package com.example.eltory.rejectcall;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eltory on 2017-05-09.
 */
public class TimeObj {

    private int weekSet = 0;
    private long startTime;
    private long endTime;
    private boolean repeat = false;
    private int[] requestCodeSet;
    private boolean isEnd = false;
    //SimpleDateFormat simpleFormat = new SimpleDateFormat("HH:mm");

    TimeObj() {
    }

    public void setWeekSet(int weekSet) {
        this.weekSet = weekSet;
    }

    public int getWeek(){
        return this.weekSet;
    }
    public String getWeekSet() {
        String days = "";

        for (int i = 0; i < 7; i++) {
            if (getDay(i)) {
                if (i == 6)
                    days += " 토 ";
                if (i == 5)
                    days += " 금 ";
                if (i == 4)
                    days += " 목 ";
                if (i == 3)
                    days += " 수 ";
                if (i == 2)
                    days += " 화 ";
                if (i == 1)
                    days += " 월 ";
                if (i == 0)
                    days += " 일 ";
            }
        }
        return days;
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

    public void setRepeat(boolean b) {
        this.repeat = b;
    }

    public boolean getRepeat() {
        return this.repeat;
    }

    public void setRequestCodeSet(int[] requestCodeSet) {
        this.requestCodeSet = requestCodeSet;
    }

    public int[] getRequestCodeSet() {
        return requestCodeSet;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isEnd() {
        return this.isEnd;
    }

    public boolean getDay(int day) {
        int temp = weekSet;

        if (((temp >> day) & 1) == 1) {
            return true;
        } else {
            return false;
        }
    }
}
