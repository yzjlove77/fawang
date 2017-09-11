package com.android.wx.french.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.model.GetRegisterDataBean;
import com.android.wx.french.model.RegisterBean;
import com.android.wx.french.model.RegisterData;
import com.android.wx.french.util.PhoneUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;
    @Bind(R.id.register_phone)
    EditText phoneEt;
    @Bind(R.id.register_idCard)
    EditText idCardEt;
    @Bind(R.id.register_password)
    EditText passwordEt;
    @Bind(R.id.register_name)
    EditText nameEt;
    @Bind(R.id.register_password_again)
    EditText passowrdEtAgain;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        super.initView();
        titleTv.setText("注册");
    }

    @OnClick({R.id.titlebar_left, R.id.register_btn})
    public void onClickBk(View view) {
        switch (view.getId()) {
            case R.id.titlebar_left:
                onBackPressed();
                break;
            case R.id.register_btn:
                String name = nameEt.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    showToast("请填写您的姓名");
                    return;
                }
                String phone = phoneEt.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号");
                    return;
                }
                if (!PhoneUtils.phoneNumber(phone)) {
                    showToast("请输入正确的手机号");
                    return;
                }
                String idCard = idCardEt.getText().toString().trim();
                if (TextUtils.isEmpty(idCard)) {
                    showToast("请输入身份证号");
                    return;
                }
                String password = passwordEt.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }
                String passwordAgain = passowrdEtAgain.getText().toString().trim();
                if (TextUtils.isEmpty(passwordAgain)) {
                    showToast("请再输一次密码");
                    return;
                }
                if (!TextUtils.equals(password, passwordAgain)) {
                    showToast("两次密码不相同");
                    return;
                }
                RegisterData registerData = new RegisterData();
                registerData.setPhone(phone);
                registerData.setIdcard(idCard);
                registerData.setPassword(password);
                registerData.setName(name);
                register(registerData);
                break;
        }
    }

    private void register(RegisterData registerData) {
        RegisterBean registerBean = new RegisterBean();
        registerBean.setApi("insertUser_yh");
        registerBean.setData(registerData);

        RequestParams params = new RequestParams("UTF-8");
        try {
            params.setBodyEntity(new StringEntity(new Gson().toJson(registerBean),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("---vi---",new Gson().toJson(registerBean));

        Helper.Post(Helper.post1, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                Log.i("---json---", json);
                GetRegisterDataBean getRegisterDataBean = Helper.jsonToBean(json, GetRegisterDataBean.class);
                showToast(getRegisterDataBean.getMsg());
                boolean res = getRegisterDataBean.isRes();
                if (res) {
                    finish();
                }
            }

            @Override
            public void onFailure(String json) {

            }
        });
    }
}
