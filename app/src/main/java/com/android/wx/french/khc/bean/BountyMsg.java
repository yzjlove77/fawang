package com.android.wx.french.khc.bean;

import com.android.wx.french.model.BasePostBean;

import java.util.ArrayList;

/**
 * Created by KangHuiCong on 2017/9/12.
 * e-mail:515849594@qq.com
 */

public class BountyMsg {
    int totalRows;
    String curPage;
    ArrayList<BountyMsgData> data;
    String success;

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public ArrayList<BountyMsgData> getData() {
        return data;
    }

    public void setData(ArrayList<BountyMsgData> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
