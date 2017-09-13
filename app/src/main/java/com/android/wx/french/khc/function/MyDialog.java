package com.android.wx.french.khc.function;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.khc.pro.IDelete;

/**
 * Created by KangHuiCong on 2017/9/12.
 * e-mail:515849594@qq.com
 */

public class MyDialog {

    Dialog dialog;
    LinearLayout layout;
    TextView dialog_content;
    Activity activity;
    Button dialog_cancel, dialog_exit;

    public MyDialog(Activity activity) {
        this.activity = activity;
    }


    public void getDeleteDialog(final IDelete iDelete, String tv) {
        dialog = new Dialog(activity);
        layout = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.dialog_back, null);
        dialog_content = (TextView) layout.findViewById(R.id.dialog_content);
        dialog_exit = (Button) layout.findViewById(R.id.dialog_exit);
        dialog_cancel = (Button) layout.findViewById(R.id.dialog_cancel);
        addDialog(dialog, layout);

        dialog_content.setText(tv);

        dialog_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                iDelete.isDelete();
            }
        });

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void addDialog(Dialog dialog, LinearLayout layout) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }
}
