package com.android.wx.french.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.model.GetCommentData;
import com.android.wx.french.model.GetRewardData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/9.
 */

public class RewardDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int ITEM_TYPE_HEAD = 0;
    public final int ITEM_TYPE_NORMAL = 1;

    private Context mContext;
    private ArrayList<GetCommentData> list;
    private LayoutInflater inflater;
    private GetRewardData rewardData;

    public RewardDetailAdapter(Context mContext, ArrayList<GetCommentData> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    public void setRewardData(GetRewardData rewardData) {
        this.rewardData = rewardData;
        notifyItemChanged(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case ITEM_TYPE_HEAD:
                view = inflater.inflate(R.layout.head_reward_detail_layout, parent, false);
                return new HeadHolder(view);
            case ITEM_TYPE_NORMAL:
                view = inflater.inflate(R.layout.item_comment_layout, parent, false);
                return new NormalHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_HEAD:
                if (rewardData != null) {
                    HeadHolder headHolder = (HeadHolder) holder;
                    headHolder.titleTv.setText(rewardData.getTitle());
                    headHolder.rewardCountTv.setText("悬赏金额：" + rewardData.getReward_amount() + "元");
                    headHolder.createTimeTv.setText("发布时间：" + rewardData.getThe_creat_time());
                    headHolder.personalInfoTv.setText(rewardData.getBzxr_specialty());
                    headHolder.dynamicInfoTv.setText(rewardData.getBzxr_dt());
                    headHolder.requestmentTv.setText(rewardData.getTask_demand());
                    headHolder.finishTimeTv.setText(rewardData.getTask_expiration_time());
                    headHolder.addressTv.setText(rewardData.getBzxr_hj());
                    String photoPath = rewardData.getBzxr_photo_path();
                    if (!TextUtils.isEmpty(photoPath)) {
                        Glide.with(mContext)
                                .load(photoPath)
                                .fitCenter()
                                .into(headHolder.imageView);
                    }
                }
                break;
            case ITEM_TYPE_NORMAL:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() + 10 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return ITEM_TYPE_HEAD;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    public static class NormalHolder extends RecyclerView.ViewHolder {

        public NormalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class HeadHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.head_title)
        TextView titleTv;
        @Bind(R.id.head_reward_count)
        TextView rewardCountTv;
        @Bind(R.id.head_create_time)
        TextView createTimeTv;
        @Bind(R.id.head_personal_info)
        TextView personalInfoTv;
        @Bind(R.id.head_dynamic_info)
        TextView dynamicInfoTv;
        @Bind(R.id.head_requestment)
        TextView requestmentTv;
        @Bind(R.id.head_finish_time)
        TextView finishTimeTv;
        @Bind(R.id.head_address)
        TextView addressTv;
        @Bind(R.id.head_image)
        ImageView imageView;
        @Bind(R.id.head_zoom_btn)
        TextView zoomTv;
        @Bind(R.id.head_zoom_layout)
        LinearLayout zoomLayout;

        public HeadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
