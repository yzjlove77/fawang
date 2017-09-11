package com.android.wx.french.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.events.PersonalDataEvent;
import com.android.wx.french.model.PersonalBean;
import com.android.wx.french.model.PersonalData;
import com.android.wx.french.presenter.PersonalPresenter;
import com.android.wx.french.view.IPersonalView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wxZhang on 2017/8/11.
 * 个人资料
 */

public class PersonalDataActivity extends BaseActivity<IPersonalView, PersonalPresenter> implements IPersonalView {

    @Bind(R.id.titlebar_name)
    TextView titleTitle;
    @Bind(R.id.personal_nickname)
    EditText nicknameEt;
    @Bind(R.id.personal_name)
    EditText nameEt;
    @Bind(R.id.personal_sex)
    EditText sexEt;
    @Bind(R.id.personal_phone)
    EditText phoneEt;
    @Bind(R.id.personal_alipay)
    EditText alipyEt;

    private boolean isChange;

    @Override
    protected PersonalPresenter createPresenter() {
        return new PersonalPresenter(mContext);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_personaldata;
    }

    @Override
    protected void initView() {
        super.initView();
        titleTitle.setText("个人信息");
        nicknameEt.setHint(sph.getNickname());
        nameEt.setHint(sph.getName());
        phoneEt.setHint(sph.getPhone());
        mPresenter.init();
    }

    @OnClick({R.id.titlebar_left, R.id.personal_btn})
    public void onClickBk(View view) {
        switch (view.getId()) {
            case R.id.titlebar_left:
                onBackPressed();
                break;
            //保存
            case R.id.personal_btn:
                PersonalData personalData = new PersonalData();
                personalData.setIdcard(sph.getIdCard());
                String nickname = nicknameEt.getText().toString().trim();
                if (!TextUtils.isEmpty(nickname)) {
                    isChange = true;
                    personalData.setNickname(nickname);
                } else {
                    personalData.setNickname(nicknameEt.getHint().toString().trim());
                }
                String name = nameEt.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    isChange = true;
                    personalData.setName(name);
                } else {
                    personalData.setName(nameEt.getHint().toString().trim());
                }
                String sex = sexEt.getText().toString().trim();
                if (!TextUtils.isEmpty(sex)) {
                    isChange = true;
                    personalData.setSex(sex);
                } else {
                    personalData.setSex(sexEt.getHint().toString().trim());
                }
                String phone = phoneEt.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    isChange = true;
                    personalData.setPhone(phone);
                } else {
                    personalData.setPhone(phoneEt.getHint().toString().trim());
                }
                String alipay = alipyEt.getText().toString().trim();
                if (!TextUtils.isEmpty(alipay)) {
                    isChange = true;
                    personalData.setAlipayNo(alipay);
                } else {
                    personalData.setAlipayNo(alipyEt.getHint().toString().trim());
                }

                if (isChange) {
                    PersonalBean bean = new PersonalBean();
                    bean.setRequestMethod("updateUser_yh_xxBySfzh");
                    bean.setData(personalData);

                    mPresenter.save(bean);
                }
                break;
        }
    }

    @Override
    public void saveInfo(boolean isSuccessful, PersonalData data) {
        if (isSuccessful) {
            sph.setName(data.getName());
            sph.setPhone(data.getPhone());
            sph.setNickname(data.getNickname());
            EventBus.getDefault().post(new PersonalDataEvent());
            finish();
        }
    }
}
