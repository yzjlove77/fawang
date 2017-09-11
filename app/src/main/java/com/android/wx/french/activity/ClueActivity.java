package com.android.wx.french.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.adapter.ClueAdapter;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.model.ClueBean;
import com.android.wx.french.model.ClueData;
import com.android.wx.french.model.GetClueData;
import com.android.wx.french.presenter.CluePresenter;
import com.android.wx.french.util.RecycleViewDivider;
import com.android.wx.french.view.IClueView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wxZhang on 2017/8/14.
 * 提供线索
 */

public class ClueActivity extends BaseActivity<IClueView, CluePresenter> implements IClueView {

    @Bind(R.id.titlebar_name)
    TextView titleTitle;
    @Bind(R.id.clue_recycler)
    RecyclerView clueRecycler;

    private String taskId;
    private ArrayList<GetClueData> clueDatas;
    private ClueAdapter clueAdapter;
    private int pager = 1;

    @Override
    protected CluePresenter createPresenter() {
        return new CluePresenter(mContext);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_clue;
    }

    @Override
    protected void initView() {
        super.initView();
        titleTitle.setText("线索");
        clueRecycler.addItemDecoration(new RecycleViewDivider(mContext, LinearLayout.HORIZONTAL));
        clueDatas = new ArrayList<>();
        clueAdapter = new ClueAdapter(mContext, clueDatas);
        clueRecycler.setAdapter(clueAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        taskId = getIntent().getStringExtra("taskId");
        mPresenter.init();

        ClueData data = new ClueData();
        data.setTask_id(taskId);

        ArrayList<String> cols = new ArrayList<>();
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

        ClueBean bean = new ClueBean();
        bean.setRequestMethod("getReportList_xxByTask_id");
        bean.setData(data);
        bean.setPageSize("10");
        bean.setCurPage("" + pager);
        bean.setCols(cols);

        mPresenter.getClueData(bean);
    }

    @OnClick({R.id.titlebar_left})
    public void onClickBk(View view) {
        switch (view.getId()) {
            case R.id.titlebar_left:
                onBackPressed();
                break;
        }
    }

    @Override
    public void showViewClue(ArrayList<GetClueData> clueDatas) {
        this.clueDatas.addAll(clueDatas);
        clueAdapter.notifyDataSetChanged();
    }

    @Override
    public void failedViewClue(String msg) {

    }
}
