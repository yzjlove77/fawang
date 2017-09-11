package com.android.wx.french.activity;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.adapter.MyreportAdapter;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.util.RecycleViewDivider;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import butterknife.Bind;

/**
 * Created by wxZhang on 2017/8/15.
 * 我的举报
 */

public class MyreportActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.bountyhunter_recycler)
    RecyclerView mRecyclerView;
    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;
    private LinearLayoutManager mLayoutManager;
    @Bind(R.id.bounty_refresh)
    TwinklingRefreshLayout refreshLayout;

    MyreportAdapter adapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
//        return R.layout.activity_myreport;
        return R.layout.activity_bountyhunter;
    }

    @Override
    protected void initView() {

        leftImg.setOnClickListener(this);
        titleTv.setText("我的举报");

        refreshLayout.startRefresh();
        SinaRefreshView headerView = new SinaRefreshView(this);
        refreshLayout.setHeaderView(headerView);
        BallPulseView boomView = new BallPulseView(this);
        boomView.setAnimatingColor(getResources().getColor(R.color.maincolor));
        refreshLayout.setBottomView(boomView);
        refreshLayout.setAutoLoadMore(true);//设置底部自动刷新
        setRefreshLayouts();
        adapter = new MyreportAdapter();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        adapter.notifyDataSetChanged();

    }

    private void setRefreshLayouts() {

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                },2000);
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                },2000);
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
