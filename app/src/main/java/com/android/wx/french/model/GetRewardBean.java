package com.android.wx.french.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/8.
 */

public class GetRewardBean {

    private int totalRows;
    private int curPage;
    private ArrayList<GetRewardData> data;
    private boolean success;

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

    public ArrayList<GetRewardData> getData() {
        return data;
    }

    public void setData(ArrayList<GetRewardData> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
