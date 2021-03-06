package com.android.wx.french.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.wx.french.R;
import com.android.wx.french.model.Album;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Album> list;
    private LayoutInflater inflater;
    private OnClickItemListener onClickItemListener;
    private int adapteType;

    public void setAdapteType(int adapteType) {
        this.adapteType = adapteType;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public ReportAdapter(Context mContext, ArrayList<Album> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_report_layout, parent, false);
        return new NormalHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        if (position == list.size() - 1) {
            normalHolder.reduceIv.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(R.drawable.home_addphoto)
                    .into(normalHolder.reportIv);
        } else {
            normalHolder.reduceIv.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(list.get(position).getImagePath())
                    .into(normalHolder.reportIv);
        }
        normalHolder.reportIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener != null) {
                    onClickItemListener.onClickItem(v, normalHolder.getAdapterPosition(), adapteType);
                }
            }
        });

        normalHolder.reduceIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener != null) {
                    onClickItemListener.onClickItem(v, normalHolder.getAdapterPosition(), adapteType);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class NormalHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_report_image)
        ImageView reportIv;
        @Bind(R.id.item_report_type)
        ImageView typeIv;
        @Bind(R.id.item_reduce_image)
        ImageView reduceIv;

        public NormalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickItemListener {
        void onClickItem(View view, int position, int type);
    }
}
