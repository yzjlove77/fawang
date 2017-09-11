package com.android.wx.french.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.view.ExposureFgView;

/**
 * Created by Administrator on 2017/8/9.
 */

public class ExposureFgPresenter extends BasePresenter<ExposureFgView> {

    private TextView titleName;
    private ImageView leftImg;
    private TextView mRightTv;
    private ExposureFgView exposureFgView;


    private Context context;
    public ExposureFgPresenter(Context context){
        this.context = context;
    }


    public void initTitleBar(){
        exposureFgView = getView();
        if (exposureFgView!=null){
            titleName = exposureFgView.getTitlename();
            leftImg = exposureFgView.getLeftImg();
            mRightTv = exposureFgView.getRightTv();
            leftImg.setVisibility(View.INVISIBLE);
            titleName.setText(R.string.expo_name);
            mRightTv.setText(R.string.expo_search);
            Drawable drawable= context.getResources().getDrawable(R.drawable.exposure_search);
            drawable.setBounds(5, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mRightTv.setCompoundDrawables(drawable,null,null,null);//设置图片
        }
    }


}
