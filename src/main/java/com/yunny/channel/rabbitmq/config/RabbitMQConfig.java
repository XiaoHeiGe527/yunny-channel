package com.yunny.channel.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {

    //*******************************队列***********************************
    /**
     * 测试队列
     */
    public final static String TEST_QUEUE_NAME = "test-demo-queue";


    //*******************************路由***********************************
    /**
     * 测试 路由
     */
    public final static String TEST_EXCHANGE_NAME = "test-demo-exchange";


    //*******************************KEY***********************************
    /**
     * 测试 KEY
     */
    public final static String TEST_ROUTING_KEY = "test-demo-key";


    //*******************************获取队列***********************************
    /**
     * 获取测试队列
     * @return
     */
    @Bean("testDemoQueueMessages")
    public Queue testDemoQueueMessages() {
        return new Queue(TEST_QUEUE_NAME);
    }


    //*******************************创建一个 topic 类型的交换器***********************************
    /**
     * 创建一个 topic 类型的交换器(测试)
     * @return
     */
    @Bean("testDemoExchange")
    TopicExchange testDemoExchange() {
        return new TopicExchange(TEST_EXCHANGE_NAME);
    }



    //*******************************绑定***********************************
    /**
     *  使用路由键（routingKey）把队列（Queue）绑定到交换器（Exchange）(测试)
     * @param testDemoQueueMessages
     * @param testDemoExchange
     * @return
     */
    @Bean
    Binding bindingTestDemoExchangeMessages(@Qualifier("testDemoQueueMessages") Queue testDemoQueueMessages, @Qualifier("testDemoExchange") TopicExchange testDemoExchange) {
        return BindingBuilder.bind(testDemoQueueMessages).to(testDemoExchange).with(TEST_ROUTING_KEY);
    }
}
