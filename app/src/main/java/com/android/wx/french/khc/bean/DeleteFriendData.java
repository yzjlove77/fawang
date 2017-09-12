package com.android.wx.french.khc.bean;

/**
 * Created by KangHuiCong on 2017/9/12.
 * e-mail:515849594@qq.com
 */

public class DeleteFriendData {
    String userid;
    String friend_userid;

    @Override
    public String toString() {
        return "DeleteFriendData{" +
                "userid='" + userid + '\'' +
                ", friend_userid='" + friend_userid + '\'' +
                '}';
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFriend_userid() {
        return friend_userid;
    }

    public void setFriend_userid(String friend_userid) {
        this.friend_userid = friend_userid;
    }
}
