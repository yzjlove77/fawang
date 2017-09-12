package com.android.wx.french.khc.bean;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class JudgeFriendMsg {
    String res;
    String msg;
    String isFriends ;
    int mode;

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIsFriends() {
        return isFriends;
    }

    public void setIsFriends(String isFriends) {
        this.isFriends = isFriends;
    }

    @Override
    public String toString() {
        return "JudgeFriendMsg{" +
                "res='" + res + '\'' +
                ", msg='" + msg + '\'' +
                ", isFriends='" + isFriends + '\'' +
                ", mode=" + mode +
                '}';
    }
}
