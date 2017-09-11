package com.android.wx.french.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.loonggg.rvbanner.lib.RecyclerViewBanner;

/**
 * Created by wxZhang on 2017/8/9.
 */

public interface HomeFgView extends TitleBarView{

    RecyclerView getRecyclerView();
    LinearLayoutManager getLayoutManager();
    RecyclerViewBanner getViewPager();
    Button getSearchBtn();



}
