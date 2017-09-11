package com.android.wx.french.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.model.GetRewardData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/18.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<GetRewardData> list;
    private LayoutInflater inflater;
    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public HomeAdapter(Context mContext, ArrayList<GetRewardData> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_adapter,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GetRewardData getRewardData = list.get(position);
        holder.titleTv.setText(getRewardData.getTitle());
        holder.contentTv.setText(getRewardData.getReward_details());
        holder.timeTv.setText("发布时间：" + getRewardData.getThe_creat_time());
        holder.moneyTv.setText(getRewardData.getReward_amount() + "元");
        holder.homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener != null) {
                    onClickItemListener.onClickItem(v, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_home_layout)
        LinearLayout homeLayout;
        @Bind(R.id.item_reward_title)
        TextView titleTv;
        @Bind(R.id.item_reward_content)
        TextView contentTv;
        @Bind(R.id.item_reward_time)
        TextView timeTv;
        @Bind(R.id.item_reward_money)
        TextView moneyTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickItemListener {
        void onClickItem(View view, int position);
    }
}
