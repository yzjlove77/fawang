package com.android.wx.french.view;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.wx.french.model.GetJudgetLoginData;
import com.android.wx.french.model.GetLoginData;

/**
 * Created by Administrator on 2017/8/11.
 */

public interface LoginView extends TitleBarView{

    EditText getUserName();
    EditText getPassWord();
    Button getMessage();
    Button getLogin();
    ImageView getWechartLogin();
    ImageView getQQLogin();
    ImageView getWeiboLogin();
    CheckBox getLoginType();

    void loginSuccessed(GetLoginData loginData, GetJudgetLoginData judgetLoginData);

    void loginFailed(String msg);
}
