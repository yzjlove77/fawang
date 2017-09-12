package com.android.wx.french.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wx.french.R;
import com.android.wx.french.khc.AddFriend;
import com.android.wx.french.khc.function.Util;
import com.android.wx.french.khc.pro.IAddFriend;
import com.android.wx.french.model.GetRewardData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/15.
 */

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<GetRewardData> list;
    private LayoutInflater inflater;
    private OnClickItemListener onClickItemListener;
    AddFriend addFriend;
    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public MyTaskAdapter(Context mContext, ArrayList<GetRewardData> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    public MyTaskAdapter (){

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mytask,parent,false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GetRewardData getRewardData = list.get(position);
        Log.i("getFb_fg_userid", getRewardData.toString());
        holder.titleTv.setText(getRewardData.getTitle());
        holder.contentTv.setText(getRewardData.getReward_details());
        holder.timeTv.setText("发布时间：" + getRewardData.getThe_creat_time());
        holder.countTv.setText("0人提供线索");
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener != null) {
                    onClickItemListener.onClickItem(v, holder.getAdapterPosition());
                }
            }
        });


        if ("1".equals(getRewardData.getType_of_task())) {
            holder.btAddFriend.setOnClickListener(new addFriend(getRewardData.getFbdsr_sfzh(),"2",getRewardData.getFbdsr_name()));
        }else {
            holder.btAddFriend.setOnClickListener(new addFriend(getRewardData.getFb_fg_userid(),"1",getRewardData.getFb_fg_name()));
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_task_title)
        TextView titleTv;
        @Bind(R.id.item_task_content)
        TextView contentTv;
        @Bind(R.id.item_task_time)
        TextView timeTv;
        @Bind(R.id.item_task_count)
        TextView countTv;
        @Bind(R.id.item_task_layout)
        LinearLayout itemLayout;
        @Bind(R.id.bt_add_friend)
        TextView btAddFriend;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class addFriend implements View.OnClickListener {
        String id;
        String type;
        String name;

        public addFriend(String fb_fg_userid, String type, String fbdsr_name) {
            this.id = fb_fg_userid;
            this.type = type;
            this.name = fbdsr_name;
        }

        @Override
        public void onClick(View v) {
            addFriend = new AddFriend(mContext, new IAddFriend() {
                @Override
                public void successAddFriend() {
                    Util.showToast(mContext,"添加好友成功！");
                }

                @Override
                public void failureAddFriend(String msg) {
                    Util.showToast(mContext,"添加好友失败！");
                }
            });

            addFriend.judgeFriend(id,type,name);

        }
    }
}
