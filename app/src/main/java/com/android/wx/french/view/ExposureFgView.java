package com.android.wx.french.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by wxZhang on 2017/8/9.
 */

public interface ExposureFgView extends TitleBarView{

    RecyclerView getRecyclerView();
    LinearLayoutManager getLayoutManager();

}
