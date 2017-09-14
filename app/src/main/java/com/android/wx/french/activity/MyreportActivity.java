package com.android.wx.french.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.adapter.MyreportAdapter;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.model.GetMyReportData;
import com.android.wx.french.model.GetMyreportBean;
import com.android.wx.french.util.MLog;
import com.android.wx.french.util.RecycleViewDivider;
import com.google.gson.Gson;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wxZhang on 2017/8/15.
 * 我的举报
 */

public class MyreportActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.bountyhunter_recycler)
    RecyclerView mRecyclerView;
    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;
    @Bind(R.id.titlebar_rigth)
    TextView titleRight;

    private LinearLayoutManager mLayoutManager;
    @Bind(R.id.bounty_refresh)
    TwinklingRefreshLayout refreshLayout;

    private int pager =1;
    MyreportAdapter adapter;

    /**
     * 时间选择
     */
    TimePickerDialog mDialogAll;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;

    private List<GetMyReportData.DataBean> list = new LinkedList<>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
//        return R.layout.activity_myreport;
        return R.layout.activity_bountyhunter;
    }

    @Override
    protected void initView() {

        leftImg.setOnClickListener(this);
        titleRight.setOnClickListener(this);
        titleTv.setText("我的举报");
titleRight.setText("筛选");
        refreshLayout.startRefresh();
        SinaRefreshView headerView = new SinaRefreshView(this);
        refreshLayout.setHeaderView(headerView);
        BallPulseView boomView = new BallPulseView(this);
        boomView.setAnimatingColor(getResources().getColor(R.color.maincolor));
        refreshLayout.setBottomView(boomView);
        refreshLayout.setAutoLoadMore(true);//设置底部自动刷新
        setRefreshLayouts();
        adapter = new MyreportAdapter(list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        adapter.notifyDataSetChanged();

    }

    private void setRefreshLayouts() {

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                pager++;
                getReport(refreshLayout,"onLoadMore","");

            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pager = 1;
                getReport(refreshLayout,"onRefresh","");
            }
        });
    }

    private void getReport(TwinklingRefreshLayout refreshLayout,String pull,String time){

        GetMyreportBean bean = new GetMyreportBean();
        bean.setRequestMethod("getReportList_xxByIdcard");
        bean.setCurPage(String.valueOf(pager));
        bean.setPageSize("20");
        ArrayList<String> cols = new ArrayList<>();
        /*"id","uuid","report_man_name","report_man_idcard","be_report_man_name",
        "be_report_man_idcard","clue_describe","clue_content",
        "is_sure","is_valid","mark","money"*/
        cols.add("id");
        cols.add("uuid");
        cols.add("report_man_name");
        cols.add("report_man_idcard");
        cols.add("be_report_man_name");
        cols.add("be_report_man_idcard");
        cols.add("clue_describe");
        cols.add("clue_content");
        cols.add("is_sure");
        cols.add("is_valid");
        cols.add("mark");
        cols.add("money");
        bean.setCols(cols);
        GetMyreportBean.DataBean data = new GetMyreportBean.DataBean();
//        data.setReport_man_idcard("360731198912054516");
        data.setReport_man_idcard(sph.getIdCard());
        data.setCjjlsj_ks(time);
        bean.setData(data);

        RequestParams params = new RequestParams();
        try {
            params.setBodyEntity(new StringEntity(new Gson().toJson(bean),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Helper.Post(Helper.getListGrid, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {

                if (pull.equals("onRefresh")){
                    list.clear();
                    refreshLayout.finishRefreshing();
                }else{
                    refreshLayout.finishLoadmore();
                }
                GetMyReportData data = Helper.jsonToBean(json,GetMyReportData.class);
                list.addAll(data.data);
                if (data.data.size()==0){
//                    showToast("已加载全部数据");
                    showDialog("已加载全部数据！");
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String json) {
                showDialog("服务器连接失败！");
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlebar_left:
                finish();
                break;

            case R.id.titlebar_rigth:
                getSelectTime();
                mDialogAll.show(getSupportFragmentManager(), "month_day_hour");
                break;
        }

    }

    private void getSelectTime() {
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        String text = getDateToString(millseconds);
//                        selectTime.setText(text);
                        MLog.mLog(text);
                        pager = 1;
                        getReport(refreshLayout,"onRefresh",text);
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("按时间筛选")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.maincolor))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.maincolor))
                .setWheelItemTextSize(16)
                .build();
    }

    private String getDateToString(long millseconds) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(millseconds));
    }

}
