package com.android.wx.french.view;


import android.widget.Button;
import android.widget.TextView;

import com.android.wx.french.util.CircleImageView;

/**
 * Created by Administrator on 2017/8/9.
 */

public interface PersonalFgView extends TitleBarView{

    CircleImageView getCircleImageView();
    TextView getDengji();
    TextView getPersonName();
    Button getMyRenwu();
    Button getMyFabu();
    Button getMyJubao();
    Button getMyLove();
    Button getMyPingjia();
    Button getMyMoney();
    Button getMyPengyq();
    Button getMyMoneyPhb();
    Button getMyFriend();


}
