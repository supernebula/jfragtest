package com.evol.domain.model;

import lombok.Builder;
import lombok.Data;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

/**
 * 最近10次访问时间
 */
@Data
@Measurement(name = "access_audit_log")
public class AccessAuditLog {

    @Column(name = "server_name", tag = true)
    private String serverName;

    @Column(name = "path", tag = true)
    private String path;

    @Column(name = "client_ip", tag = true)
    private String clientIp;

    @Column(name = "elapsed_milli")
    private int elapsedMilli;


}
