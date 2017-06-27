package com.example.eltory.rejectcall;

import android.widget.Switch;

import java.io.Serializable;

/**
 * Created by eltor on 2017-06-26.
 */
public class ContactItem implements Serializable{

    private String name;
    private String phoneNumber;
    private boolean check ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
