package com.android.wx.french.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/9/9.
 */

public class SharePreferenceHelper {

    private static SharePreferenceHelper helper;
    private Context mContext;
    private SharedPreferences sp;

    private SharePreferenceHelper(Context mContext) {
        this.mContext = mContext;
        sp = mContext.getSharedPreferences("config_info", Context.MODE_PRIVATE);
    }

    public static SharePreferenceHelper getInstance(Context mContext) {
        if (helper == null) {
            helper = new SharePreferenceHelper(mContext);
        }
        return helper;
    }

    //保存身份证号
    public String getIdCard() {
        return sp.getString("id_card", "");
    }
    public void setIdCard(String idCard) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("id_card", idCard);
        edit.commit();
    }

    //保存手机号
    public String getPhone() {
        return sp.getString("phone_number", "");
    }
    public void setPhone(String phoneNumber) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("phone_number", phoneNumber);
        edit.commit();
    }

    //保存姓名
    public String getName() {
        return sp.getString("name", "");
    }
    public void setName(String name) {
        sp.edit().putString("name", name).commit();
    }

    //保存昵称
    public String getNickname() {
        return sp.getString("nickname", "");
    }
    public void setNickname(String nickname) {
        sp.edit().putString("nickname", nickname).commit();
    }
}
