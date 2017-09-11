package com.android.wx.french.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.android.wx.french.R;

/**
 * Created by Administrator on 2017/8/14.
 */

public class SelectPhotoUtils {

    protected View mView;
    private Dialog mDialog;
    private Button xiangjiBtn,cancleBtn,xiangceBtn;
    private Context context;

    public SelectPhotoUtils(Context context){
        this.context = context;
    }

    private void showSaveDialog() {
        // TODO Auto-generated method stub
        mView = LayoutInflater.from(context).inflate(R.layout.select_photo_dialog,
                null);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
            }
        });

        displayDialog(context, mView, Gravity.BOTTOM, Color.TRANSPARENT);

    }
    public void displayDialog(Context context, View view, int gravity,
                              int backGround) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        dismissDialog();
        mDialog = new Dialog(context, R.style.SelectPhotoDialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        Window window = mDialog.getWindow();
        window.setGravity(gravity);
        window.setBackgroundDrawable(new ColorDrawable(backGround));
        window.setWindowAnimations(R.style.ShareAnimation);
        WindowManager.LayoutParams mParams = mDialog.getWindow()
                .getAttributes();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        mParams.width = (int) (display.getWidth() * 1.0);
        mDialog.getWindow().setAttributes(mParams);
        mDialog.show();
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mDialog = null;
    }

    public void getCamera(View.OnClickListener listener){
        xiangjiBtn.setOnClickListener(listener);
    }

    public void getAlbum(View.OnClickListener listener){
        xiangceBtn.setOnClickListener(listener);
    }

}
