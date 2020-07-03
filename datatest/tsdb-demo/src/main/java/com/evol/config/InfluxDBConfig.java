package com.evol.config;

import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class InfluxDBConfig {

    @Value("${spring.influx.user}")
    private String userName;

    @Value("${spring.influx.password}")
    private String password;

    @Value("${spring.influx.url}")
    private String url;

    private String database;

    private String retentionPolicy;

    private InfluxDB influxDB;

    static OkHttpClient.Builder client = new OkHttpClient.Builder()
            .readTimeout(200, TimeUnit.SECONDS);

    public InfluxDBConfig() {

    }

    public InfluxDBConfig(String userName, String password, String url, String database) {
        this.userName = userName;
        this.password = password;
        this.url = url;
        this.database = database;
        build();
    }

    public InfluxDBConfig(String database) {
        this.database = database;
        build();
    }

    private void build(){
        if(influxDB == null){
            influxDB = InfluxDBFactory.connect(this.url,this.userName,this.password, client);
        }
        influxDB.setDatabase(this.database);
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
    }

    @Bean
    public InfluxDB getInfluxDB() {
        build();
        return influxDB;
    }
}
