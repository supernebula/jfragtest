package com.evol.controller;

import org.influxdb.InfluxDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/influx")
public class InfluxDBController {

//    @Value("${spring.influx.user}")
//    private String userName;
//
//    @Value("${spring.influx.password}")
//    private String password;
//
//    @Value("${spring.influx.url}")
//    private String url;
//
//
//    @Autowired
//    private InfluxDB influxDB;


    @GetMapping("/insert")
    public void insert(){

    }


}
