package com.android.wx.french.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.wx.french.R;

/**
 * Created by Administrator on 2017/8/14.
 */

public class BountyHunterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context context;

    public BountyHunterAdapter(Context context){
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    //建立枚举2个item类型
    public enum ITEM_TYPE {
        One,
        Other
    }


    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
            return ITEM_TYPE.One.ordinal();
        }
        return ITEM_TYPE.Other.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载ItemView的时候根据不同TYPE加载不同的布局
        if (viewType == ITEM_TYPE.One.ordinal()) {
            return new headViewHolder(mLayoutInflater.inflate(R.layout.item_bounty_one, parent, false));
        } else {
            return new ItemViewHolder(mLayoutInflater.inflate(R.layout.item_bounty_other, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof headViewHolder) {
//            ((headViewHolder) holder).mTextView.setText(data.get(position).getQuersion());
        }else{

        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }


    //头布局的ViewHolder
    public static class headViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public headViewHolder(View itemView) {
            super(itemView);
//            mTextView = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    //普通item的ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public ItemViewHolder(View itemView) {
            super(itemView);
//            mTextView = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }

}
