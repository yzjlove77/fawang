package com.android.wx.french.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/9.
 */

public class GetLoginBean {

    private String res;
    @SerializedName("User_yh")
    private GetLoginData data;
    private String msg;
    private String mode;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public GetLoginData getData() {
        return data;
    }

    public void setData(GetLoginData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
