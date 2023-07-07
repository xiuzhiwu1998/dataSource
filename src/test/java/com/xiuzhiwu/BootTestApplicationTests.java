package com.xiuzhiwu;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@SpringBootTest
class BootTestApplicationTests {

    @Value("${rocketmq.url}")
    private String rocketMqUrl;

    @Test
    void contextLoads() {
        Cache<String,String> cache = Caffeine.newBuilder()
                .maximumSize(16 * 1024)
                .expireAfterWrite(6 * 60000L, TimeUnit.MILLISECONDS).build();

        cache.put("1", "这是caffeine测试");
        cache.put("1", "这是caffeine测试2");
        cache.put("1", "这是caffeine测试3");

        System.out.println(cache.getIfPresent("1"));
        System.out.println(cache.get("1", Function.identity()));
    }

    @Test
    void contextLoads2() {
        Cache<String, List<String>> cache = Caffeine.newBuilder()
                .maximumSize(16 * 1024)
                .expireAfterWrite(6 * 60000L, TimeUnit.MILLISECONDS).build();

        List<String> strings = new ArrayList<>();
        strings.add("ceshi");
        strings.add("ceshi2");
        strings.add("ceshi3");
        cache.put("1", strings);
        cache.put("2", strings);


        System.out.println(cache.getIfPresent("1"));
        System.out.println(cache.get("1", this::defaultListLoad));
        cache.invalidate("1");

        System.out.println(cache.get("1", this::defaultListLoad));
        System.out.println(cache.get("2", this::defaultListLoad));
    }

    @Test
    void contextLoads3() {
        LoadingCache<String,String> cache = Caffeine.newBuilder()
                .maximumSize(16 * 1024)
                .expireAfterWrite(6 * 60000L, TimeUnit.MILLISECONDS)
                .build(this::defaultStringLoad);

        cache.put("1", "这是caffeine测试");
        cache.put("1", "这是caffeine测试2");
        cache.put("1", "这是caffeine测试3");

        System.out.println(cache.getIfPresent("1"));
        System.out.println(cache.get("1", Function.identity()));
    }

    String defaultStringLoad(String key){
        return key + "default";
    }

    List<String> defaultListLoad(String key){
        return new ArrayList<>();
    }


    @Test
    void contextLoads4() {
        Map<String,Object> map = new HashMap<>();
        map.put("createtime", LocalDateTime.now());

        Timestamp createtimeTs = Timestamp.valueOf((LocalDateTime) map.get("createtime"));
        // Timestamp createtime = (Timestamp) map.get("createtime");
        System.out.println(createtimeTs);
    }


    @Test
    void sendMessage(){
        // 创建一个生产者，参数为group name
        DefaultMQProducer producer = new DefaultMQProducer("test");
        // 指定nameServer地址
        producer.setNamesrvAddr(rocketMqUrl);
        // 指定失败重试次数
        producer.setRetryTimesWhenSendFailed(2);
        // 设置发送消息超时时间，默认3s
        producer.setSendMsgTimeout(5);
        try {
            // 开启生产者
            producer.start();
            // 循环发送10条消息
            for (int i=0; i<10; i++){
                Message message = new Message();
                message.setTopic("testTopic");
                message.setTags("testTag");
                message.setBody(("hello," + i).getBytes());

                producer.send(message);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            producer.shutdown();
        }
    }


}
