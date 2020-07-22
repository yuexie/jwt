package com.xieyue.jwt.thread;

import com.xieyue.jwt.config.ThreadPoolExecutorConfig;
import com.xieyue.jwt.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;


/**
 * @ClassName :   PoolTest 多线程，线程池测试
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-22 19:57
 */
@Component
public class PoolTest {

    @Autowired
    ThreadPoolExecutorConfig threadPoolExecutorConfig;

//    @Autowired
//    private ExecutorService executorService = (ExecutorService)SpringUtils.getBean("threadPoolExecutorService");

    public void taskOne(){
        TaskThread task = new TaskThread();
        threadPoolExecutorConfig.getExecutorService().execute(task);
    }

    public void taskTwo(){
        TaskThread task = new TaskThread();
        threadPoolExecutorConfig.getExecutorService().execute(task);
    }

    public void shutdownTask(){
        threadPoolExecutorConfig.getExecutorService().shutdown();
    }

    public void restart(){
        //threadPoolExecutorConfig.restart();
    }
}

