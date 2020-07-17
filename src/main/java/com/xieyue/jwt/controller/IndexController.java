package com.xieyue.jwt.controller;

import com.alibaba.fastjson.JSON;
import com.xieyue.jwt.base.ParamsNotNull;
import com.xieyue.jwt.base.ParamsPostWant;
import com.xieyue.jwt.constants.Const;
import com.xieyue.jwt.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @program: jwt
 * @description:
 * @author: xieyue
 * @create: 2020-05-25 16:35
 **/

@RestController
public class IndexController {

    @Value("${app.start.time}")
    private String appStartTime;


    @GetMapping("get")
    public String getTestGetFunc(@ParamsNotNull String id){
        long time_now = System.currentTimeMillis();
        String res = getDistanceTime(Const.APP_START_TIME,time_now);
        return res;
    }

    @PostMapping("post")
    @ParamsPostWant(wantParams = {"name","age"})
    public String getTestPostFunc(@RequestBody Map reqBody){

        long time_now = System.currentTimeMillis();
        String res = getDistanceTime(Const.APP_START_TIME,time_now);

        System.out.println(res);
        System.out.println(reqBody);

        return res;
    }


    @GetMapping("time")
    public String getAppStartTime(){
        long time_now = System.currentTimeMillis();
        String res = getDistanceTime(Const.APP_START_TIME,time_now);
        return res;
    }

    @GetMapping("token")
    public String getToken() throws Exception{
        Map<String, Object> claims = new HashMap<>();
        claims.put("txCode",    "2001");
        claims.put("brNo",      "10001");
        claims.put("reqDate",   "20200420");
        claims.put("reqTime",   "12:04:22");

        return JwtUtil.creatJWT(claims);
    }



    public static String getDistanceTime(long time1, long time2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;

        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) return day + "天"+hour + "小时"+min + "分钟" + sec + "秒";
        if (hour != 0) return hour + "小时"+ min + "分钟" + sec + "秒";
        if (min != 0) return min + "分钟" + sec + "秒";
        if (sec != 0) return sec + "秒" ;
        return "0秒";
    }

    public static void main(String[] args) {
        ThreadLocal<String> local = new ThreadLocal<>();
        Random random = new Random();
        IntStream.range(0,5).forEach( a -> new Thread(() -> {
            //为每一个线程设置local值
            local.set(a + " - " + random.nextInt(10));
            System.out.println("线程和local值：" + local.get());
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }).start());
    }
}
