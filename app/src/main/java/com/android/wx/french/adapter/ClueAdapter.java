package com.android.wx.french.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.model.GetClueData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ClueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<GetClueData> list;
    private LayoutInflater inflater;
    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public ClueAdapter(Context mContext, ArrayList<GetClueData> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_clue_layout, parent, false);
        return new NormalHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        GetClueData getClueData = list.get(position);
        normalHolder.nameTv.setText("举报人：" + getClueData.getReport_man_name());
        normalHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener != null) {
                    onClickItemListener.onClickItem(v, normalHolder.getAdapterPosition());
                }
            }
        });

        normalHolder.submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener != null) {
                    onClickItemListener.onClickItem(v, normalHolder.getAdapterPosition());
                }
            }
        });

        normalHolder.distributionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener != null) {
                    onClickItemListener.onClickItem(v, normalHolder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class NormalHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_clue_layout)
        LinearLayout itemLayout;
        @Bind(R.id.item_clue_name)
        TextView nameTv;
        @Bind(R.id.item_clue_time)
        TextView timeTv;
        @Bind(R.id.item_clue_btn)
        TextView submitTv;
        @Bind(R.id.item_distribution_btn)
        TextView distributionTv;

        public NormalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
