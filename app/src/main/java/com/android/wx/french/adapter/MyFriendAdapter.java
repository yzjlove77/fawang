package com.android.wx.french.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wx.french.R;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.khc.bean.DeleteFriendBean;
import com.android.wx.french.khc.bean.DeleteFriendData;
import com.android.wx.french.khc.bean.DeleteFriendMsg;
import com.android.wx.french.khc.bean.GetFriendMsgData;
import com.android.wx.french.khc.function.MyDialog;
import com.android.wx.french.khc.pro.IDelete;
import com.android.wx.french.util.SharePreferenceHelper;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class MyFriendAdapter extends BaseAdapter{
    Context context;
    Activity activity;
    ArrayList<GetFriendMsgData> list;
    GetFriendMsgData bean;
    int count;
    ViewHolder holder;
    SharePreferenceHelper sph;

    public MyFriendAdapter(Context context, Activity activity,ArrayList<GetFriendMsgData> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
        count = list.size();
        sph = SharePreferenceHelper.getInstance(context);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        bean = list.get(position);
        Log.i("GetFriendMsgData", bean.toString());

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_myfriend, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.name.setText(bean.getFriend_username());
        holder.btDelete.setOnClickListener(new click(bean.getFriend_userid(),position));
//        holder.btDelete.setOnClickListener(new click("2",position));
        return convertView;
    }

    public void changeCount(int count) {
        this.count = count;
    }

    static class ViewHolder {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.bt_delete)
        Button btDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private class click implements View.OnClickListener, IDelete {
        String friend_userid;
        int position;

        public click(String friend_userid, int position) {
            this.friend_userid = friend_userid;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            MyDialog myDialog = new MyDialog(activity);
            myDialog.getDeleteDialog(this,"确认删除该好友？");
        }

        @Override
        public void isDelete() {
            DeleteFriendData data = new DeleteFriendData();
            data.setUserid(sph.getIdCard());
            data.setFriend_userid(friend_userid);

            Log.i("DeleteFriendData",data.toString());

            DeleteFriendBean bean = new DeleteFriendBean();
            bean.setRequestMethod("deleteMyFriends");
            bean.setData(data);

            RequestParams params = new RequestParams("UTF-8");
            try {
                params.setBodyEntity(new StringEntity(new Gson().toJson(bean),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Helper.Post(Helper.post1, params, new OnHandleCallback() {
                @Override
                public void onSuccess(String json) {
                    DeleteFriendMsg msg = Helper.jsonToBean(json, DeleteFriendMsg.class);
                    Log.i("DeleteFriendData",msg.toString());
                    if ("true".equals(msg.getRes())) {
                        list.remove(position);
                        count = list.size();
                        notifyDataSetChanged();
                        Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "删除失败！--1", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(String json) {
                    Toast.makeText(context, "删除失败！--2", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
