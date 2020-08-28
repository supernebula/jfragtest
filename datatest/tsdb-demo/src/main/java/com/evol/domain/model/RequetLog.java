package com.evol.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

/**
 * 最近10次访问时间
 */
@Data
@Measurement(name = "request_log")
public class RequetLog {

    @JSONField(name = "server_name")
    @Column(name = "server_name", tag = true)
    private String serverName;

    @JSONField(name = "path")
    @Column(name = "path", tag = true)
    private String path;

    @JSONField(name = "client_ip")
    @Column(name = "client_ip", tag = true)
    private String clientIp;

    @JSONField(name = "elapsed_milli")
    @Column(name = "elapsed_milli")
    private int elapsedMilli;


}
