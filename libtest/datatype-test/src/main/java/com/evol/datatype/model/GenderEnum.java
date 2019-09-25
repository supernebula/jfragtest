package com.evol.datatype.model;

public enum GenderEnum {

    MALE(0, "男"),
    FEMALE(1,"女");

    private int code;
    private String name;

    GenderEnum(int code, String name){
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
