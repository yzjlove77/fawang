package com.android.wx.french.view;

import com.android.wx.french.model.GetCommentData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/10.
 */

public interface ICommentView {

    void showViewComment(ArrayList<GetCommentData> commentDatas);

    void failedViewComment(String msg);
}
