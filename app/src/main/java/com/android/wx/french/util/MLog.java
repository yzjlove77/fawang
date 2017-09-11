package com.android.wx.french.util;

import android.util.Log;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MLog {

    public static final String TAG = "French";
    public static final boolean IS_DEBUG = true;

    public static void mLog(String msg) {
        if (IS_DEBUG) {
            Log.i(TAG, msg);
        }
    }
}
