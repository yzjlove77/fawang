package com.android.wx.french.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.wx.french.util.SharePreferenceHelper;

import butterknife.ButterKnife;

/**
 * Created by wxZhang on 2017/8/8.
 */

public abstract class BaseActivity <V, T extends BasePresenter<V>> extends AppCompatActivity {

    protected T mPresenter;
    protected Activity mContext;
    protected SharePreferenceHelper sph;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(createPresenter()!=null) {
            mPresenter = createPresenter();
            mPresenter.attachView((V) this);
        }
        mContext = this;
        setContentView(initLayout());//初始化布局
        sph = SharePreferenceHelper.getInstance(mContext);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null) {
            mPresenter.detachView();
        }
    }

    protected abstract T createPresenter();//实现MVP模式

    abstract protected int initLayout();//用于引入布局文件

    protected  void initView(){}//初始化view

    protected  void initData(){}//初始化数据

    protected void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
