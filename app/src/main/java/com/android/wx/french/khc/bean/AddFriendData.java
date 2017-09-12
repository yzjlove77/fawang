package com.android.wx.french.khc.bean;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class AddFriendData {
    String user_type;
    String userid;
    String username;
    String friend_user_type;
    String friend_userid;
    String friend_username;

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriend_user_type() {
        return friend_user_type;
    }

    public void setFriend_user_type(String friend_user_type) {
        this.friend_user_type = friend_user_type;
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

    @Override
    public String toString() {
        return "AddFriendData{" +
                "user_type='" + user_type + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", friend_user_type='" + friend_user_type + '\'' +
                ", friend_userid='" + friend_userid + '\'' +
                ", friend_username='" + friend_username + '\'' +
                '}';
    }
}
