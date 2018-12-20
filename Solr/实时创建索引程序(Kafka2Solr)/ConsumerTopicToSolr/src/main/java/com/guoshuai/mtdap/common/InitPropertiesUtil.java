package com.guoshuai.mtdap.common;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class InitPropertiesUtil {

    private static Logger logger = Logger.getLogger(InitPropertiesUtil.class.getName());
    //todo 测试 一会需要删除
    private static TimeHelper timeHelper;


    /**
     * get kafka's properties
     * @return java.util.Properties
     */
    public static Properties initKafkaProp() {
        Properties prop = new Properties();
        InputStream in = InitPropertiesUtil.class.getResourceAsStream("/kafka.properties");

        try {
            if (in == null) {
                logger.error("kafka's properties init failed in is null");
            }
            prop.load(new BufferedInputStream(in));
            logger.info("kafka's properties init ok");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return prop;
    }

    /**
     * get phoenix's properties
     *
     * @return java.util.Properties
     */
    public static Properties initPhoenixProp() {
        Properties prop = new Properties();
        InputStream in = InitPropertiesUtil.class.getResourceAsStream("/phoenix.properties");

        try {
            if (in == null) {
                logger.error("phoenix's properties init failed in is null");
            }
            prop.load(new BufferedInputStream(in));
            logger.info("phoenix's properties init ok");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return prop;
    }

    /**
     * get phoenix's properties
     *
     * @return java.util.Properties
     */
    public static Properties initSolrProp() {
        Properties prop = new Properties();
        InputStream in = InitPropertiesUtil.class.getResourceAsStream("/solr.properties");

        try {
            if (in == null) {
                logger.error("solr's properties init failed in is null");
            }
            prop.load(new BufferedInputStream(in));
            logger.info("solr's properties init ok");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return prop;
    }

    /**
     * 获取trigger.properties文件来判断是否需要对数据源进行处理
     * @return java.util.Properties
     */
    public static Properties initTriggerProp() {
        Properties prop = new Properties();
        InputStream in = InitPropertiesUtil.class.getResourceAsStream("/trigger.properties");

        try {
            if (in == null) {
                logger.error("trigger.properties init failed in is null");
            }
            prop.load(new BufferedInputStream(in));
            logger.info("trigger.properties init ok");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return prop;
    }
    /**
     * 读取xml文件
     * 遍历出来,放到list或者是放到map中
     */
    public static ArrayList<String>  readXML() {
        //发布到服务器上时:出现找不到Main.xml文件
        String LOCAL_LIST_PATH = InitPropertiesUtil.class.getClassLoader().getResource("Main.xml").getPath();
        //String LOCAL_LIST_PATH = InitPropertiesUtil.class.getResource("/Main.xml").getPath();
        InputStream stream = InitPropertiesUtil.class.getResourceAsStream("/Main.xml");
        /*System.out.println("path: "+LOCAL_LIST_PATH);*/
        //1.读取
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            //document = reader.read(LOCAL_LIST_PATH);
            document = reader.read(stream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        /*System.out.println("document: "+document);*/
        //2.获得根元素
        Element rootElement = document.getRootElement();
        //3.遍历
        Iterator it = rootElement.elementIterator();
        Element element  = null;
        ArrayList<String> list = new ArrayList<>();
        while(it.hasNext()){
            //迭代
            element = (Element)it.next();
            /*获取key的值
            String key = element.attributeValue("name");*/
            //获取到value的值
            String value = element.elementText("value");
            //放到一个数组
            list.add(value);
        }
        return list;
    }

    public static void main(String[] args){
           // readXML();
    }
}
