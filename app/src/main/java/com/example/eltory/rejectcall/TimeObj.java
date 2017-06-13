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
    SimpleDateFormat simpleFormat = new SimpleDateFormat("HH:mm");

    TimeObj() {
    }

    public void setWeekSet(int weekSet) {
        this.weekSet = weekSet;
    }

    public String getWeekSet() {
        String days = "";

        for(int i = 0; i < 7; i++){
        if(getDay(i))
        {
            if(i == 6)
                days+=" 토 ";
            if(i == 5)
                days+=" 금 ";
            if(i == 4)
                days+=" 목 ";
            if(i == 3)
                days+=" 수 ";
            if(i == 2)
                days+=" 화 ";
            if(i == 1)
                days+=" 월 ";
            if(i == 0)
                days+=" 일 ";
        }
        }
        return days;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return simpleFormat.format(new Date(startTime));
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return simpleFormat.format(new Date(endTime));
    }

    public void setRepeat(boolean b) {
        this.repeat = b;
    }

    public boolean getRepeat() {
        return this.repeat;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRequestCodeSet(int[] requestCodeSet) {
        this.requestCodeSet = requestCodeSet;
    }

    public int[] getRequestCodeSet() {
        return requestCodeSet;
    }

    public boolean getDay(int day) {
        int temp = weekSet;

        if (((temp >> day) & 1) == 1) {
            return true;                    // 선택한 요일이 '1'이면 true 반환
        } else {
            return false;                   // 선택한 요일이 '0'이면 false 반환
        }
    }
}
