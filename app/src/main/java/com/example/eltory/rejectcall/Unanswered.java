package com.example.eltory.rejectcall;

/**
 * Created by eltor on 2017-05-18.
 */
public class Unanswered {

    private long calledTime;
    private String name ;
    private String phoneNum;
    private int numOfCalled = 0;

    public long getCalledTime() {
        return calledTime;
    }

    public void setCalledTime(long calledTime) {
        this.calledTime = calledTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setNumOfCalled(int numOfCalled) {
        this.numOfCalled = numOfCalled;
    }

    public int getNumOfCalled() {
        return numOfCalled;
    }
}
