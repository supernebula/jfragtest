package com.evol.domain;

import lombok.Data;

@Data
public class AcSTA {

    private String deviceNumber;

    private String instruct;

    private String road;

    private String cardKey;

    private String orderId;

    private int ac;

    private int current;

    private int power;

    private int nowDeg;

    private int startDeg;

    private int useDeg;

    private int remainingTime;

    private int temperature1;

    private int temperature2;

    private int cpVoltage;

    private long time;

}
