package com.android.wx.french.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/7.
 */

public class LoginBean implements Serializable {

    private String RequestMethod;
    private LoginData data;

    public String getRequestMethod() {
        return RequestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        RequestMethod = requestMethod;
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }
}
