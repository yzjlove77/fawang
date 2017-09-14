package com.android.wx.french.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.android.wx.french.R;
import com.android.wx.french.adapter.CourtAdapter;
import com.android.wx.french.adapter.CourtNameAdapter;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.model.CourtNamebean;
import com.android.wx.french.model.GetNoticebean;
import com.lidroid.xutils.http.RequestParams;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wxZhang on 2017/2/22.
 * 法院名称列表
 *
 */

public class CourtNameListPopWin extends PopupWindow implements AdapterView.OnItemClickListener{

    private View conentView;
    private ListView mLeftListView;
    private ListView mRightListView;
    private CourtNameAdapter courtnameadapter;
    private List<GetNoticebean.GetNotice> list = new LinkedList<>();
    private List<CourtNamebean.CourtName> beanlist = new LinkedList<>();
    private CourtAdapter courtadapter;
    private Context mContext;
    private Button mButton;

    public CourtNameListPopWin(final Activity context, List<CourtNamebean.CourtName> beanlists) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        beanlist = beanlists;
        conentView = inflater.inflate(R.layout.courtname_popupwindow, null);
        //courtname_layout
        mLeftListView = (ListView) conentView.findViewById(R.id.songda_leftlistview);
        mRightListView = (ListView) conentView.findViewById(R.id.songda_rightlistview);
        mButton = (Button) conentView.findViewById(R.id.court_pop_dismiss);

        GetNotice();//获取法院名称数据
        //地区
        courtadapter = new CourtAdapter(context, list);
        mLeftListView.setAdapter(courtadapter);
        mLeftListView.setOnItemClickListener(this);
        courtadapter.notifyDataSetChanged();
        //法院
        courtnameadapter = new CourtNameAdapter(context, beanlist);
        mRightListView.setAdapter(courtnameadapter);
        courtnameadapter.notifyDataSetChanged();

        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        this.setHeight(h/9*8);//6*5
        this.setHeight(h/9*10);//6*5
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimationPreview);
//        this.showAsDropDown(parent);

    }

    private void GetNotice() {
        // TODO Auto-generated method stub
        RequestParams params = new RequestParams();
        params.addBodyParameter("sign", "gaoyuan");
        Helper.Post("http://116.62.36.84/Api/index.php/home/people/GetNotice", params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                // TODO Auto-generated method stub
//                Log.i("TAG",json);

                GetNoticebean bean = Helper.jsonToBean(json, GetNoticebean.class);
                list.addAll(bean.data);
                courtadapter.notifyDataSetChanged();
                beanlist.addAll(list.get(0).fy_data);


            }

            @Override
            public void onFailure(String json) {
                // TODO Auto-generated method stub

            }
        });
    }


    public void setmRightListViewOnItem(AdapterView.OnItemClickListener listViewOnItem){
        mRightListView.setOnItemClickListener(listViewOnItem);
    }

    public void setDismissPop(View.OnClickListener listener){
        mButton.setOnClickListener(listener);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        beanlist.clear();
        beanlist.addAll(list.get(i).fy_data);
        //法院名称
        courtnameadapter = new CourtNameAdapter(mContext, beanlist);
        mRightListView.setAdapter(courtnameadapter);
        courtadapter.setSelectedPosition(i);
        courtadapter.notifyDataSetInvalidated();
        courtnameadapter.notifyDataSetChanged();

    }


    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            if (Build.VERSION.SDK_INT < 24) {
                this.showAsDropDown(parent);
            } else {
                int[] location = new int[2];
                parent.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                this.showAtLocation(parent, Gravity.NO_GRAVITY, 0, y + parent.getHeight());
            }
//            this.showAsDropDown(parent, parent.getLayoutParams().width, parent.getLayoutParams().height);
        } else {
            this.dismiss();
        }
    }
}
