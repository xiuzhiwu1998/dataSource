package com.xiuzhiwu;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/***
 * @Author xiuzhiwu
 * @Date 2023/6/9 15:07
 * @Description
 */
@SpringBootTest
public class RocketMqTesta {

    @Value("${rocketmq.url}")
    private String rocketMqUrl;

    @Test
    void pullMessage(){
        // 定义一个pull消费者
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("cg");
        // 定义一个push消费者
        // DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("cg");
        // 指定nameServer
        consumer.setNamesrvAddr(rocketMqUrl);
        // 指定从哪里消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        try {
            // 指定topic和tag
            consumer.subscribe("testTopic", "testTag");
            // consumer.setMessageModel(MessageModel.BROADCASTING);
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
    }
}






















