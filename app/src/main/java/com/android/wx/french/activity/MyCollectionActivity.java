package com.android.wx.french.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.adapter.MyTaskAdapter;
import com.android.wx.french.adapter.OnClickItemListener;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.khc.bean.CollectBean;
import com.android.wx.french.khc.bean.CollectData;
import com.android.wx.french.model.GetRewardBean;
import com.android.wx.french.model.GetRewardData;
import com.android.wx.french.util.RecycleViewDivider;
import com.android.wx.french.util.SharePreferenceHelper;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/15.
 * 我的收藏
 */

public class MyCollectionActivity extends BaseActivity implements OnClickItemListener {

    @Bind(R.id.mycollection_refresh)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.titlebar_left)
    ImageView titlebarLeft;
    @Bind(R.id.titlebar_name)
    TextView titlebarName;
    @Bind(R.id.titlebar_rigth)
    TextView titlebarRigth;

    MyTaskAdapter adapter;
    @Bind(R.id.rv_collect)
    RecyclerView rvCollect;
    private ArrayList<GetRewardData> getRewardDatas;
    private LinearLayoutManager mLayoutManager;
    private int pager;

    protected SharePreferenceHelper sph;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_mycollection;
    }

    @Override
    protected void initView() {
        titlebarName.setText("我的任务");

        refreshLayout.startRefresh();
        SinaRefreshView headerView = new SinaRefreshView(this);
        refreshLayout.setHeaderView(headerView);
        BallPulseView boomView = new BallPulseView(this);
        boomView.setAnimatingColor(getResources().getColor(R.color.maincolor));
        refreshLayout.setBottomView(boomView);
        refreshLayout.setAutoLoadMore(true);//设置底部自动刷新
        setRefreshLayouts();

        getRewardDatas = new ArrayList<>();
        adapter = new MyTaskAdapter(mContext, getRewardDatas);
        mLayoutManager = new LinearLayoutManager(this);
        rvCollect.setLayoutManager(mLayoutManager);
        rvCollect.setAdapter(adapter);
        rvCollect.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        adapter.notifyDataSetChanged();
        adapter.setOnClickItemListener(this);
    }


    private void setRefreshLayouts() {

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pager = 1;
                getRewardData(refreshLayout, "onRefresh");
            }
        });

    }

    @OnClick(R.id.titlebar_left)
    public void onClick() {
        finish();
    }


    @Override
    public void onClickItem(View view, int position) {
        GetRewardData getRewardData = getRewardDatas.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("rewardData", getRewardData);
        startActivity(new Intent(mContext, RewardDetailActivity.class).putExtras(bundle));
    }

    private void getRewardData(TwinklingRefreshLayout refreshLayout,String pull) {
        sph = SharePreferenceHelper.getInstance(mContext);
        CollectData collectBean = new CollectData();
        collectBean.setUser_type(2);
        collectBean.setUserid(sph.getIdCard());

        ArrayList<String> cols = new ArrayList<>();
        cols.add("flow_with_task_id");
        cols.add("flow_with_task_title");

        CollectBean bean = new CollectBean();
        bean.setRequestMethod("getMyAttentionById");
        bean.setData(collectBean);
        bean.setCols(cols);
        bean.setCurPage("" + pager);
        bean.setPageSize("20");

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
                    getRewardDatas.clear();
                    refreshLayout.finishRefreshing();
                }else{
                    refreshLayout.finishLoadmore();
                }
                GetRewardBean bean = Helper.jsonToBean(json, GetRewardBean.class);
                int totalRows = bean.getTotalRows();
                if (totalRows <= 0) {
                    showToast("当前没有收藏的悬赏任务");
                    return;
                }
                ArrayList<GetRewardData> getRewardDatas = bean.getData();
                MyCollectionActivity.this.getRewardDatas.addAll(getRewardDatas);
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
}
