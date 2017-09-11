package com.android.wx.french.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.base.BasePresenter;

import butterknife.Bind;

/**
 * Created by wxZhang on 2017/8/11.
 * 意见反馈
 *
 */

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {

        leftImg.setOnClickListener(this);
        titleTv.setText(R.string.setting_yijian);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebar_left:
                finish();
                break;
        }
    }
}
