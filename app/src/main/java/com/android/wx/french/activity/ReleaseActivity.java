package com.android.wx.french.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.model.JsonBean;
import com.android.wx.french.model.ReleaseBean;
import com.android.wx.french.model.ReleaseData;
import com.android.wx.french.presenter.ReleasePresenter;
import com.android.wx.french.util.AbsolutePathUtil;
import com.android.wx.french.util.GetJsonDataUtil;
import com.android.wx.french.util.MLog;
import com.android.wx.french.util.PermissionHelper;
import com.android.wx.french.util.SelectPhotoUtils;
import com.android.wx.french.view.IReleaseView;
import com.android.wx.french.widget.popupwindow.PopupWindowPhoto;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.json.JSONArray;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;

import static com.android.wx.french.widget.popupwindow.PopupWindowPhoto.TAKE_PICTURE;

/**
 * Created by wxZhang on 2017/8/11.
 * 发布爆料
 */

public class ReleaseActivity extends BaseActivity<IReleaseView, ReleasePresenter> implements IReleaseView, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;
    @Bind(R.id.select_time_tv)
    TextView selectTime;
    @Bind(R.id.release_title)
    EditText titleEt;
    @Bind(R.id.release_type_group)
    RadioGroup typeRg;
    @Bind(R.id.release_personal_info)
    EditText personalInfoEt;
    @Bind(R.id.release_dynamic_info)
    EditText dynamicInfoEt;
    @Bind(R.id.release_requirement)
    EditText requirementEt;
    @Bind(R.id.release_address)
    EditText addressEt;
    @Bind(R.id.release_select_city)
    TextView selectCityTv;
    @Bind(R.id.release_reward)
    EditText rewardEt;
    @Bind(R.id.release_reward_type_one)
    TextView rewardTypeOne;
    @Bind(R.id.release_reward_type_two)
    TextView rewardTypeTwo;
    @Bind(R.id.release_reward_type_three)
    TextView rewardTypeThree;
    @Bind(R.id.release_reward_type_four)
    TextView rewardTypeFour;
    @Bind(R.id.release_detail)
    EditText detailEt;
    @Bind(R.id.release_idcard)
    EditText idcardEt;
    @Bind(R.id.release_image)
    ImageView releaseIv;
    @Bind(R.id.release_btn)
    Button releaseBtn;


    /**
     * 时间选择
     */
    TimePickerDialog mDialogAll;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
    /**
     * 选择照片
     */
    SelectPhotoUtils selectPhotoUtils;
    private String rewardType;
    private String releaseType;
    private OptionsPickerView addressDialog;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private Uri imageUri;
    private String imagePath;
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread==null){//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    getAddressSelect();
                    break;
                case MSG_LOAD_FAILED:
                    break;

            }
        }
    };

    @Override
    protected ReleasePresenter createPresenter() {
        return new ReleasePresenter(mContext);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_release;
    }

    @Override
    protected void initView() {
        titleTv.setText("发布申请");
        mPresenter.initView();
        leftImg.setOnClickListener(this);
        selectTime.setOnClickListener(this);
        releaseBtn.setOnClickListener(this);
        selectCityTv.setOnClickListener(this);
        rewardTypeOne.setOnClickListener(this);
        rewardTypeTwo.setOnClickListener(this);
        rewardTypeThree.setOnClickListener(this);
        rewardTypeFour.setOnClickListener(this);
        typeRg.setOnCheckedChangeListener(this);
        releaseIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_left:
                finish();
                break;
            case R.id.select_time_tv:
                getSelectTime();
                mDialogAll.show(getSupportFragmentManager(), "month_day_hour_minute");
                break;
            //选择省市区
            case R.id.release_select_city:
                if (isLoaded) {
                    getAddressSelect();
                } else {
                    mHandler.sendEmptyMessage(MSG_LOAD_DATA);
                }
                break;
            case R.id.release_reward_type_one:
                if (rewardTypeOne.isSelected()) {
                    return;
                }
                rewardEt.setVisibility(View.VISIBLE);
                rewardType = "1";
                rewardTypeOne.setSelected(true);
                rewardTypeTwo.setSelected(false);
                rewardTypeThree.setSelected(false);
                rewardTypeFour.setSelected(false);
                break;
            case R.id.release_reward_type_two:
                if (rewardTypeTwo.isSelected()) {
                    return;
                }
                rewardEt.setVisibility(View.GONE);
                rewardType = "2";
                rewardTypeOne.setSelected(false);
                rewardTypeTwo.setSelected(true);
                rewardTypeThree.setSelected(false);
                rewardTypeFour.setSelected(false);
                break;
            case R.id.release_reward_type_three:
                if (rewardTypeThree.isSelected()) {
                    return;
                }
                rewardEt.setVisibility(View.VISIBLE);
                rewardType = "3";
                rewardTypeOne.setSelected(false);
                rewardTypeTwo.setSelected(false);
                rewardTypeThree.setSelected(true);
                rewardTypeFour.setSelected(false);
                break;
            case R.id.release_reward_type_four:
                if (rewardTypeFour.isSelected()) {
                    return;
                }
                rewardEt.setVisibility(View.GONE);
                rewardType = "4";
                rewardTypeOne.setSelected(false);
                rewardTypeTwo.setSelected(false);
                rewardTypeThree.setSelected(false);
                rewardTypeFour.setSelected(true);
                break;
            //发布
            case R.id.release_btn:
                String title = titleEt.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    showToast("请输入悬赏标题");
                    return;
                }
                if (TextUtils.isEmpty(releaseType)) {
                    showToast("请选择悬赏类型");
                    return;
                }
                String personalInfo = personalInfoEt.getText().toString().trim();
                if (TextUtils.isEmpty(personalInfo)) {
                    showToast("请输入被执行人个性信息");
                    return;
                }
                String dynamicInfo = dynamicInfoEt.getText().toString().trim();
                if (TextUtils.isEmpty(dynamicInfo)) {
                    showToast("请输入被执行人动态信息");
                    return;
                }
                String requestment = requirementEt.getText().toString().trim();
                if (TextUtils.isEmpty(requestment)) {
                    showToast("请填写要求");
                    return;
                }
                String detail = detailEt.getText().toString().trim();
                if (TextUtils.isEmpty(detail)) {
                    showToast("请填写任务详情");
                    return;
                }
                String time = selectTime.getText().toString().trim();
                if (TextUtils.isEmpty(time)) {
                    showToast("请选择时间");
                    return;
                }
                String city = selectCityTv.getText().toString().trim();
                if (TextUtils.isEmpty(city)) {
                    showToast("请选择户籍信息");
                    return;
                }
                String address = addressEt.getText().toString().trim();
                if (TextUtils.isEmpty(address)) {
                    showToast("请输入详细地址");
                    return;
                }
                String idcard = idcardEt.getText().toString().trim();
                if (TextUtils.isEmpty(idcard)) {
                    showToast("请输入被执行人身份证");
                    return;
                }
                if (!rewardTypeOne.isSelected() && !rewardTypeTwo.isSelected() &&
                        !rewardTypeThree.isSelected() && !rewardTypeFour.isSelected()) {
                    showToast("请选择赏金类型");
                    return;
                }
                String reward = rewardEt.getText().toString().trim();
                if (rewardTypeOne.isSelected() || rewardTypeFour.isSelected()) {
                    if (TextUtils.isEmpty(reward)) {
                        showToast("请输入悬赏金额");
                        return;
                    }
                }
                ReleaseData data = new ReleaseData();
                data.setTitle(title);
                data.setBzxr_specialty(personalInfo);
                data.setBzxr_dt(dynamicInfo);
                data.setTask_expiration_time(time);
                data.setIs_sqgffb("0");
                data.setReward_details(detail);
                data.setTask_demand(requestment);
                data.setReward_amount(reward);
                data.setType_of_reward(rewardType);
                data.setBzxr_hj(city);
                data.setBzxr_idcard(idcard);
                data.setFbdsr_name(sph.getName());
                data.setFbdsr_sjhm(sph.getPhone());
                data.setFbdsr_sfzh(sph.getIdCard());
                data.setBzxlx(releaseType);
                data.setBzxr_adress(address);
                if (!TextUtils.isEmpty(imagePath)) {
                    data.setBzxr_photo_path(imagePath);
                }

                ReleaseBean bean = new ReleaseBean();
                bean.setRequestMethod("insertTaskByDsr");
                bean.setData(data);
                mPresenter.release(bean);
                break;
            case R.id.release_image:
                boolean permission = PermissionHelper.getInstance(mContext).isPermission(mContext,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionHelper.PERMISSION_PHOTO);
                MLog.mLog("permission = " + permission);
                if (permission) {
                    startCamera();
                }
                break;
        }
    }

    private void startCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String sdcardState = Environment.getExternalStorageState();
        String sdcardPathDir = Environment.getExternalStorageDirectory().getPath() + "/French/temp/tempImage/";
        File file = null;
        if (Environment.MEDIA_MOUNTED.endsWith(sdcardState)) {
            // 有sd卡，是否有myImage文件夹
            File fileDir = new File(sdcardPathDir);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            // 是否有headImg文件s
            file = new File(sdcardPathDir + System.currentTimeMillis()
                    + ".JPEG");
        }
        if (file != null) {
            imageUri = Uri.fromFile(file);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            mContext.startActivityForResult(cameraIntent, TAKE_PICTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PopupWindowPhoto.TAKE_PICTURE://拍照
                if (RESULT_OK == resultCode) {
                    imagePath = AbsolutePathUtil.getAbsolutePath(this, imageUri);
                    Glide.with(mContext)
                            .load(imagePath)
                            .into(releaseIv);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionHelper.PERMISSION_PHOTO:
                if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    startCamera();
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

    private void getSelectTime() {
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        String text = getDateToString(millseconds);
                        selectTime.setText(text);
                    }
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("时间选择")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.maincolor))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.maincolor))
                .setWheelItemTextSize(12)
                .build();
    }

    private String getDateToString(long millseconds) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(millseconds));
    }

    private void getAddressSelect() {
        if (addressDialog == null) {
            addressDialog = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    String tx = options1Items.get(options1).getPickerViewText() +
                            options2Items.get(options1).get(options2) +
                            options3Items.get(options1).get(options2).get(options3);
                    selectCityTv.setText(tx);
                }
            }).setSubmitText("确定")//确定按钮文字
                    .setCancelText("取消")//取消按钮文字
                    .setTitleText("城市选择")//标题
                    .setSubCalSize(18)//确定和取消文字大小
                    .setTitleSize(20)//标题文字大小
                    .setTitleColor(Color.WHITE)//标题文字颜色
                    .setSubmitColor(Color.WHITE)//确定按钮文字颜色
                    .setCancelColor(Color.WHITE)//取消按钮文字颜色
                    .setTitleBgColor(ActivityCompat.getColor(mContext, R.color.maincolor))//标题背景颜色 Night mode
                    .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                    .setContentTextSize(18)//滚轮文字大小
//                    .setLinkage(true)//设置是否联动，默认true
                    .setLabels("省", "市", "区")//设置选择的三级单位
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .setCyclic(false, false, false)//循环与否
//                    .setSelectOptions(1, 1, 1)  //设置默认选中项
                    .setOutSideCancelable(true)//点击外部dismiss default true
                    .isDialog(false)//是否显示为对话框样式
                    .build();
            addressDialog.setPicker(options1Items, options2Items, options3Items);//添加数据源
        }
        addressDialog.show();
    }

    private void initJsonData() {
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    public void release(boolean isSuccessful, String msg) {
        showToast(msg);
        if (isSuccessful) {
            finish();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //寻人
            case R.id.release_type_person:
                releaseType = "1";
                break;
            //找物
            case R.id.release_type_thing:
                releaseType = "2";
                break;
        }
    }
}
