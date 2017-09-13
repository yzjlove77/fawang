package com.android.wx.french.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.adapter.RewardDetailAdapter;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.khc.pro.IInsertCollect;
import com.android.wx.french.khc.InsertCollect;
import com.android.wx.french.events.DoCommentEvent;
import com.android.wx.french.model.CommentBean;
import com.android.wx.french.model.CommentData;
import com.android.wx.french.model.GetCommentData;
import com.android.wx.french.model.GetRewardData;
import com.android.wx.french.presenter.CommentPresenter;
import com.android.wx.french.util.RecycleViewDivider;
import com.android.wx.french.view.ICommentView;
import com.android.wx.french.widget.popupwindow.PopupWindowComment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class RewardDetailActivity extends BaseActivity<ICommentView, CommentPresenter> implements ICommentView,IInsertCollect{

    @Bind(R.id.titlebar_name)
    TextView titleTitle;
    @Bind(R.id.reward_detail_recycler)
    RecyclerView mRecyclerView;

    private LinearLayoutManager layoutManager;
    private GetRewardData rewardData;
    private RewardDetailAdapter adapter;
    private ArrayList<GetCommentData> commentDatas;
    private int pager = 1;
    private PopupWindowComment popupComment;

    InsertCollect insertCollect;
    @Override
    protected CommentPresenter createPresenter() {
        return new CommentPresenter(mContext);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_reward_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        titleTitle.setText("详情页");
        insertCollect = new InsertCollect(this,this);

        EventBus.getDefault().register(this);
        layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayout.HORIZONTAL));
        commentDatas = new ArrayList<>();
        adapter = new RewardDetailAdapter(mContext, commentDatas);
        mRecyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        rewardData = (GetRewardData) bundle.getSerializable("rewardData");
        adapter.setRewardData(rewardData);
        mPresenter.init();
    }

    @Override
    protected void initData() {
        super.initData();
        CommentData data = new CommentData();
        data.setFbsqr_contentid(rewardData.getId());

        ArrayList<String> cols = new ArrayList<>();
        cols.add("fbsqr_name");
        cols.add("fbsqr_title");
        cols.add("fbsqr_contentid");
        cols.add("fbsqr_comment");
        cols.add("jbr_id");
        cols.add("jbr_name");
        cols.add("jbr_comment");
        cols.add("jbr_idcard");

        CommentBean bean = new CommentBean();
        bean.setRequestMethod("getComment");
        bean.setData(data);
        bean.setCols(cols);
        bean.setCurPage("" + pager);
        bean.setPageSize("20");
        mPresenter.getCommentData(bean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DoCommentEvent event) {
        initData();
    }

    @OnClick({R.id.titlebar_left, R.id.reward_detail_report, R.id.reward_detail_comment,R.id.bt_collect})
    public void onClickBk(View view) {
        switch (view.getId()) {
            case R.id.titlebar_left:
                onBackPressed();
                break;
            //提供线索
            case R.id.reward_detail_report:
                Bundle bundle = new Bundle();
                bundle.putSerializable("rewardData", rewardData);
                Intent intent = new Intent(mContext, ReportActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.bt_collect:
                insertCollect.insertCollect(rewardData.getTitle(),1);
            //评论
            case R.id.reward_detail_comment:
                if (popupComment == null) {
                    popupComment = new PopupWindowComment(mContext);
                }
                popupComment.setName(sph.getName());
                popupComment.setTitle(rewardData.getTitle());
                popupComment.setTaskId(rewardData.getId());
                popupComment.showPopupWindow();
                break;
        }
    }

    @Override
    public void showViewComment(ArrayList<GetCommentData> commentDatas) {
        this.commentDatas.clear();
        this.commentDatas.addAll(commentDatas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failedViewComment(String msg) {

    }

    @Override
    public void successInsertCollect() {
        showToast("收藏成功！");
    }

    @Override
    public void failureInsertCollect(String msg) {
        showToast("收藏失败！" + msg);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
