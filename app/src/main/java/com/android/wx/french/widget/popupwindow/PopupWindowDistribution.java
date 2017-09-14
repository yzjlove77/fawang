package com.android.wx.french.widget.popupwindow;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.wx.french.R;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.model.DistributionBean;
import com.android.wx.french.model.DistributionData;
import com.android.wx.french.model.GetRegisterDataBean;
import com.android.wx.french.util.MLog;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/11.
 */

public class PopupWindowDistribution extends BasePopupWindow {

    @Bind(R.id.popup_mark)
    EditText markEt;
    @Bind(R.id.popup_money)
    EditText moneyEt;
    @Bind(R.id.popup_distribution_layout)
    LinearLayout popupLayout;
    @Bind(R.id.popup_true)
    CheckBox trueCb;
    @Bind(R.id.popup_effective)
    CheckBox effectiveCb;

    private String taskId;

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public PopupWindowDistribution(Activity mContext) {
        super(mContext);
    }

    public PopupWindowDistribution(Activity mContext, int w, int h) {
        super(mContext, w, h);
    }

    @OnClick({R.id.popup_cancel, R.id.popup_sure})
    public void onClickBk(View view) {
        switch (view.getId()) {
            case R.id.popup_cancel:
                dismiss();
                break;
            case R.id.popup_sure:
                String mark = markEt.getText().toString().trim();
                if (TextUtils.isEmpty(mark)) {
                    return;
                }
                String money = moneyEt.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    return;
                }
                DistributionData data = new DistributionData();
                data.setId(taskId);
                if (trueCb.isChecked()) {
                    data.setIs_sure("" + 1);
                } else {
                    data.setIs_sure("" + 0);
                }
                if (effectiveCb.isChecked()) {
                    data.setIs_valid("" + 1);
                } else {
                    data.setIs_valid("" + 0);
                }
                data.setMark(mark);
                data.setMoney(money);

                DistributionBean bean = new DistributionBean();
                bean.setRequestMethod("updateMarkandMoney");
                bean.setData(data);
                distribution(bean);
                break;
        }
    }

    @Override
    public View getPopupView() {
        return LayoutInflater.from(mContext).inflate(R.layout.popup_distribution_layout, null);
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

    private void distribution (DistributionBean bean) {
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
