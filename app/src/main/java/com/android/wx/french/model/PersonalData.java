package com.android.wx.french.model;

/**
 * Created by Administrator on 2017/9/9.
 */

public class PersonalData {

    /**
     * idcard : 360731198912054516
     * name : 李四
     * nickname : 小李
     * phone : 15666554545
     * bank_Card : 6222021001116245702
     * alipayNo : 6222021001116245702
     */

    private String idcard;
    private String name;
    private String nickname;
    private String phone;
    private String bank_Card;
    private String alipayNo;
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank_Card() {
        return bank_Card;
    }

    public void setBank_Card(String bank_Card) {
        this.bank_Card = bank_Card;
    }

    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
    }
}
