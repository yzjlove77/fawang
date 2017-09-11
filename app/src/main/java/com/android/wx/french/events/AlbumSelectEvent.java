package com.android.wx.french.events;

import com.android.wx.french.model.Album;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/11.
 */

public class AlbumSelectEvent {

    private ArrayList<Album> albums;

    public AlbumSelectEvent(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }
}
