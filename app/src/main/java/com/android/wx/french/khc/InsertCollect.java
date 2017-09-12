package com.android.wx.french.khc;

import android.content.Context;
import android.util.Log;

import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.khc.bean.InsertCollectBean;
import com.android.wx.french.khc.bean.InsertCollectData;
import com.android.wx.french.khc.bean.InsertCollectMsg;
import com.android.wx.french.khc.pro.IInsertCollect;
import com.android.wx.french.util.SharePreferenceHelper;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class InsertCollect {
    protected SharePreferenceHelper sph;

    Context context;
    IInsertCollect iInsertCollect;

    public InsertCollect(Context context,IInsertCollect iInsertCollect) {
        this.context = context.getApplicationContext();
        this.iInsertCollect = iInsertCollect;
    }

    public void insertCollect(String title,int id) {
        sph = SharePreferenceHelper.getInstance(context);

        //收藏接口还没出来，接口调错了，调成关注了
        InsertCollectData insertCollectData = new InsertCollectData();
        insertCollectData.setUser_type(2);
        insertCollectData.setUserid(sph.getPhone());
        insertCollectData.setUsername(sph.getName());
        insertCollectData.setFlow_with_task_id(id);
        insertCollectData.setFlow_with_task_title(title);

        InsertCollectBean insertCollectBean = new InsertCollectBean();
        insertCollectBean.setData(insertCollectData);
        insertCollectBean.setRequestMethod("insertMyAttention");

        RequestParams params = new RequestParams("UTF-8");
        try {
            params.setBodyEntity(new StringEntity(new Gson().toJson(insertCollectBean),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("---insertCollect---",new Gson().toJson(insertCollectBean));

        Helper.Post(Helper.getListGrid, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                InsertCollectMsg bean = Helper.jsonToBean(json, InsertCollectMsg.class);
                Log.i("InsertCollectMsg", bean.toString());
                if (bean.isRes()) {
                    iInsertCollect.successInsertCollect();
                }else {
                    iInsertCollect.failureInsertCollect("1");
                }
            }
            @Override
            public void onFailure(String json) {
                iInsertCollect.failureInsertCollect("2");
            }
        });
    }
}
