package test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.utils.Utils;

import java.io.IOException;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void run() {

        KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProp);
        while (true) {
            String msg = "这就是发送的消息";
            producer.send(new ProducerRecord<String, String>(topicName,"message", msg));
            Utils.sleep(3000);
            /*如果不是一直发送需要 关闭生产者
            producer.close();*/
        }
    }

    public static void main(String[] args) {
        run();
    }
}


