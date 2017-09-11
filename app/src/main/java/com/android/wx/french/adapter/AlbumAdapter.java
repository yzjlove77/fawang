package com.android.wx.french.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Album> alba;
    private LayoutInflater inflater;
    private OnItemCheckedListener onItemCheckedListener;

    public void setOnItemCheckedListener(OnItemCheckedListener onItemCheckedListener) {
        this.onItemCheckedListener = onItemCheckedListener;
    }

    public AlbumAdapter(Context mContext, ArrayList<Album> alba) {
        this.mContext = mContext;
        this.alba = alba;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_album_layout, parent, false);
        return new NormalHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalHolder viewHolder = (NormalHolder) holder;
        final Album album = alba.get(position);
        String imagePath = album.getImagePath();
        if (imagePath.endsWith(".mp4")) {
            viewHolder.videoSignIv.setVisibility(View.VISIBLE);
        } else {
            viewHolder.videoSignIv.setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(imagePath)
                .into(viewHolder.albumImage);
        viewHolder.albumCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alba.get(position).setSelected(isChecked);
                if (onItemCheckedListener != null) {
                    onItemCheckedListener.onItemChecked(position, isChecked);
                }
            }
        });
        viewHolder.albumCb.setChecked(album.isSelected());
    }

    @Override
    public int getItemCount() {
        return alba != null ? alba.size() : 0;
    }

    public static class NormalHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_album_checkbox)
        CheckBox albumCb;
        @Bind(R.id.item_album_image)
        ImageView albumImage;
        @Bind(R.id.item_album_video_sign)
        ImageView videoSignIv;

        public NormalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemCheckedListener {
        void onItemChecked(int position, boolean isChecked);
    }
}
