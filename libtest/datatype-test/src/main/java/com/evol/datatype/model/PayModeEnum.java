package com.evol.datatype.model;

/**
 * 支付方式类型
 */
public enum PayModeEnum {


    ALIPAY(1, "支付宝"),
    WECHAT(2, "微信"),
    BALANCE(3, "余额"),
    COIN(4, "投币"),
    OFFLINE_CARD(5, "线下卡"),
    FREE_RIGHT(6, "免费特权"),
    ONLINE_CARD(7, "线上卡");


    private int code;
    private String name;

    PayModeEnum(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
