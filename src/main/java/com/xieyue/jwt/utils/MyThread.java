package com.xieyue.jwt.utils;

/**
 * @program: jwt
 * @description:
 * @author: xieyue
 * @create: 2020-05-11 19:14
 **/

public class MyThread extends Thread {

    public  void run(){
        System.out.println("执行线程的名称: " + Thread.currentThread().getName());
        System.out.println("执行线程的对象: " + this.getName());
    }
}
