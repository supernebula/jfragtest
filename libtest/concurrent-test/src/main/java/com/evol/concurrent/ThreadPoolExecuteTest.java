package com.evol.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecuteTest {
    @Test
    public void test01() throws Exception{
        AtomicInteger atomicInteger = new AtomicInteger();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 0L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(2));
        for(int i = 0;i<4;i++){
            executor.execute(()->{
                System.out.println("Thread:"+atomicInteger.getAndIncrement());

                try {
                    //模拟正在处理东西
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });


        }
        Thread.sleep(1000);
        System.out.println("corePoolSize:"+executor.getCorePoolSize());
        System.out.println("poolSize:"+executor.getPoolSize());
        System.out.println("taskCount:"+executor.getTaskCount());
        /*
        corePoolSize:2
        poolSize:2
        taskCount:4
         */


        for(int i = 0;i<2;i++){
            executor.execute(()->{
                System.out.println("Thread:"+atomicInteger.getAndIncrement());

                try {
                    //模拟正在处理东西
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        System.out.println("corePoolSize:"+executor.getCorePoolSize());
        System.out.println("poolSize:"+executor.getPoolSize());
        System.out.println("taskCount:"+executor.getTaskCount());
        /**
         * corePoolSize:2
         * poolSize:4
         * taskCount:6
         */

        //再加入线程会报错
/*        for(int i = 0;i<2;i++){
            executor.execute(()->{
                System.out.println("Thread:"+atomicInteger.getAndIncrement());

                try {
                    //模拟正在处理东西
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }*/

    }
}
