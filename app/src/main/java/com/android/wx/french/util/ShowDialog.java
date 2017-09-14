package com.android.wx.french.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.wx.french.R;

/**
 * Created by wxZhang on 2017/9/13.
 */

public class ShowDialog extends Dialog {

    private TextView tv;
    private Button button,shi,fou;
    private LinearLayout linearLayout;

    public ShowDialog(Context context){
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nulldata_dialog);
        tv = (TextView)findViewById(R.id.nulldialog_content);
        button = (Button) findViewById(R.id.nulldialog_btn);
        linearLayout = (LinearLayout) findViewById(R.id.nulldialog_shifou);
        shi = (Button) findViewById(R.id.nulldialog_shi);
        fou = (Button) findViewById(R.id.nulldialog_fou);

    }
    public void setContent(String message){
        tv.setText(message);
    }

    public void setButtonOnClick(View.OnClickListener listener){
        button.setOnClickListener(listener);
    }

    public void setButtonText(String string){
        button.setText(string);
    }

    public void setVisishifou(){
        button.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    public void shiButton(View.OnClickListener listener){
        shi.setOnClickListener(listener);
    }

    public void fouButton(View.OnClickListener listener){
        fou.setOnClickListener(listener);
    }



}
