package mtdap.consumer;



import com.google.gson.Gson;
import mtdap.common.EsHelper;
import mtdap.common.InitPropertiesUtil;
import mtdap.common.TimeHelper;
import net.sf.json.JSONObject;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description  从二次识别后的卡口记录 写入到ES
 * Param
 * return
 **/

public class ReKakoudata2Es {

    private static Logger logger = Logger.getLogger(ReKakoudata2Es.class.getName());
    private static Properties kafkaProp = InitPropertiesUtil.initKafkaProp();

    private static Properties triggerProp = InitPropertiesUtil.initTriggerProp();
    private static TimeHelper timeHelper;
    private static String IsChange_passTime;
    private static String IsChange_licenseType;
    private static String trigger_passTime;
    private static String trigger_licenseType;

    private static String IsChange_deptId;
    private static String trigger_deptId;
    /*获取到的入库时间是当前时间*/
    private static String IsChange_insertTime;
    private static String trigger_insertTime;

    private static ArrayList<String> xmlList = InitPropertiesUtil.readXML();
    public BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>(500000);
    long flagTime = System.currentTimeMillis();
    Properties properties;
    KafkaConsumer<String, String> consumer;


    static {
        new EsHelper();
        IsChange_passTime = triggerProp.getProperty("passTimeChange"); //true or  other
        IsChange_licenseType = triggerProp.getProperty("licenseTypeChange");//true or  other
        trigger_passTime = triggerProp.getProperty("kafka_passingTime");//kafka  field
        trigger_licenseType = triggerProp.getProperty("kafka_licenseType");//kafka  field

        /*dept_id*/
        IsChange_deptId = triggerProp.getProperty("deptId");
        trigger_deptId = triggerProp.getProperty("kafka_deptId");
        /*insert_time*/
        IsChange_insertTime = triggerProp.getProperty("insert_time");
        trigger_insertTime = triggerProp.getProperty("kafka_insertTime");
    }

    public ReKakoudata2Es() {
        properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProp.getProperty("bootstrap"));
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProp.getProperty("groupid"));
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaProp.getProperty("clientid"));
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProp.getProperty("keyDeserializer"));
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProp.getProperty("valueDeserializer"));
        //properties.put("security.protocol", kafkaProp.getProperty("security.protocol"));
        //properties.put("sasl.kerberos.service.name", kafkaProp.getProperty("sasl.kerberos.service.name"));
        consumer = new KafkaConsumer<String, String>(properties);
        //properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,kafkaProp.getProperty("auto.offset.reset"));
        logger.info("********** Get consumer configuration info.");
    }

    /**
     * 实现一个带有再平衡监听的订阅方法
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

    /***
     * @Description: change2Map 将kafka消费到的record 返回转换成Map传到es
     */
    public Map<String, Object> change2Map(String message) {
        Map<String, Object> jsonMap = new HashMap<>();
        /*转成json格式*/
        JSONObject map = JSONObject.fromObject(message);

        for (int i = 0; i < xmlList.size(); ) {
            if ("true".equals(IsChange_passTime) && trigger_passTime.equals(xmlList.get(i))) {
                if (null != map.get(xmlList.get(i)) && !"".equals(map.get(xmlList.get(i)) + "".trim()))
                    jsonMap.put(xmlList.get(i + 1), timeHelper.ChangeRecordTime2DocTime(map.get(xmlList.get(i)).toString()));
            } else if ("true".equals(IsChange_licenseType) && trigger_licenseType.equals(xmlList.get(i))) {
                if (null != map.get(xmlList.get(i)) && !"".equals(map.get(xmlList.get(i)) + "".trim()))
                    jsonMap.put(xmlList.get(i + 1),
                            map.get(xmlList.get(i)).toString().length() > 1 ? map.get(xmlList.get(i)).toString().substring(1)
                                    : map.get(xmlList.get(i)).toString());
            } else if ("true".equals(IsChange_deptId) && trigger_deptId.equals(xmlList.get(i))) {
                if (null != map.get(xmlList.get(i)) && !"".equals(map.get(xmlList.get(i)) + "".trim()))
                    jsonMap.put(xmlList.get(i + 1), map.get(xmlList.get(i).split(",").toString()));
            } else if ("true".equals(IsChange_insertTime) && trigger_insertTime.equals(xmlList.get(i))) {
                if (null != map.get(xmlList.get(i)) && !"".equals(map.get(xmlList.get(i)) + "".trim()))
                    jsonMap.put(xmlList.get(i + 1), timeHelper.getCalenderDateString(System.currentTimeMillis()));
            } else {
                if (null != map.get(xmlList.get(i)) && !"".equals(map.get(xmlList.get(i)) + "".trim()))
                    jsonMap.put(xmlList.get(i + 1), map.get(xmlList.get(i)).toString());
            }
            i += 2;
        }
        return jsonMap;
    }

    /***
     * @Description: change2Map 将kafka消费到的record,中间处理后  转成json
     */
    public String change2EsJson(String message) {
        Gson gson = new Gson();
        Map<String, Object> jsonMap = new HashMap<>();
        /*转成json格式*/
        JSONObject map = JSONObject.fromObject(message);

        for (int i = 0; i < xmlList.size(); ) {
            if ("true".equals(IsChange_passTime) && trigger_passTime.equals(xmlList.get(i))) {
                if (null != map.get(xmlList.get(i)) && !"".equals(map.get(xmlList.get(i)) + "".trim()))
                    jsonMap.put(xmlList.get(i + 1), timeHelper.ChangeRecordTime2DocTime(map.get(xmlList.get(i)).toString()));
            } else if ("true".equals(IsChange_licenseType) && trigger_licenseType.equals(xmlList.get(i))) {
                if (null != map.get(xmlList.get(i)) && !"".equals(map.get(xmlList.get(i)) + "".trim()))
                    jsonMap.put(xmlList.get(i + 1),
                            map.get(xmlList.get(i)).toString().length() > 1 ? map.get(xmlList.get(i)).toString().substring(1)
                                    : map.get(xmlList.get(i)).toString());
            } else if ("true".equals(IsChange_deptId) && trigger_deptId.equals(xmlList.get(i))) {
                if (null != map.get(xmlList.get(i)) && !"".equals(map.get(xmlList.get(i)) + "".trim()))
                    jsonMap.put(xmlList.get(i + 1), map.get(xmlList.get(i).split(",").toString()));
            } else if ("true".equals(IsChange_insertTime) && trigger_insertTime.equals(xmlList.get(i))) {
                if (null != map.get(xmlList.get(i)) && !"".equals(map.get(xmlList.get(i)) + "".trim()))
                    jsonMap.put(xmlList.get(i + 1), timeHelper.getCalenderDateString(System.currentTimeMillis()));
            } else {
                if (null != map.get(xmlList.get(i)) && !"".equals(map.get(xmlList.get(i)) + "".trim()))
                    jsonMap.put(xmlList.get(i + 1), map.get(xmlList.get(i)).toString());
            }
            i += 2;
        }
        String strJson = gson.toJson(jsonMap);
        return strJson;
    }

    /***
     * @Description: updateKafkaToES
     * 1:将从kafka拿到的records转换成Document 2:collection这些doc 写入到Solr
     */
    public void updateKafkaToES(ConsumerRecords<String, String> records) {
        for (ConsumerRecord<String, String> record : records) {
            String esJson = change2EsJson(record.value());
            blockingQueue.add(esJson);
        }

        try {
            //EsHelper.putData(change2EsJson(record.value())); //ok
            //外部控制循环 先将数据放到blockqueen 中 5s 或 大于20条
            if (blockingQueue.size() > 20) {
                LinkedList<String> linkedList = new LinkedList<>();
                System.out.println("trigger commit is > 20");
                blockingQueue.drainTo(linkedList);
                EsHelper.bulk3(linkedList);
                flagTime = System.currentTimeMillis();
            }

            if (flagTime > 0 && System.currentTimeMillis() - flagTime > 5000) {
                LinkedList<String> linkedList = new LinkedList<>();
                System.out.println("trigger commit is 5s");
                blockingQueue.drainTo(linkedList);
                EsHelper.bulk3(linkedList);
                flagTime = System.currentTimeMillis();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doWork() {
        KafkaConsumer consumer = subscribeTopic(Arrays.asList(kafkaProp.getProperty("topic")));
        List<Map<String, String>> recordList;
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(2000);
                /*for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.value());
                }*/
                updateKafkaToES(records);

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            consumer.close();
        }
    }

    public static void main(String args[]) throws IOException {
        ReKakoudata2Es consumer = new ReKakoudata2Es();
        consumer.doWork();
    }

}
