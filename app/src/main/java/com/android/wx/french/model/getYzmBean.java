package com.android.wx.french.model;

/**
 * Created by Administrator on 2017/9/13.
 */

public class getYzmBean  {


    /**
     * RequestMethod : getYzm
     * data : {"phone":"15706804781","idcard":"360731199508164918","name":"方小平"}
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
         * phone : 15706804781
         * idcard : 360731199508164918
         * name : 方小平
         */

        private String phone;
        private String idcard;
        private String name;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
