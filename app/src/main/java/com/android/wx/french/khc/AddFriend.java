package com.android.wx.french.khc;

import android.content.Context;
import android.util.Log;

import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.khc.bean.AddFriendBean;
import com.android.wx.french.khc.bean.AddFriendData;
import com.android.wx.french.khc.bean.AddFriendMsg;
import com.android.wx.french.khc.bean.JudgeFriendBean;
import com.android.wx.french.khc.bean.JudgeFriendData;
import com.android.wx.french.khc.bean.JudgeFriendMsg;
import com.android.wx.french.khc.function.Util;
import com.android.wx.french.khc.pro.IAddFriend;
import com.android.wx.french.util.SharePreferenceHelper;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class AddFriend {
    Context context;
    IAddFriend iAddFriend;

    SharePreferenceHelper sph;

    public AddFriend(Context context, IAddFriend iAddFriend) {
        this.context = context;
        this.iAddFriend = iAddFriend;
        sph = SharePreferenceHelper.getInstance(context);
    }

    public void judgeFriend(String id,String type,String name) {
        JudgeFriendData judgeFriendData = new JudgeFriendData();
        judgeFriendData.setUserid(sph.getIdCard());
        judgeFriendData.setFriend_userid(id);

        JudgeFriendBean bean = new JudgeFriendBean();
        bean.setData(judgeFriendData);
        bean.setRequestMethod("isFriends2");

        RequestParams params = new RequestParams("UTF-8");
        try {
            params.setBodyEntity(new StringEntity(new Gson().toJson(bean),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Helper.Post(Helper.post1, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                JudgeFriendMsg bean = Helper.jsonToBean(json, JudgeFriendMsg.class);
                Log.i("JudgeFriendMsg", bean.toString());
                switch (bean.getIsFriends()) {
                    case "yes":
                        Util.showToast(context,"对方已经是你的好友了！");
                        break;
                    case "no":
                        addFriend(type,name,id);
                        break;
                }

            }

            @Override
            public void onFailure(String json) {
                Util.showToast(context,"添加好友失败！");
            }
        });
    }

    public void addFriend(String type,String name,String id) {
        AddFriendData addFriendData = new AddFriendData();
        addFriendData.setUserid(sph.getIdCard());
        addFriendData.setUsername(sph.getName());
        addFriendData.setUser_type("2");
        addFriendData.setFriend_user_type(type);
        addFriendData.setFriend_username(name);
        addFriendData.setFriend_userid(id);

        AddFriendBean bean = new AddFriendBean();
        bean.setData(addFriendData);
        bean.setRequestMethod("insertMyFriends");

        Log.i("AddFriendMsg--",addFriendData.toString());

        RequestParams params = new RequestParams("UTF-8");
        try {
            params.setBodyEntity(new StringEntity(new Gson().toJson(bean),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Helper.Post(Helper.post1, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                AddFriendMsg bean = Helper.jsonToBean(json, AddFriendMsg.class);
                Log.i("AddFriendMsg",bean.toString());
                if ("true".equals(bean.getRes())){
                    iAddFriend.successAddFriend();
                }else {
                    iAddFriend.failureAddFriend("1");
                }

            }

            @Override
            public void onFailure(String json) {
                iAddFriend.failureAddFriend("2");
            }
        });
    }
}
