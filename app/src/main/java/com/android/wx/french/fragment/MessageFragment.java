package com.android.wx.french.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wxZhang on 2017/8/9.
 * 消息
 */

public class MessageFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;
    @Bind(R.id.titlebar_rigth)
    TextView mRigthTv;

    private ArrayList<Fragment> fragments;
    @Bind(R.id.tab_viewPager)
    ViewPager viewPager;
    @Bind(R.id.tab_leftstring)
    TextView sixin;//私信
    @Bind(R.id.tab_rightstring)
    TextView tongzhi;//通知
    @Bind(R.id.tab_line)
    View line;//底线
    private int line_width;//底线的宽度

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initViewPager();

    }
    private void initView() {
        leftImg.setVisibility(View.GONE);
        titleTv.setText(R.string.tab_message);
        mRigthTv.setVisibility(View.GONE);
        sixin.setOnClickListener(this);
        tongzhi.setOnClickListener(this);
    }

    private void initViewPager() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new PrivateLetterFragment());
        fragments.add(new PrivateLetterFragment());
        line_width = getActivity().getWindowManager().getDefaultDisplay().getWidth()
                / (fragments.size());
        line.getLayoutParams().width = line_width-(line_width/2);
        line.requestLayout();
        viewPager.setAdapter(new FragmentStatePagerAdapter(
                getActivity().getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragments.get(arg0);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                changeState(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                float tagerX = arg0 * line_width+line_width/4 + arg2 / fragments.size();
                ViewPropertyAnimator.animate(line).translationX(tagerX)
                        .setDuration(0);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /* 根据传入的值来改变状态 */
    private void changeState(int arg0) {

        if (arg0 == 0) {
            sixin.setTextColor(getResources().getColor(R.color.maincolor));
            tongzhi.setTextColor(getResources().getColor(R.color.secendcolor));
            ViewPropertyAnimator.animate(sixin).scaleX(1.2f).setDuration(200);
            ViewPropertyAnimator.animate(sixin).scaleY(1.2f).setDuration(200);
            ViewPropertyAnimator.animate(tongzhi).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(tongzhi).scaleY(1.0f).setDuration(200);
        }

        if(arg0 ==1) {
            tongzhi.setTextColor(getResources().getColor(R.color.maincolor));
            sixin.setTextColor(getResources().getColor(R.color.secendcolor));
            ViewPropertyAnimator.animate(sixin).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(sixin).scaleY(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(tongzhi).scaleX(1.2f).setDuration(200);
            ViewPropertyAnimator.animate(tongzhi).scaleY(1.2f).setDuration(200);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_leftstring:
               viewPager.setCurrentItem(0);
                break;

            case R.id.tab_rightstring:
                viewPager.setCurrentItem(1);
                break;

        }
    }
}
