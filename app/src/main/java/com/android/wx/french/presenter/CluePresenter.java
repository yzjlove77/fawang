package com.android.wx.french.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.model.ClueBean;
import com.android.wx.french.model.GetClueBean;
import com.android.wx.french.util.MLog;
import com.android.wx.french.view.IClueView;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/9/11.
 */

public class CluePresenter extends BasePresenter<IClueView> {

    private Context mContext;
    private IClueView clueView;

    public CluePresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void init() {
        clueView = getView();
    }

    public void getClueData(ClueBean bean) {
        RequestParams params = new RequestParams("UTF-8");
        try {
            params.setBodyEntity(new StringEntity(new Gson().toJson(bean),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MLog.mLog("send : " + new Gson().toJson(bean));

        Helper.Post(Helper.getListGrid, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                MLog.mLog("json : " + json);
                GetClueBean getClueBean = Helper.jsonToBean(json, GetClueBean.class);
                if (TextUtils.equals("true", getClueBean.getSuccess())) {
                    clueView.showViewClue(getClueBean.getData());
                } else {
                }

            }

            @Override
            public void onFailure(String json) {
            }
        });
    }
}
