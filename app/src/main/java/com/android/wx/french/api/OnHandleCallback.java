package com.android.wx.french.api;


/**
 * 将数据处理逻辑和界面交换逻辑解耦，通过回调接口做数据传递；
 * 注意回调函数必须在主线程执行
 * @author wxzhang
 * 2015.11.12
 */
public interface OnHandleCallback {

	/**
	 * 处理成功的回调
	 */
	void onSuccess(String json);

	/**
	 * 处理失败的回调
	 */
	void onFailure(String json);


}
