package com.android.wx.french.model;

/**
 * Created by Administrator on 2017/9/8.
 */

public class RewardBean extends BasePostBean {

    private RewardData data;
    private String pageSize;
    private String curPage;


    public RewardData getData() {
        return data;
    }

    public void setData(RewardData data) {
        this.data = data;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }
}
