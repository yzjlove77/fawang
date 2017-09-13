package com.android.wx.french.khc.bean;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class GetFriendMsgData {
    String id;
    String xh;
    String friend_userid;
    String friend_username;
    String friend_user_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getFriend_userid() {
        return friend_userid;
    }

    public void setFriend_userid(String friend_userid) {
        this.friend_userid = friend_userid;
    }

    public String getFriend_username() {
        return friend_username;
    }

    public void setFriend_username(String friend_username) {
        this.friend_username = friend_username;
    }

    public String getFriend_user_type() {
        return friend_user_type;
    }

    public void setFriend_user_type(String friend_user_type) {
        this.friend_user_type = friend_user_type;
    }

    @Override
    public String toString() {
        return "GetFriendMsgData{" +
                "id='" + id + '\'' +
                ", xh='" + xh + '\'' +
                ", friend_userid='" + friend_userid + '\'' +
                ", friend_username='" + friend_username + '\'' +
                ", friend_user_type='" + friend_user_type + '\'' +
                '}';
    }
}
