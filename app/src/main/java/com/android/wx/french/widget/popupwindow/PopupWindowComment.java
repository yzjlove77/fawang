package com.android.wx.french.widget.popupwindow;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.wx.french.R;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.events.DoCommentEvent;
import com.android.wx.french.model.DoCommentBean;
import com.android.wx.french.model.DoCommentData;
import com.android.wx.french.model.GetRegisterDataBean;
import com.android.wx.french.util.MLog;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/11.
 */

public class PopupWindowComment extends BasePopupWindow {

    @Bind(R.id.popup_comment_layout)
    LinearLayout popupLayout;
    @Bind(R.id.popup_comment_content)
    EditText contentEt;

    private String taskId;
    private String name;
    private String title;

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PopupWindowComment(Activity mContext) {
        super(mContext);
    }

    public PopupWindowComment(Activity mContext, int w, int h) {
        super(mContext, w, h);
    }

    @OnClick({R.id.popup_cancel, R.id.popup_sure})
    public void onClickBk(View view) {
        switch (view.getId()) {
            case R.id.popup_cancel:
                dismiss();
                break;
            case R.id.popup_sure:
                String content = contentEt.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                DoCommentData data = new DoCommentData();
                data.setFbsqr_name(name);
                data.setFbsqr_title(title);
                data.setFbsqr_contentid(taskId);
                data.setFbsqr_comment(content);
                data.setIs_public("" + 1);
                data.setComment_role("" + 1);

                DoCommentBean bean = new DoCommentBean();
                bean.setRequestMethod("insertComment");
                bean.setData(data);

                comment(bean);
                break;
        }
    }

    @Override
    public View getPopupView() {
        return LayoutInflater.from(mContext).inflate(R.layout.popup_comment_layout, null);
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

    private void comment (DoCommentBean bean) {
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
                    contentEt.setText("");
                    EventBus.getDefault().post(new DoCommentEvent());
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
