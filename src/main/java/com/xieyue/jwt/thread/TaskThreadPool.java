package com.xieyue.jwt.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @ClassName :   TaskThreadPool
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-22 23:26
 */
@Slf4j
public class TaskThreadPool extends ThreadPoolExecutor {

    public TaskThreadPool(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        String logInfo = String.format(" thread %d 开始执行任务！-> %s\n", t.getId(), t.getName());
        log.info("beforeExecute: " + logInfo);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        //注意super的位置
        String logInfo = String.format(" thread %s 任务执行结束！-> %s\n", r, t);
        log.info("afterExecute: 任务执行结束！->" + logInfo);

        super.afterExecute(r, t);
    }

    @Override
    protected void terminated() {
        super.terminated();
        log.info("terminated: 线程池关闭！");
    }
}
