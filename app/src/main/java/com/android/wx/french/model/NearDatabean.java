package com.android.wx.french.model;

/**
 * Created by Administrator on 2017/9/12.
 */

public class NearDatabean {


    /**
     * RequestMethod : getNearBzxrxxByJbrzb
     * data : {"bzxr_adress_lng":"120.219375","bzxr_adress_lat":"30.259244"}
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
         * bzxr_adress_lng : 120.219375
         * bzxr_adress_lat : 30.259244
         */

        private String bzxr_adress_lng;
        private String bzxr_adress_lat;

        public String getBzxr_adress_lng() {
            return bzxr_adress_lng;
        }

        public void setBzxr_adress_lng(String bzxr_adress_lng) {
            this.bzxr_adress_lng = bzxr_adress_lng;
        }

        public String getBzxr_adress_lat() {
            return bzxr_adress_lat;
        }

        public void setBzxr_adress_lat(String bzxr_adress_lat) {
            this.bzxr_adress_lat = bzxr_adress_lat;
        }
    }
}
