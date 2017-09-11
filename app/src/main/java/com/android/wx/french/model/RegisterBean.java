package com.android.wx.french.model;

/**
 * Created by Administrator on 2017/9/8.
 */

public class RegisterBean {

    private String RequestMethod;
    private RegisterData data;

    public String getApi() {
        return RequestMethod;
    }

    public void setApi(String RequestMethod) {
        this.RequestMethod = RequestMethod;
    }

    public RegisterData getData() {
        return data;
    }

    public void setData(RegisterData data) {
        this.data = data;
    }
}
