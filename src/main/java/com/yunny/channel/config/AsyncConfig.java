package com.yunny.channel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程配置 参考文档：https://www.xncoding.com/2017/07/20/spring/sb-async.html
 *
 * @author LJ
 */
@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 线程池维护线程的最少数量
        executor.setCorePoolSize(5);
        // 线程池维护线程的最大数量,只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(50);
        // 缓存队列
        executor.setQueueCapacity(10000);
        // 允许的空闲时间,当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);

        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 表明等待所有线程执行完，默认为false
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时长
        executor.setAwaitTerminationSeconds(600);
        // 线程名称前缀
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize(); // 如果不初始化，导致找到不到执行器
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error(">>>>>>   异步线程异常: \r\n {} \r\n {} \r\n {}", method.getName(), params, ex);
    }
}