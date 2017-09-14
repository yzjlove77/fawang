package com.android.wx.french.khc.pro;

import com.android.wx.french.khc.bean.GetFriendMsgData;

import java.util.ArrayList;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public interface IGetFriend {
    void successGetFriend(int totalRows, ArrayList<GetFriendMsgData> list);
    void failureGetFriend(String msg);
}
