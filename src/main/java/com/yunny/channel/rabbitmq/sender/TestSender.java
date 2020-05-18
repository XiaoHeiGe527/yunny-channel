package com.yunny.channel.rabbitmq.sender;

import com.yunny.channel.rabbitmq.config.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void testSend(String testStr) {
        this.amqpTemplate.convertAndSend(RabbitMQConfig.TEST_EXCHANGE_NAME, RabbitMQConfig.TEST_ROUTING_KEY, testStr);
    }
}
