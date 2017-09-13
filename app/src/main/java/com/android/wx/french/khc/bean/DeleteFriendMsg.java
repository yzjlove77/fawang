package com.android.wx.french.khc.bean;

/**
 * Created by KangHuiCong on 2017/9/12.
 * e-mail:515849594@qq.com
 */

public class DeleteFriendMsg {
    String res;
    String mode;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "DeleteFriendMsg{" +
                "res='" + res + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }
}
