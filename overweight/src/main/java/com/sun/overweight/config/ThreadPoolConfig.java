package com.sun.overweight.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 功能点：
 *
 * @author neal
 * @date 2021/08/31
 */
@Configuration
@Slf4j
public class ThreadPoolConfig {
    /**
     * 参数初始化
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程数量大小
     */
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    /**
     * 线程池最大容纳线程数
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT << 1 + 1;
    /**
     * 线程空闲后的存活时长
     */
    private static final int KEEP_ALIVE_TIME = 30;

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("pool-daemon-%d").setDaemon(true).build();

    @Bean
    public ExecutorService executorService() {
        ExecutorService threadPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                THREAD_FACTORY,
                new ThreadPoolExecutor.AbortPolicy()
        );
        return threadPool;
    }

}
