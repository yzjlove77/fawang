package com.android.wx.french.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/9.
 */

public class GetCommentBean {

    private int totalRows;
    private int curPage;
    private ArrayList<GetCommentData> data;
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

    public ArrayList<GetCommentData>  getData() {
        return data;
    }

    public void setData(ArrayList<GetCommentData>  data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
