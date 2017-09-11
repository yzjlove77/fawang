package com.android.wx.french.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.base.BasePresenter;

import butterknife.Bind;

/**
 * Created by wxZhang on 2017/8/11.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;
    @Bind(R.id.setting_yijian_btn)
    Button advBtn;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {

        leftImg.setOnClickListener(this);
        titleTv.setText(R.string.setting_name);
advBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

           case R.id.titlebar_left:
            finish();
            break;

            case R.id.setting_yijian_btn:
startActivity(new Intent(this,FeedbackActivity.class));
                break;

        }
    }
}
