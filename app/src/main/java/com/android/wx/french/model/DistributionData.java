package com.android.wx.french.model;

/**
 * Created by Administrator on 2017/9/11.
 */

public class DistributionData {


    /**
     * id : 3
     * is_sure : 0
     * is_valid : 0
     * mark : 12
     * money : 1000
     */

    private String id;
    private String is_sure;
    private String is_valid;
    private String mark;
    private String money;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_sure() {
        return is_sure;
    }

    public void setIs_sure(String is_sure) {
        this.is_sure = is_sure;
    }

    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
