package com.android.wx.french.model;

/**
 * Created by Administrator on 2017/9/13.
 */

public class ValidateYzmBean {

    /**
     * RequestMethod : validateYzm
     * data : {"yzm":"466629","idcard":"360731199508164918"}
     */

    private String RequestMethod;
    private DataBean data;

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

    public static class DataBean {
        /**
         * yzm : 466629
         * idcard : 360731199508164918
         */

        private String yzm;
        private String idcard;

        public String getYzm() {
            return yzm;
        }

        public void setYzm(String yzm) {
            this.yzm = yzm;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }
    }
}
