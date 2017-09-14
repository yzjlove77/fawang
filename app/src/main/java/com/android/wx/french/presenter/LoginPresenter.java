package com.android.wx.french.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wx.french.R;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.model.GetJudgeLoginBean;
import com.android.wx.french.model.GetLoginBean;
import com.android.wx.french.model.JudgeLoginBean;
import com.android.wx.french.model.JudgeLoginData;
import com.android.wx.french.model.LoginBean;
import com.android.wx.french.model.LoginData;
import com.android.wx.french.model.TestBodey;
import com.android.wx.french.view.LoginView;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/8/11.
 */

public class LoginPresenter extends BasePresenter<LoginView> implements CompoundButton.OnCheckedChangeListener {

    private Context context;

    private Button loginBtn;
    private TextView titleName;
    private LoginView loginView;
    private EditText usernameEt, passwordEt;
    private CheckBox loginTypeCb;

    private boolean isJudge;


    public LoginPresenter(Context context){
        this.context = context;
    }

    public void LoginBtn(){
        loginView = getView();

        if (loginView!=null){
            loginBtn = loginView.getLogin();
            titleName = loginView.getTitlename();
            titleName.setText(R.string.login_name);
            loginBtn.setOnClickListener(listener);
            usernameEt = loginView.getUserName();
            passwordEt = loginView.getPassWord();
            loginTypeCb = loginView.getLoginType();

            loginTypeCb.setOnCheckedChangeListener(this);
        }
    }

    public void testRequest(){

//        OkHttpClient client = new OkHttpClient();
//        MediaType mediaType = MediaType.parse("application/octet-stream");
//        RequestBody body = RequestBody.create(mediaType, "{\"RequestMethod\":\"getAuditLoggingById\",\r\n\"data\":{\"id\":\"1\"}\r\n}\r\n");
//        Request request = new Request.Builder()
//                .url("http://116.62.162.52:6060/fwzx/rest/CommonService/post1")
//                .post(body)
//                .addHeader("cache-control", "no-cache")
//                .addHeader("postman-token", "24d60fae-dde8-520e-03a8-170a3af86cf5")
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        TestBodey body = new TestBodey();
        body.setRequestMethod("getAuditLoggingById");
        TestBodey.DataBean bean = new TestBodey.DataBean();
        bean.setId("1");
        body.setData(bean);
        Log.i("--request---",new Gson().toJson(body));


//        frenchApi.getResult();
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),new Gson().toJson(body));
//        frenchApi.getResult(requestBody);



    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.login_btn:
                    String username = usernameEt.getText().toString().trim();
                    if (TextUtils.isEmpty(username)) {
                        Toast.makeText(context, "请输入身份证号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String passowrd = passwordEt.getText().toString().trim();
                    if (TextUtils.isEmpty(passowrd)) {
                        Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    login(username, passowrd);
                    break;

            }
        }
    };

    private void login(String username, String passowrd) {
        if (isJudge) {
            JudgeLoginData loginData = new JudgeLoginData();
            loginData.setFg_email(username);
            loginData.setFg_password(passowrd);

            JudgeLoginBean bean = new JudgeLoginBean();
            bean.setRequestMethod("loginByFg");
            bean.setData(loginData);

            RequestParams params = new RequestParams("UTF-8");
            try {
                params.setBodyEntity(new StringEntity(new Gson().toJson(bean), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.i("---vi---", new Gson().toJson(bean));

            Helper.Post(Helper.post1, params, new OnHandleCallback() {
                @Override
                public void onSuccess(String json) {
                    Log.i("---json---", json);
                    GetJudgeLoginBean getLoginBean = Helper.jsonToBean(json, GetJudgeLoginBean.class);
                    String msg = getLoginBean.getMsg();
                    if (getLoginBean.isRes()) {
                        loginView.loginSuccessed(null, getLoginBean.getData());
                    } else {
                        loginView.loginFailed(msg);
                    }

                }

                @Override
                public void onFailure(String json) {
                    loginView.loginFailed("网路异常");
                }
            });
        } else {
            LoginData loginData = new LoginData();
            loginData.setIdCard(username);
            loginData.setPassword(passowrd);

            LoginBean bean = new LoginBean();
            bean.setRequestMethod("queryuser_yhLog");
            bean.setData(loginData);

            RequestParams params = new RequestParams("UTF-8");
            try {
                params.setBodyEntity(new StringEntity(new Gson().toJson(bean), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.i("---vi---", new Gson().toJson(bean));

            Helper.Post(Helper.post1, params, new OnHandleCallback() {
                @Override
                public void onSuccess(String json) {
                    Log.i("---json---", json);
                    GetLoginBean getLoginBean = Helper.jsonToBean(json, GetLoginBean.class);
                    String msg = getLoginBean.getMsg();
                    String res = getLoginBean.getRes();
                    if (TextUtils.equals("true", res)) {
                        loginView.loginSuccessed(getLoginBean.getData(), null);
                    } else {
                        loginView.loginFailed(msg);
                    }

                }

                @Override
                public void onFailure(String json) {
                    loginView.loginFailed("网路异常");
                }
            });
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isJudge = isChecked;
    }
}
