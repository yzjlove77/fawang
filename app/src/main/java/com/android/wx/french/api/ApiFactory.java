package com.android.wx.french.api;

/**
 * Created by wxZhang on 2017/8/8.
 */

public class ApiFactory {

    protected static final Object monitor = new Object();

   static ApiFrench frenchApiSingleton = null;

    public static ApiFrench getFrenchApiSingleton() {
        synchronized (monitor) {
            if (frenchApiSingleton == null) {
                frenchApiSingleton = new ApiRetrofit().getFrenchApiService();
            }
            return frenchApiSingleton;
        }
    }

}
