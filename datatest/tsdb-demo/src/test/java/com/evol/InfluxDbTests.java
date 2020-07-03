package com.evol;

import com.evol.domain.AcSTA;
import com.evol.domain.model.AccessAuditLog;
import com.evol.model.CarAcStatus;
import com.evol.model.CarAcStatusSmall;
import com.evol.util.TimeMonitor;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//https://github.com/influxdata/influxdb-java
//https://github.com/HaiyangLiang/springboot2-influxdb-demo/blob/master/src/main/java/com/influxdb/demo/service/InfluxService.java
//https://www.frankfeekr.cn/2019/07/24/influxdb-tutorial-start/
//https://www.baeldung.com/java-influxdb

// https://www.jianshu.com/p/1be8b7273b89   influxdb+grafana实战总结
// https://www.cnblogs.com/iiiiher/p/8046600.html  influxdb最佳实战-监控对比
// https://jasper-zhang1.gitbooks.io/influxdb/content/   InfluxDB中文文档
@RunWith(SpringRunner.class)
@SpringBootTest
public class InfluxDbTests {

    @Value("${spring.influx.user}")
    private String userName;

    @Value("${spring.influx.password}")
    private String password;

    @Value("${spring.influx.url}")
    private String url;

    private String databaseName = "ddcx";




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

    @Test
    public void mutliInsertTest(){
        TimeMonitor.run(() -> {
            for(int i = 0; i < 90000; i++){
                selectAndInsertPointTest();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //@Test
    public void selectAndInsertPointTest(){
        influxDB.setDatabase(databaseName);
        QueryResult queryResult = influxDB.query(new Query("SELECT * FROM \"car_ac_sta_small\" ORDER BY \"time\" DESC  LIMIT 90150")); //9015
        if(queryResult.hasError()){
            System.out.println(queryResult.getError());
        }
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        //List<CarAcStatus> carAcStatusList = resultMapper.toPOJO(queryResult, CarAcStatus.class);
        List<CarAcStatusSmall> carAcStatusList = resultMapper.toPOJO(queryResult, CarAcStatusSmall.class);

        Duration duration = Duration.ofSeconds(1122960); //112296

        carAcStatusList.forEach(e -> {
            e.setTime(e.getTime().plus(duration));
        });

//        TimeMonitor.run(() -> {
//            carAcStatusList.forEach(e -> {
//                e.setTime(e.getTime().plus(duration));
//            });
//        });




        //carAcStatusList.forEach(e -> insertCarSTA(e));
        carAcStatusList.forEach(e -> insertCarSTASmall(e));

        System.out.println("carAcStatusList=" + carAcStatusList.size());
    }




    public void insertCarSTA(CarAcStatus acSTA){
        String measurement = "car_ac_sta";

        influxDB.setDatabase("ddcx");

        try {
            influxDB.write(Point.measurement(measurement)
                    .tag("device_number", acSTA.getDeviceNumber())
                    .tag("instruct", acSTA.getInstruct())
                    .tag("road", acSTA.getRoad())
                    .tag("cardKey", StringUtils.isEmpty(acSTA.getCardKey()) ? "" : acSTA.getCardKey())
                    .tag("orderId", StringUtils.isEmpty(acSTA.getOrderId()) ? "" : acSTA.getOrderId())

                    .addField("ac", acSTA.getAc())
                    .addField("current", acSTA.getCurrent())
                    .addField("power", acSTA.getPower())
                    .addField("nowDeg", acSTA.getNowDeg())
                    .addField("startDeg", acSTA.getStartDeg())
                    .addField("useDeg", acSTA.getUseDeg())
                    .addField("remainingTime", acSTA.getRemainingTime())
                    .addField("temperature1", acSTA.getTemperature1())
                    .addField("temperature2", acSTA.getTemperature2())
                    .addField("cpVoltage", acSTA.getCpVoltage())
                    .time(acSTA.getTime().getEpochSecond(), TimeUnit.SECONDS)
                    .build());
        }catch (Exception ex){
            System.out.println(ex);
        }
    }


    /**
     * 少 tag
     * @param acSTA
     */
    public void insertCarSTASmall(CarAcStatusSmall acSTA){
        String measurement = "car_ac_sta_small";

        influxDB.setDatabase("ddcx");

        try {
            influxDB.write(Point.measurement(measurement)
                    .tag("device_number", acSTA.getDeviceNumber())
                    .tag("road", acSTA.getRoad())

                    .addField("ac", acSTA.getAc())
                    .addField("current", acSTA.getCurrent())
                    .addField("power", acSTA.getPower())
                    .addField("nowDeg", acSTA.getNowDeg())
                    .addField("startDeg", acSTA.getStartDeg())
                    .addField("useDeg", acSTA.getUseDeg())
                    .addField("remainingTime", acSTA.getRemainingTime())
                    .addField("temperature1", acSTA.getTemperature1())
                    .addField("temperature2", acSTA.getTemperature2())
                    .addField("cpVoltage", acSTA.getCpVoltage())
                    .time(acSTA.getTime().getEpochSecond(), TimeUnit.SECONDS)
                    .build());
        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    @Test
    public void  insertPojoTest() throws InterruptedException {

        for (int i = 0; i < 1000000; i ++){



            AccessAuditLog log = new AccessAuditLog();
            log.setClientIp("127.0.0.1");
            log.setServerName("DDCX-ORDER-SERVER");
            log.setPath("/tenant/test");
            log.setElapsedMilli((new Random(System.currentTimeMillis()).nextInt(1500)));

            Point point = Point.measurementByPOJO(AccessAuditLog.class)
                    .addFieldsFromPOJO(log)
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .build();
            influxDB.setDatabase("ddcx");
            influxDB.write(point);

            Thread.sleep(200);
        }




    }

}
