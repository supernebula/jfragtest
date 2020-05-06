package com.evol.concurrent.component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SingleThreadPoolHolder {

    private static int POOL_SIZE = 2;
    private static int MAX_POOL_SIZE = 12;
    private static long KEEP_ALIVE_TIME = 0L;
    private static TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    public static ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(2, 4, 0L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(2));




}
