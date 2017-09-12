package com.android.wx.french.activity;

import android.view.View;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.model.GetClueData;
import com.android.wx.french.widget.popupwindow.BasePopupWindow;
import com.android.wx.french.widget.popupwindow.PopupWindowClueSubmit;
import com.android.wx.french.widget.popupwindow.PopupWindowDistribution;

import butterknife.Bind;
import butterknife.OnClick;

public class ClueDetailActivity extends BaseActivity implements BasePopupWindow.OnClickBtnListener {

    @Bind(R.id.clue_detail_report_name)
    TextView reportNameTv;
    @Bind(R.id.clue_detail_report_idcard)
    TextView reportIdcardTv;
    @Bind(R.id.clue_detail_be_reported_name)
    TextView beReportedNameTv;
    @Bind(R.id.clue_detail_be_reported_idcard)
    TextView beReportedIdcardTv;
    @Bind(R.id.clue_detail_description)
    TextView descriptionTv;
    @Bind(R.id.clue_detail_content)
    TextView contentTv;

    private GetClueData clueData;
    private PopupWindowClueSubmit popupSubmit;
    private PopupWindowDistribution popupDistribution;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_clue_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        clueData = (GetClueData) getIntent().getExtras().getSerializable("clueData");
        reportNameTv.setText(clueData.getReport_man_name());
        reportIdcardTv.setText(clueData.getReport_man_idcard());
        beReportedNameTv.setText(clueData.getBe_report_man_name());
        beReportedIdcardTv.setText(clueData.getBe_report_man_idcard());
        descriptionTv.setText(clueData.getClue_describe());
        contentTv.setText(clueData.getClue_content());
    }

    @OnClick({R.id.titlebar_left, R.id.clue_detail_btn, R.id.clue_distribution_btn})
    public void onClickBk(View view) {
        switch (view.getId()) {
            case R.id.titlebar_left:
                onBackPressed();
                break;
            //提交
            case R.id.clue_detail_btn:
                showSubimtPopup();
                break;
            //分配
            case R.id.clue_distribution_btn:
                showDistributionPopup();
                break;
        }
    }

    private void showSubimtPopup() {
        if (popupSubmit == null) {
            popupSubmit = new PopupWindowClueSubmit(mContext);
            popupSubmit.setOnClickBtnListener(this);
        }
        popupSubmit.setTaskId(clueData.getId());
        popupSubmit.setCourtCode(sph.getCourtCode());
        popupSubmit.showPopupWindow();
    }

    private void showDistributionPopup() {
        if (popupDistribution == null) {
            popupDistribution = new PopupWindowDistribution(mContext);
        }
        popupDistribution.setTaskId(clueData.getId());
        popupDistribution.showPopupWindow();
    }

    @Override
    public void onClickBtn(View view) {
        switch (view.getId()) {
            case R.id.popup_cancel:
                popupSubmit.dismiss();
                break;
            case R.id.popup_sure:
                popupSubmit.dismiss();
                break;
        }
    }
}
