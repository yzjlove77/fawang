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
            banners.add(new Banner("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487221110004&di=d6043e4b0c90ddf3ea5096c3d8eb8f58&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2014%2F067%2F5116EPAUD762_1000x500.jpg"));
            banners.add(new Banner("http://p0.so.qhimgs1.com/t01ef541809e45ccbec.jpg"));
            banners.add(new Banner("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490438881557&di=e61065ccc8d7b44591e1c4ba8df672ee&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F18d8bc3eb13533fa00428309a0d3fd1f41345b24.jpg"));
            banners.add(new Banner("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490440243430&di=6f8d7c608a4e3fbe4130c93fc0f20850&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201509%2F09%2F20150909184342_mkrWc.jpeg"));
            banners.add(new Banner("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490440556037&di=ade75ba29126922124b063a2a57873f7&imgtype=0&src=http%3A%2F%2Fi2.download.fd.pchome.net%2Ft_960x600%2Fg1%2FM00%2F0E%2F05%2FooYBAFTbGOmIDPSLAAXPs6l7AQMAACSVgDyBqkABc_L421.jpg"));
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
