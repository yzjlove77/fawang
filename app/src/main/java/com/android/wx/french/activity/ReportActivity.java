package com.android.wx.french.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.adapter.OnClickItemListener;
import com.android.wx.french.adapter.ReportAdapter;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.model.GetRewardData;
import com.android.wx.french.model.ReportBean;
import com.android.wx.french.model.ReportData;
import com.android.wx.french.presenter.ReportPresenter;
import com.android.wx.french.util.AbsolutePathUtil;
import com.android.wx.french.util.MLog;
import com.android.wx.french.util.PermissionHelper;
import com.android.wx.french.view.IReportView;
import com.android.wx.french.widget.popupwindow.PopupWindowPhoto;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class ReportActivity extends BaseActivity<IReportView, ReportPresenter> implements IReportView, OnClickItemListener {

    @Bind(R.id.titlebar_name)
    TextView titleTitle;
    @Bind(R.id.report_image_recycler)
    RecyclerView imageRecycler;
    @Bind(R.id.report_video_recycler)
    RecyclerView videoRecycler;
    @Bind(R.id.report_content)
    EditText contentEt;
    @Bind(R.id.report_name)
    TextView nameTv;
    @Bind(R.id.report_idcard)
    TextView idcardTv;
    @Bind(R.id.report_description)
    EditText descriptionEt;

    private ReportAdapter imageAdapter, videoAdapter;
    private ArrayList<String> imageUrls, videoUrls;
    private LinearLayoutManager imageLayoutManager, videoLayoutManager;
    private PopupWindowPhoto popupWindowPhoto;
    private GetRewardData rewardData;

    @Override
    protected ReportPresenter createPresenter() {
        return new ReportPresenter(mContext);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_report;
    }

    @Override
    protected void initView() {
        super.initView();
        titleTitle.setText("提供线索");

        imageLayoutManager = new LinearLayoutManager(mContext);
        imageLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageRecycler.setLayoutManager(imageLayoutManager);
        imageUrls = new ArrayList<>();
        imageUrls.add("");
        imageAdapter = new ReportAdapter(mContext, imageUrls);
        imageRecycler.setAdapter(imageAdapter);
        imageAdapter.setOnClickItemListener(this);

        videoLayoutManager = new LinearLayoutManager(mContext);
        videoLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        videoRecycler.setLayoutManager(videoLayoutManager);
        videoUrls = new ArrayList<>();
        videoUrls.add("");
        videoAdapter = new ReportAdapter(mContext, videoUrls);
        videoRecycler.setAdapter(videoAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        rewardData = (GetRewardData) bundle.getSerializable("rewardData");

        mPresenter.init();
    }

    @OnClick({R.id.titlebar_left, R.id.report_btn})
    public void onClickBk(View view) {
        switch (view.getId()) {
            case R.id.titlebar_left:
                onBackPressed();
                break;
            case R.id.report_btn:
                String description = descriptionEt.getText().toString().trim();
                if (TextUtils.isEmpty(description)) {
                    return;
                }
                String content = contentEt.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                ReportData data = new ReportData();
                data.setTask_id(rewardData.getId());
                String reporterName = sph.getName();
                if (!TextUtils.isEmpty(reporterName)) {
                    data.setReport_man_name(reporterName);
                }
                String reporterNameIdCard = sph.getIdCard();
                if (!TextUtils.isEmpty(reporterNameIdCard)) {
                    data.setReport_man_idcard(reporterNameIdCard);
                }
                data.setClue_describe(description);
                data.setClue_content(content);
                String name = nameTv.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    data.setBe_report_man_name(name);
                }
                String idcard = idcardTv.getText().toString().trim();
                if (!TextUtils.isEmpty(idcard)) {
                    data.setBe_report_man_idcard(idcard);
                }

                ReportBean bean = new ReportBean();
                bean.setRequestMethod("insertReport");
                bean.setData(data);

                mPresenter.report(bean);
                break;
        }
    }

    @Override
    public void onClickItem(View view, int position) {
        switch (view.getId()) {
            case R.id.item_report_image:
                if (position == imageUrls.size() - 1) {
                    boolean permission = PermissionHelper.getInstance(mContext).isPermission(mContext,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionHelper.PERMISSION_PHOTO);
                    MLog.mLog("permission = " + permission);
                    if (permission) {
//                        showPopupPhoto();
                    }
                }
                break;
        }
    }

    private void showPopupPhoto() {
        if (popupWindowPhoto == null) {
            popupWindowPhoto = new PopupWindowPhoto(mContext);
            popupWindowPhoto.setProfileImage(true);
        }
        popupWindowPhoto.showPopupWindow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PopupWindowPhoto.RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK && null != data) {// 相册返回
                    Uri uri = data.getData();
                    if (uri != null) {
                        popupWindowPhoto.cutPhoto(uri);
                    }
                } else {
                }
                break;
            case PopupWindowPhoto.TAKE_PICTURE://拍照
                if (RESULT_OK == resultCode) {
                    popupWindowPhoto.cutPhoto(popupWindowPhoto.getImageUri());
                } else {

                }
                break;
            case PopupWindowPhoto.CUT_PHOTO_REQUEST_CODE:
                if (null != data) {
                    String absolutePath = AbsolutePathUtil.getAbsolutePath(this, popupWindowPhoto.getImageUri());
                    MLog.mLog("absolutePath = " + absolutePath);
                    imageUrls.add(imageUrls.size() - 1, absolutePath);
                    imageAdapter.notifyItemChanged(imageUrls.size() - 1);
                }
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionHelper.PERMISSION_PHOTO:
                if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    showPopupPhoto();
                } else {
                    showMissingPermissionDialog();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("缺少权限，快去设置吧");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    protected void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public void showViewReport(String msg) {
        showToast(msg);
        finish();
    }

    @Override
    public void failedViewReport(String msg) {
        showToast(msg);
    }
}
