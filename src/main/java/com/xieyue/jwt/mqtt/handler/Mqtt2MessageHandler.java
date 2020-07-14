package com.xieyue.jwt.mqtt.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 * <p>描述:配置channel1消息处理 </p>
 *
 */
@Log4j2
@Component
public class Mqtt2MessageHandler implements MessageHandler {
	
	@ServiceActivator(inputChannel = "channel2")
	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		log.info("[ channel2 ] 主题：{}，QOS:{}，数据：{}",
				message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC),
				message.getHeaders().get(MqttHeaders.RECEIVED_QOS),
				message.getPayload());
	}

}
