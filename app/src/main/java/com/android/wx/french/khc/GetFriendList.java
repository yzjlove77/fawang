package com.android.wx.french.khc;

import android.content.Context;

import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.khc.bean.GetFriendBean;
import com.android.wx.french.khc.bean.GetFriendData;
import com.android.wx.french.khc.bean.GetFriendMsg;
import com.android.wx.french.khc.pro.IGetFriend;
import com.android.wx.french.util.SharePreferenceHelper;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class GetFriendList {
    Context context ;
    IGetFriend iGetFriend;
    SharePreferenceHelper sph;

    public GetFriendList(Context context,IGetFriend iGetFriend) {
        this.context = context;
        this.iGetFriend = iGetFriend;
    }

    public void getFriendList(int pager) {

        sph = SharePreferenceHelper.getInstance(context);
        GetFriendData collectBean = new GetFriendData();
        collectBean.setUserid(sph.getIdCard());

        ArrayList<String> cols = new ArrayList<>();
        cols.add("id");
        cols.add("friend_user_type");
        cols.add("friend_userid");
        cols.add("friend_username");

        GetFriendBean bean = new GetFriendBean();
        bean.setRequestMethod("getMyFriends");
        bean.setData(collectBean);
        bean.setCols(cols);
        bean.setCurPage("" + pager);
        bean.setPageSize("20");

        RequestParams params = new RequestParams("UTF-8");
        try {
            params.setBodyEntity(new StringEntity(new Gson().toJson(bean),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Helper.Post(Helper.getListGrid, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                GetFriendMsg bean = Helper.jsonToBean(json, GetFriendMsg.class);
                if (("true").equals(bean.getSuccess())) {
                    iGetFriend.successGetFriend(bean.getTotalRows(), bean.getData());
                }else {
                    iGetFriend.failureGetFriend("1");
                }
            }

            @Override
            public void onFailure(String json) {
                iGetFriend.failureGetFriend("2");
            }
        });
    }
}
