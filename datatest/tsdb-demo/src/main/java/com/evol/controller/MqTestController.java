package com.evol.controller;

import com.alibaba.fastjson.JSON;
import com.evol.domain.model.AccessAuditLog;
//import com.evol.service.IndicatorService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RequestMapping("/mqtest")
@RestController
public class MqTestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private IndicatorService indicatorService;

    @GetMapping("log")
    public String log(){

        Random random = new Random(System.currentTimeMillis());
        int milli = random.nextInt(4000);
        AccessAuditLog accessAuditLog = new AccessAuditLog();
        accessAuditLog.setServerName("data-test-server");
        accessAuditLog.setPath("/mqtest/log");
        accessAuditLog.setClientIp("127.0.0.1");
        accessAuditLog.setElapsedMilli(milli);

        String json = JSON.toJSONString(accessAuditLog);
        //rabbitTemplate.setChannelTransacted(false);
        rabbitTemplate.convertAndSend("audit.log.exchange", "access.audit", json);
        return json;
    }


    @GetMapping("audit")
    public String audit(){

        Random random = new Random(System.currentTimeMillis());
        int milli = random.nextInt(1000);
        AccessAuditLog accessAuditLog = new AccessAuditLog();
        accessAuditLog.setServerName("data-test-server");
        accessAuditLog.setPath("/mqtest/audit");
        accessAuditLog.setClientIp("127.0.0.1");
        accessAuditLog.setElapsedMilli(milli);

        String json = JSON.toJSONString(accessAuditLog);
        //rabbitTemplate.setChannelTransacted(false);
        rabbitTemplate.convertAndSend("audit.log.exchange", "access.audit", json);
        return json;
    }

//    @GetMapping("kafkaSend")
//    public String kafkaSend(){
//
//
//        Random random = new Random(System.currentTimeMillis());
//        int milli = random.nextInt(1000);
//        AccessAuditLog accessAuditLog = new AccessAuditLog();
//        accessAuditLog.setServerName("data-test-server");
//        accessAuditLog.setPath("/mqtest/kafkaSend");
//        accessAuditLog.setClientIp("127.0.0.1");
//        accessAuditLog.setElapsedMilli(milli);
//
//        String json = JSON.toJSONString(accessAuditLog);
//
//        indicatorService.sendMessage("audit-test-log", json);
//        return json;
//
//    }

}
