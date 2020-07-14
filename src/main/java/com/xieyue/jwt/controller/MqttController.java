package com.xieyue.jwt.controller;

import com.xieyue.jwt.config.mqtt.MqttAutoConfiguration;
import com.xieyue.jwt.utils.MqttUtils;
import com.xieyue.jwt.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
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

	@Autowired
	private MqttAutoConfiguration mqttConfig;

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

	@GetMapping(value = { "/add" })
	public Result getMqttTopic(){
		String channelName = "channel1";
		String [] topics = {"/ZKYC/TEST"};
		List<String> listTopics = mqttConfig.addTopics(channelName, topics);

		return Result.result(listTopics);
	}
}
