package com.android.wx.french.khc.bean;

import com.android.wx.french.model.BasePostBean;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class AddFriendBean extends BasePostBean{

    AddFriendData data;

    public AddFriendData getData() {
        return data;
    }

    public void setData(AddFriendData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AddFriendBean{" +
                "data=" + data +
                '}';
    }
}
