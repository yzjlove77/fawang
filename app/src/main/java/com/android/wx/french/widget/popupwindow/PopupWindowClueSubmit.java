package com.android.wx.french.widget.popupwindow;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.wx.french.R;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.model.ExecuteBean;
import com.android.wx.french.model.ExecuteData;
import com.android.wx.french.model.GetRegisterDataBean;
import com.android.wx.french.util.MLog;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/9/11.
 */

public class PopupWindowClueSubmit extends BasePopupWindow implements View.OnClickListener {

    @Bind(R.id.popup_submit_layout)
    LinearLayout popupLayout;
    @Bind(R.id.popup_cancel)
    Button cancelBtn;
    @Bind(R.id.popup_sure)
    Button sureBtn;
    @Bind(R.id.popup_year_number)
    EditText yearNumberEt;
    @Bind(R.id.popup_number)
    EditText numberEt;
    @Bind(R.id.popup_case_no)
    EditText caseNoEt;
    @Bind(R.id.popup_description)
    EditText descriptionEt;
    @Bind(R.id.popup_zxdw)
    CheckBox zxdwCb;
    @Bind(R.id.popup_arrested)
    CheckBox arrestedCb;
    @Bind(R.id.popup_undertaker_name)
    EditText undertakerNameEt;
    @Bind(R.id.popup_undertaker_phone)
    EditText undertakerPhoneEt;

    private String taskId;
    private String courtCode;

    public void setCourtCode(String courtCode) {
        this.courtCode = courtCode;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public PopupWindowClueSubmit(Activity mContext) {
        super(mContext);
        cancelBtn.setOnClickListener(this);
        sureBtn.setOnClickListener(this);
    }

    public PopupWindowClueSubmit(Activity mContext, int w, int h) {
        super(mContext, w, h);
    }

    @Override
    public View getPopupView() {
        return LayoutInflater.from(mContext).inflate(R.layout.popup_clue_submit_layout, null);
    }

    @Override
    public View getAnimView() {
        return popupLayout;
    }

    @Override
    public Animation getShowAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1.0f, 0, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //3秒完成动画
        scaleAnimation.setDuration(300);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        return scaleAnimation;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_cancel:
                dismiss();
                break;
            case R.id.popup_sure:
                String yearNumber = yearNumberEt.getText().toString().trim();
                if (TextUtils.isEmpty(yearNumber)) {
                    return;
                }
                String number = numberEt.getText().toString().trim();
                if (TextUtils.isEmpty(number)) {
                    return;
                }
                String caseNo = caseNoEt.getText().toString().trim();
                if (TextUtils.isEmpty(caseNo)) {
                    return;
                }
                String description = descriptionEt.getText().toString().trim();
                if (TextUtils.isEmpty(description)) {
                    return;
                }
                String undertakerName = undertakerNameEt.getText().toString().trim();
                if (TextUtils.isEmpty(undertakerName)) {
                    return;
                }
                String undertakerPhone = this.undertakerPhoneEt.getText().toString().trim();
                if (TextUtils.isEmpty(undertakerPhone)) {
                    return;
                }
                ExecuteData data = new ExecuteData();
                data.setTask_id(taskId);
                data.setSanh(yearNumber);
                data.setSabh(number);
                data.setFydm(courtCode);
                data.setAh(caseNo);
                if (zxdwCb.isSelected()) {
                    data.setResult("" + 1);
                } else {
                    data.setResult("" + 0);
                }
                data.setResult_describe(description);
                if (arrestedCb.isChecked()) {
                    data.setIs_arrest_bzxr("" + 1);
                } else {
                    data.setIs_arrest_bzxr("" + 0);
                }
                data.setCbr_name(undertakerName);
                data.setCbr_sjhm(undertakerPhone);

                ExecuteBean bean = new ExecuteBean();
                bean.setRequestMethod("insertZxxg");
                bean.setData(data);

                submit(bean);
                break;
        }
    }

    private void submit(ExecuteBean bean) {
        RequestParams params = new RequestParams("UTF-8");
        try {
            String s = new Gson().toJson(bean);
            MLog.mLog("send : " + s);
            params.setBodyEntity(new StringEntity(s,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Helper.Post(Helper.post1, params, new OnHandleCallback() {
            @Override
            public void onSuccess(String json) {
                MLog.mLog("json : " + json);
                GetRegisterDataBean getRegisterDataBean = Helper.jsonToBean(json, GetRegisterDataBean.class);
                if (getRegisterDataBean.isRes()) {
                    Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(String json) {
            }
        });
    }
}
