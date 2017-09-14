package com.android.wx.french.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.wx.french.R;
import com.android.wx.french.adapter.BountyHunterAdapter;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.khc.bean.BountyMsg;
import com.android.wx.french.khc.bean.BountyMsgData;
import com.android.wx.french.khc.function.Util;
import com.android.wx.french.model.RewardBean;
import com.android.wx.french.model.RewardData;
import com.android.wx.french.util.RecycleViewDivider;
import com.android.wx.french.view.BountyHunterView;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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

    private int pager;
    private ArrayList<BountyMsgData> list = new ArrayList<>();

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
            adapter = new BountyHunterAdapter(context,list);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 10, R.color.secendcolor));
            adapter.notifyDataSetChanged();

        }
    }

    private void getListData(TwinklingRefreshLayout refreshLayout,String pull) {
        RewardData rewardData = new RewardData();

        ArrayList<String> cols = new ArrayList<>();
        cols.add("ranking");
        cols.add("total");
        cols.add("totalMoney");
        cols.add("validtotal");
        cols.add("report_man_name");
        cols.add("report_man_idcard");

        RewardBean bean = new RewardBean();
        bean.setRequestMethod("getBountyRank");
        bean.setData(rewardData);
        bean.setCols(cols);
        bean.setCurPage("" + pager);
        bean.setPageSize("10");

        RequestParams params = new RequestParams("UTF-8");
        try {
            params.setBodyEntity(new StringEntity(new Gson().toJson(bean),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("---vi---",new Gson().toJson(bean));
        Helper.Post(Helper.getListGrid, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                Log.i("---json---",json);
                if (pull.equals("onRefresh")){
                    list.clear();
                    refreshLayout.finishRefreshing();
                }else{
                    refreshLayout.finishLoadmore();
                }
                BountyMsg bean = Helper.jsonToBean(json, BountyMsg.class);
                int totalRows = bean.getTotalRows();
                if (totalRows <= 0) {
                    Util.showToast(context,"暂时没有人上榜");

                }
                ArrayList<BountyMsgData> dataList = bean.getData();
                list.addAll(dataList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String json) {
                if (pull.equals("onRefresh")){
                    refreshLayout.finishRefreshing();
                }else{
                    pager--;
                    refreshLayout.finishLoadmore();
                }
            }
        });
    }


    private void setRefreshLayouts(){
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pager ++;
                        getListData(refreshLayout, "load");
                    }
                },2000);
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pager = 1;
                        getListData(refreshLayout, "onRefresh");
                    }
                },2000);
            }
        });
    }


}
