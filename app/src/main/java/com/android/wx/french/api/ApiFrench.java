package com.android.wx.french.api;

import android.database.Observable;

import com.android.wx.french.model.TestBodey;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/8.
 */

public interface ApiFrench {

    //    胡正伟:
//    http://116.62.162.52:6060/fwzx/rest/ListGridService/getListGrid
//    胡正伟:
//    http://116.62.162.52:6060/fwzx/rest/CommonService/post1

    public  String getListGrid = "ListGridService/getListGrid";

    public String post1 = "CommonService/post1";


//    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST(post1)
    Call<TestBodey> postAgencyLogin(@Body RequestBody route);//实现json格式的传输

    @POST(post1)
    Observable<TestBodey> getResult(@Body RequestBody route);



}
