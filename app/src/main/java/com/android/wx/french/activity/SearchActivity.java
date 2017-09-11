package com.android.wx.french.activity;

import android.view.View;
import android.widget.ImageView;

import com.android.wx.french.R;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.base.BasePresenter;

import butterknife.Bind;

/**
 * Created by wxZhang on 2017/8/14.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.search_finish)
    ImageView imageView;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }


}
