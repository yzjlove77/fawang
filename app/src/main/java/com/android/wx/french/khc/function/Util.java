package com.android.wx.french.khc.function;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by KangHuiCong on 2017/9/12.
 * e-mail:515849594@qq.com
 */

public class Util {

    public static void showToast(Context context,String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
