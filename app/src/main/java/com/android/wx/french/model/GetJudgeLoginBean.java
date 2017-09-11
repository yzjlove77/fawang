package com.android.wx.french.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/10.
 */

public class GetJudgeLoginBean {

    private boolean res;
    @SerializedName("User_fg")
    private GetJudgetLoginData data;
    private String msg;
    private int mode;

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public GetJudgetLoginData getData() {
        return data;
    }

    public void setData(GetJudgetLoginData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
