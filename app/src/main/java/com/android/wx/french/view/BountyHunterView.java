package com.android.wx.french.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

/**
 * Created by wxZhang on 2017/8/14.
 */

public interface BountyHunterView {

    RecyclerView getRecycleView();
    LinearLayoutManager getLayoutManager();
    TwinklingRefreshLayout getRefreshLayout();

}
