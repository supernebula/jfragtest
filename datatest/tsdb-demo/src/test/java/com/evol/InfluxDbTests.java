package com.evol;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//https://github.com/influxdata/influxdb-java
//https://github.com/HaiyangLiang/springboot2-influxdb-demo/blob/master/src/main/java/com/influxdb/demo/service/InfluxService.java
@RunWith(SpringRunner.class)
@SpringBootTest
public class InfluxDbTests {

    @Value("${spring.influx.user}")
    private String userName;

    @Value("${spring.influx.password}")
    private String password;

    @Value("${spring.influx.url}")
    private String url;

    private String databaseName = "dddd";




    @Autowired
    private InfluxDB influxDB;


    @Test
    public void contextLoads() {
    }

    @Test
    public void createDatabaseTest(){
        influxDB.query(new Query("CREATE DATABASE " + databaseName));
        influxDB.setDatabase(databaseName);

    }

    @Test
    public void deleteDatabaseTest(){
        influxDB.query(new Query("DROP DATABASE " + databaseName));
        influxDB.setDatabase(databaseName);
    }

    @Test
    public void showDatabaseTest(){
        QueryResult result = influxDB.query(new Query("SHOW DATABASES"));
        //log.info(result.toString());
        System.out.println(result.toString());
    }

    @Test
    public void insertPointTest() throws InterruptedException {
        String measurement = "car_battery";

        Random random = new Random();
        influxDB.setDatabase(databaseName);

        for (int i = 0; i < 100; i++){
            influxDB.write(Point.measurement(measurement)
                    .tag("device_number", "800009")
                    .tag("customer_code", "0")
                    .tag("device_ways", "1")
                    .tag("order_id", "21071")
                    .tag("bat_type", "6")
                    .tag("vin", "00060026F00060026")
                    .addField("rvol", 4128 + random.nextInt(1000))
                    .addField("rcur", 948 + random.nextInt(100))
                    .addField("ptemp", 0 + random.nextInt(10))
                    .addField("ntemp", 0 + random.nextInt(10))
                    .addField("bat_pow", 700 + random.nextInt(100))
                    .addField("capacity", 0)
                    .addField("max_charge_vol", 4128 + random.nextInt(200))
                    .addField("max_charge_cur", 2500 + random.nextInt(500))
                    .addField("sigle_max_vol", 367 + random.nextInt(100))
                    .addField("sigle_min_vol", 357 + random.nextInt(100))
                    .addField("start_sigle_max_vol", 357 + random.nextInt(100))
                    .addField("start_sigle_min_vol", 4128 + random.nextInt(800))
                    .addField("end_sigle_max_vol", 2500 + random.nextInt(700))
                    .addField("end_sigle_min_vol", 367 + random.nextInt(100))
                    .addField("max_temp", 58 + random.nextInt(10))
                    .addField("start_vol", 3525 + random.nextInt(1000))
                    .time(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() + (i * 10000),
                            TimeUnit.MILLISECONDS)
                    .build());

            Thread.sleep(10000);
            System.out.println(i);
        }



    }

}
