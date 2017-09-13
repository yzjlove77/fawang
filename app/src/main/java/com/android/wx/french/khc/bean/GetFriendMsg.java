package com.android.wx.french.khc.bean;

import java.util.ArrayList;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class GetFriendMsg {
    int totalRows;
    ArrayList<GetFriendMsgData> data;
    int curPage;
    String success;


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

    public ArrayList<GetFriendMsgData> getData() {
        return data;
    }

    public void setData(ArrayList<GetFriendMsgData> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
