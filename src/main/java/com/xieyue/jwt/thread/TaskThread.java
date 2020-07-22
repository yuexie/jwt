package com.xieyue.jwt.thread;

/**
 * @ClassName :   TaskThread
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-22 20:03
 */
public class TaskThread implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
