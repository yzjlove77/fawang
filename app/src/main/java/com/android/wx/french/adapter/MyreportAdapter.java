package com.android.wx.french.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.model.GetMyReportData;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class MyreportAdapter extends RecyclerView.Adapter<MyreportAdapter.ViewHolder> {

    private List<GetMyReportData.DataBean > list ;

    public MyreportAdapter(List<GetMyReportData.DataBean > list){

        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myreport,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.content.setText(list.get(position).getClue_describe());
    }

    @Override
    public int getItemCount() {
        return list.isEmpty()?0:list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
private TextView content,time;
        public ViewHolder(View itemView) {
            super(itemView);

            content = (TextView) itemView.findViewById(R.id.my_report_content);
            time = (TextView) itemView.findViewById(R.id.my_report_time);


        }
    }
}
