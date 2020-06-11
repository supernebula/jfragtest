package com.evol.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ESClientConfig {

    @Value("${elasticSearch.host}")
    private String host;

    @Value("${elasticSearch.port}")
    private int port;



    @Bean(destroyMethod="close")
    @Scope("singleton")
    public RestHighLevelClient getRestHighLevelClient(){

        HttpHost httpHost = new HttpHost(host, port,"http");

        RestClientBuilder builder = RestClient.builder(httpHost);
        //RestClient restClient = builder.build();
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
        System.out.println("init RestHighLevelClient");
        return restHighLevelClient;

    }

}
