package com.android.wx.french.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wx.french.R;
import com.android.wx.french.activity.SearchActivity;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.view.HomeFgView;
import com.bumptech.glide.Glide;
import com.loonggg.rvbanner.lib.RecyclerViewBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxZhang on 2017/8/9.
 */

public class HomeFgPresenter  extends BasePresenter<HomeFgView>{

    private Context context;
    private RecyclerViewBanner recyclerViewBanner;
    private HomeFgView homeFgView;
    private ImageView leftImg;
    private TextView titleName;
    private TextView mRightTv;
    private Button searchBtn;

    public HomeFgPresenter(Context context){
        this.context = context;
    }

    public void getTitleBar(){
        homeFgView = getView();
        if (homeFgView!=null){
            leftImg = homeFgView.getLeftImg();
            titleName = homeFgView.getTitlename();
            mRightTv = homeFgView.getRightTv();
            searchBtn = homeFgView.getSearchBtn();
            leftImg.setVisibility(View.GONE);
            titleName.setText(R.string.tab_home);
            mRightTv.setVisibility(View.GONE);
            searchBtn.setOnClickListener(listener);
        }
    }


    public void getViewPageData(){

        homeFgView = getView();
        if (homeFgView!=null){
            recyclerViewBanner = homeFgView.getViewPager();
            final List<Banner> banners = new ArrayList<>();
            banners.add(new Banner("http://116.62.36.84/lunbotu/1.png"));
            banners.add(new Banner("http://116.62.36.84/lunbotu/2.png"));
            banners.add(new Banner("http://116.62.36.84/lunbotu/3.png"));
            banners.add(new Banner("http://116.62.36.84/lunbotu/4.png"));
            recyclerViewBanner.setRvBannerData(banners);
            recyclerViewBanner.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
                @Override
                public void switchBanner(int position, AppCompatImageView bannerView) {

                    Glide.with(bannerView.getContext())
                            .load(banners.get(position).getUrl())
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .thumbnail(0.1f)
                            .into(bannerView);
                }
            });
            recyclerViewBanner.setOnRvBannerClickListener(new RecyclerViewBanner.OnRvBannerClickListener() {
                @Override
                public void onClick(int position) {
                    Toast.makeText(context, "position: " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, SearchActivity.class));
        }
    };

    private class Banner {

        String url;

        public Banner(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}
