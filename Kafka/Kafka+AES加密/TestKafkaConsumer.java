package com.enjoyor.mtdap.test;

import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created with IDEA
 * author 郭帅
 * date 15:04 2018/11/29
 */
public class TestKafkaConsumer extends ShutdownableThread {
    private static String bootstrap;
    private static String groupId;
    private static String topic;
    private static String IsAntoCommit;
    private static String timeOut;
    private static String maxPollRecords;
    private static String autoOffsetReset;
    private static String keySerializer;
    private static String valueSerializer;
    private static Properties prop = new Properties();
    private static KafkaConsumer<String, String> consumer;
    ConsumerRecords<String, String> msgList;

    TestKafkaConsumer() {
        /*false 不关闭*/
        super("TestKafkaConsumer", false);
    }

    public static void init() {
        try {
            prop.load(TestKafkaConsumer.class.getClassLoader().getResourceAsStream("kafka.properties"));
            bootstrap = prop.getProperty("bootstrap");
            groupId = prop.getProperty("groupId");
            topic = prop.getProperty("topic");
            keySerializer = prop.getProperty("keyDeserializer");
            valueSerializer = prop.getProperty("valueDeserializer");
            prop.put("bootstrap.servers", bootstrap);
            prop.put("group.id", groupId);
            prop.put("key.deserializer", keySerializer);
            prop.put("value.deserializer", valueSerializer);

            consumer= new KafkaConsumer<>(prop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doWork() {
        /*初始化*/
        init();
        /*订阅topic*/
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            /*每秒拉多条,放在ConsumerRecords中*/
            msgList = consumer.poll(1000);
            for(ConsumerRecord<String,String> record :msgList){
                /*取出每一条ConsumerRecords中的每一条ConsumerRecord*/
                System.out.println("AES加密后的kafka Record："+record.value());
                String decryptMsg = AesEncodeUtils.decrypt(record.value());
                System.out.println("AES解密后："+decryptMsg);
                System.out.println();
                Utils.sleep(3000);
            }
        }
    }



    public static void main(String[] args) {
        TestKafkaConsumer testKafkaConsumer = new TestKafkaConsumer();
        testKafkaConsumer.doWork();
    }


}
