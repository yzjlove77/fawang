package com.android.wx.french.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.adapter.ExposureAdapter;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BaseFragment;
import com.android.wx.french.model.Exposurebean;
import com.android.wx.french.model.getExposureDatabean;
import com.android.wx.french.presenter.ExposureFgPresenter;
import com.android.wx.french.view.ExposureFgView;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by wxZhang on 2017/8/9.
 * 曝光
 *
 */

public class ExposureFragment extends BaseFragment<ExposureFgView,ExposureFgPresenter> implements ExposureFgView{

    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;
    @Bind(R.id.titlebar_rigth)
    TextView mRigthTv;
    @Bind(R.id.exposure_refresh)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.exposure_recycler)
    RecyclerView mRecycleView;

    LinearLayoutManager linearLayoutManager;

    private List<getExposureDatabean.getExposureData> list = new LinkedList<>();

    private ExposureAdapter adapter;

    private int pager;


    @Override
    protected ExposureFgPresenter createPresenter() {
        return new ExposureFgPresenter(getContext());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_exposure;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.initTitleBar();

        refreshLayout.startRefresh();
        SinaRefreshView headerView = new SinaRefreshView(getContext());
        refreshLayout.setHeaderView(headerView);
        BallPulseView boomView = new BallPulseView(getContext());
        boomView.setAnimatingColor(getResources().getColor(R.color.maincolor));
        refreshLayout.setBottomView(boomView);
        refreshLayout.setAutoLoadMore(true);//设置底部自动刷新
        setRefreshLayouts();

        linearLayoutManager = new LinearLayoutManager(getActivity());
       linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(linearLayoutManager);

        adapter = new ExposureAdapter(list);
        mRecycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    private void setRefreshLayouts() {

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                pager +=1;
                getExproData(refreshLayout,"onLoadMore");
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);

                pager = 1;
                getExproData(refreshLayout,"onRefresh");

            }
        });
    }

    private  void getExproData(TwinklingRefreshLayout refreshLayout,String pull){


        Exposurebean bean = new Exposurebean();
        bean.setRequestMethod("getSxbzxr");
        List<String> clonenew = new ArrayList<>();

        clonenew.add("sanh");
        clonenew.add("sabh");
        clonenew.add("fydm");
        clonenew.add("fymc");
        clonenew.add("ah");
        clonenew.add("sxbzxrmc");
        clonenew.add("sxbzxrxh");
        clonenew.add("sxbzxrzjhm");
        clonenew.add("sxid");
        clonenew.add("fbrq");
        clonenew.add("flwsqdyw");
        clonenew.add("bzxrlxqk");
        clonenew.add("type");
        clonenew.add("ip");
        clonenew.add("czyh");
        clonenew.add("bz");
        clonenew.add("czrq");
        clonenew.add("lx");
        clonenew.add("cjjlsj");
        clonenew.add("cbr_name");
        clonenew.add("cbr_mail");
        clonenew.add("cbr_userid");
        clonenew.add("cbr_sjhm");

        //2016-01-31","fbrq_js":"2016-2-15"
        Exposurebean.DataBean data = new Exposurebean.DataBean();
        data.setFbrq_js("2016-2-15");
        data.setFbrq_ks("2016-01-31");
        bean.setData(data);
        bean.setCols(clonenew);
        bean.setPageSize("20");
        bean.setCurPage(String.valueOf(pager));
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
                getExposureDatabean bean = Helper.jsonToBean(json,getExposureDatabean.class);
                list.addAll(bean.data);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String json) {

            }
        });

    }


    @Override
    public RecyclerView getRecyclerView() {
        return mRecycleView;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return linearLayoutManager;
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
}
