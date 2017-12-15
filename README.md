# hadoop_study

hadoop学习
    谷歌三篇论文
        Google  Big Table
            HBase
         Google  MapReduce
            MapReduce
        GFS谷歌文件系统
            HDFS
    主流的三大分布式计算系统 :

        Hadoop
            开源
            Yahoo，Facebook，Amazon以及国内的百度，阿里巴巴等众多互联网公司
都以Hadoop为基础搭建自己的分布式计算系统
        Spark
            加州大学伯克利分校的实验室开发,它在Hadoop的基础上进行了一些架构上的改良
            Hadoop 与Spark的区别
                Spark与Hadoop最大区别在于:
Hadoop是基于硬盘存储数据
Spark使用内存存储数据,因此Spark可以提供超过Hadoop100倍的运算速度

缺点:断电后 内存数据会丢失,所以Spark不能用于处理需要长期保存的数据
        Storm
            Strom是推特Twitter主推的分布式计算系统
它在Hadoop的基础上提供了实时运算的特性，可以实时的处理大数据流
            Strom与前两者的区别

                Storm不进行数据的收集和存储工作，它直接通过网络实时的接受数据并且实时的处理数据，
然后直接通过网络实时的传回结果。
        总结:
             Hadoop，Spark和Storm是目前最重要的三大分布式计算系统，
Hadoop常用于离线的复杂的大数据处理，Spark常用于离线的快速的大数据处理，而Storm常用于在线的实时的大数据处理。
    为什么要学习hadoop
        Hadoop已经出到了3.0.0 (2017.12.13 前天发布)
已经形成一个生态体系,类似Linux系统,想学习大数据,就离不开hadoop

        Hadoop提供了一个可靠的共享存储和分析系统。HDFS实现存储，而MapReduce实现分析处理。
这两部分是它的核心。
    Hadoop的主要项目 <22312037_1383144326wtS8.png>
    专业术语
        分布式和集群
            分布式:一个业务拆成多个子业务,部署在不同的服务器上
            集群:同一个业务 部署在多个服务器上
        集中式计算和分布式计算
            集中式计算:
就是通过不断增加处理器的数量
来增强单个计算机的计算能力 比如:超级计算机
            分布式计算:
就是把一组计算机通过网络相互连接 组成分散系统
然后将需要处理的大量数据分散成多个部分,交由分散系统内
计算机组同时计算,最后将这些计算结果合并得到最终的结果
    大数据
        4V
        达到PB级别
            GB  TB  PB EB ZB YB
        大数据的技术基础:
            源自于Google2003-2004年发布的三篇论文 
MapReduce、Google File System和BigTable,提出了一套全新的分布式理论
    怎样学
        阅读文档
            必须积累单词
            怎样阅读源码
                第一阶段
                    学习Hadoop基本使用和基本原理,从应用角度对Hadoop进行了解和学习
                    在应用层面上需要达到
                        使用hadoop shell对hdfs进行操作，
使用hdfs API编写一些程序上传，下载文件；
使用MapReduce API编写一个数据处理程序。
                    试着了解它的内部原理(博客,书籍)
                        对于HDFS
                            了解HDFS的基本架构以及各模块的功能
                        对于MapReduce
                            了解它具体的工作流程,知道partition shuffle sort 等的工作原理
                            可以在纸上画出MapReduce的流程,越详细越好

                    这个阶段的注意事项:
                        多看一些知名博客
                        多读读Hadoop权威指南(先选择性的看相关的)
                        有项目,联系项目驱动学习
                        多讲给别人听
                第二阶段
                    从无到有,开始阅读Hadoop源码(工作一年后了)
                    书籍:Hadoop技术内幕
                    最终目的:
                        对hadoop源代码整体架构和局部的很多细节，有了一定的了解
                        比如你知道MapReduce Scheduler是怎样实现的，MapReduce shuffle过程中，map端做了哪些事情，reduce端做了哪些事情，是如何实现的等等。
                        这个阶段完成后，当你遇到问题或者困惑点时，可以迅速地在Hadoop源代码中定位相关的类和具体的函数，通过阅读源代码解决问题，这时候，hadoop源代码变成了你解决问题的参考书。
                第三阶段
                    根据需求,修改源代码
                    当成一种修养.通过阅读hadoop源代码，加深自己对分布式系统的理解
        给别人讲出来
        看书
            Hadoop初学者
                Hadoop权威指南(必读)
                Hadoop实战
                Hadoop Operation
            Hadoop中级
                《Hadoop技术内幕：深入解析MapReduce架构设计与实现原理》
                《深入解析Hadoop Common和HDFS架构设计与实现原理》
                《深入解析Hadoop Common和HDFS架构设计与实现原理》
    YARN
        Yet Another Resource Negotiator，另一种资源协调者
        为上层应用提供统一的资源管理和调度
    Common
        一组分布式文件系统和通用
I/O组件与接口(序列化  Java RPC 和持久化数据结构)
    MapReduce
        分布式数据处理模型和执行环境
,运行于大型商用主机的并行计算框架
    HDFS
        分布式文件系统,运行于大型商用集群
    Zookeeper
        一个分布式 可用性高的协调服务
提供分布式锁之类的基本服务用于构建分布式技术
    Hbase
        一个分布式 按列存储的数据库
使用HDFS作为底层存储,同时支持MapReduce的
批量式计算和点查询
    Hive
        一个分布式 按列存储的数据仓库
管理HDFS中存储的数据,并提供基于sql
的查询语言用以查询数据
