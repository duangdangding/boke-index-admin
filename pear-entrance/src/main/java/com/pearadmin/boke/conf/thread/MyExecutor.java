package com.pearadmin.boke.conf.thread;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author lushao
 * @Description 异步线程池
 * @Date 2021/7/2 18:42
 * @Version 1.0
 */
@Configuration
@EnableAsync
public class MyExecutor implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        核心线程数
        int i = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(i * 2);
//        最大线程数
        executor.setMaxPoolSize(i * 2);
//        线程队列数量
        executor.setQueueCapacity(10);
//        线程名前缀
        executor.setThreadNamePrefix("myexecutor-checkurl-");
//        拒绝策略
        /**
         * ThreadPoolExecutor.AbortPolicy：丢弃任务并抛出RejectedExecutionException异常(默认)；
         * ThreadPoolExecutor.DiscardPolic：丢弃任务，但是不抛出异常；
         * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务；
         * ThreadPoolExecutor.CallerRunsPolic：由调用线程处理该任务；
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
