package com.example.eltory.rejectcall;

/**
 * Created by eltor on 2017-05-18.
 */
public class Unanswered {

    private String calledTime;
    private String name ;
    private String phoneNum;
    private int numOfCalled = 0;

    public String getCalledTime() {
        return calledTime;
    }

    public void setCalledTime(String calledTime) {
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