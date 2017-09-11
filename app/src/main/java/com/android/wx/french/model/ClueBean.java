package com.android.wx.french.model;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ClueBean extends BasePostBean {

    private ClueData data;
    private String pageSize;
    private String curPage;

    public ClueData getData() {
        return data;
    }

    public void setData(ClueData data) {
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
