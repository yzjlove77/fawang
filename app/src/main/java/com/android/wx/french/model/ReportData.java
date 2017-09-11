package com.android.wx.french.model;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ReportData {


    /**
     * task_id : 16
     * report_man_name : 张三
     * report_man_idcard : 360731198912054516
     * be_report_man_name : asd
     * be_report_man_idcard : 330324199402127253
     * clue_describe : 举报描述
     * clue_content : 描述举报jub描述举报举报xx
     * listMedia : 1&,&/1/upload_temp/2015-10-09/1/公司名称变更.jpg&;&2&,&/1/upload_temp/2015-10-09/1/公司名称变更.jpg
     */

    private String task_id;
    private String report_man_name;
    private String report_man_idcard;
    private String be_report_man_name;
    private String be_report_man_idcard;
    private String clue_describe;
    private String clue_content;
    private String listMedia;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getReport_man_name() {
        return report_man_name;
    }

    public void setReport_man_name(String report_man_name) {
        this.report_man_name = report_man_name;
    }

    public String getReport_man_idcard() {
        return report_man_idcard;
    }

    public void setReport_man_idcard(String report_man_idcard) {
        this.report_man_idcard = report_man_idcard;
    }

    public String getBe_report_man_name() {
        return be_report_man_name;
    }

    public void setBe_report_man_name(String be_report_man_name) {
        this.be_report_man_name = be_report_man_name;
    }

    public String getBe_report_man_idcard() {
        return be_report_man_idcard;
    }

    public void setBe_report_man_idcard(String be_report_man_idcard) {
        this.be_report_man_idcard = be_report_man_idcard;
    }

    public String getClue_describe() {
        return clue_describe;
    }

    public void setClue_describe(String clue_describe) {
        this.clue_describe = clue_describe;
    }

    public String getClue_content() {
        return clue_content;
    }

    public void setClue_content(String clue_content) {
        this.clue_content = clue_content;
    }

    public String getListMedia() {
        return listMedia;
    }

    public void setListMedia(String listMedia) {
        this.listMedia = listMedia;
    }
}
