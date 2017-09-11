package com.android.wx.french.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/11.
 */

public class GetClueBean {

    private int totalRows;
    private int curPage;
    private ArrayList<GetClueData> data;
    private String success;

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public ArrayList<GetClueData> getData() {
        return data;
    }

    public void setData(ArrayList<GetClueData> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
