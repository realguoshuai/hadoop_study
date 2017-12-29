<map version="1.0.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1513993172244" ID="ID_806719942" MODIFIED="1513993192420" TEXT="hadoop&#x4f2a;&#x5206;&#x5e03;&#x5f0f;&#x642d;&#x5efa;">
<node CREATED="1513994083390" FOLDED="true" ID="ID_1003145456" MODIFIED="1513995447883" POSITION="right" TEXT="&#x6dfb;&#x52a0;&#x57df;&#x540d;&#x6620;&#x5c04;">
<node CREATED="1513994089949" ID="ID_1174464606" MODIFIED="1513994256022">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      vim /etc/hosts(&#38656;&#35201;root&#26435;&#38480;),&#32534;&#36753;master&#30340;<b><font color="#cc0033">&#22495;&#21517;&#26144;&#23556;&#26381;&#21153;</font></b>
    </p>
  </body>
</html>
</richcontent>
</node>
<node CREATED="1513994113588" FOLDED="true" ID="ID_7320697" MODIFIED="1513994171155" TEXT="&#x5728;&#x6587;&#x672c;&#x7684;&#x672b;&#x5c3e;&#x8ffd;&#x52a0;&#x4f60;&#x81ea;&#x5df1;&#x7684;&#x6240;&#x6709;&#x8282;&#x70b9;&#x4fe1;&#x606f;">
<node CREATED="1513994139548" ID="ID_1568158244" MODIFIED="1513994148475" TEXT="172.17.0.7&#xa0;&#xa0;&#xa0;&#xa0; master&#xa;172.17.0.10&#xa0;&#xa0;&#xa0;&#xa0; slave1&#xa;172.17.0.33&#xa0;&#xa0;&#xa0;&#xa0; slave2&#xa;172.17.0.8&#xa0;&#xa0;&#xa0;&#xa0; slave3&#xa;172.17.0.34&#xa0;&#xa0;&#xa0;&#xa0; client"/>
</node>
<node CREATED="1513994280988" ID="ID_1201605246" MODIFIED="1513994303785" TEXT="&#x4f7f;&#x7528;cat&#x547d;&#x4ee4;&#x67e5;&#x770b;&#x662f;&#x5426;&#x6210;&#x529f;"/>
<node CREATED="1513994318772" ID="ID_1660930661" MODIFIED="1513994346568" TEXT="&#x96c6;&#x7fa4;&#x4e2d;&#x7684;&#x6bcf;&#x53f0;&#x673a;&#x5668;&#x90fd;&#x8981;&#x914d;&#x7f6e;">
<icon BUILTIN="stop-sign"/>
</node>
</node>
<node CREATED="1513993351980" FOLDED="true" ID="ID_939641521" MODIFIED="1513996363836" POSITION="right" TEXT="&#x4e0b;&#x8f7d;jdk">
<node CREATED="1513993449647" ID="ID_1176958945" MODIFIED="1513995591569" TEXT="&#x5728;&#x7ebf;&#x5b89;&#x88c5;,&#x5b89;&#x88c5;&#x5230;&#x5f53;&#x524d;&#x76ee;&#x5f55;    &#x5efa;&#x8bae;/opt/java/&#x4e0b;"/>
<node CREATED="1513993674431" ID="ID_1202509737" MODIFIED="1513993710320">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <b><font color="#cc0033">wget http://download.oracle.com/otn/java/jdk/7u76-b13/jre-7u76-linux-x64.tar.gz</font></b>
    </p>
  </body>
</html>
</richcontent>
<icon BUILTIN="button_ok"/>
</node>
<node CREATED="1513993728391" ID="ID_704083795" MODIFIED="1513993737948" TEXT="&#x89e3;&#x538b;jdk&#x5b89;&#x88c5;&#x5305;"/>
<node CREATED="1513993739126" ID="ID_352873156" MODIFIED="1513993945639" TEXT="tar -axvf    jdk&#x538b;&#x7f29;&#x5305;&#x540d;"/>
<node CREATED="1513993391120" ID="ID_1402520722" MODIFIED="1513996356051" TEXT="&#x914d;&#x7f6e;jdk&#x73af;&#x5883;&#x53d8;&#x91cf;">
<node CREATED="1513995491931" ID="ID_1047943168" MODIFIED="1513995501960" TEXT="vim /etc/profile"/>
<node CREATED="1513995502355" FOLDED="true" ID="ID_1109386248" MODIFIED="1513995807192" TEXT="&#x5728;profile&#x6587;&#x4ef6;&#x672b;&#x5c3e;&#x6dfb;&#x52a0;&#x4ee5;&#x4e0b;&#x4ee3;&#x7801;,shift + g &#x76f4;&#x63a5;&#x8df3;&#x5230;&#x6587;&#x4ef6;&#x6700;&#x540e;&#x4e00;&#x884c;:">
<node CREATED="1513995520947" ID="ID_965800801" MODIFIED="1513995539602" TEXT="export JAVA_HOME=/opt/java/jdk1.7.0_76&#xa;export JAVA_BIN=/opt/java/jdk1.7.0_76/bin&#xa;export PATH=$PATH:$JAVA_HOME/bin&#xa;export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar&#xa;export JAVA_HOME JAVA_BIN PATH CLASSPATH"/>
</node>
</node>
</node>
<node CREATED="1513995628578" FOLDED="true" ID="ID_1472947259" MODIFIED="1513995795720" POSITION="right" TEXT="&#x62f7;&#x8d1d;&#x5230;&#x96c6;&#x7fa4;&#x4e2d;&#x5176;&#x4ed6;&#x670d;&#x52a1;&#x5668;">
<icon BUILTIN="button_ok"/>
<node CREATED="1513995672065" ID="ID_298870958" MODIFIED="1513995734964" TEXT="scp   &#x9700;&#x8981;&#x62f7;&#x8d1d;&#x7684;&#x6587;&#x4ef6;&#x5939;    root@192.168.137.11:/etc/"/>
<node CREATED="1513995651113" ID="ID_1821215658" MODIFIED="1513995663993" TEXT=" scp  /etc/profile  root@192.168.137.11:/etc/"/>
<node CREATED="1513995750121" ID="ID_422377199" MODIFIED="1513995786376" TEXT="&#x5b89;&#x88c5;&#x597d;&#x7684;jdk  hadoop &#x914d;&#x7f6e;&#x6587;&#x4ef6; &#x90fd;&#x53ef;&#x4ee5;&#x91c7;&#x7528;&#x8fd9;&#x79cd;&#x65b9;&#x5f0f;">
<icon BUILTIN="button_ok"/>
</node>
</node>
<node CREATED="1513993193764" FOLDED="true" ID="ID_331242715" MODIFIED="1513995956975" POSITION="right" TEXT="&#x521b;&#x5efa;hadoop&#x7528;&#x6237;">
<node CREATED="1513993218746" ID="ID_1563925755" MODIFIED="1513995886430" TEXT=" adduser hadoop"/>
<node CREATED="1513993227725" ID="ID_950495052" MODIFIED="1513995884005" TEXT=" passwd hadoop  &#x5bc6;&#x7801;123456 "/>
<node CREATED="1513995890528" ID="ID_394555669" MODIFIED="1513995897503" TEXT="reboot #&#x91cd;&#x542f;"/>
<node CREATED="1513995938063" ID="ID_1775241616" MODIFIED="1513995954557" TEXT="su hadoop #&#x5207;&#x6362;&#x5230;hadoop&#x7528;&#x6237;"/>
</node>
<node CREATED="1513993212265" FOLDED="true" ID="ID_724708725" MODIFIED="1513995823153" POSITION="right" TEXT="&#x52a0;&#x5165;&#x5230;&#x7528;&#x6237;&#x7ec4;">
<node CREATED="1513993244471" ID="ID_382612188" MODIFIED="1513993253888" TEXT="sudo usermod -G sudo hadoop"/>
</node>
<node CREATED="1513993364391" FOLDED="true" ID="ID_1387526991" MODIFIED="1513995987150" POSITION="right" TEXT="&#x914d;&#x7f6e;ssh&#x514d;&#x5bc6;&#x767b;&#x5f55;">
<node CREATED="1513994734903" FOLDED="true" ID="ID_617338957" MODIFIED="1513994785944" TEXT="&#x5148;&#x751f;&#x6210;&#x5bc6;&#x5319;&#xa;">
<node CREATED="1513994378330" ID="ID_373197916" MODIFIED="1513994405880" TEXT="&#x6267;&#x884c;ssh-keygen&#x751f;&#x6210;&#x670d;&#x52a1;&#x5668;&#x5bc6;&#x5319;"/>
<node CREATED="1513994434146" FOLDED="true" ID="ID_1019945397" MODIFIED="1513994583010" TEXT="&#x4e00;&#x8def;&#x56de;&#x8f66;,&#x9047;&#x5230;y/n&#x9009;y">
<node CREATED="1513994481897" ID="ID_466257074" MODIFIED="1513994522103" TEXT="&#x7b2c;&#x4e00;&#x4e2a;&#x8be2;&#x95ee;&#x5c06;&#x516c;&#x79c1;&#x5319;&#x5b58;&#x653e;&#x5728;&#x54ea;,&#x56de;&#x8f66;&#x8868;&#x793a;&#x9ed8;&#x8ba4;"/>
<node CREATED="1513994522665" ID="ID_1982415161" MODIFIED="1513994554862" TEXT="&#x7b2c;&#x4e8c;&#x8bf7;&#x6c42;&#x8f93;&#x5165;&#x5bc6;&#x5319;,&#x56de;&#x8f66;&#x8868;&#x793a;&#x4f7f;&#x7528;&#x7a7a;&#x5bc6;&#x5319;"/>
<node CREATED="1513994560721" ID="ID_1399213652" MODIFIED="1513994578671" TEXT="&#x7b2c;&#x4e09;&#x4e2a;&#x8868;&#x793a;&#x786e;&#x8ba4;&#x5bc6;&#x5319;,&#x56de;&#x8f66;"/>
</node>
<node CREATED="1513994584105" FOLDED="true" ID="ID_764195592" MODIFIED="1513994693448" TEXT="ls -all /root/.ssh&#x67e5;&#x770b;&#x662f;&#x5426;&#x751f;&#x6210;id_rsa&#x548c;id_rsa_pub ">
<node CREATED="1513994658216" ID="ID_1092807805" MODIFIED="1513994665393" TEXT="&#x516c;&#x94a5;&#x7528;&#x4e8e;&#x52a0;&#x5bc6;&#xff0c;&#x79c1;&#x94a5;&#x7528;&#x4e8e;&#x89e3;&#x5bc6;"/>
<node CREATED="1513994666369" ID="ID_492869984" MODIFIED="1513994673367" TEXT="rsa&#x8868;&#x793a;&#x7b97;&#x6cd5;&#x4e3a;RSA&#x7b97;&#x6cd5;"/>
</node>
</node>
<node CREATED="1513994751361" FOLDED="true" ID="ID_346888257" MODIFIED="1513994899838" TEXT="&#x62f7;&#x8d1d;&#x5bc6;&#x5319;&#x5230;&#x672c;&#x673a;">
<node CREATED="1513994771097" ID="ID_989247371" MODIFIED="1513994887309" TEXT="ssh-copy-id master  #&#x7b2c;&#x4e00;&#x6b21;&#x9700;&#x8981;&#x8f93;&#x5165;&#x5bc6;&#x7801;"/>
<node CREATED="1513994795911" ID="ID_425945751" MODIFIED="1513994884922" TEXT="ssh-master   #&#x6d4b;&#x8bd5;&#x514d;&#x5bc6;"/>
</node>
<node CREATED="1513994896967" FOLDED="true" ID="ID_1024385308" MODIFIED="1513994946598" TEXT="&#x62f7;&#x8d1d;&#x5bc6;&#x5319;&#x5230;&#x5176;&#x4ed6;&#x670d;&#x52a1;&#x5668;">
<node CREATED="1513994918687" ID="ID_859800834" MODIFIED="1513994942076" TEXT="ssh-copy-id  slave1  #slave1-slaveX"/>
</node>
<node CREATED="1513994952022" FOLDED="true" ID="ID_1625317622" MODIFIED="1513995158436" TEXT="&#x5728;&#x5176;&#x4f59;&#x8282;&#x70b9; &#x91cd;&#x590d;&#x4e0a;&#x8ff0;&#x64cd;&#x4f5c;,&#x6700;&#x7ec8;&#x786e;&#x4fdd;&#x6bcf;&#x4e00;&#x4e2a;&#x8282;&#x70b9;&#x4e0a;&#x90fd;&#x6709;&#x5176;&#x4f59;&#x670d;&#x52a1;&#x5668;&#x751f;&#x6210;&#x7684;&#x5bc6;&#x5319;">
<node CREATED="1513995011285" ID="ID_1524560490" MODIFIED="1513995035353" TEXT="&#x4f7f;&#x7528; ssh slave1&#x670d;&#x52a1;&#x5668;&#x4e4b;&#x95f4;&#x8fdb;&#x884c;&#x4e92;&#x8054;"/>
<node CREATED="1513995071014" ID="ID_1619991921" MODIFIED="1513995101536" TEXT="&#x54ea;&#x4e00;&#x6b65;&#x9700;&#x8981;&#x8f93;&#x5165;&#x5bc6;&#x7801;&#x8868;&#x793a;&#x4e24;&#x8005;&#x4e4b;&#x95f4;&#x5bc6;&#x5319;&#x6ca1;&#x6709;&#x62f7;&#x8d1d;"/>
</node>
</node>
<node CREATED="1513995989463" FOLDED="true" ID="ID_2869868" MODIFIED="1513996435708" POSITION="right" TEXT="&#x5b89;&#x88c5;hadoop">
<node CREATED="1513996001639" FOLDED="true" ID="ID_283299306" MODIFIED="1513996211469" TEXT="&#x4e0b;&#x8f7d;hadoop&#x538b;&#x7f29;&#x5305;">
<node CREATED="1513996060462" ID="ID_1595194482" MODIFIED="1513996209861" TEXT="1:&#x65b9;&#x6cd5;&#x5f88;&#x591a; &#x4e0b;&#x8f7d; windows&#x4e0b; rz &#x62ff;&#x5230;&#xa;2;wget  hadoop&#x4e0b;&#x8f7d;&#x94fe;&#x63a5;,&#x5728;&#x7ebf;&#x4e0b;&#x8f7d;&#x5230;&#x5f53;&#x524d;&#x76ee;&#x5f55;&#xa;3:&#x9ed8;&#x8ba4;/home/hadoop"/>
</node>
<node CREATED="1513996056375" FOLDED="true" ID="ID_1061594275" MODIFIED="1513996236869" TEXT="&#x89e3;&#x538b;hadoop">
<node CREATED="1513996221326" ID="ID_307265642" MODIFIED="1513996234324" TEXT="[hadoop@master ~]#  cd   /home/hadoop&#xa;[hadoop@master ~]#  tar  -zxvf  /home/hadoop/hadoop-2.6.4.tar.gz "/>
</node>
<node CREATED="1513996261853" ID="ID_309212406" MODIFIED="1513996270866" TEXT="&#x914d;&#x7f6e;hadoop&#x73af;&#x5883;&#x53d8;&#x91cf;">
<node CREATED="1513996272126" ID="ID_972502299" MODIFIED="1513996297468" TEXT="&#x8fdb;&#x5165;/home/hadoop&#x76ee;&#x5f55;"/>
<node CREATED="1513996299021" FOLDED="true" ID="ID_215030526" MODIFIED="1513996331220" TEXT="vi .bash_profile">
<node CREATED="1513996327261" ID="ID_1734000631" MODIFIED="1513996330131" TEXT="PATH=$PATH:$HOME/bin:/home/hadoop/hadoop-2.6.4/sbin       &#xa;export PATH&#xa;export JAVA_HOME=/opt/java/jdk1.7.0_76&#xa;export JAVA_BIN=/opt/java/jdk1.7.0_76/bin&#xa;export PATH=$PATH:$JAVA_HOME/bin&#xa;export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar&#xa;export JAVA_HOME JAVA_BIN PATH CLASSPATH&#xa;&#xa;HADOOP_HOME=/home/hadoop/hadoop-2.6.4&#xa;HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop&#xa;PATH=$HADOOP_HOME/bin:$PATH&#xa;export HADOOP_HOME HADOOP_CONF_DIR PATH"/>
</node>
</node>
<node CREATED="1513996336580" FOLDED="true" ID="ID_214919705" MODIFIED="1513996380996" TEXT="&#x4f7f;&#x73af;&#x5883;&#x53d8;&#x91cf;&#x751f;&#x6548;">
<node CREATED="1513996372460" ID="ID_424630330" MODIFIED="1513996380083" TEXT=" source  .bash_profile"/>
</node>
</node>
<node CREATED="1513996437028" FOLDED="true" ID="ID_1726428012" MODIFIED="1513997006128" POSITION="right" TEXT="&#x914d;&#x7f6e;hadoop&#x914d;&#x7f6e;&#x6587;&#x4ef6;">
<node CREATED="1513996458340" ID="ID_1804734193" MODIFIED="1513996699529" TEXT="hadoop-env.sh">
<node CREATED="1513996474204" ID="ID_1541855940" MODIFIED="1513996486361" TEXT="hadoop&#x542f;&#x52a8;&#x65f6;&#x914d;&#x7f6e;&#x7684;&#x73af;&#x5883;&#x53d8;&#x91cf;"/>
<node CREATED="1513996628954" ID="ID_1127658053" MODIFIED="1513996666217" TEXT="&#x5728;&#x914d;&#x7f6e;&#x6587;&#x4ef6;&#x7684;&#x672b;&#x5c3e;&#xa;export JAVA_HOME=/opt/java/jdk1.7.0_76  #&#x81ea;&#x5df1;jdk&#x7684;&#x8def;&#x5f84;">
<icon BUILTIN="penguin"/>
</node>
</node>
<node CREATED="1513996673707" FOLDED="true" ID="ID_903174185" MODIFIED="1513996724273" TEXT="yarn-env.sh">
<node CREATED="1513996693394" ID="ID_550011291" MODIFIED="1513996694641" TEXT="export JAVA_HOME=/opt/java/jdk1.7.0_76"/>
</node>
<node CREATED="1513996489035" FOLDED="true" ID="ID_144386749" MODIFIED="1513996802233" TEXT="core-site.xml">
<node CREATED="1513996501732" ID="ID_1231727065" MODIFIED="1513996517665" TEXT="Hadoop Core&#x7684;&#x914d;&#x7f6e;&#x9879;"/>
<node CREATED="1513996518188" ID="ID_1994108764" MODIFIED="1513996536651" TEXT="&#x4f8b;&#x5982;HDFS&#x548c;MapReduce&#x5e38;&#x7528;&#x7684;I/O&#x64cd;&#x4f5c;"/>
<node CREATED="1513996742385" ID="ID_1021856393" MODIFIED="1513996800353" TEXT="&lt;configuration&gt;&#xa;        &lt;property&gt;&#xa;             &lt;name&gt;fs.defaultFS&lt;/name&gt;                   &#x9;&#x9;&#x9;&lt;value&gt;hdfs://master:9000&lt;/value&gt; &#xa;             &lt;description&gt;NameNode URI.&lt;/description&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;              &lt;name&gt;io.file.buffer.size&lt;/name&gt;  &#xa;              &lt;value&gt;131072&lt;/value&gt;&#xa;              &lt;description&gt;Size of read/write buffer used inSequenceFiles.&lt;/description&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;              &lt;name&gt;hadoop.tmp.dir&lt;/name&gt;  &#xa;              &lt;value&gt;file:///home/hadoop/temp&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;              &lt;name&gt;hadoop.proxyuser.root.hosts&lt;/name&gt;&#xa;              &lt;value&gt;*&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;              &lt;name&gt;hadoop.proxyuser.root.groups&lt;/name&gt;&#xa;              &lt;value&gt;*&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;&lt;/configuration&gt;">
<icon BUILTIN="penguin"/>
</node>
</node>
<node CREATED="1513996543291" FOLDED="true" ID="ID_1387402062" MODIFIED="1513996832137" TEXT="hdfs-site.xml">
<node CREATED="1513996817804" ID="ID_1920028421" MODIFIED="1513996819881" TEXT="">
<icon BUILTIN="penguin"/>
</node>
<node CREATED="1513996821313" ID="ID_743860050" MODIFIED="1513996830968" TEXT="&lt;configuration&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;dfs.namenode.secondary.http-address&lt;/name&gt;&#xa;                &lt;value&gt;master:9001&lt;/value&gt;&#xa;         &lt;description&gt;The secondary namenode http server address andport.&lt;/description&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;dfs.namenode.name.dir&lt;/name&gt;&#xa;                &lt;value&gt;file:///home/hadoop/hadoop-2.6.4/dfs/name&lt;/value&gt;&#xa;        &lt;description&gt;Path on the local filesystem where the NameNodestores the namespace and transactions logs persistently.&lt;/description&gt;     &#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;dfs.datanode.data.dir&lt;/name&gt;                        &lt;value&gt;file:///home/hadoop/hadoop-2.6.4/dfs/data&lt;/value&gt;&#xa;             &lt;description&gt;Comma separated list of paths on the local filesystemof a DataNode where it should store its blocks.&lt;/description&gt;  &#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;dfs.namenode.checkpoint.dir&lt;/name&gt;   &#xa;                &lt;value&gt;file:///home/hadoop/hadoop-2.6.4/dfs/namesecondary&lt;/value&gt;&#xa;        &lt;description&gt;Determines where on the local filesystem the DFSsecondary name node should store the temporary images to merge. If this is acomma-delimited list of directories then the image is replicated in all of thedirectories for redundancy.&lt;/description&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;dfs.replication&lt;/name&gt;                              &lt;value&gt;2&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;dfs.webhdfs.enabled&lt;/name&gt;&#xa;&#xa;&#xa;                &lt;value&gt;true&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;dfs.permissions&lt;/name&gt;                         &lt;value&gt;false&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;dfs.web.ugi&lt;/name&gt;                             &lt;value&gt;supergroup&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;&lt;/configuration&gt;"/>
</node>
<node CREATED="1513996552859" FOLDED="true" ID="ID_1028116076" MODIFIED="1513996872912" TEXT="mapred-site.xml">
<node CREATED="1513996868097" ID="ID_1680292627" MODIFIED="1513996871672" TEXT="&lt;configuration&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;mapreduce.framework.name&lt;/name&gt;  &#xa;                      &lt;value&gt;yarn&lt;/value&gt;                    &#xa;        &lt;description&gt;Theruntime framework for executing MapReduce jobs. Can be one of local, classic oryarn.&lt;/description&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;mapreduce.jobhistory.address&lt;/name&gt;&#xa;                &lt;value&gt;master:10020&lt;/value&gt;&#xa;            &lt;description&gt;MapReduce JobHistoryServer IPC host:port&lt;/description&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;mapreduce.jobhistory.webapp.address&lt;/name&gt;&#xa;                &lt;value&gt;master:19888&lt;/value&gt;&#xa;               &lt;description&gt;MapReduce JobHistoryServer Web UI host:port&lt;/description&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;        &lt;name&gt;mapred.remote.os&lt;/name&gt;&#xa;        &lt;value&gt;Linux&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;        &lt;name&gt;mapreduce.app-submission.cross-platform&lt;/name&gt;&#xa;        &lt;value&gt;true&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;        &lt;name&gt;mapreduce.application.classpath&lt;/name&gt;&#xa;        &lt;value&gt;&#xa;        /home/hadoop/hadoop-2.6.4/etc/hadoop,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/common/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/common/lib/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/hdfs/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/hdfs/lib/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/mapreduce/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/mapreduce/lib/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/yarn/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/yarn/lib/*&#xa;       &lt;/value&gt;&#xa;       &lt;/property&gt;    &#xa;&lt;/configuration&gt;">
<icon BUILTIN="penguin"/>
</node>
</node>
<node CREATED="1513996563995" FOLDED="true" ID="ID_990465416" MODIFIED="1513996900680" TEXT="yarn-site.xml">
<node CREATED="1513996896296" ID="ID_1327153109" MODIFIED="1513996899511" TEXT="&lt;configuration&gt;&#xa;          &lt;property&gt;&#xa;                &lt;name&gt;yarn.resourcemanager.hostname&lt;/name&gt;&#xa;                &lt;value&gt;master&lt;/value&gt;&#xa;                &lt;description&gt;The hostname of theRM.&lt;/description&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;yarn.nodemanager.aux-services&lt;/name&gt;&#xa;                &lt;value&gt;mapreduce_shuffle&lt;/value&gt;&#xa;   &lt;description&gt;Shuffle service that needs to be set for Map Reduceapplications.&lt;/description&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;yarn.nodemanager.aux-services.mapreduce.shuffle.class&lt;/name&gt;&#xa;                &lt;value&gt;org.apache.hadoop.mapred.ShuffleHandler&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;yarn.resourcemanager.address&lt;/name&gt;&#xa;                &lt;value&gt;master:8032&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;yarn.resourcemanager.scheduler.address&lt;/name&gt;&#xa;                &lt;value&gt;master:8030&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;yarn.resourcemanager.resource-tracker.address&lt;/name&gt;&#xa;                &lt;value&gt;master:8031&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;yarn.resourcemanager.admin.address&lt;/name&gt;&#xa;                &lt;value&gt;master:8033&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;                &lt;name&gt;yarn.resourcemanager.webapp.address&lt;/name&gt;&#xa;                &lt;value&gt;master:8088&lt;/value&gt;&#xa;        &lt;/property&gt;&#xa;        &lt;property&gt;&#xa;    &lt;name&gt;yarn.application.classpath&lt;/name&gt;&#xa;    &lt;value&gt;&#xa;        /home/hadoop/hadoop-2.6.4/etc/hadoop,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/common/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/common/lib/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/hdfs/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/hdfs/lib/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/mapreduce/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/mapreduce/lib/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/yarn/*,&#xa;        /home/hadoop/hadoop-2.6.4/share/hadoop/yarn/lib/*&#xa;    &lt;/value&gt;&#xa;  &lt;/property&gt;&#xa;&lt;/configuration&gt;"/>
</node>
<node CREATED="1513996570827" FOLDED="true" ID="ID_835912535" MODIFIED="1513997000855" TEXT="slaves">
<node CREATED="1513996997138" ID="ID_1254602741" MODIFIED="1513996999799" TEXT="[hadoop@master hadoop]# vi  slaves&#xa;slaver1&#xa;slaver2"/>
</node>
<node CREATED="1513996585011" FOLDED="true" ID="ID_1809722519" MODIFIED="1513996614211" TEXT="&#x6ce8;&#x610f;&#xa;">
<icon BUILTIN="messagebox_warning"/>
<node CREATED="1513996594803" ID="ID_1487719627" MODIFIED="1513996602833" TEXT="hadoop-env.sh&#x548c;yarn-env.sh&#x91cc;&#x9762;&#x90fd;&#x8981;&#x6dfb;&#x52a0;jdk&#x7684;&#x73af;&#x5883;&#x53d8;&#x91cf;"/>
</node>
</node>
<node CREATED="1513997170488" FOLDED="true" ID="ID_1446079214" MODIFIED="1513997227155" POSITION="right" TEXT="&#x62f7;&#x8d1d;&#x4e3b;&#x8282;&#x70b9;&#x5230;&#x5176;&#x5b83;&#x5b50;&#x8282;&#x70b9;">
<icon BUILTIN="button_ok"/>
<node CREATED="1513997192335" ID="ID_850131779" MODIFIED="1513997220741" TEXT="&#x5728;&#x4e3b;&#x8282;&#x70b9;&#x6267;&#x884c;:&#xa;scp  -r  /home/hadoop/hadoop-2.6.4/  hadoop@slaver1:/home/hadoop/&#xa;scp  -r  /home/hadoop/hadoop-2.6.4/  hadoop@slaver2:/home/hadoop/&#xa;scp  -r  /home/hadoop/hadoop-2.6.4/  hadoop@slaver3:/home/hadoop/&#xa;..."/>
</node>
<node CREATED="1513997234991" FOLDED="true" ID="ID_755060249" MODIFIED="1513997262367" POSITION="right" TEXT="&#x683c;&#x5f0f;&#x5316;&#x4e3b;&#x8282;&#x70b9;&#x7684;namenode">
<node CREATED="1513997244687" ID="ID_655096018" MODIFIED="1513997256159" TEXT="[hadoop@master ~]# cd  /home/hadoop/hadoop-2.6.4&#xa;[hadoop@master hadoop-2.6.4]# ./bin/hadoop  namenode  -format"/>
</node>
<node CREATED="1513997263324" FOLDED="true" ID="ID_1960245681" MODIFIED="1513997358343" POSITION="right" TEXT="&#x542f;&#x52a8;hadoop">
<node CREATED="1513997322236" ID="ID_826189111" MODIFIED="1513997339629" TEXT="&#x5728;&#x4e3b;&#x8282;&#x70b9;:&#xa;[hadoop@master ~]# cd  /home/hadoop/hadoop-2.6.4&#xa;[hadoop@master hadoop-2.6.4]#./sbin/start-all.sh"/>
<node CREATED="1513997341150" ID="ID_1484033663" MODIFIED="1513997354886" TEXT="&#x6bcf;&#x4e2a;&#x8282;&#x70b9;&#x90fd;&#x6309;&#x7167;&#x4e0a;&#x9762;&#x6b65;&#x9aa4;&#x5f00;&#x542f;"/>
</node>
<node CREATED="1513997359628" FOLDED="true" ID="ID_1721541231" MODIFIED="1513997426308" POSITION="right" TEXT="&#x67e5;&#x770b;&#x662f;&#x5426;&#x542f;&#x52a8;&#x6210;&#x529f;">
<node CREATED="1513997394110" FOLDED="true" ID="ID_601198396" MODIFIED="1513997400300" TEXT="&#x4e3b;&#x8282;&#x70b9;">
<node CREATED="1513997368044" ID="ID_258239592" MODIFIED="1513997381684" TEXT="[hadoop@master hadoop-2.6.4]# jps&#xa;NameNode&#xa;SecondaryNameNode&#xa;ResourceManager&#xa;Jps"/>
</node>
<node CREATED="1513997401181" ID="ID_1123793222" MODIFIED="1513997403211" TEXT="&#x5b50;&#x8282;&#x70b9;">
<node CREATED="1513997403917" ID="ID_47510998" MODIFIED="1513997419808" TEXT="[hadoop@slaver1 hadoop-2.6.4]# jps&#xa;DataNode&#xa;NodeManager&#xa;Jps"/>
</node>
</node>
</node>
</map>
