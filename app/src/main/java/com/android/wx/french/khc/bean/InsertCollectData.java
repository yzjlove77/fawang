package com.android.wx.french.khc.bean;

/**
 * Created by KangHuiCong on 2017/9/11.
 * e-mail:515849594@qq.com
 */

public class InsertCollectData{
    int user_type;
    String userid;
    String username;
    int flow_with_task_id;
    String flow_with_task_title;

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFlow_with_task_id() {
        return flow_with_task_id;
    }

    public void setFlow_with_task_id(int flow_with_task_id) {
        this.flow_with_task_id = flow_with_task_id;
    }

    public String getFlow_with_task_title() {
        return flow_with_task_title;
    }

    public void setFlow_with_task_title(String flow_with_task_title) {
        this.flow_with_task_title = flow_with_task_title;
    }
}
