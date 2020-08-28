//package com.evol.listener;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.evol.domain.model.AccessAuditLog;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.security.sasl.SaslServer;
//import java.util.Date;
//
//@Component
//@Slf4j
//public class AuditLogConsumer {
//    /**
//     * 指令下发队列相关常量
//     */
//    public static final String INSTRUCT_LOG_EXCHANGE = "audit.log.exchange";
//    public static final String INSTRUCT_LOG_QUEUE = "audit-log-queue";
//    public static final String INSTRUCT_LOG_KEY = "access.audit";
//
//
//
//    @RabbitListener(bindings = {
//            @QueueBinding(value = @Queue(INSTRUCT_LOG_QUEUE), exchange = @Exchange(INSTRUCT_LOG_EXCHANGE), key = {INSTRUCT_LOG_KEY})
//    })
//    public void consumeLog(String message) {
//        AccessAuditLog instructLog = JSONObject.parseObject(message, AccessAuditLog.class);
//        try {
//            String auditStr = JSON.toJSONString(instructLog);
//            System.out.println(auditStr + "     " + System.currentTimeMillis());
//        } catch (Exception e) {
//            log.error("记录日志报错，日志将记录到文件中", e);
//            log.info("记录日志到文件，{}", message);
//        }
//    }
//}
