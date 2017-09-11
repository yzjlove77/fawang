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
import com.android.wx.french.events.AlbumSelectEvent;
import com.android.wx.french.model.Album;
import com.android.wx.french.model.GetRewardData;
import com.android.wx.french.model.ReportBean;
import com.android.wx.french.model.ReportData;
import com.android.wx.french.presenter.ReportPresenter;
import com.android.wx.french.util.AbsolutePathUtil;
import com.android.wx.french.util.MLog;
import com.android.wx.french.util.PermissionHelper;
import com.android.wx.french.view.IReportView;
import com.android.wx.french.widget.popupwindow.PopupWindowPhoto;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private ArrayList<Album> albums, videoAlbums;

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
        EventBus.getDefault().register(this);
        imageLayoutManager = new LinearLayoutManager(mContext);
        imageLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageRecycler.setLayoutManager(imageLayoutManager);
        albums = new ArrayList<>();
        albums.add(new Album());
        imageAdapter = new ReportAdapter(mContext, albums);
        imageRecycler.setAdapter(imageAdapter);
        imageAdapter.setOnClickItemListener(this);

        videoLayoutManager = new LinearLayoutManager(mContext);
        videoLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        videoRecycler.setLayoutManager(videoLayoutManager);
        videoAlbums = new ArrayList<>();
        videoAlbums.add(new Album());
        videoAdapter = new ReportAdapter(mContext, videoAlbums);
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
                int size = albums.size();
                String images = "1&,&/1/";
                for (int i = 0; i < size - 1; i++) {
                    if (i == size - 2) {
                        images = images + albums.get(i).getImagePath();
                    } else {
                        images = images + albums.get(i).getImagePath() + "&;&1&,&/1/";
                    }
                }
                data.setListMedia(images);

                ReportBean bean = new ReportBean();
                bean.setRequestMethod("insertReport");
                bean.setData(data);

                mPresenter.report(bean);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AlbumSelectEvent event) {
        albums.addAll(albums.size() - 1, event.getAlbums());
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickItem(View view, int position) {
        switch (view.getId()) {
            case R.id.item_report_image:
                if (position == albums.size() - 1) {
                    boolean permission = PermissionHelper.getInstance(mContext).isPermission(mContext,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionHelper.PERMISSION_PHOTO);
                    MLog.mLog("permission = " + permission);
                    if (permission) {
                        showPopupPhoto();
                    }
                }
                break;
            case R.id.item_reduce_image:
                albums.remove(position);
                imageAdapter.notifyItemRemoved(position);
                break;
        }
    }

    private void showPopupPhoto() {
        if (popupWindowPhoto == null) {
            popupWindowPhoto = new PopupWindowPhoto(mContext);
        }
        popupWindowPhoto.showPopupWindow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PopupWindowPhoto.TAKE_PICTURE://拍照
                if (RESULT_OK == resultCode) {
                    Uri imageUri = popupWindowPhoto.getImageUri();
                    imageUrls.add(imageUrls.size() - 1, AbsolutePathUtil.getAbsolutePath(this, imageUri));
                    imageAdapter.notifyDataSetChanged();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
