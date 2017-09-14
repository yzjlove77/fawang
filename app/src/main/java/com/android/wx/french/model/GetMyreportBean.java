package com.android.wx.french.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class GetMyreportBean {


    /**
     * RequestMethod : getReportList_xxByIdcard
     * data : {"report_man_idcard":"360731198912054516"}
     * pageSize : 10
     * curPage : 1
     * cols : ["id","uuid","report_man_name","report_man_idcard","be_report_man_name","be_report_man_idcard","clue_describe","clue_content","is_sure","is_valid","mark","money"]
     */

    private String RequestMethod;
    private DataBean data;
    private String pageSize;
    private String curPage;
    private List<String> cols;

    public String getRequestMethod() {
        return RequestMethod;
    }

    public void setRequestMethod(String RequestMethod) {
        this.RequestMethod = RequestMethod;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public List<String> getCols() {
        return cols;
    }

    public void setCols(List<String> cols) {
        this.cols = cols;
    }

    public static class DataBean {
        /**
         * report_man_idcard : 360731198912054516
         */

        private String report_man_idcard;
        private String cjjlsj_ks;

        public String getCjjlsj_ks() {
            return cjjlsj_ks;
        }

        public void setCjjlsj_ks(String cjjlsj_ks) {
            this.cjjlsj_ks = cjjlsj_ks;
        }

        public String getReport_man_idcard() {
            return report_man_idcard;
        }

        public void setReport_man_idcard(String report_man_idcard) {
            this.report_man_idcard = report_man_idcard;
        }
    }
}
