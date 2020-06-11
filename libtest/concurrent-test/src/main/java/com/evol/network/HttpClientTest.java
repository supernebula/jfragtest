package com.evol.network;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.evol.concurrent.component.TimeMonitor;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.junit.jupiter.api.Test;


public class HttpClientTest {


    @Test
    public void simpleTest() {
        for (int i = 1; i <= 3000; i++){
            long run = TimeMonitor.run(this::simpleAccess);
            System.out.println("--------第" + i + "次调用--------");
            try {
                //Thread.sleep(100);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }


    }


    @Test
    public void simpleTest1() throws HttpProcessException {

        System.out.println("--------第1次调用--------");

        simpleAccess();


        System.out.println("--------第2次调用--------");

        simpleAccess();




    }

    private void simpleAccess() {

        System.out.println("--------简单方式调用（默认post）--------");
        String url = "https://www.baidu.com/";
        HttpConfig config = HttpConfig.custom();
        //简单调用
        String resp = null;
        try {
            resp = HttpClientUtil.get(config.url(url));
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }

        System.out.println("请求url："+ url);
        System.out.println("请求结果内容长度："+ resp.length());
        //System.out.println(resp);


    }

}
