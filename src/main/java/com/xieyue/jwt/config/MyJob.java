package com.xieyue.jwt.config;

import com.xieyue.jwt.Constants.Const;
import com.xieyue.jwt.utils.MqttUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @program: jwt
 * @description:
 * @author: xieyue
 * @create: 2020-07-01 09:21
 **/

@Slf4j
@Configuration
@EnableScheduling   // 开启定时任务
public class MyJob {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    //@Scheduled(cron = "*/3 * * * * ?")//运行周期时间可配:3S
    /*
    public void sendMessage(){
        String msg = "SERVER-MSG:" + String.valueOf(System.currentTimeMillis());
        System.out.println("来自服务端的消息:" + msg);

        simpMessagingTemplate.convertAndSend("/topic/echoTest/price", msg);
    }
    */


    //@Scheduled(cron = "*/3 * * * * ?")//运行周期时间可配:3S
    /*
    public void sendMqttMessage(){
        String channelName      = "channel1";
        String topic            = "/topic/test/msg";
        String messageContent   = "测试----" + String.valueOf(Const.MQTT_SEND_NUM) + "----" + UUID.randomUUID().toString() + "----"
                + LocalDateTime.now();
        log.info("#mqtt#测试信息 - {} - {} ", Const.MQTT_SEND_NUM, messageContent);
        try{
            MqttUtils.sendMessage(topic, messageContent, channelName);
        }catch (Exception ex){
            log.info(ex.getMessage());
        }

        Const.MQTT_SEND_NUM ++;
    }
    */

    /*
    @Scheduled(fixedRate = 50000)
    public void sendMessageToUser(){
        System.out.println("来自服务端的消息,推送给指定用户");
        simpMessagingTemplate.convertAndSendToUser("10","/server/sendMessageByServer",System.currentTimeMillis());
    }
    */
}