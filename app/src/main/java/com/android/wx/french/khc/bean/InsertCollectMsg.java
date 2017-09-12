package com.android.wx.french.khc.bean;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class InsertCollectMsg {
    boolean res;
    String mode;


    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
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
        return "InsertCollectMsg{" +
                "res='" + res + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }
}
