package com.android.wx.french.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.adapter.MyFriendAdapter;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.khc.GetFriendList;
import com.android.wx.french.khc.bean.GetFriendMsgData;
import com.android.wx.french.khc.function.Refresh;
import com.android.wx.french.khc.pro.IGetFriend;
import com.android.wx.french.khc.pro.IRefresh;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class MyFriendActivity extends BaseActivity implements View.OnClickListener, IRefresh, IGetFriend {
    @Bind(R.id.titlebar_left)
    ImageView titlebarLeft;
    @Bind(R.id.titlebar_name)
    TextView titlebarName;
    @Bind(R.id.titlebar_rigth)
    TextView titlebarRigth;
    @Bind(R.id.lv_friend)
    ListView lvFriend;
    @Bind(R.id.myfriend_refresh)
    TwinklingRefreshLayout refreshLayout;

    int pager;
    GetFriendList getFriendList;
    String pull;
    MyFriendAdapter adapter;
    private ArrayList<GetFriendMsgData> friendList = new ArrayList<>();


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_myfriend;
    }

    @Override
    protected void initView() {
        titlebarLeft.setOnClickListener(this);
        titlebarName.setText("我的朋友");

        getFriendList = new GetFriendList(this,this);
        Refresh refresh = new Refresh(this, refreshLayout,this);
        refresh.setRefreshLayouts();

    }


    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void refresh() {
        pager = 1;
        pull = "onRefresh";
        getFriendList.getFriendList(pager);
    }

    @Override
    public void load() {
        pager ++;
        pull = "load";
        getFriendList.getFriendList(pager);
    }

    @Override
    public void successGetFriend(int totalRows,ArrayList<GetFriendMsgData> list) {
        if (totalRows <= 0) {
            showToast("还没有任何好友");
            refreshLayout.finishRefreshing();
        }
        else {
            if (pull.equals("onRefresh")) {
                friendList.clear();
                refreshLayout.finishRefreshing();
            } else {
                refreshLayout.finishLoadmore();
            }
            friendList.addAll(list);
            if (adapter == null) {
                adapter = new MyFriendAdapter(this,this, friendList);
                lvFriend.setAdapter(adapter);
            } else {
                adapter.changeCount(friendList.size());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void failureGetFriend(String msg) {
        if (pull.equals("onRefresh")){
            refreshLayout.finishRefreshing();
        }else{
            pager--;
            refreshLayout.finishLoadmore();
        }
        showToast(msg);
    }
}
