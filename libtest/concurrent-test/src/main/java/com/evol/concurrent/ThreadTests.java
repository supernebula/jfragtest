package com.evol.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadTests {

    ///private final Calculator calculator = new Calculator();

    @Test
    void addition() {
//        assertEquals(2, calculator.add(1, 1));
        assertEquals(2, 2);
    }

    @Test
    void threaPoolTest(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3,
                5,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 10; i++) {
            int j = i;
            String taskName = "任务" + j;
            executor.execute(() -> {
                //模拟任务内部处理耗时
                try {
                    TimeUnit.SECONDS.sleep(j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + taskName + "处理完毕");
            });
        }
        //关闭线程池
        executor.shutdown();
    }

}
