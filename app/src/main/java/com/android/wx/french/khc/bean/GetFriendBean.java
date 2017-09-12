package com.android.wx.french.khc.bean;

import com.android.wx.french.model.BasePostBean;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class GetFriendBean extends BasePostBean{
    GetFriendData data;
    String pageSize;
    String curPage;

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

    public GetFriendData getData() {
        return data;
    }

    public void setData(GetFriendData data) {
        this.data = data;
    }
}
