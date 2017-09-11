package com.android.wx.french.presenter;

import android.content.Context;
import android.util.Log;

import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.model.GetRegisterDataBean;
import com.android.wx.french.model.PersonalBean;
import com.android.wx.french.view.IPersonalView;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/9/9.
 */

public class PersonalPresenter extends BasePresenter<IPersonalView> {

    private Context mContext;
    private IPersonalView personalView;

    public PersonalPresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void init() {
        personalView = getView();
    }

    public void save(PersonalBean bean) {
        RequestParams params = new RequestParams("UTF-8");
        try {
            String s = new Gson().toJson(bean);
            Log.i("---s---", s);
            params.setBodyEntity(new StringEntity(s,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Helper.Post(Helper.post1, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                Log.i("---json---", json);
                GetRegisterDataBean getRegisterDataBean = Helper.jsonToBean(json, GetRegisterDataBean.class);
                personalView.saveInfo(getRegisterDataBean.isRes(), bean.getData());
            }

            @Override
            public void onFailure(String json) {
            }
        });
    }
}
