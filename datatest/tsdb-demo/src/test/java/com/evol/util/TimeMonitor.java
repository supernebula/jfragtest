package com.evol.util;

/**
 * 监控执行时间
 */
public class TimeMonitor {

    /**
     * 执行并返回执行时间（毫秒数）
     * @param runnable
     * @return 毫秒数
     */
    public static long run(Runnable runnable){


        long startTime=System.currentTimeMillis();   //获取开始时间
        try {
            runnable.run();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        long endTime=System.currentTimeMillis(); //获取结束时间
        long elapsed = endTime - startTime;

        System.out.println("-------------------TimeMonitor:程序运行时间： " + elapsed + "ms----------------");

        return elapsed;
    }

}
