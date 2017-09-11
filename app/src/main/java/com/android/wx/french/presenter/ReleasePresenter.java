package com.android.wx.french.presenter;

import android.content.Context;
import android.util.Log;

import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.model.GetRegisterDataBean;
import com.android.wx.french.model.ReleaseBean;
import com.android.wx.french.view.IReleaseView;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/9/9.
 */

public class ReleasePresenter extends BasePresenter<IReleaseView> {

    private Context mContext;
    private IReleaseView releaseView;

    public ReleasePresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void initView() {
        releaseView = getView();
    }

    public void release(ReleaseBean bean) {

        RequestParams params = new RequestParams("UTF-8");
        try {
            params.setBodyEntity(new StringEntity(new Gson().toJson(bean),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("---vi---",new Gson().toJson(bean));

        Helper.Post(Helper.post1, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                Log.i("---json---", json);
                GetRegisterDataBean getRegisterDataBean = Helper.jsonToBean(json, GetRegisterDataBean.class);
                releaseView.release(getRegisterDataBean.isRes(), getRegisterDataBean.getMsg());
            }

            @Override
            public void onFailure(String json) {
                releaseView.release(false, "网络异常");
            }
        });
    }
}
