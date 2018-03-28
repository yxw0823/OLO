package com.fh.controller.olo.olopdproduct.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.formula.functions.T;

public class OloProductTask {
    private static class SingletonHolder {
        private static final OloProductTask INSTANCE = new OloProductTask();
    }

    // 队列大小
    private final int QUEUE_LENGTH = 5;

    // 基于内存的阻塞队列
    private BlockingQueue<T> queue = new LinkedBlockingQueue<T>(QUEUE_LENGTH);

    // 创建计划任务执行器
    private ScheduledExecutorService es = Executors.newScheduledThreadPool(1);

    /**
     * 构造函数，执行execute方法
     */
    private OloProductTask() {
        execute();
    }

    public static OloProductTask getOloProductTask() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 添加信息至队列中
     * 
     * @param content
     */
    public void addQueue(T content) {
        queue.add(content);
    }

    /**
     * 初始化执行
     */
    public void execute() {
        // 每一分钟执行一次
        es.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                try {
                    T content = queue.take();
                    System.out.println(content);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, 0, 1, TimeUnit.MINUTES);
    }
}