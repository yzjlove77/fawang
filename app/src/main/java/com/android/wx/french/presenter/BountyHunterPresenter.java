package com.android.wx.french.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.wx.french.R;
import com.android.wx.french.adapter.BountyHunterAdapter;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.util.RecycleViewDivider;
import com.android.wx.french.view.BountyHunterView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

/**
 * Created by Administrator on 2017/8/14.
 */

public class BountyHunterPresenter extends BasePresenter<BountyHunterView> {

    private Context context;
    private BountyHunterView bountyHunterView;
    private BountyHunterAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TwinklingRefreshLayout refreshLayout;

    public BountyHunterPresenter(Context context){
        this.context = context;
    }


    public void setData(){

        bountyHunterView = getView();

        if (bountyHunterView!=null){

            recyclerView = bountyHunterView.getRecycleView();
            layoutManager = bountyHunterView.getLayoutManager();
            refreshLayout = bountyHunterView.getRefreshLayout();

            refreshLayout.startRefresh();
            SinaRefreshView headerView = new SinaRefreshView(context);
            refreshLayout.setHeaderView(headerView);
            BallPulseView boomView = new BallPulseView(context);
            boomView.setAnimatingColor(context.getResources().getColor(R.color.maincolor));
            refreshLayout.setBottomView(boomView);
            refreshLayout.setAutoLoadMore(true);//设置底部自动刷新
            setRefreshLayouts();
            adapter = new BountyHunterAdapter(context);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 10, R.color.secendcolor));
            adapter.notifyDataSetChanged();


        }
    }

    private void setRefreshLayouts(){


        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                },2000);
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                },2000);
            }
        });
    }


}
