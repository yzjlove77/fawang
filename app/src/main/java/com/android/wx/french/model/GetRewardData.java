package com.android.wx.french.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/8.
 */

public class GetRewardData implements Serializable {


    /**
     * xh : 1
     * is_gyxs : 0
     * reward_details : 悬赏任务详情
     * type_of_reward : 1
     * id : 9
     * type_of_task : 1
     * title : 悬赏标题
     * bzxr_dt : 被执行人动态
     * fb_fg_name :
     * task_completion_time :
     * the_creat_time : 2017-09-03 15:34:58
     * js_fybm1 : 1301
     * bzxr_hj : 被执行人户籍信息
     * bzxr_adress_jwzb: 被执行人-常用地址-经纬坐标
     * bzxr_photo_path : /1/upload_temp/2017-07-21/353/照片.jpg
     * fbdsr_name : 王美丽
     * fb_fg_userid :
     * type_of_task_mc : 社会发布
     * fb_fybm1 :
     * completion_status : 0
     * task_expiration_time : 2017-09-28
     * is_sqgffb : 0
     * reward_amount : 5000
     * js_fymc : 杭州杭铁
     * hava_been_paid : 0
     * fbdsr_sjhm : 17671771889
     * bzxr_specialty : 被执行人个性信息
     * fbdsr_sfzh : 421081188906080761
     * task_demand : 悬赏任务要求
     * task_release_time :
     * fb_fymc :
     * audit_status : 0
     * bzxr_id : 11
     */

    private String xh;
    private String is_gyxs;
    private String reward_details;
    private String type_of_reward;
    private String id;
    private String type_of_task;
    private String title;
    private String bzxr_dt;
    private String fb_fg_name;
    private String task_completion_time;
    private String the_creat_time;
    private String js_fybm1;
    private String bzxr_hj;
    private String bzxr_adress_jwzb;
    private String bzxr_photo_path;
    private String fbdsr_name;
    private String fb_fg_userid;
    private String type_of_task_mc;
    private String fb_fybm1;
    private String completion_status;
    private String bzxr_adress;
    private String task_expiration_time;
    private String is_sqgffb;
    private String reward_amount;
    private String js_fymc;
    private String bzxr_idcard;
    private String hava_been_paid;
    private String fbdsr_sjhm;
    private String bzxr_specialty;
    private String fbdsr_sfzh;
    private String task_demand;
    private String task_release_time;
    private String fb_fymc;
    private String bzxlx;
    private String audit_status;

    @Override
    public String toString() {
        return "GetRewardData{" +
                "xh='" + xh + '\'' +
                ", is_gyxs='" + is_gyxs + '\'' +
                ", reward_details='" + reward_details + '\'' +
                ", type_of_reward='" + type_of_reward + '\'' +
                ", id='" + id + '\'' +
                ", type_of_task='" + type_of_task + '\'' +
                ", title='" + title + '\'' +
                ", bzxr_dt='" + bzxr_dt + '\'' +
                ", fb_fg_name='" + fb_fg_name + '\'' +
                ", task_completion_time='" + task_completion_time + '\'' +
                ", the_creat_time='" + the_creat_time + '\'' +
                ", js_fybm1='" + js_fybm1 + '\'' +
                ", bzxr_hj='" + bzxr_hj + '\'' +
                ", bzxr_adress_jwzb='" + bzxr_adress_jwzb + '\'' +
                ", bzxr_photo_path='" + bzxr_photo_path + '\'' +
                ", fbdsr_name='" + fbdsr_name + '\'' +
                ", fb_fg_userid='" + fb_fg_userid + '\'' +
                ", type_of_task_mc='" + type_of_task_mc + '\'' +
                ", fb_fybm1='" + fb_fybm1 + '\'' +
                ", completion_status='" + completion_status + '\'' +
                ", bzxr_adress='" + bzxr_adress + '\'' +
                ", task_expiration_time='" + task_expiration_time + '\'' +
                ", is_sqgffb='" + is_sqgffb + '\'' +
                ", reward_amount='" + reward_amount + '\'' +
                ", js_fymc='" + js_fymc + '\'' +
                ", bzxr_idcard='" + bzxr_idcard + '\'' +
                ", hava_been_paid='" + hava_been_paid + '\'' +
                ", fbdsr_sjhm='" + fbdsr_sjhm + '\'' +
                ", bzxr_specialty='" + bzxr_specialty + '\'' +
                ", fbdsr_sfzh='" + fbdsr_sfzh + '\'' +
                ", task_demand='" + task_demand + '\'' +
                ", task_release_time='" + task_release_time + '\'' +
                ", fb_fymc='" + fb_fymc + '\'' +
                ", bzxlx='" + bzxlx + '\'' +
                ", audit_status='" + audit_status + '\'' +
                '}';
    }

    public String getBzxr_adress_jwzb() {
        return bzxr_adress_jwzb;
    }

    public void setBzxr_adress_jwzb(String bzxr_adress_jwzb) {
        this.bzxr_adress_jwzb = bzxr_adress_jwzb;
    }

    public String getBzxr_adress() {
        return bzxr_adress;
    }

    public void setBzxr_adress(String bzxr_adress) {
        this.bzxr_adress = bzxr_adress;
    }

    public String getBzxr_idcard() {
        return bzxr_idcard;
    }

    public void setBzxr_idcard(String bzxr_idcard) {
        this.bzxr_idcard = bzxr_idcard;
    }

    public String getBzxlx() {
        return bzxlx;
    }

    public void setBzxlx(String bzxlx) {
        this.bzxlx = bzxlx;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getIs_gyxs() {
        return is_gyxs;
    }

    public void setIs_gyxs(String is_gyxs) {
        this.is_gyxs = is_gyxs;
    }

    public String getReward_details() {
        return reward_details;
    }

    public void setReward_details(String reward_details) {
        this.reward_details = reward_details;
    }

    public String getType_of_reward() {
        return type_of_reward;
    }

    public void setType_of_reward(String type_of_reward) {
        this.type_of_reward = type_of_reward;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_of_task() {
        return type_of_task;
    }

    public void setType_of_task(String type_of_task) {
        this.type_of_task = type_of_task;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBzxr_dt() {
        return bzxr_dt;
    }

    public void setBzxr_dt(String bzxr_dt) {
        this.bzxr_dt = bzxr_dt;
    }

    public String getFb_fg_name() {
        return fb_fg_name;
    }

    public void setFb_fg_name(String fb_fg_name) {
        this.fb_fg_name = fb_fg_name;
    }

    public String getTask_completion_time() {
        return task_completion_time;
    }

    public void setTask_completion_time(String task_completion_time) {
        this.task_completion_time = task_completion_time;
    }

    public String getThe_creat_time() {
        return the_creat_time;
    }

    public void setThe_creat_time(String the_creat_time) {
        this.the_creat_time = the_creat_time;
    }

    public String getJs_fybm1() {
        return js_fybm1;
    }

    public void setJs_fybm1(String js_fybm1) {
        this.js_fybm1 = js_fybm1;
    }

    public String getBzxr_hj() {
        return bzxr_hj;
    }

    public void setBzxr_hj(String bzxr_hj) {
        this.bzxr_hj = bzxr_hj;
    }

    public String getBzxr_photo_path() {
        return bzxr_photo_path;
    }

    public void setBzxr_photo_path(String bzxr_photo_path) {
        this.bzxr_photo_path = bzxr_photo_path;
    }

    public String getFbdsr_name() {
        return fbdsr_name;
    }

    public void setFbdsr_name(String fbdsr_name) {
        this.fbdsr_name = fbdsr_name;
    }

    public String getFb_fg_userid() {
        return fb_fg_userid;
    }

    public void setFb_fg_userid(String fb_fg_userid) {
        this.fb_fg_userid = fb_fg_userid;
    }

    public String getType_of_task_mc() {
        return type_of_task_mc;
    }

    public void setType_of_task_mc(String type_of_task_mc) {
        this.type_of_task_mc = type_of_task_mc;
    }

    public String getFb_fybm1() {
        return fb_fybm1;
    }

    public void setFb_fybm1(String fb_fybm1) {
        this.fb_fybm1 = fb_fybm1;
    }

    public String getCompletion_status() {
        return completion_status;
    }

    public void setCompletion_status(String completion_status) {
        this.completion_status = completion_status;
    }

    public String getTask_expiration_time() {
        return task_expiration_time;
    }

    public void setTask_expiration_time(String task_expiration_time) {
        this.task_expiration_time = task_expiration_time;
    }

    public String getIs_sqgffb() {
        return is_sqgffb;
    }

    public void setIs_sqgffb(String is_sqgffb) {
        this.is_sqgffb = is_sqgffb;
    }

    public String getReward_amount() {
        return reward_amount;
    }

    public void setReward_amount(String reward_amount) {
        this.reward_amount = reward_amount;
    }

    public String getJs_fymc() {
        return js_fymc;
    }

    public void setJs_fymc(String js_fymc) {
        this.js_fymc = js_fymc;
    }

    public String getHava_been_paid() {
        return hava_been_paid;
    }

    public void setHava_been_paid(String hava_been_paid) {
        this.hava_been_paid = hava_been_paid;
    }

    public String getFbdsr_sjhm() {
        return fbdsr_sjhm;
    }

    public void setFbdsr_sjhm(String fbdsr_sjhm) {
        this.fbdsr_sjhm = fbdsr_sjhm;
    }

    public String getBzxr_specialty() {
        return bzxr_specialty;
    }

    public void setBzxr_specialty(String bzxr_specialty) {
        this.bzxr_specialty = bzxr_specialty;
    }

    public String getFbdsr_sfzh() {
        return fbdsr_sfzh;
    }

    public void setFbdsr_sfzh(String fbdsr_sfzh) {
        this.fbdsr_sfzh = fbdsr_sfzh;
    }

    public String getTask_demand() {
        return task_demand;
    }

    public void setTask_demand(String task_demand) {
        this.task_demand = task_demand;
    }

    public String getTask_release_time() {
        return task_release_time;
    }

    public void setTask_release_time(String task_release_time) {
        this.task_release_time = task_release_time;
    }

    public String getFb_fymc() {
        return fb_fymc;
    }

    public void setFb_fymc(String fb_fymc) {
        this.fb_fymc = fb_fymc;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }
}
