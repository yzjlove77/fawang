package com.android.wx.french.khc.function;

import android.content.Context;
import android.os.Handler;

import com.android.wx.french.R;
import com.android.wx.french.khc.pro.IRefresh;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class Refresh {

    Context context;
    TwinklingRefreshLayout refreshLayout;
    IRefresh iRefresh;

    public Refresh(Context context, TwinklingRefreshLayout refreshLayout, IRefresh iRefresh) {
        this.context = context;
        this.refreshLayout = refreshLayout;
        this.iRefresh = iRefresh;
    }

    public void setRefreshLayouts() {
        refreshLayout.startRefresh();
        SinaRefreshView headerView = new SinaRefreshView(context);
        refreshLayout.setHeaderView(headerView);
        BallPulseView boomView = new BallPulseView(context);
        boomView.setAnimatingColor(context.getResources().getColor(R.color.maincolor));
        refreshLayout.setBottomView(boomView);
        refreshLayout.setAutoLoadMore(true);//设置底部自动刷新

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iRefresh.load();
                    }
                },2000);
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iRefresh.refresh();
                    }
                },2000);
            }
        });
    }
}
