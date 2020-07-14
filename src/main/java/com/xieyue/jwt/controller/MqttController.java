package com.xieyue.jwt.controller;

import com.xieyue.jwt.config.mqtt.MqttAutoConfiguration;
import com.xieyue.jwt.utils.MqttUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>

 */
@Slf4j
@RestController
@AutoConfigureAfter(value = MqttAutoConfiguration.class)
@RequestMapping("mqtt")
public class MqttController {


	@GetMapping(value = { "/sendMessage" })
	public String sendMessage(String topic) {
		String messageContent = "测试----" + UUID.randomUUID().toString() + "----"
				+ LocalDateTime.now();
		log.info("topic:{}--测试信息: {} ",topic, messageContent);
		MqttUtils.sendMessage(topic, messageContent);
		return messageContent;
	}
	
	@GetMapping(value = { "/sendMessageByChannel" })
	public String sendMessageByChannel(String topic, String channelName) {
		String messageContent = "测试----" + UUID.randomUUID().toString() + "----"
				+ LocalDateTime.now();
		log.info("测试信息 {} ", messageContent);
		MqttUtils.sendMessage(topic, messageContent, channelName);
		return messageContent;
	}
}
