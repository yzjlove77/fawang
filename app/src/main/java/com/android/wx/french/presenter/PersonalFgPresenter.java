package com.android.wx.french.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.activity.BountyHunterActivity;
import com.android.wx.french.activity.MyMoneyActivity;
import com.android.wx.french.activity.MyTaskActivity;
import com.android.wx.french.activity.MyTheyActivity;
import com.android.wx.french.activity.MyreportActivity;
import com.android.wx.french.activity.PersonalDataActivity;
import com.android.wx.french.activity.SettingActivity;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.util.CircleImageView;
import com.android.wx.french.view.PersonalFgView;

/**
 * Created by Administrator on 2017/8/9.
 */

public class PersonalFgPresenter extends BasePresenter<PersonalFgView> {

    private Context context;
    private PersonalFgView personalFgView;
    private Button fabuBtn;
    private Button renwuBtn;
    private Button jubaoBtn;
    private Button loveBtn;
    private Button pingjiaBtn;
    private Button moneyBtn;
    private Button pyqBtn;
    private Button phbBtn;
    private CircleImageView circleImageView;
    private TextView dengjiTv;
    private TextView nameTv;
    private ImageView leftImage;
    private TextView titleNamel;
    private TextView mRightTv;

    public PersonalFgPresenter(Context context) {
        this.context = context;
    }

    public void initTitleBar() {
        personalFgView = getView();
        if (personalFgView != null) {

            leftImage = personalFgView.getLeftImg();
            titleNamel = personalFgView.getTitlename();
            mRightTv = personalFgView.getRightTv();
            phbBtn = personalFgView.getMyMoneyPhb();
            jubaoBtn = personalFgView.getMyJubao();
            renwuBtn = personalFgView.getMyRenwu();
            moneyBtn = personalFgView.getMyMoney();
            pingjiaBtn = personalFgView.getMyPingjia();

            leftImage.setImageResource(R.drawable.my_setting);
            titleNamel.setText(R.string.my);
            Drawable drawable = context.getResources().getDrawable(R.drawable.my_xiugai);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mRightTv.setCompoundDrawables(drawable, null, null, null);//设置图片
            leftImage.setOnClickListener(listener);
            mRightTv.setOnClickListener(listener);
            phbBtn.setOnClickListener(listener);

            jubaoBtn.setOnClickListener(listener);
            pingjiaBtn.setOnClickListener(listener);
            renwuBtn.setOnClickListener(listener);
            moneyBtn.setOnClickListener(listener);

        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                //设置
                case R.id.titlebar_left:
                    context.startActivity(new Intent(context, SettingActivity.class));
                    break;

                case R.id.titlebar_rigth:
                    context.startActivity(new Intent(context, PersonalDataActivity.class));
                    break;

                case R.id.my_moneyphb_btn:
                    context.startActivity(new Intent(context, BountyHunterActivity.class));
                    break;

                case R.id.my_jubao_btn:
                    context.startActivity(new Intent(context, MyreportActivity.class));
                    break;

                case R.id.my_renwu_btn:
                    context.startActivity(new Intent(context, MyTaskActivity.class));
                    break;

                case R.id.my_pingjia_btn:
                    context.startActivity(new Intent(context, MyTheyActivity.class));
                    break;

                case R.id.my_money_btn:
                    context.startActivity(new Intent(context, MyMoneyActivity.class));
                    break;

            }
        }
    };
}
