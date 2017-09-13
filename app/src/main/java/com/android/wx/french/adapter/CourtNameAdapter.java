package com.android.wx.french.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.model.CourtNamebean;

import java.util.List;

/**
 * 法院名称
 * */
public class CourtNameAdapter extends BaseAdapter{

	private Context context;
	private List<CourtNamebean.CourtName> list;
	public int selectedPosition;
	public CourtNameAdapter(Context context, List<CourtNamebean.CourtName> list){
		
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
			convertView = LayoutInflater.from(context).inflate(R.layout.rightlistview_courtname, parent, false);
			houdle.mTextView = (TextView) convertView .findViewById(R.id.right_listview_courtnametv);
			convertView.setTag(houdle);
		}else {
			houdle = (ViewHoudle) convertView.getTag();
		}
		
		houdle.mTextView.setText(list.get(position).fymc);
		
		if (selectedPosition == position) {
			houdle.mTextView.setSelected(true);
			houdle.mTextView.setPressed(true);
			houdle.mTextView.setTextColor(0xff0085cf);
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			houdle.mTextView.setSelected(false);
			houdle.mTextView.setPressed(false);
			houdle.mTextView.setTextColor(0xff000000);
			convertView.setBackgroundColor(Color.WHITE);
		}
		
		return convertView;
		
	}

	class ViewHoudle{
		
		private TextView mTextView;
		
	}
	
	
}
