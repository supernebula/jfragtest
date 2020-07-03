package com.evol.model;


import lombok.Data;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Measurement(name = "car_ac_sta_small")
@Data
public class CarAcStatusSmall {

    @Column(name = "time")
    private Instant time;

    @Column(name = "device_number")
    private String deviceNumber;


    @Column(name = "road")
    private String road;



    @Column(name = "ac")
    private Long ac;

    @Column(name = "current")
    private Long current;

    @Column(name = "power")
    private Long power;

    @Column(name = "nowDeg")
    private Long nowDeg;

    @Column(name = "startDeg")
    private Long startDeg;

    @Column(name = "useDeg")
    private Long useDeg;

    @Column(name = "remainingTime")
    private Long remainingTime;

    @Column(name = "temperature1")
    private Long temperature1;

    @Column(name = "temperature2")
    private Long temperature2;

    @Column(name = "cpVoltage")
    private Long cpVoltage;
}
