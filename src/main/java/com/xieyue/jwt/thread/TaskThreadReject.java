package com.xieyue.jwt.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName :   TaskThreadReject
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-22 22:20
 */
@Slf4j
public class TaskThreadReject implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        //实际生产中，我们最好把任务也赋值一下关键的log信息，方便 这些任务被抛弃以后存储在本地，等待时机
        //再重新拉出来继续执行，否则丢弃掉的任务也蛮可惜的,重要的信息不可以丢失
        //这里只是演示方便弄了个toString而已
        log.info(r.toString());
        System.out.println("runnable 任务 被丢弃了" + r.toString());
    }
}
