package com.evol.domain;

import lombok.Data;

import java.util.Date;

@Data
public class EBikePower {

    private String deviceNumber;

    private String instruct;

    private String road;

    private int power;

    private long time;

}
