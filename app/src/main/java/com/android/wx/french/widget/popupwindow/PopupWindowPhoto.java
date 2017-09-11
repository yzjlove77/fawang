package com.android.wx.french.widget.popupwindow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.android.wx.french.R;
import com.android.wx.french.activity.AlbumActivity;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/8.
 */

public class PopupWindowPhoto extends BasePopupWindow {

    @Bind(R.id.popup_layout)
    LinearLayout popupLayout;

    public static final int RESULT_LOAD_IMAGE = 1;
    public static final int TAKE_PICTURE = 2;
    public static final int CUT_PHOTO_REQUEST_CODE = 3;
    private Uri imageUri;
    private boolean isProfileImage;
    private int maxImages;

    public PopupWindowPhoto(Activity context) {
        super(context);
    }

    public PopupWindowPhoto(Activity context, int w, int h) {
        super(context, w, h);
    }

    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    public void setProfileImage(boolean profileImage) {
        isProfileImage = profileImage;
    }

    @Override
    public Animation getShowAnimation() {
        Animation anima = new AlphaAnimation(0.0f, 1.0f);
        anima.setDuration(400);
        return anima;
    }

    @Override
    public View getPopupView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_photo_layout, null);
        return view;
    }

    @Override
    public View getAnimView() {
        return popupLayout;
    }

    @OnClick({R.id.popup_camera, R.id.popup_album, R.id.popup_cancel})
    public void onClick(View view){
        switch (view.getId()) {
            //拍照
            case R.id.popup_camera:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String sdcardState = Environment.getExternalStorageState();
                String sdcardPathDir = Environment.getExternalStorageDirectory().getPath() + "/French/temp/tempImage/";
                File file = null;
                if (Environment.MEDIA_MOUNTED.endsWith(sdcardState)) {
                    // 有sd卡，是否有myImage文件夹
                    File fileDir = new File(sdcardPathDir);
                    if (!fileDir.exists()) {
                        fileDir.mkdirs();
                    }
                    // 是否有headImg文件s
                    file = new File(sdcardPathDir + System.currentTimeMillis()
                            + ".JPEG");
                }
                if (file != null) {
                    imageUri = Uri.fromFile(file);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    mContext.startActivityForResult(cameraIntent, TAKE_PICTURE);
                }
                break;
            //相册
            case R.id.popup_album:
                if (isProfileImage) {
                    File outputImage = new File(Environment.getExternalStorageDirectory().getPath() + "/French/temp/tempImage/",
                            "output_image.jpg");
                    imageUri = Uri.fromFile(outputImage);

                    try {
                        if (outputImage.exists()) {
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(Intent.ACTION_PICK,null);
                    //此处调用了图片选择器
                    //如果直接写intent.setDataAndType("image/*");
                    //调用的是系统图库
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    mContext.startActivityForResult(intent, RESULT_LOAD_IMAGE);
                    dismiss();
                } else {
                    mContext.startActivity(new Intent(mContext, AlbumActivity.class).putExtra("maxImages", maxImages));
                }
                break;
            //取消
            case R.id.popup_cancel:
                break;
        }
        dismiss();
    }

    public void cutPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 1024);
        intent.putExtra("outputY", 1024);

        intent.putExtra("outputFormat", "JPEG");    // 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", false);

        imageUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        mContext.startActivityForResult(intent, PopupWindowPhoto.CUT_PHOTO_REQUEST_CODE);
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
