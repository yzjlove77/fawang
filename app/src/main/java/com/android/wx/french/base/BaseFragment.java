package com.android.wx.french.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.wx.french.util.SharePreferenceHelper;

import butterknife.ButterKnife;

/**
 * Created by wxZhang on 2017/8/9.
 */

public abstract class BaseFragment <V, T extends BasePresenter<V>> extends Fragment {

    protected T mPresenter;
    protected Activity mContext;
    protected SharePreferenceHelper sph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(createPresenter()!=null) {
            mPresenter = createPresenter();
            mPresenter.attachView((V) this);
        }
        mContext = getActivity();
        sph = SharePreferenceHelper.getInstance(mContext);
//        mPresenter = createPresenter();
//        mPresenter.attachView((V) this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(createViewLayoutId(),container,false);
        ButterKnife.bind(this,rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract T createPresenter();//实现Presenter

    protected abstract int createViewLayoutId();//创建Fragment布局

    protected  void initView(View rootView){}//初始化view


}
