package com.android.wx.french.api;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 接口请求工具类
 * 
 * */
public class Helper {

//	胡正伟:
//	http://116.62.162.52:6060/fwzx/rest/ListGridService/getListGrid
//	胡正伟:
//	http://116.62.162.52:6060/fwzx/rest/CommonService/post1

	public static String getListGrid= "http://116.62.162.52:6060/fwzx/rest/ListGridService/getListGrid";
	public static String post1 = "http://116.62.162.52:6060/fwzx/rest/CommonService/post1";

	private static HttpUtils http = new HttpUtils(30000);
	private static Gson gson = new Gson();
	public static void Post(String url, RequestParams params, final OnHandleCallback result) {
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException exc, String t) {
				result.onFailure(t);
			}

			@Override
			public void onSuccess(ResponseInfo<String> t) {
				result.onSuccess(t.result);
			}

		});
	}


	public static void Get(String url, RequestParams params, final OnHandleCallback result) {
		
		http.send(HttpMethod.GET, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException exc, String t) {
				result.onFailure(t);
			}

			@Override
			public void onSuccess(ResponseInfo<String> t) {
				result.onSuccess(t.result);
			}

		});
	}
	
	public static <T> T jsonToBean(String jsonResult, Class<T> clazz) {
		return gson.fromJson(jsonResult, clazz);
	}

}
