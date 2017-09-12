package com.android.wx.french.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.base.BaseFragment;
import com.android.wx.french.events.PersonalDataEvent;
import com.android.wx.french.presenter.PersonalFgPresenter;
import com.android.wx.french.util.CircleImageView;
import com.android.wx.french.view.PersonalFgView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/8/9.
 */

public class PersonalFragment extends BaseFragment<PersonalFgView, PersonalFgPresenter> implements PersonalFgView {

    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;
    @Bind(R.id.titlebar_rigth)
    TextView mRigthTv;
    @Bind(R.id.my_fabu_btn)
    Button fabuBtn;
    @Bind(R.id.my_renwu_btn)
    Button renwuBtn;
    @Bind(R.id.my_jubao_btn)
    Button jubaoBtn;
    @Bind(R.id.my_love_btn)
    Button loveBtn;
    @Bind(R.id.my_pingjia_btn)
    Button pingjiaBtn;
    @Bind(R.id.my_money_btn)
    Button moneyBtn;
    @Bind(R.id.my_pengyq_btn)
    Button pyqBtn;
    @Bind(R.id.my_moneyphb_btn)
    Button phbBtn;
    @Bind(R.id.my_photo_img)
    CircleImageView circleImageView;
    @Bind(R.id.my_dengji_tv)
    TextView dengjiTv;
    @Bind(R.id.my_name_tv)
    TextView nameTv;
    @Bind(R.id.my_friend_btn)
    Button myFriendBtn;


    @Override
    protected PersonalFgPresenter createPresenter() {
        return new PersonalFgPresenter(getContext());
    }

    @Override
    protected int createViewLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        mPresenter.initTitleBar();

        nameTv.setText(sph.getName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PersonalDataEvent event) {
        nameTv.setText(sph.getName());
    }

    @Override
    public ImageView getLeftImg() {
        return leftImg;
    }

    @Override
    public TextView getTitlename() {
        return titleTv;
    }

    @Override
    public TextView getRightTv() {
        return mRigthTv;
    }

    @Override
    public CircleImageView getCircleImageView() {
        return circleImageView;
    }

    @Override
    public TextView getDengji() {
        return dengjiTv;
    }

    @Override
    public TextView getPersonName() {
        return nameTv;
    }

    @Override
    public Button getMyRenwu() {
        return renwuBtn;
    }

    @Override
    public Button getMyFabu() {
        return fabuBtn;
    }

    @Override
    public Button getMyJubao() {
        return jubaoBtn;
    }

    @Override
    public Button getMyLove() {
        return loveBtn;
    }

    @Override
    public Button getMyPingjia() {
        return pingjiaBtn;
    }

    @Override
    public Button getMyMoney() {
        return moneyBtn;
    }

    @Override
    public Button getMyPengyq() {
        return pyqBtn;
    }

    @Override
    public Button getMyMoneyPhb() {
        return phbBtn;
    }

    @Override
    public Button getMyFriend() {
        return myFriendBtn;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
