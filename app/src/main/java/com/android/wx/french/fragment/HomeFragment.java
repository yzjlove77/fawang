package com.android.wx.french.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wx.french.R;
import com.android.wx.french.activity.RewardDetailActivity;
import com.android.wx.french.adapter.HomeAdapter;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BaseFragment;
import com.android.wx.french.custom.DropdownButton;
import com.android.wx.french.custom.DropdownItemObject;
import com.android.wx.french.custom.DropdownListView;
import com.android.wx.french.model.GetRewardBean;
import com.android.wx.french.model.GetRewardData;
import com.android.wx.french.model.NearDatabean;
import com.android.wx.french.model.RewardBean;
import com.android.wx.french.model.RewardData;
import com.android.wx.french.presenter.HomeFgPresenter;
import com.android.wx.french.util.RecycleViewDivider;
import com.android.wx.french.view.HomeFgView;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lidroid.xutils.http.RequestParams;
import com.loonggg.rvbanner.lib.RecyclerViewBanner;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wxZhang on 2017/8/9.
 * 首页
 *
 */

public class HomeFragment extends BaseFragment<HomeFgView,HomeFgPresenter> implements HomeFgView, HomeAdapter.OnClickItemListener {

    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;
    @Bind(R.id.titlebar_rigth)
    TextView mRigthTv;
    @Bind(R.id.rv_banner)
    RecyclerViewBanner recyclerViewBanner;
    @Bind(R.id.home_search_btn)
    Button searchBtn;
    @Bind(R.id.home_mRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.home_pull_refresh)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.home_appbarlayout)
    AppBarLayout appBarLayout;

    private View mask;
    private DropdownButton chooseType, chooseLabel, chooseOrder,chooseLast;
    private DropdownListView dropdownType, dropdownLabel, dropdownOrder,typeDownLable;
    private Animation dropdown_in, dropdown_out, dropdown_mask_out;
    private List<String> typeAll;
    private List<String> labelAll;
    private List<String> orderAll;
    private List<String> lastAll;
    private HomeAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private DropdownButtonsController dropdownButtonsController = new DropdownButtonsController();
    private int pager;
    private ArrayList<GetRewardData> getRewardDatas;

    @Override
    protected HomeFgPresenter createPresenter() {
        return new HomeFgPresenter(getContext());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPresenter.getViewPageData();

    }

    @Override
    protected void initView(View rootView) {

        mPresenter.getTitleBar();
        mask = rootView.findViewById(R.id.mask);

        refreshLayout.startRefresh();
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        refreshLayout.setHeaderView(headerView);
        BallPulseView boomView = new BallPulseView(getContext());
        boomView.setAnimatingColor(getResources().getColor(R.color.maincolor));
        refreshLayout.setBottomView(boomView);
        refreshLayout.setAutoLoadMore(true);//设置底部自动刷新
        setRefreshLayouts();

        chooseType = (DropdownButton) rootView.findViewById(R.id.nearButton);
        chooseLabel = (DropdownButton) rootView.findViewById(R.id.chooseLabel);
        chooseOrder = (DropdownButton) rootView.findViewById(R.id.choosetype);
        chooseLast = (DropdownButton) rootView.findViewById(R.id.chooseOrder);
        //dropdownType, dropdownLabel, dropdownOrder
        dropdownType = (DropdownListView) rootView.findViewById(R.id.dropdownnear);
        dropdownLabel = (DropdownListView) rootView.findViewById(R.id.dropdownLabeltime);
        dropdownOrder = (DropdownListView) rootView.findViewById(R.id.dropdownmoney);
        typeDownLable = (DropdownListView) rootView.findViewById(R.id.dropdowntype);

        dropdown_in = AnimationUtils.loadAnimation(getContext(),R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(getContext(),R.anim.dropdown_out);
        dropdown_mask_out = AnimationUtils.loadAnimation(getContext(),R.anim.dropdown_mask_out);

        dropdownButtonsController.init();
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownButtonsController.hide();
            }
        });

        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        getRewardDatas = new ArrayList<>();
        adapter = new HomeAdapter(getActivity(), getRewardDatas);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnClickItemListener(this);

    }

    private void setRefreshLayouts() {

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                pager++;
                getRewardData(refreshLayout, "onLoadMore");
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pager = 1;
                getRewardData(refreshLayout, "onRefresh");
            }
        });

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
        cols.add("bzxr_adress_lng");
        cols.add("bzxr_adress_lat");
        cols.add("bzxlx");

        RewardBean bean = new RewardBean();
        bean.setRequestMethod("getTask");
        bean.setData(rewardData);
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
                    Toast.makeText(getActivity(), "当前没有悬赏任务", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<GetRewardData> getRewardDatas = bean.getData();
                HomeFragment.this.getRewardDatas.addAll(getRewardDatas);
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

    @Override
    public RecyclerView getRecyclerView() {
        return null;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return null;
    }

    @Override
    public RecyclerViewBanner getViewPager() {
        return recyclerViewBanner;
    }

    @Override
    public Button getSearchBtn() {
        return searchBtn;
    }

    @Override
    public ImageView getLeftImg() {
        return leftImg;
    }

    @Override
    public TextView getTitlename() {
        return titleTv;
    }

    @Override
    public TextView getRightTv() {
        return mRigthTv;
    }

    @Override
    public void onClickItem(View view, int position) {
        switch (view.getId()) {
            case R.id.item_home_layout:
                GetRewardData getRewardData = getRewardDatas.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("rewardData", getRewardData);
                startActivity(new Intent(mContext, RewardDetailActivity.class).putExtras(bundle));
                break;
        }
    }

    public class DropdownButtonsController implements DropdownListView.Container{

        private DropdownListView currentDropdownList;
        // 全部标签
        private List<DropdownItemObject> datasetAllLabel;
        // 全部分类
        private List<DropdownItemObject> datasetType;
        // 地区
        private List<DropdownItemObject> datasetMyLabel;
        // 排序
        private List<DropdownItemObject> datasetOrder;
        //类别
        private List<DropdownItemObject> lastOrder;


        @Override
        public void show(DropdownListView view) {
            if (currentDropdownList != null) {
                currentDropdownList.clearAnimation();
                currentDropdownList.startAnimation(dropdown_out);
                currentDropdownList.setVisibility(View.GONE);
                currentDropdownList.button.setChecked(false);
                appBarLayout.setExpanded(false);
            }
            appBarLayout.setExpanded(false);
            currentDropdownList = view;
            mask.clearAnimation();
            mask.setVisibility(View.VISIBLE);
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_in);
            currentDropdownList.setVisibility(View.VISIBLE);
            currentDropdownList.button.setChecked(true);

            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (verticalOffset >= 0) {
                        refreshLayout.setEnabled(true);
                    } else {
                        refreshLayout.setEnabled(false);
                    }
                }
            });
        }

        @Override
        public void hide() {
            if (currentDropdownList != null) {
                currentDropdownList.clearAnimation();
                currentDropdownList.startAnimation(dropdown_out);
                currentDropdownList.button.setChecked(false);
                mask.clearAnimation();
                mask.startAnimation(dropdown_mask_out);
            }
            currentDropdownList = null;
        }

        @Override
        public void onSelectionChanged(DropdownListView view) {
            String Content = view.button.textView.getText().toString();
            updateLabels(getCurrentLabels());

            //分类点击
            if (view == dropdownType) {
                chooseType.setText(Content);
Log.i("---near--","-----");
                NearDatabean bean = new NearDatabean();
                bean.setRequestMethod("getNearBzxrxxByJbrzb");

                NearDatabean.DataBean bean1 = new NearDatabean.DataBean();
                bean1.setBzxr_adress_lat("30.259244");
                bean1.setBzxr_adress_lng("120.219375");
                bean.setData(bean1);

                RequestParams params = new RequestParams("UTF-8");
                try {
                    params.setBodyEntity(new StringEntity(new Gson().toJson(bean),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Helper.Post(Helper.post1, params, new OnHandleCallback() {
                    @Override
                    public void onSuccess(String json) {
                        Log.i("---near---", json);

//                        if (pull.equals("onRefresh")){
                            getRewardDatas.clear();
                            refreshLayout.finishRefreshing();
//                        }else{
//                            refreshLayout.finishLoadmore();
//                        }
                        GetRewardBean bean = Helper.jsonToBean(json, GetRewardBean.class);
                        int totalRows = bean.getTotalRows();
                        if (totalRows <= 0) {
                            Toast.makeText(getActivity(), "附近没有悬赏任务", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ArrayList<GetRewardData> getRewardDatas = bean.getData();
                        HomeFragment.this.getRewardDatas.addAll(getRewardDatas);
                        adapter.notifyDataSetChanged();


//                    try {
//                        JSONObject sj = new JSONObject(json);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                    }

                    @Override
                    public void onFailure(String json) {

                    }
                });


            }
            // 地区点击
            else if (view == dropdownLabel) {
                chooseLabel.setText(Content);
            }
            // 排序点击
            else if (view == dropdownOrder) {
                chooseOrder.setText(Content);
            }
            //点击类别
            else if(view == typeDownLable){
                chooseLast.setText(Content);
            }
        }

        void reset() {
            chooseType.setChecked(false);
            chooseLabel.setChecked(false);
            chooseOrder.setChecked(false);
            chooseLast.setChecked(false);

            dropdownType.setVisibility(View.GONE);
            dropdownLabel.setVisibility(View.GONE);
            dropdownOrder.setVisibility(View.GONE);
            typeDownLable.setVisibility(View.GONE);

            mask.setVisibility(View.GONE);

            dropdownType.clearAnimation();
            dropdownLabel.clearAnimation();
            dropdownOrder.clearAnimation();
            typeDownLable.clearAnimation();

            mask.clearAnimation();

        }

        void init() {
            reset();







            typeAll = new ArrayList<String>();
            typeAll.add(0, "附近");
            typeAll.add(1, "西溪街道");
            typeAll.add(2, "莫干山路");
            labelAll = new ArrayList<String>();
            labelAll.add(0, "时间");
            labelAll.add(1, "一小时前");
            labelAll.add(2, "一周前");
            labelAll.add(3, "一月前");
            labelAll.add(4, "一年前");
            orderAll = new ArrayList<String>();
            orderAll.add(0, "金额");
            orderAll.add(1, "100");
            orderAll.add(2, "1000");
            orderAll.add(3, "10000");

            lastAll = new ArrayList<>();
            lastAll.add(0, "类别");
            lastAll.add(1, "全部");
            lastAll.add(2, "个人发布");
            lastAll.add(3, "机构发布");
            lastAll.add(4, "寻人悬赏");
            lastAll.add(5, "寻物悬赏");


            // 全部标签
            datasetAllLabel = new ArrayList<DropdownItemObject>();
            // 全部分类
            datasetType = new ArrayList<DropdownItemObject>();
            // 地区
            datasetMyLabel = new ArrayList<DropdownItemObject>();
            // 排序
            datasetOrder = new ArrayList<DropdownItemObject>();
            // 排序
            lastOrder = new ArrayList<DropdownItemObject>();

            for (int i = 0; i < typeAll.size(); i++) {
                datasetType.add(new DropdownItemObject(typeAll.get(i), i,
                        typeAll.get(i)));
            }
            dropdownType.bind(datasetType, chooseType, this, 0);

            for (int i = 0; i < labelAll.size(); i++) {
                datasetMyLabel.add(new DropdownItemObject(labelAll.get(i), i,
                        labelAll.get(i)));
            }
            dropdownLabel.bind(datasetMyLabel, chooseLabel, this, 0);

            for (int i = 0; i < orderAll.size(); i++) {
                datasetOrder.add(new DropdownItemObject(orderAll.get(i), i,
                        orderAll.get(i)));
            }

            dropdownOrder.bind(datasetOrder, chooseOrder, this, 0);

            for (int i = 0; i < lastAll.size(); i++) {
                lastOrder.add(new DropdownItemObject(lastAll.get(i), i,
                        lastAll.get(i)));
            }
            typeDownLable.bind(lastOrder,chooseLast,this,0);

            dropdown_mask_out
                    .setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (currentDropdownList == null) {
                                reset();
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
        }

        private List<DropdownItemObject> getCurrentLabels() {
            return datasetAllLabel;
        }

        void updateLabels(List<DropdownItemObject> targetList) {
            if (targetList == getCurrentLabels()) {
                datasetAllLabel = targetList;
            }
        }
    }

}
