package com.xieyue.jwt.config;

import com.xieyue.jwt.thread.TaskThreadFactory;
import com.xieyue.jwt.thread.TaskThreadPool;
import com.xieyue.jwt.thread.TaskThreadReject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @ClassName :   ThreadPoolExecutorConfig
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-22 20:17
 */
@Slf4j
@Configuration
public class ThreadPoolExecutorConfig {

    @Autowired
    ThreadPoolProperties threadPoolProperties;



    private ExecutorService executorService;


    /**
     * 本质上来说 线程池的执行逻辑其实很简单：
     * 如果当前线程池的线程个数小于CORE_POOL_SIZE 那么有任务到来的时候 就直接创建一个线程 执行这个任务
     * 如果当前线程池的线程个数已经到了CORE_POOL_SIZE这个极限，那么新来的任务就会被放到workQueue中
     * 如果workQueue里面的任务已满，且MAXIMUM_POOL_SIZE这个值大于CORE_POOL_SIZE，那么此时线程池会继续创建线程执行任务
     * 如果workQueue满了，且线程池的线程数量也已经达到了MAXIMUM_POOL_SIZE 那么就会把任务丢给rejectedExecutionHandler 来处理
     * 当线程池中的线程超过了CORE_POOL_SIZE的哪些线程 如果空闲时间到了KEEP_ALIVE_TIME 那么就会自动销毁
     * 当设置allowCoreThreadTimeOut(true)时，线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭
     */
    @Bean("threadPoolExecutorService")
    public ExecutorService ThreadPoolExecutorService() {

        log.info("start asyncServiceExecutor");

        if(executorService == null || executorService.isShutdown()){
            executorService = new TaskThreadPool(threadPoolProperties.CORE_POOL_SIZE,
                    threadPoolProperties.MAXIMUM_POOL_SIZE,
                    threadPoolProperties.KEEP_ALIVE_TIME,
                    TimeUnit.MILLISECONDS,
                    threadPoolProperties.workQueue,
                    threadPoolProperties.threadFactory,
                    threadPoolProperties.rejectedExecutionHandler);
        }
        return executorService;
    }

    /**
     * @description  获取线程池服务
     * @author       XieYue
     * @date         2020/7/23 0:36 
     * @Param        
     * @return       
     **/
    public ExecutorService getExecutorService(){
        if(executorService == null || executorService.isShutdown()){
            executorService = new TaskThreadPool(threadPoolProperties.CORE_POOL_SIZE,
                    threadPoolProperties.MAXIMUM_POOL_SIZE,
                    threadPoolProperties.KEEP_ALIVE_TIME,
                    TimeUnit.MILLISECONDS,
                    threadPoolProperties.workQueue,
                    threadPoolProperties.threadFactory,
                    threadPoolProperties.rejectedExecutionHandler);
        }
        return executorService;
    }





}
