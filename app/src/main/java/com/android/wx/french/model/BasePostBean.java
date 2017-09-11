package com.android.wx.french.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/8.
 */

public class BasePostBean {

    protected String RequestMethod;
    protected ArrayList<String> cols;

    public String getRequestMethod() {
        return RequestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        RequestMethod = requestMethod;
    }

    public ArrayList<String> getCols() {
        return cols;
    }

    public void setCols(ArrayList<String> cols) {
        this.cols = cols;
    }
}
