package com.android.wx.french.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.wx.french.R;
import com.android.wx.french.adapter.ReportAdapter;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.events.AlbumSelectEvent;
import com.android.wx.french.model.Album;
import com.android.wx.french.model.GetRewardData;
import com.android.wx.french.model.ReportBean;
import com.android.wx.french.model.ReportData;
import com.android.wx.french.presenter.ReportPresenter;
import com.android.wx.french.util.AbsolutePathUtil;
import com.android.wx.french.util.MLog;
import com.android.wx.french.util.PermissionHelper;
import com.android.wx.french.view.IReportView;
import com.android.wx.french.widget.popupwindow.PopupWindowPhoto;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.FileBody;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class ReportActivity extends BaseActivity<IReportView, ReportPresenter> implements IReportView, ReportAdapter.OnClickItemListener {

    @Bind(R.id.titlebar_name)
    TextView titleTitle;
    @Bind(R.id.report_image_recycler)
    RecyclerView imageRecycler;
    @Bind(R.id.report_video_recycler)
    RecyclerView videoRecycler;
    @Bind(R.id.report_content)
    EditText contentEt;
    @Bind(R.id.report_name)
    TextView nameTv;
    @Bind(R.id.report_idcard)
    TextView idcardTv;
    @Bind(R.id.report_description)
    EditText descriptionEt;

    private ReportAdapter imageAdapter, videoAdapter;
    private ArrayList<String> imageUrls, videoUrls;
    private LinearLayoutManager imageLayoutManager, videoLayoutManager;
    private PopupWindowPhoto popupWindowPhoto;
    private GetRewardData rewardData;
    private ArrayList<Album> albums, videoAlbums;

    @Override
    protected ReportPresenter createPresenter() {
        return new ReportPresenter(mContext);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_report;
    }

    @Override
    protected void initView() {
        super.initView();
        titleTitle.setText("提供线索");
        EventBus.getDefault().register(this);
        imageLayoutManager = new LinearLayoutManager(mContext);
        imageLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageRecycler.setLayoutManager(imageLayoutManager);
        albums = new ArrayList<>();
        albums.add(new Album());
        imageAdapter = new ReportAdapter(mContext, albums);
        imageRecycler.setAdapter(imageAdapter);
        imageAdapter.setOnClickItemListener(this);

        videoLayoutManager = new LinearLayoutManager(mContext);
        videoLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        videoRecycler.setLayoutManager(videoLayoutManager);
        videoAlbums = new ArrayList<>();
        videoAlbums.add(new Album());
        videoAdapter = new ReportAdapter(mContext, videoAlbums);
        videoRecycler.setAdapter(videoAdapter);
        videoAdapter.setAdapteType(1);
        videoAdapter.setOnClickItemListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        rewardData = (GetRewardData) bundle.getSerializable("rewardData");
        mPresenter.init();
    }

    @OnClick({R.id.titlebar_left, R.id.report_btn})
    public void onClickBk(View view) {
        switch (view.getId()) {
            case R.id.titlebar_left:
                onBackPressed();
                break;
            case R.id.report_btn:

                int size = albums.size();
//                String images = "1&,&/1/";
                for (int j = 0; j < size - 1; j++) {
                    Log.i("----kaishi----","图片开始上传");

                    HttpParams params = new BasicHttpParams();
                    /**
                     * 设置管理器最大连接数，连接数超过次数需要排队
                     */
                    ConnManagerParams.setMaxTotalConnections(params, 4000);
                    // 增加每个路由的默认最大连接到20-->200
                    ConnPerRouteBean connPerRoute = new ConnPerRouteBean(200);

                    // 对localhost:80增加最大连接到50	  连接端口设置
                    HttpHost localhost = new HttpHost("locahost", 8080);
                    connPerRoute.setMaxForRoute(new HttpRoute(localhost), 50);

                    ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);

                    SchemeRegistry schemeRegistry = new SchemeRegistry();

                    schemeRegistry.register(

                            new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

                    schemeRegistry.register(

                            new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

                    ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
                    File file = new File(albums.get(j).getImagePath());
                    HttpClient ht = new DefaultHttpClient();
                    //	HttpPost post = new HttpPost("http://localhost:8080/testAction/uploadServlet");
                    HttpPost post = new HttpPost("http://116.62.162.52:6060/fwzx/rest/CommonService/ReceiveFile2/" + file.getName());
                    HttpResponse rs = null;
                    try {
                        Log.i("---filepath---",file.getPath());
                        File testFile = new File(file.getPath());
                        post.setEntity(new InputStreamEntity(new FileInputStream(testFile), testFile.length()));
                        rs = ht.execute(post);
                        System.out.println("文件上传成功");
                        if (rs.getStatusLine().getStatusCode() == 200) // 200表示服务器成功响应
                        {
                            String s = EntityUtils.toString(rs.getEntity(), "utf-8");
                            Log.i("--resout--",s);
                            System.out.println(s);
                        }
                    }catch (Exception e){

                    }
                }

                String description = descriptionEt.getText().toString().trim();
                if (TextUtils.isEmpty(description)) {

                    return;
                }
                String content = contentEt.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {

                    return;
                }
                ReportData data = new ReportData();
                data.setTask_id(rewardData.getId());
                String reporterName = sph.getName();
                if (!TextUtils.isEmpty(reporterName)) {
                    data.setReport_man_name(reporterName);
                }
                String reporterNameIdCard = sph.getIdCard();
                if (!TextUtils.isEmpty(reporterNameIdCard)) {
                    data.setReport_man_idcard(reporterNameIdCard);
                }
                data.setClue_describe(description);
                data.setClue_content(content);
                String name = nameTv.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    data.setBe_report_man_name(name);
                }
                String idcard = idcardTv.getText().toString().trim();
                if (!TextUtils.isEmpty(idcard)) {
                    data.setBe_report_man_idcard(idcard);
                }
//               int size = albums.size();
                String images = "1&,&/1/";
                for (int i = 0; i < size - 1; i++) {

                   if (i == size - 2) {
                        images = images + albums.get(i).getImagePath();
                    } else {
                        images = images + albums.get(i).getImagePath() + "&;&1&,&/1/";
                    }
                }
                data.setListMedia(images);

                ReportBean bean = new ReportBean();
                bean.setRequestMethod("insertReport");
                bean.setData(data);
                mPresenter.report(bean);
                break;
        }
    }

    private void upFile(String path){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    upLoadByCommonPost(new File(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void upLoadByHttpClient4() throws ClientProtocolException,IOException {
        HttpClient httpclient=new DefaultHttpClient();
       httpclient.getParams().setParameter(
               CoreProtocolPNames.PROTOCOL_VERSION,
               HttpVersion.HTTP_1_1);

        HttpPost httppost = new HttpPost("");
        File  file= new File("");
        MultipartEntity entity=new MultipartEntity();
        FileBody fileBody=new FileBody(file);
       entity.addPart("uploadfile", fileBody);
        httppost.setEntity(entity);
        HttpResponse response= httpclient.execute(httppost);
        HttpEntity resEntity= response.getEntity();
        if (resEntity !=null){
            Log.i("0000",EntityUtils.toString(resEntity));
        }if (resEntity!=null){
            resEntity.consumeContent();
        }
        httpclient.getConnectionManager().shutdown();
    }




    private void upLoadByCommonPost(File uploadFile) throws IOException {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        String path = uploadFile.getAbsolutePath();
        String serverUrl = "http://116.62.162.52:6060/fwzx/rest/CommonService/ReceiveFile2/";
        java.net.URL url = new java.net.URL(serverUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url
                .openConnection();
        // 允许输入输出流
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        //POST方法
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("Charset", "UTF-8");
        httpURLConnection.setRequestProperty("Content-Type",
                "multipart/form-data;boundary=" + boundary);
        DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
        dos.writeBytes(twoHyphens + boundary + end);
        dos.writeBytes("Content-Disposition: form-data;filename=\""
                + path.substring(path.lastIndexOf("/") + 1) + "\"" + end);
        Log.i("---path--",path.substring(path.lastIndexOf("/")+1));
        dos.writeBytes(end);
        FileInputStream fis = new FileInputStream(path);
        byte[] buffer = new byte[8192]; // 8k
        int count = 0;
        // 读取文件
        while ((count = fis.read(buffer)) != -1) {
            dos.write(buffer, 0, count);
        }
        fis.close();
        dos.writeBytes(end);
        dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
        dos.flush();
        InputStream is = httpURLConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String result = br.readLine();
        Log.i("---上传图片-1--", result);
        if (result != null && !result.equals("")) {
            try {
                JSONObject obj = new JSONObject(result);
//                JSONObject ob = new JSONObject(obj.getString("data"));
//                JSONObject jsonObject = new JSONObject(ob.getString("photo"));
//                saveList.add("./Uploads/ocr/"+jsonObject.getString("savename"));
                Log.i("---上传图片---", obj.toString());

            } catch (JSONException e) {
                // TODO Auto-generated catch block
            }
        }
        dos.close();
        is.close();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AlbumSelectEvent event) {
        int type = event.getType();
        if (1 == type) {
            videoAlbums.addAll(videoAlbums.size() - 1, event.getAlbums());
            videoAdapter.notifyDataSetChanged();
        } else {
            albums.addAll(albums.size() - 1, event.getAlbums());
            imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickItem(View view, int position, int adapterType) {
        switch (view.getId()) {
            case R.id.item_report_image:
                if (1 == adapterType) {
                    if (position == videoAlbums.size() - 1) {
                        startActivity(new Intent(mContext, AlbumActivity.class).putExtra("start_type", 1));
                    }
                } else {
                    if (position == albums.size() - 1) {
                        boolean permission = PermissionHelper.getInstance(mContext).isPermission(mContext,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionHelper.PERMISSION_PHOTO);
                        MLog.mLog("permission = " + permission);
                        if (permission) {
                            showPopupPhoto();
                        }
                    }
                }
                break;
            case R.id.item_reduce_image:
                if (1 == adapterType) {
                    videoAlbums.remove(position);
                    videoAdapter.notifyItemRemoved(position);
                } else {
                    albums.remove(position);
                    imageAdapter.notifyItemRemoved(position);
                }
                break;
        }
    }

    private void showPopupPhoto() {
        if (popupWindowPhoto == null) {
            popupWindowPhoto = new PopupWindowPhoto(mContext);
        }
        popupWindowPhoto.showPopupWindow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PopupWindowPhoto.TAKE_PICTURE://拍照
                if (RESULT_OK == resultCode) {
                    Uri imageUri = popupWindowPhoto.getImageUri();
                    Album album = new Album();
                    album.setImagePath(AbsolutePathUtil.getAbsolutePath(this, imageUri));
                    albums.add(albums.size() - 1, album);
                    imageAdapter.notifyDataSetChanged();
                }
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionHelper.PERMISSION_PHOTO:
                if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    showPopupPhoto();
                } else {
                    showMissingPermissionDialog();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("缺少权限，快去设置吧");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    protected void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public void showViewReport(String msg) {
        showToast(msg);
        finish();
    }

    @Override
    public void failedViewReport(String msg) {
        showToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
