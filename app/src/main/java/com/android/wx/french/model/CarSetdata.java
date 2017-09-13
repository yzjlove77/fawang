package com.android.wx.french.model;

/**
 * Created by Administrator on 2017/9/12.
 */

public class CarSetdata  {


    /**
     * RequestMethod : viewClbkxxByCphm
     * data : {"cphm":"浙DKS079"}
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
         * cphm : 浙DKS079
         */

        private String cphm;

        public String getCphm() {
            return cphm;
        }

        public void setCphm(String cphm) {
            this.cphm = cphm;
        }
    }
}
