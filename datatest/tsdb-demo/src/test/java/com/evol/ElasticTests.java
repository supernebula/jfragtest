package com.evol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evol.domain.EBikePower;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticTests {

    @Autowired
    private RestHighLevelClient esHighClient;

    @Autowired
    private InfluxDB influxDB;

    @Test
    public void indexTest(){
        String result = "null";

        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest("_all");
            GetIndexResponse getIndexResponse = esHighClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
            String[] indexs = getIndexResponse.getIndices();
            result = StringUtils.join(indexs, ",");
            System.out.println(result);
        }catch (Exception ex){
            System.out.println(ex);
        }finally {
        }

    }

    @Test
    public void searchTest(){
        String result = "none";
        SearchRequest searchRequest = new SearchRequest("device_instruct_log");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("createTime");//按照年龄排序
        fieldSortBuilder.sortMode(SortMode.MIN);//从小到大排序

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        long startTime = LocalDateTime.of(2020, 4, 1, 0, 0 , 0).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long endTime = LocalDateTime.of(2020, 6, 12, 0, 0 , 0).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        boolQueryBuilder.must( QueryBuilders.rangeQuery("createTime").from(startTime).to(endTime))
                .must(QueryBuilders.matchPhraseQuery("name", "STA"))
                .must(QueryBuilders.matchPhraseQuery("deviceNumber", "141700"));


        //sourceBuilder.query(boolQueryBuilder).sort(fieldSortBuilder);//多条件查询
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.size(10000);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse response = esHighClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            JSONArray jsonArray = new JSONArray();
            int i = 0;
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                JSONObject jsonObject = JSON.parseObject(sourceAsString);
                jsonArray.add(jsonObject);
                System.out.println(sourceAsString);
                System.out.println(i);
                i++;
                if(StringUtils.startsWith(sourceAsString, "STA")){
                    continue;
                }
                try {
                    insertTs((String) jsonObject.get("msg"), (long)jsonObject.get("queneTime"));
                }catch (InterruptedException e){
                    e.printStackTrace();
                }catch (Exception ex1){
                    ex1.printStackTrace();
                }

            }
            result = "查询成功";
        } catch (IOException e) {
            e.printStackTrace();
            result = "查询失败";
        } catch (NullPointerException ex){
            ex.printStackTrace();
        }


        System.out.println(result);
    }


    private void insertTs(String eBikeSTA, long queneTime) throws InterruptedException {
        String staInstruct = eBikeSTA;
        String[] parameters = StringUtils.split(staInstruct, '#');
        String deviceNumber = parameters[0];
        String instruct = parameters[1];
        int ac = NumberUtils.toInt(parameters[2]);
        List<EBikePower> eBikePowers = new ArrayList<>();
        for (int i = 3; i < parameters.length; i++){
            String[] roadAndPower = StringUtils.split(parameters[i], ':');
            String road = roadAndPower[0];
            int power = NumberUtils.toInt(roadAndPower[1]);
            EBikePower item = new EBikePower();
            item.setDeviceNumber(deviceNumber);
            item.setInstruct(instruct);
            item.setRoad(road);
            item.setPower(power);
            item.setTime(queneTime);
            insertEbikePower(item);
        }



    }

    public void insertEbikePower(EBikePower eBikePower) throws InterruptedException {
        String measurement = "ebike_power";

        influxDB.setDatabase("ddcx");

        influxDB.write(Point.measurement(measurement)
                .tag("device_number", eBikePower.getDeviceNumber())
                .tag("road", eBikePower.getRoad())
                .addField("power", eBikePower.getPower())
                .time(eBikePower.getTime(), TimeUnit.MILLISECONDS)
                .build());



    }

}
