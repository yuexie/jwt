package com.xieyue.jwt.config;

import com.xieyue.jwt.thread.TaskThreadFactory;
import com.xieyue.jwt.thread.TaskThreadReject;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

/**
 * @ClassName :   ThreadPoolProperties
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-22 23:46
 */
@Data
@Configuration
public class ThreadPoolProperties {

    /**
     * 获得Java虚拟机可用的处理器个数 + 1
     */
    private static final int THREADS = Runtime.getRuntime().availableProcessors() + 1;

    //核心线程池的大小
    public static final int CORE_POOL_SIZE      = 10;

    //最大线程池的大小
    public static final int MAXIMUM_POOL_SIZE   = 20;

    //超过核心线程池的大小哪些线程 最多可以存活多久
    public static final long KEEP_ALIVE_TIME    = 3000;

    //创建线程的线程工厂，这个建议一定要自己重写一下，因为可以增加很多关键信息，方便出问题的时候dump或者看日志能定位到问题
    public final ThreadFactory threadFactory    = new TaskThreadFactory("wodetestfactory");

    //阻塞队列 --本篇文章的重点
    public final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue(100);

    //拒绝策略--本篇文章的重点
    //public static RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
    public final RejectedExecutionHandler rejectedExecutionHandler = new TaskThreadReject();

}
