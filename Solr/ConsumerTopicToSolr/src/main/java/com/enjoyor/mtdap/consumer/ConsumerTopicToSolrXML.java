package com.enjoyor.mtdap.consumer;


import com.enjoyor.mtdap.common.InitPropertiesUtil;
import com.enjoyor.mtdap.common.SolrClientHelper;
import com.enjoyor.mtdap.common.TimeHelper;
import net.sf.json.JSONObject;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.*;


public class ConsumerTopicToSolrXML {

    private static Logger logger = Logger.getLogger(ConsumerTopicToSolrXML.class.getName());
    private static Properties kafkaProp = InitPropertiesUtil.initKafkaProp();

    private static Properties triggerProp = InitPropertiesUtil.initTriggerProp();
    private static TimeHelper timeHelper;
    private static String IsChange_passTime;
    private static String IsChange_licenseType;
    private static String trigger_passTime;
    private static String trigger_licenseType;

    private static ArrayList<String> xmlList = InitPropertiesUtil.readXML();

    Properties properties;
    KafkaConsumer<String, String> consumer;

    SolrInputDocument change2Doc;

    static {
        IsChange_passTime = triggerProp.getProperty("passTimeChange"); //true or  other
        IsChange_licenseType = triggerProp.getProperty("licenseTypeChange");//true or  other
        trigger_passTime = triggerProp.getProperty("kafka_passingTime");//kafka  field
        trigger_licenseType = triggerProp.getProperty("kafka_licenseType");//kafka  field
        System.out.println("IsChange_passTime: " + IsChange_passTime + " IsChange_licenseType: " + IsChange_licenseType + " trigger_passTime: " + trigger_passTime + " trigger_licenseType: " + trigger_licenseType);
    }

    public ConsumerTopicToSolrXML() {
        properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProp.getProperty("bootstrap"));
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProp.getProperty("groupid"));
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaProp.getProperty("clientid"));
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProp.getProperty("keyDeserializer"));
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProp.getProperty("valueDeserializer"));

        consumer = new KafkaConsumer<String, String>(properties);
        logger.info("********** Get consumer configuration info.");
    }

    /**
     * 实现一个带有再平衡监听的订阅方法
     *
     * @param topics
     * @return
     */
    public KafkaConsumer subscribeTopic(List topics) {

        this.consumer.subscribe(topics, new ConsumerRebalanceListener() {
            //本方法在消费者平衡操作开始之前、消费者停止拉取消息之后被调用
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                logger.info("********** onPartitionsRevoked().");
                //提交偏移量，以避免数据重复消费
                consumer.commitSync();
            }

            //本方法在平衡之后、消费者开始重新拉取消息之前被调用
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                logger.info("********** onPartitionsAssigned()");
                //重置偏移量，保证消费者从正确的位置开始消费
                long committedOffset = -1;
                for (TopicPartition topicPartition : partitions) {
                    committedOffset = consumer.committed(topicPartition).offset();
                    consumer.seek(topicPartition, committedOffset + 1);
                }
            }
        });

        return this.consumer;
    }

    /**
     * 创建一个单线程消费者，采用自动提交机制
     */
    public void singleThreadConsumer() {
        //TODO
        KafkaConsumer consumer = subscribeTopic(Arrays.asList(kafkaProp.getProperty("topic")));
        List<Map<String, String>> recordList;
        //kafka 高级api
        //consumer.subscribe(Collections.singletonList(kafkaProp.getProperty("topic")));//offset
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                updateKafkaToSolr(records);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            consumer.close();
        }
    }

    /***
     * @Description: change2Doc 将kafka消费到的record 返回转换成solr格式的document
     */
    public SolrInputDocument change2Doc(String message) {
        //TODO  使用遍历xml文件的形式代替循环
        /*
         * 1:调用InitPropertiesUtil.readXML()返回一个包含所有value的list数组
         * 2:遍历数组,然后转换
         * 3;问题:日期格式不一样的需要转换  车辆类型需要截取   这一类 (可以做一个判断,判断值是否是需要改的字符串 )
         * */

        SolrInputDocument doc = new SolrInputDocument();
        JSONObject map = JSONObject.fromObject(message);

        for (int i = 0; i < xmlList.size(); ) {
            /*System.out.println(xmlList.get(i));*/
            /*recordId id pointId pointId direction direction laneNumber laneNumber licenseType licenseType
            licenseNumber licenseNumber speed speed passingTime passingTime licenseColor color pic1 picPath
             vehLength vehLength*/
            //todo  有一些数据需要处理   eg:日期格式需要转换Solr;车辆类型需要截取
            //todo 放到map中??   前提是代码中不能出现字段名,字段名只能通过配置文件获取
            //todo  或者将需要修改的字段 放到一个单独的list??
            //todo 使用properties配置文件的方式单独管理 这些需要改变的字段
            //IsChange_passTime   IsChange_licenseType 因为kafka的字段总是0和偶数,所以ok
            /*使用if-else或者是switch比较?switch效率更好但是只能对单一常量进行匹配
             IsChange_passTime IsChange_licenseType
             添加到短路与  用作对是否触发的条件做判断
             */
            if ("true".equals(IsChange_passTime) && trigger_passTime.equals(xmlList.get(i))) {
                doc.addField(xmlList.get(i + 1), timeHelper.ChangeRecordTime2DocTime(map.get(xmlList.get(i)).toString()));
            } else if ("true".equals(IsChange_licenseType) && trigger_licenseType.equals(xmlList.get(i))) {
                doc.addField(xmlList.get(i + 1),
                        map.get(xmlList.get(i)).toString().length() > 1 ? map.get(xmlList.get(i)).toString().substring(1)
                                : map.get(xmlList.get(i)).toString());
            } else {
                doc.addField(xmlList.get(i + 1), map.get(xmlList.get(i)).toString());
            }
            i += 2;
        }
        //logger.info("  ***** " + doc);
        return doc;
    }

    /***
     * @Description: updateKafkaToSolr
     * 1:将从kafka拿到的records转换成Document 2:collection这些doc 写入到Solr
     */
    public void updateKafkaToSolr(ConsumerRecords<String, String> records) {

        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

        for (ConsumerRecord<String, String> record : records) {
            //System.out.println("-------kafka--------"+record);
            change2Doc = change2Doc(new String(record.value()));
            docs.add(change2Doc);
        }
        if (docs.size() > 0) {
            SolrClientHelper.addDucuments(docs);
            docs.clear();
        }
    }

    public static void main(String args[]) throws IOException {
        ConsumerTopicToSolrXML consumer = new ConsumerTopicToSolrXML();
        consumer.singleThreadConsumer();
    }
}
