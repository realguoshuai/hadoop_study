phoenix 自定义函数UDF-测试(用于轨迹反查)
201906041152,001|201906041155,002|...
时间1,卡口1|时间2,卡口2

输入: 参数1:201906041152,001|201906041155,002|... 字符串 参数2: 201906040000 字符串 参数3:201906042359
	判断是否在参数2范围
判断: 201906041152,001|201906041155,002|... 是否在参数2,参数3的范围区间 (是输出1,否 输出2)
输出0,001|1,002
实现步骤
1:继承ScalarFunction类 实现自定义函数 打包
2:上传到hdfs上
3:CREATE FUNCTION judgeTrackTime(varchar) returns varchar as 'com.guoshuai.mtdap.UDFS.PhoenixUdfDemo' using jar 'hdfs://hacluster/MTDAP/UDF/PhoenixUDF.jar'

hdfs上传:hdfs dfs -put file:/opt/PhoenixUDF.jar hdfs://hacluster/MTDAP/UDF/
查看文件:hadoop fs -ls hdfs://hacluster/MTDAP/UDF
删除文件:hadoop fs -rm  hdfs://hacluster/MTDAP/UDF/PhoenixUDF.jar

------------------------------------------------------------------------
注意:程序的hbase-site.xml配置文件和服务端都需要添加一下配置
<property>
  <name>phoenix.functions.allowUserDefinedFunctions</name>
  <value>true</value>
</property>
<property>
  <name>fs.hdfs.impl</name>
  <value>org.apache.hadoop.hdfs.DistributedFileSystem</value>
</property>
<property>
  <name>hbase.dynamic.jars.dir</name>
  <value>hdfs://hacluster/MTDAP/UDF</value>
</property>



异常:如果不添加上面的
抛:User defined functions are configured to not be allowed. To allow configure phoenix.functions.allowUserDefinedFunctions to true.
