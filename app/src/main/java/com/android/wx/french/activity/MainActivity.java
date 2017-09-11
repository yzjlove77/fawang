package com.android.wx.french.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.wx.french.R;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.fragment.ExposureFragment;
import com.android.wx.french.fragment.HomeFragment;
import com.android.wx.french.fragment.MessageFragment;
import com.android.wx.french.fragment.PersonalFragment;

import butterknife.Bind;


/**
 * Created by Administrator on 2017/8/8.
 */

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.home_btn)
    RadioButton homeBtn;
    @Bind(R.id.exposure_btn)
    RadioButton exposureBtn;
    @Bind(R.id.message_btn)
    RadioButton messageBtn;
    @Bind(R.id.personal_btn)
    RadioButton personalBtn;
    private RadioButton currentBtn;
    private Fragment currentFragment = null;
    @Bind(R.id.home_release_btn)
    ImageView releaseImg;



    private enum Module {

        home,		//首页
        exposure, 	//曝光
        message, 	//消息
        personal    //我

    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentBtn = (RadioButton) radioGroup.getChildAt(0);
        currentBtn.setSelected(true);
        //radiogroup监听事件
        radioGroup.setOnCheckedChangeListener(this);
        setFragment(Module.home);
        releaseImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,ReleaseActivity.class));
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        currentBtn.setSelected(false);
        if (checkedId == R.id.home_btn) {
            currentBtn = homeBtn;
            this.setFragment(Module.home);
        } else if (checkedId == R.id.exposure_btn) {
            currentBtn = exposureBtn;
            this.setFragment(Module.exposure);
        } else if (checkedId == R.id.message_btn) {
            currentBtn = messageBtn;
            this.setFragment(Module.message);
        }  else if (checkedId == R.id.personal_btn) {
            currentBtn = personalBtn;
            this.setFragment(Module.personal);
        }
        currentBtn.setSelected(true);

    }

    private void setFragment(Module modlue) {
        // TODO Auto-generated method stub
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        switch (modlue) {

            case home:
                currentFragment = fm.findFragmentByTag(Module.home.toString());
                if (currentFragment == null) {
                    currentFragment = new HomeFragment();
                    transaction.add(R.id.content_id, currentFragment, Module.home.toString());
                }
                break;

            case exposure:

                currentFragment = fm.findFragmentByTag(Module.exposure.toString());
                if (currentFragment == null) {
                   currentFragment = new ExposureFragment();
                    transaction.add(R.id.content_id, currentFragment, Module.exposure.toString());
                }
                break;


            case message:
                currentFragment = fm.findFragmentByTag(Module.message.toString());
                if (currentFragment == null) {
                    currentFragment = new MessageFragment();
                    transaction.add(R.id.content_id, currentFragment, Module.message.toString());
                }
                break;

            case personal:
                currentFragment = fm.findFragmentByTag(Module.personal.toString());
                if (currentFragment == null) {
                    currentFragment = new PersonalFragment();
                    transaction.add(R.id.content_id, currentFragment, Module.personal.toString());
                }
                break;

            default:
                break;
        }
        transaction.show(currentFragment);
        transaction.commit();
    }

    private long exitTime = 0;
    /**
     * 捕捉返回事件按钮
     * 因为此 Activity 继承 TabActivity 用 onKeyDown 无响应，所以改用 dispatchKeyEvent
     * 一般的 Activity 用 onKeyDown 就可以了
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            moveTaskToBack(true);
        }
    }

}
