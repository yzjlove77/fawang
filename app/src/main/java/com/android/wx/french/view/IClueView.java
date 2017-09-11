package com.android.wx.french.view;

import com.android.wx.french.model.GetClueData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface IClueView {

    void showViewClue(ArrayList<GetClueData> clueDatas);

    void failedViewClue(String msg);
}
