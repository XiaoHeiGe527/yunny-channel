package com.yunny.channel.rabbitmq.listener;


import com.alibaba.fastjson.JSON;
import com.yunny.channel.common.query.TestMqQuery;
import com.yunny.channel.common.util.StringUtil;
import com.yunny.channel.rabbitmq.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestListener {

    @RabbitListener(queues = RabbitMQConfig.TEST_QUEUE_NAME)
    public void testDemoListener(String testStr){

        if(StringUtil.isEmpty(testStr)){
            log.error("处理 消息出错 testStr为空");
            return;
        }

        log.info(" MQ接收的 testStr :[{}]",testStr);

        TestMqQuery tmq = JSON.parseObject(testStr,TestMqQuery.class);

        log.info("TestMqQuery:[{}]",tmq.toString());

    }
}
