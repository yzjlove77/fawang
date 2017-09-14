package com.android.wx.french.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.model.CommentBean;
import com.android.wx.french.model.GetCommentBean;
import com.android.wx.french.util.MLog;
import com.android.wx.french.view.ICommentView;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/9/10.
 */

public class CommentPresenter extends BasePresenter<ICommentView> {

    private ICommentView commentView;
    private Context mContext;

    public CommentPresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void init() {
        commentView = getView();
    }

    public void getCommentData(CommentBean bean) {
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
                MLog.mLog("getCommentData json : " + json);
                GetCommentBean getCommentBean = Helper.jsonToBean(json, GetCommentBean.class);
                if (TextUtils.equals("true", getCommentBean.getSuccess())) {
                    commentView.showViewComment(getCommentBean.getData());
                } else {
                }

            }

            @Override
            public void onFailure(String json) {
            }
        });
    }
}
