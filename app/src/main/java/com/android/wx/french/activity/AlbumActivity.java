package com.android.wx.french.activity;

import android.database.Cursor;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wx.french.R;
import com.android.wx.french.adapter.AlbumAdapter;
import com.android.wx.french.base.BaseActivity;
import com.android.wx.french.base.BasePresenter;
import com.android.wx.french.events.AlbumSelectEvent;
import com.android.wx.french.model.Album;
import com.android.wx.french.util.MLog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class AlbumActivity extends BaseActivity {

    @Bind(R.id.titlebar_name)
    TextView titleTitle;
    @Bind(R.id.titlebar_rigth)
    TextView titleRightTv;
    @Bind(R.id.album_recycler)
    RecyclerView albumRecycler;

    private GridLayoutManager layoutManager;
    private ArrayList<Album> albums;
    private AlbumAdapter albumAdapter;
    private Handler mHandler;
    private int startType;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_album;
    }

    @Override
    protected void initView() {
        super.initView();
        titleTitle.setText("图片");
        titleRightTv.setVisibility(View.VISIBLE);
        titleRightTv.setText("确定");
        mHandler = new Handler();
        layoutManager = new GridLayoutManager(mContext, 3);
        albumRecycler.setLayoutManager(layoutManager);
        albums = new ArrayList<>();
        albumAdapter = new AlbumAdapter(mContext, albums);
        albumRecycler.setAdapter(albumAdapter);

        startType = getIntent().getIntExtra("start_type", 0);
    }

    @Override
    protected void initData() {
        super.initData();
        final String[] columns = {MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        if (1 == startType) {
            Cursor videoCursor = this
                    .getApplicationContext()
                    .getContentResolver()
                    .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns,
                            null, null, orderBy + " DESC");
            for (int i = 0; i < videoCursor.getCount(); i++) {
                videoCursor.moveToPosition(i);
                int dataColumnIndex = videoCursor
                        .getColumnIndex(MediaStore.Video.Media.DATA);
                String videoUrl = videoCursor.getString(dataColumnIndex);
                MLog.mLog("videoUrl = " + videoUrl);
                Album album = new Album();
                album.setSelected(false);
                album.setImagePath(videoUrl);
                albums.add(album);
            }
        }
        if (0 == startType) {
            Cursor imageCursor = this
                    .getApplicationContext()
                    .getContentResolver()
                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                            null, null, orderBy + " DESC");
            for (int i = 0; i < imageCursor.getCount(); i++) {
                imageCursor.moveToPosition(i);
                int dataColumnIndex = imageCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA);
                String imageUrl = imageCursor.getString(dataColumnIndex);
                Album album = new Album();
                album.setSelected(false);
                album.setImagePath(imageUrl);
                albums.add(album);
            }
        }
        postAndNotifyAdapter(mHandler, albumRecycler, albumAdapter);
    }

    @OnClick({R.id.titlebar_left, R.id.titlebar_rigth})
    public void onClickBk(View view) {
        switch (view.getId()) {
            case R.id.titlebar_left:
                onBackPressed();
                break;
            case R.id.titlebar_rigth:
                int size = albums.size();
                ArrayList<Album> selectedAlbums = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    Album album = albums.get(i);
                    if (album.isSelected()) {
                        selectedAlbums.add(album);
                    }
                }
                if (selectedAlbums.size() == 0) {
                    Toast.makeText(this, "请选择要发送的图片", Toast.LENGTH_SHORT).show();
                } else {
                    AlbumSelectEvent event = new AlbumSelectEvent(selectedAlbums);
                    event.setType(startType);
                    EventBus.getDefault().post(event);
                    finish();
                }
                break;
        }
    }

    private void postAndNotifyAdapter(final Handler handler, final RecyclerView recyclerView, final RecyclerView.Adapter adapter) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!recyclerView.isComputingLayout()) {
                    adapter.notifyDataSetChanged();
                } else {
                    postAndNotifyAdapter(handler, recyclerView, adapter);
                }
            }
        });
    }
}
