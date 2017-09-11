package com.android.wx.french.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/3.
 */

public class PermissionHelper {

    public static final int PERMISSION_PHOTO = 22;
    public static final int PERMISSION_WRITE= 23;
    public static final int PERMISSION_PHONE = 24;

    private static PermissionHelper helper;
    private Context mContext;

    private PermissionHelper(Context context){
        this.mContext = context;
    }

    public static PermissionHelper getInstance(Context context){
        if (helper == null) {
            helper = new PermissionHelper(context);
        }
        return helper;
    }

    public boolean isPermission(Object object, String[] permissions, int requestCode){
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> permissionList = needPermission(permissions);
            int size = permissionList.size();
            if (size > 0) {
                if (object instanceof Activity) {
                    ActivityCompat.requestPermissions((Activity) object, permissionList.toArray(new String[size]), requestCode);
                } else if (object instanceof Fragment) {
                    ((Fragment)object).requestPermissions(permissionList.toArray(new String[size]), requestCode);
                }
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    private ArrayList<String> needPermission(String...permissions){
        ArrayList<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(mContext, permission);
            if (PackageManager.PERMISSION_GRANTED != checkSelfPermission) {
                permissionList.add(permission);
            }
        }
        return permissionList;
    }
}
