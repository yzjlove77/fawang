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
import com.android.wx.french.model.GetRewardBean;
import com.android.wx.french.model.GetRewardData;
import com.android.wx.french.model.RewardBean;
import com.android.wx.french.model.RewardData;
import com.android.wx.french.util.RecycleViewDivider;
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
import butterknife.ButterKnife;
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
//                pager = 1;
//                getRewardData(refreshLayout, "onRefresh");
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
        RewardData rewardData = new RewardData();
//        rewardData.setFbdsr_sfzh(sph.getIdCard());
        rewardData.setFbdsr_sfzh("");
        ArrayList<String> cols = new ArrayList<>();
        cols.add("id");
        cols.add("type_of_task");
        cols.add("is_sqgffb");
        cols.add("is_gyxs");
        cols.add("type_of_task_mc");
        cols.add("title");
        cols.add("reward_details");
        cols.add("reward_amount");
        cols.add("type_of_reward");
        cols.add("hava_been_paid");
        cols.add("audit_court_status");
        cols.add("audit_insurer_status");
        cols.add("bzxr_photo_path");
        cols.add("task_release_time");
        cols.add("task_expiration_time");
        cols.add("task_completion_time");
        cols.add("the_creat_time");
        cols.add("bzxr_idcard");
        cols.add("fbdsr_name");
        cols.add("fbdsr_sjhm");
        cols.add("fbdsr_sfzh");
        cols.add("fb_fymc");
        cols.add("fb_fybm1");
        cols.add("fb_fg_name");
        cols.add("fb_fg_userid");
        cols.add("js_fymc");
        cols.add("js_fybm1");
        cols.add("bzxr_specialty");
        cols.add("bzxr_dt");
        cols.add("bzxr_hj");
        cols.add("task_demand");
        cols.add("completion_status");
        cols.add("bzxr_adress");
        cols.add("bzxr_adress_jwzb");
        cols.add("bzxlx");

        RewardBean bean = new RewardBean();
        bean.setRequestMethod("getTask");
        bean.setData(rewardData);
        bean.setCols(cols);
//        bean.setCurPage("" + pager);
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
//                    pager--;
                    refreshLayout.finishLoadmore();
                }
            }
        });
    }
}
