package com.guoshuai.mtdap.test;

import com.alibaba.fastjson.JSON;
import com.guoshuai.mtdap.producer.AcquireKkData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.utils.Utils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IDEA
 * author 郭帅
 * date 13:26 2018/11/29
 */
public class TestProducer {
    private static String bootstrap;
    private static String topicName;
    private static String keyDeserializer;
    private static String valueDeserializer;
    private static Logger logger = Logger.getLogger(TestProducer.class.getName());
    static Properties kafkaProp = new Properties();
    static {
        try {
            kafkaProp.load(TestProducer.class.getClassLoader().getResourceAsStream("kafka.properties"));
            bootstrap = kafkaProp.getProperty("bootstrap");
            topicName = kafkaProp.getProperty("topic");
            keyDeserializer = kafkaProp.getProperty("keySerializer");
            valueDeserializer = kafkaProp.getProperty("valueSerializer");
            kafkaProp.put("bootstrap.servers", bootstrap);
            kafkaProp.put("key.serializer", keyDeserializer);
            kafkaProp.put("value.serializer", valueDeserializer);
            logger.info("put success");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("catch erro ");
        }
    }


    public static void run() {

        KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProp);
        while (true) {
            String msg = "{record_id:4907344097,point_id:360104008001,license_color:3,pass_time:2018-11-30 09:07:37,license_num:赣xxxxxx}";
            String encryptMsg = AesEncodeUtils.encrypt(msg);
            producer.send(new ProducerRecord<String, String>(topicName,"AesEncode", JSON.toJSONString(encryptMsg)));
            Utils.sleep(5000);
            /*如果不是一直发送需要 关闭生产者
            producer.close();*/
        }
    }

    public static void producerKKdata(){
        AcquireKkData kkData = new AcquireKkData();
        List<Map<String, String>> recordsList = kkData.getKkData("2018-08-23 08:20:30", "2018-08-23 08:20:40");
        KafkaProducer<String,String> kfaProducer = new KafkaProducer<>(kafkaProp);
        String record;
        while (true){
            for(Map<String, String> fieldMap:recordsList){
                /*源码：new ProducerRecord<>(topic,key(可以为null),value)*/
                /*将记录加密 */
                record = AesEncodeUtils.encryptJson((JSON) JSON.toJSON(fieldMap));
                kfaProducer.send(new ProducerRecord<>(topicName,fieldMap.get("record_id"),record));
                Utils.sleep(3000);
            }
        }

    }
    public static void main(String[] args) {
        //run();
        producerKKdata();
    }
}


