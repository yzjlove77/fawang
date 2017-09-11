package com.android.wx.french.base;

import com.android.wx.french.api.ApiFactory;
import com.android.wx.french.api.ApiFrench;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by wxZhang on 2017/8/8.
 */

public abstract class BasePresenter<V>{

    protected Reference<V> mViewRef;

    public static final ApiFrench frenchApi = ApiFactory.getFrenchApiSingleton();

    public void attachView(V view){
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView(){
        return mViewRef.get();
    }

    public boolean isViewAttached(){
        return mViewRef != null&&mViewRef.get()!=null;
    }

    public void detachView(){
        if(mViewRef!=null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
