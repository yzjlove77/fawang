package com.android.wx.french.widget.popupwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.PopupWindow;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/3.
 */

public abstract class BasePopupWindow {

    protected PopupWindow mPopupWindow;
    protected View mPopupView, mAnimView, mDismissView;
    protected Activity mContext;

    protected Animation showAnimation;

    protected OnClickBtnListener onClickBtnListener;

    public void setOnClickBtnListener(OnClickBtnListener onClickBtnListener) {
        this.onClickBtnListener = onClickBtnListener;
    }

    public BasePopupWindow(Activity mContext) {
        initView(mContext, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public BasePopupWindow(Activity mContext, int w, int h) {
        initView(mContext, w, h);
    }

    private void initView(Activity mContext, int w, int h) {
        this.mContext = mContext;
        mPopupView = getPopupView();
        ButterKnife.bind(this, mPopupView);
        mAnimView = getAnimView();

        mPopupWindow = new PopupWindow(mPopupView, w, h);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(0);

        showAnimation = getShowAnimation();
    }

    public void showPopupWindow() {
        try2ShowPopup(0, null);
    }

    public void showPopupWindow(int res) {
        try2ShowPopup(res, null);
    }

    public void showPopupWindow(View v) {
        try2ShowPopup(0, v);
    }

    private void try2ShowPopup(int res, View v) {
        //传递了view
        if (res == 0 && v != null) {
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] + v.getHeight());
        }
        //传递了res
        if (res != 0 && v == null) {
            mPopupWindow.showAtLocation(mContext.findViewById(res), Gravity.CENTER, 0, 0);
        }
        //什么都没传递，取顶级view的id
        if (res == 0 && v == null) {
            mPopupWindow.showAtLocation(mContext.findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
        }
        if (showAnimation != null && mAnimView != null) {
            mAnimView.clearAnimation();
            mAnimView.startAnimation(showAnimation);
        }
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public abstract View getPopupView();

    public abstract View getAnimView();

    public abstract Animation getShowAnimation();

    public interface OnClickBtnListener {
        void onClickBtn(View view);
    }
}
