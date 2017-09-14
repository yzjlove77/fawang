package com.android.wx.french.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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
import com.android.wx.french.model.ValidateYzmBean;
import com.android.wx.french.model.getYzmBean;
import com.android.wx.french.util.MLog;
import com.android.wx.french.util.PhoneUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.OnClick;
/**注册*/
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
    @Bind(R.id.register_message)
    EditText messageEt;
    @Bind(R.id.register_btn)
    Button rebtn;

    @Bind(R.id.getmessage_btn)
    Button messageBtn;

    private String falg;

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
        passowrdEtAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (passwordEt.getText().length()==passowrdEtAgain.getText().length()&&(!TextUtils.isEmpty(messageEt.getText().toString()))
                        &&(!TextUtils.isEmpty(messageEt.getText().toString()))&&(!TextUtils.isEmpty(phoneEt.getText().toString()))
                        &&(!TextUtils.isEmpty(idCardEt.getText().toString()))&&(!TextUtils.isEmpty(nameEt.getText().toString()))){
                    rebtn.setBackgroundResource(R.drawable.button_blue_bac);
                }else{
                    rebtn.setBackgroundResource(R.drawable.button_gray_bac);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!PhoneUtils.phoneNumber(s.toString())){
                    messageBtn.setBackgroundResource(R.drawable.button_gray_bac);
                    messageBtn.setClickable(false);
                }else{
                    messageBtn.setBackgroundResource(R.drawable.button_blue_bac);
                    messageBtn.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick({R.id.titlebar_left, R.id.register_btn,R.id.getmessage_btn})
    public void onClickBk(View view) {
        switch (view.getId()) {
            case R.id.titlebar_left:
                onBackPressed();
                break;

            case R.id.getmessage_btn:
                getMessage();
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
                if (!PhoneUtils.isPersonCode(idCard)){
                    showDialog("请输入正确的身份证号");
                    return;
                }
                String password = passwordEt.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }
                validataYzm();
                if (!(falg.equals("666"))){
                showDialog("请输入正确的验证码");
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

    private void getMessage() {

        if (TextUtils.isEmpty(nameEt.getText().toString().trim())){
            showDialog("请输入姓名");
            return;
        }

        String phone = phoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showDialog("请输入手机号");
            return;
        }
        if (!PhoneUtils.phoneNumber(phone)) {
            showDialog("请输入正确的手机号");
            return;
        }
        String idCard = idCardEt.getText().toString().trim();
        if (TextUtils.isEmpty(idCard)) {
            showDialog("请输入身份证号");
            return;
        }
        if (!PhoneUtils.isPersonCode(idCard)){
            showDialog("请输入正确的身份证号");
            return;
        }
        getYzmBean bean = new getYzmBean();
        bean.setRequestMethod("getYzm");
        getYzmBean.DataBean data = new getYzmBean.DataBean();
        data.setName(nameEt.getText().toString().trim());
        data.setIdcard(idCard);
        data.setPhone(phone);
        bean.setData(data);
        RequestParams params = new RequestParams();
        try {
            params.setBodyEntity(new StringEntity(new Gson().toJson(bean),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Helper.Post(Helper.post1, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                try {
                    JSONObject js = new JSONObject(json);
                    if (js.getString("res").equals("true")){
                        showDialog("验证码获取成功！");
                    }else {
                        showDialog("验证码获取失败，请重试！");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MLog.mLog(json);

            }

            @Override
            public void onFailure(String json) {
                showDialog("服务器连接失败！");
            }
        });
    }

    private void validataYzm(){
        ValidateYzmBean bean = new ValidateYzmBean();
        bean.setRequestMethod("validateYzm");
        ValidateYzmBean.DataBean data = new ValidateYzmBean.DataBean();
        data.setIdcard(idCardEt.getText().toString().trim());
        data.setYzm(messageEt.getText().toString().trim());
        bean.setData(data);
        RequestParams params = new RequestParams();
        try {
            params.setBodyEntity(new StringEntity(new Gson().toJson(bean),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Helper.Post(Helper.post1, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {

                try {
                    JSONObject js = new JSONObject(json);
                    if (js.getString("res").equals("false")){
                        showDialog(js.getString("msg"));
                        falg = "666";
                    }else{
                        showDialog(js.getString("msg"));
                        falg = "999";
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MLog.mLog(json);

            }

            @Override
            public void onFailure(String json) {

            }
        });

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
        Helper.Post(Helper.post1, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {

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
