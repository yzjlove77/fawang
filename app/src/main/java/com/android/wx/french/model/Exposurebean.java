package com.android.wx.french.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class Exposurebean implements Serializable {


    /**
     * RequestMethod : getSxbzxr
     * data : {"fbrq_ks":"2016-01-31","fbrq_js":"2016-2-15"}
     * cols : ["sanh","sabh","fydm","fymc","ah","sxbzxrmc","sxbzxrxh","sxbzxrzjhm","sxid","fbrq","flwsqdyw","bzxrlxqk","type","ip","czyh","bz","czrq","lx","cjjlsj","cbr_name","cbr_mail","cbr_userid","cbr_sjhm"]
     * pageSize : 2
     * curPage : 1
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
         * fbrq_ks : 2016-01-31
         * fbrq_js : 2016-2-15
         */

        private String fbrq_ks;
        private String fbrq_js;

        public String getFbrq_ks() {
            return fbrq_ks;
        }

        public void setFbrq_ks(String fbrq_ks) {
            this.fbrq_ks = fbrq_ks;
        }

        public String getFbrq_js() {
            return fbrq_js;
        }

        public void setFbrq_js(String fbrq_js) {
            this.fbrq_js = fbrq_js;
        }
    }
}
