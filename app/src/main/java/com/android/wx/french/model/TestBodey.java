package com.android.wx.french.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class TestBodey implements Serializable{

    /**
     * RequestMethod : getAuditLoggingById
     * data : {"id":"1"}
     * cols : ["audit_fg_name","audit_fg_userid","reason","audit_results","task_id","audit_completion_time","audit_results_name"]
     */

    private String RequestMethod;
    private DataBean data;
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

    public List<String> getCols() {
        return cols;
    }

    public void setCols(List<String> cols) {
        this.cols = cols;
    }

    public static class DataBean {
        /**
         * id : 1
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    @Override
    public String toString() {
        return "TestBodey{" +
                "RequestMethod='" + RequestMethod + '\'' +
                ", data=" + data +
                ", cols=" + cols +
                '}';
    }

}
