package com.android.wx.french.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.model.GetNoticebean;

import java.util.List;

/**
 * 法院地区
 * @author wxZhang
 * 2016/4/15
 * */
public class CourtAdapter extends BaseAdapter{

	private Context context;
	
	private List<GetNoticebean.GetNotice> list;
	
	public int selectedPosition;
	
	public CourtAdapter(Context context, List<GetNoticebean.GetNotice> list){
		
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.isEmpty()?0:list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHoudle houdle;
		if (convertView == null) {
			
			houdle = new ViewHoudle();
			convertView = LayoutInflater.from(context).inflate(R.layout.courtlistview_item, parent, false);
			houdle.nameTextView = (TextView) convertView .findViewById(R.id.court_name_itemtv);
			convertView .setTag(houdle);
		}else {
			houdle = (ViewHoudle) convertView.getTag();
		}
		
		houdle.nameTextView.setText(list.get(position).fy_name);
		
		if (selectedPosition == position) {
			houdle.nameTextView.setSelected(true);
			houdle.nameTextView.setPressed(true);
			houdle.nameTextView.setTextColor(0xff0085cf);
			
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			houdle.nameTextView.setSelected(false);
			houdle.nameTextView.setPressed(false);
			houdle.nameTextView.setTextColor(0xff000000);
			convertView.setBackgroundColor(0xfff2f2f2);
		}
		
		return convertView;
	}
	
	class ViewHoudle{
		
		private TextView nameTextView;
		
	}

}
