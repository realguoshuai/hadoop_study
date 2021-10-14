![image](https://github.com/realguoshuai/hadoop_study/blob/master/hadoop%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE/%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE/hadoop/index.jpg)
## hadoop         
* [hadoop科普](https://github.com/realguoshuai/hadoop_study/wiki/%E7%A7%91%E6%99%AEHadoop) 
* [大数据书籍](https://github.com/realguoshuai/hadoop_study/wiki/%E5%A4%A7%E6%95%B0%E6%8D%AE%E4%B9%A6%E7%B1%8D)
* [大数据学习路线图](https://github.com/realguoshuai/hadoop_study/wiki/%E5%A4%A7%E6%95%B0%E6%8D%AE%E5%AD%A6%E4%B9%A0%E8%B7%AF%E7%BA%BF)
#### hadoop 导图笔记
* [hadoop伪分布式搭建](https://github.com/realguoshuai/hadoop_study/tree/master/hadoop%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE/%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE/hadoop/hadoop%E9%9B%86%E7%BE%A4%E6%90%AD%E5%BB%BA)
* [hadoop介绍](https://github.com/realguoshuai/hadoop_study/tree/master/hadoop%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE/%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE/hadoop/hadoop%E7%A7%91%E6%99%AE)
* [hadoop经典面试题及解题思路](https://github.com/realguoshuai/hadoop_study/tree/master/hadoop%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE/%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE/hadoop/%E9%9D%A2%E8%AF%95%E9%A2%98)
## hive 
*  [阅读官方文档](https://github.com/realguoshuai/hadoop_study/wiki/hadoop%E7%A4%BE%E5%8C%BA%E5%AE%98%E7%BD%91)
*  [导图笔记](https://github.com/realguoshuai/hadoop_study/wiki/%E5%A4%A7%E7%89%9BBlog)
*  [hive必会50道sql && 答案](https://github.com/realguoshuai/hadoop_study/blob/master/HiveSQL50.scala)
*  [hive开窗函数](https://blog.csdn.net/wangpei1949/article/details/81437574)
*  hive基本是大数据入门必学的,在离线计算用的比较多,市场需求大,有sql基础入门比较快
*  TODO hive调优 ... 
### Scala
*  [阅读官方文档](https://yq.aliyun.com/topic/69?utm_content=m_17543)
*  [导图笔记](https://github.com/realguoshuai/hadoop_study/wiki/%E5%A4%A7%E7%89%9BBlog)
*  [练手项目](https://github.com/realguoshuai/flink-train/tree/master/flink-train/src/main/scala/com/bigdata/train/scala)
*  scala 工作中flink/spark代码一般都是使用 Scala 实现 优先学! 
## Spark 
*  [阅读官方文档](https://yq.aliyun.com/topic/69?utm_content=m_17543) 
*  [导图笔记](https://github.com/realguoshuai/hadoop_study/wiki/%E5%A4%A7%E7%89%9BBlog)
*  [练手项目](https://github.com/realguoshuai/flink-train/tree/master/flink-train/src/main/scala/com/bigdata/train/spark)
#### SparkSQL  
*  [Spark DSL语法操作DF](https://github.com/realguoshuai/hadoop_study/blob/master/Spark2x/SparkSqlTrain.scala)
*  [Spark/Hive开窗函数](https://github.com/realguoshuai/hadoop_study/blob/master/Spark2x/SparkWindowSqlTrain.scala)
*  [Spark同步hive数据到solr](https://github.com/realguoshuai/hadoop_study/tree/master/Spark2x/mtdap-sparksql)
#### Spark Structured Streaming
*  后台我已经使用flink替代掉  
## Flink 
*  [Flink初识](https://github.com/realguoshuai/hadoop_study/blob/master/Flink/Flink%E6%96%87%E6%A1%A3/Flink%20%E5%88%9D%E8%AF%86.txt)
*  [Flink原理&40道面试题](https://github.com/realguoshuai/hadoop_study/blob/master/Flink/Flink%E6%96%87%E6%A1%A3/Flink%20%E5%8E%9F%E7%90%86.txt)
*  [Flink 常见QA](https://github.com/realguoshuai/hadoop_study/tree/master/Flink/QA%E5%BD%92%E6%A1%A3)
*  [Flink开发者资料](https://ververica.cn/developers-resources/)  
*  [Flink 阿里直播(每周四晚20:00-21:00))](https://github.com/flink-china/flink-training-course/)
*  [新老手都值得看的Flink关键技术解析与优化实战(转)](https://mp.weixin.qq.com/s/wrb_mSLbt_Zn5l8l-hKEBw)
*  真.流计算利器,但是资料比较少,建议直接看官方文档 我会不定期的将整理的文档和遇到bug的解决方式上传到Flink的对应目录下
*  18年夏天开始接手,到现在Flink开发近2年了,现独自负责公司实时计算部分.两年成果:后台使用flink 基于交通卡口数据(过车数据)实现了(实时ETL&&实时指标汇总&&实时预警)三大类,近30个job,已上线一个省会两个地级市; 
*  不足:FI平台的局限性,线上只能使用较低1.4.0版本(已更新1.7.2) 主要是写业务函数+DataStream/Process Function+缓存层,新版的东西还是没实际应用过(比如cep/table)
#### Flink项目
*  [Flink 学习项目](https://github.com/realguoshuai/flink-train/tree/master/flink-train)
*  [IDEA内使用socket模拟实时数据源 进行flink流处理](https://github.com/realguoshuai/hadoop_study/tree/master/Flink/Flink%E9%A1%B9%E7%9B%AE/flink%2
