package com.android.wx.french.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.model.GetJudgetLoginData;
import com.android.wx.french.model.GetLoginData;
import com.android.wx.french.presenter.LoginPresenter;
import com.android.wx.french.view.LoginView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wxZhang on 2017/8/11.
 */

public class LoginActivity extends BaseActivity<LoginView,LoginPresenter> implements LoginView{

    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.login_register)
    TextView registerBtn;
    @Bind(R.id.login_idCard)
    EditText idCardEt;
    @Bind(R.id.login_password)
    EditText passwordEt;
    @Bind(R.id.login_type)
    CheckBox loginTypeCb;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mPresenter.LoginBtn();
//        mPresenter.testRequest();
        idCardEt.setText(sph.getIdCard());
        idCardEt.setSelection(idCardEt.getText().length());

    }

    @OnClick({R.id.login_register})
    public void onClickBk(View view) {
        switch (view.getId()) {
            case R.id.login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void loginSuccessed(GetLoginData loginData, GetJudgetLoginData judgetLoginData) {
        if (loginData != null) {
            sph.setIdCard(loginData.getIdcard());
            sph.setName(loginData.getName());
            String nickName = loginData.getNickName();
            sph.setNickname(nickName);
            sph.setPhone(loginData.getPhone());
//            startActivity(new Intent(mContext, MainActivity.class));
        }
        if (judgetLoginData != null) {
            String name = judgetLoginData.getFg_name();
            sph.setName(name);
            sph.setPhone("");
            sph.setCourtCode(judgetLoginData.getFybm());
        }
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void loginFailed(String msg) {
        showToast(msg);
    }

    @Override
    public EditText getUserName() {
        return idCardEt;
    }

    @Override
    public EditText getPassWord() {
        return passwordEt;
    }

    @Override
    public Button getMessage() {
        return null;
    }

    @Override
    public Button getLogin() {
        return loginBtn;
    }

    @Override
    public ImageView getWechartLogin() {
        return null;
    }

    @Override
    public ImageView getQQLogin() {
        return null;
    }

    @Override
    public ImageView getWeiboLogin() {
        return null;
    }

    @Override
    public ImageView getLeftImg() {
        return leftImg;
    }

    @Override
    public TextView getTitlename() {
        return titleTv;
    }

    @Override
    public TextView getRightTv() {
        return null;
    }

    @Override
    public CheckBox getLoginType() {
        return loginTypeCb;
    }
}
