package com.android.wx.french.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.presenter.BountyHunterPresenter;
import com.android.wx.french.view.BountyHunterView;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/8/14.
 * 赏金猎人排行版
 */

public class BountyHunterActivity extends BaseActivity <BountyHunterView,BountyHunterPresenter> implements BountyHunterView, View.OnClickListener {

    @Bind(R.id.bountyhunter_recycler)
    RecyclerView mRecyclerView;
    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;
    private LinearLayoutManager mLayoutManager;
    @Bind(R.id.bounty_refresh)
    TwinklingRefreshLayout refreshLayout;

    @Override
    protected BountyHunterPresenter createPresenter() {
        return new BountyHunterPresenter(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_bountyhunter;
    }

    @Override
    protected void initView() {
        leftImg.setOnClickListener(this);
        titleTv.setText("赏金猎人排行榜");
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void initData() {
        mPresenter.setData();
    }

    @Override
    public RecyclerView getRecycleView() {
        return mRecyclerView;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @Override
    public TwinklingRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    @Override
    public void onClick(View v) {
        finish();
    }


}
