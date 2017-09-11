package com.android.wx.french.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.model.getExposureDatabean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class ExposureAdapter extends RecyclerView.Adapter<ExposureAdapter.ViewHould> {

    private List<getExposureDatabean.getExposureData> list;

    public ExposureAdapter (List<getExposureDatabean.getExposureData> list){

        this.list = list;

    }
    @Override
    public ViewHould onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exposure_layout,parent,false);
        ViewHould hould = new ViewHould(view);
        return hould;
    }

    @Override
    public void onBindViewHolder(ViewHould holder, int position) {

        holder.nameTv.setText(list.get(position).sxbzxrmc);

        holder.IdTv.setText(list.get(position).sxbzxrzjhm);
    }

    @Override
    public int getItemCount() {
        return list.isEmpty()?0:list.size();
    }

    public class ViewHould extends RecyclerView.ViewHolder {

        private TextView nameTv,IdTv;


        public ViewHould(View itemView) {
            super(itemView);

            nameTv = (TextView) itemView.findViewById(R.id.exposure_name);
            IdTv = (TextView) itemView.findViewById(R.id.exposure_id);


        }
    }
}
