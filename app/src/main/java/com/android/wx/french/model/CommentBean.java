package com.android.wx.french.model;

/**
 * Created by Administrator on 2017/9/10.
 */

public class CommentBean extends BasePostBean {

    private CommentData data;
    private String pageSize;
    private String curPage;

    public CommentData getData() {
        return data;
    }

    public void setData(CommentData data) {
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
