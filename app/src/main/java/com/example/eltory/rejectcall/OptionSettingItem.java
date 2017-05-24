package com.example.eltory.rejectcall;

import android.widget.Switch;

/**
 * Created by eltory on 2017-03-10.
 */
public class OptionSettingItem {

    private String titleStr;
    private String descStr;
    private boolean chkSwitch;

    public void setTitleStr(String title) {
        this.titleStr = title;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setDescStr(String descStr) {
        this.descStr = descStr;
    }

    public String getDescStr() {
        return descStr;
    }

    public void setChkSwitch(boolean chkSwitch) {
        this.chkSwitch = chkSwitch;
    }

    public boolean getChkSwitch() {
        return chkSwitch;
    }
}

