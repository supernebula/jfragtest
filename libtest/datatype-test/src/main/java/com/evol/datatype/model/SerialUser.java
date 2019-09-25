package com.evol.datatype.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SerialUser implements Serializable {

    private String name;
    private int age;
    private Double money;
    private boolean enabled;
    private GenderEnum gender;
    private Date createTime;
}
