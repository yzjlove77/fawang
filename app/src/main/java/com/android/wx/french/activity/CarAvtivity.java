package com.android.wx.french.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.api.Helper;
import com.android.wx.french.api.OnHandleCallback;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.model.CarSetdata;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/9/12.
 */

public class CarAvtivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.search_car)
    Button button;
    @Bind(R.id.car_number)
    EditText editText;
    @Bind(R.id.titlebar_left)
    ImageView leftImg;
    @Bind(R.id.titlebar_name)
    TextView titleTv;
    @Bind(R.id.name)
    TextView naem1 ;
    @Bind(R.id.name2)
    TextView naem2 ;
    @Bind(R.id.name3)
    TextView naem3;



    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();


        leftImg.setOnClickListener(this);
        titleTv.setText("拍车牌");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarSetdata data = new CarSetdata();
                data.setRequestMethod("viewClbkxxByCphm");
                CarSetdata.DataBean bean  = new CarSetdata.DataBean();
                bean.setCphm(editText.getText().toString());
                data.setData(bean);
                RequestParams params = new RequestParams("UTF-8");
                try {
                    params.setBodyEntity(new StringEntity(new Gson().toJson(data),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Helper.Post(Helper.post1, params, new OnHandleCallback() {
                    @Override
                    public void onSuccess(String json) {
                        Log.i("---json---", json);
                        try {
                            JSONObject sj = new JSONObject(json);
                            JSONObject oj = new JSONObject(sj.getString("data"));
                            naem1.setText("法院代码："+oj.getString("fydm"));
                            naem2.setText("法院名称："+oj.getString("fymc"));
                            naem3.setText("车牌号码："+oj.getString("cphm"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(String json) {

                    }
                });
            }
        });

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_car;
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
