package com.android.wx.french.khc.bean;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class AddFriendMsg {
    String res;
    String msg;
    String mode;

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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "AddFriendMsg{" +
                "res='" + res + '\'' +
                ", msg='" + msg + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }
}
