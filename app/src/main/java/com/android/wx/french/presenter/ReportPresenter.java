package com.android.wx.french.presenter;

import android.content.Context;

import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.model.GetRegisterDataBean;
import com.android.wx.french.model.ReportBean;
import com.android.wx.french.util.MLog;
import com.android.wx.french.view.IReportView;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ReportPresenter extends BasePresenter<IReportView> {

    private Context mContext;
    private IReportView reportView;

    public ReportPresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void init() {
        reportView = getView();
    }

    public void report(ReportBean bean) {
        RequestParams params = new RequestParams("UTF-8");
        try {
            params.setBodyEntity(new StringEntity(new Gson().toJson(bean),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MLog.mLog("send : " + new Gson().toJson(bean));

        Helper.Post(Helper.post1, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                MLog.mLog("json = " + json);
                GetRegisterDataBean getRegisterDataBean = Helper.jsonToBean(json, GetRegisterDataBean.class);
                String msg = getRegisterDataBean.getMsg();
                if (getRegisterDataBean.isRes()) {
                    reportView.showViewReport(msg);
                } else {
                    reportView.failedViewReport(msg);
                }
            }

            @Override
            public void onFailure(String json) {
            }
        });
    }
}
