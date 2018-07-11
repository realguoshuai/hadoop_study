<map version="1.0.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1531182177679" ID="ID_307975777" MODIFIED="1531269315344">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Solr&#26597;&#35810;Kafka
    </p>
  </body>
</html>
</richcontent>
<node CREATED="1531186078540" ID="ID_1920822665" MODIFIED="1531193850446" POSITION="left" TEXT="update">
<node CREATED="1531193443868" ID="ID_1361575210" MODIFIED="1531193446227" TEXT="util">
<node CREATED="1531193447405" ID="ID_479875541" MODIFIED="1531193454930" TEXT="KafkaHellper"/>
<node CREATED="1531193455364" ID="ID_958666250" MODIFIED="1531202357598" TEXT="LoggerHelper">
<node CREATED="1531202133966" ID="ID_575948240" MODIFIED="1531202139642" TEXT="import org.apache.log4j.Logger;"/>
<node CREATED="1531202127990" ID="ID_873661856" MODIFIED="1531202130578" TEXT="public static final Logger ROOT_LOGGER = Logger &#x9;&#x9;&#x9;.getLogger(LoggerHelper.class.getClass());">
<node CREATED="1531202109927" ID="ID_1643998263" MODIFIED="1531202113972" TEXT="&#x65e5;&#x5fd7;&#x8bb0;&#x5f55;"/>
</node>
<node CREATED="1531202172222" ID="ID_280483017" MODIFIED="1531202208003" TEXT="public static final Logger LOG = Logger.getLogger(&quot;RoolingFile&quot;);">
<node CREATED="1531202219454" ID="ID_767446797" MODIFIED="1531202228883" TEXT="&#x666e;&#x901a;&#x65e5;&#x5fd7;&#x8bb0;&#x5f55;"/>
</node>
<node CREATED="1531202209214" ID="ID_1623349785" MODIFIED="1531202216587" TEXT="public static final Logger AMQ_LOG = Logger.getLogger(&quot;AMQ&quot;);">
<node CREATED="1531202237296" ID="ID_1740707591" MODIFIED="1531202238684" TEXT="AMQ &#x76f8;&#x5173;&#x65e5;&#x5fd7;&#x8bb0;&#x5f55;"/>
</node>
</node>
<node CREATED="1531193461524" FOLDED="true" ID="ID_687930815" MODIFIED="1531207050213">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      SolrClientHelpe&#160;&#160;Solr&#24037;&#20855;&#31867;
    </p>
  </body>
</html></richcontent>
<node CREATED="1531193880348" ID="ID_414384678" MODIFIED="1531194464925" TEXT="getSolrClient()"/>
<node CREATED="1531194388717" ID="ID_872464868" MODIFIED="1531194474998" TEXT="getCloudSolrClient()"/>
<node CREATED="1531194483132" ID="ID_550065906" MODIFIED="1531194489284" TEXT="queryAllCollections()"/>
<node CREATED="1531194490244" ID="ID_339149489" MODIFIED="1531194500061" TEXT="initProperties()"/>
<node CREATED="1531194546036" ID="ID_1956580546" MODIFIED="1531194550181" TEXT="getSortClause()"/>
<node CREATED="1531194562092" ID="ID_864279307" MODIFIED="1531194563694" TEXT="createSimpleSolrClient()"/>
<node CREATED="1531194580716" ID="ID_1535992480" MODIFIED="1531194582614" TEXT="createCloudSolrClient()">
<node CREATED="1531194583765" ID="ID_307024298" MODIFIED="1531194589422" TEXT="&#x4ece;zookeeper&#x4e2d;&#x83b7;&#x53d6;cloude&#x4fe1;&#x606f;&#xff0c;&#x5e76;&#x5efa;&#x7acb;client&#x5bf9;&#x8c61;"/>
</node>
<node CREATED="1531194644325" ID="ID_971239718" MODIFIED="1531194694398" TEXT="addDucuments(Collection&lt;SolrInputDocument&gt; docs)"/>
<node CREATED="1531194702021" ID="ID_1627156801" MODIFIED="1531194703400" TEXT="addDucument(SolrInputDocument doc)"/>
<node CREATED="1531194714964" ID="ID_361399977" MODIFIED="1531194716101" TEXT="deleteById(String id)"/>
<node CREATED="1531194725325" ID="ID_792346402" MODIFIED="1531194726436" TEXT="deleteByQuery(String queryCon)"/>
<node CREATED="1531194738133" ID="ID_1437403147" MODIFIED="1531194739739" TEXT="queryDocs(String queryCon)"/>
</node>
<node CREATED="1531207030107" ID="ID_1061899517" MODIFIED="1531207032565" TEXT="page">
<node CREATED="1531207033830" ID="ID_1579026755" MODIFIED="1531207053944" TEXT="public class Page&lt;T&gt; implements Serializable"/>
<node CREATED="1531207063853" FOLDED="true" ID="ID_1637429870" MODIFIED="1531207394103" TEXT="&#x5c5e;&#x6027;">
<node CREATED="1531207076942" ID="ID_369514150" MODIFIED="1531207391709" TEXT="private int pageSize = 10;     //&#x6bcf;&#x9875;&#x5927;&#x5c0f;&#xa;private int totalPage;     //&#x603b;&#x9875;&#x6570;&#xa;private int totalCount;     //&#x603b;&#x6570;&#xa;private int currentPage = 1;     //&#x5f53;&#x524d;&#x9875;&#xa;private int currentCount;      //&#x5f53;&#x524d;&#x9875;&#x7684;&#x6570;&#x91cf;&#xa;private String sortExp;     &#xa;private String sortDir;     &#xa;List&lt;T&gt; result;"/>
</node>
<node CREATED="1531207128591" ID="ID_295462249" MODIFIED="1531207131212" TEXT="&#x65b9;&#x6cd5;">
<node CREATED="1531207131766" FOLDED="true" ID="ID_576751484" MODIFIED="1531207271516" TEXT="setResult&#xff08;&#xff09;">
<node CREATED="1531207237393" ID="ID_1829996437" MODIFIED="1531207269748" TEXT="public void setResult(List&lt;T&gt; result) {&#xa;        if(result != null) {&#xa;            Iterator var3 = result.iterator();&#xa;&#xa;            label31:&#xa;            while(true) {&#xa;                Object t;&#xa;                do {&#xa;                    if(!var3.hasNext()) {&#xa;                        break label31;&#xa;                    }&#xa;&#xa;                    t = (Object)var3.next();&#xa;                } while(!(t instanceof Map));&#xa;&#xa;                Iterator var5 = ((HashMap)t).keySet().iterator();&#xa;&#xa;                while(var5.hasNext()) {&#xa;                    Object key = var5.next();&#xa;                    if(((HashMap)t).get(key) instanceof CLOB) {&#xa;                        try {&#xa;                            CLOB clob = (CLOB)((HashMap)t).get(key);&#xa;                            ((HashMap)t).put(key, clob.getSubString(1L, (int)clob.length()));&#xa;                        } catch (SQLException var7) {&#xa;                            ;&#xa;                        } } } }  }&#xa;        this.result = result;&#xa;    }"/>
</node>
<node CREATED="1531207282831" FOLDED="true" ID="ID_1664844552" MODIFIED="1531207481060">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      getTotalPage&#65288;&#65289;
    </p>
  </body>
</html></richcontent>
<node CREATED="1531207285543" ID="ID_897888747" MODIFIED="1531207439197" TEXT="public int getTotalPage() {&#xa;        if(this.pageSize == 0) {&#xa;            return 0;&#xa;        } else {&#xa;            if(this.totalCount % this.pageSize == 0) {&#xa;                this.totalPage = this.totalCount / this.pageSize;&#xa;            } else {&#xa;                this.totalPage = this.totalCount / this.pageSize + 1;&#xa;            }&#xa;            return this.totalPage;&#xa;        }&#xa;  }"/>
</node>
<node CREATED="1531207482752" FOLDED="true" ID="ID_306552676" MODIFIED="1531207543764" TEXT="getCurrentPage&#xff08;&#xff09;">
<node CREATED="1531207505334" ID="ID_977126276" MODIFIED="1531207514789" TEXT=" public int getCurrentPage() {&#xa;        if(this.currentPage &lt;= 0) {&#xa;            this.currentPage = 1;&#xa;        }&#xa;        if(this.currentPage &gt; this.getTotalPage()) {&#xa;            this.currentPage = this.getTotalPage();&#xa;        }&#xa;        return this.currentPage;&#xa;    }"/>
</node>
<node CREATED="1531207546102" FOLDED="true" ID="ID_142496844" MODIFIED="1531207598421" TEXT="setCurrentPage&#xff08;&#xff09;">
<node CREATED="1531207558134" ID="ID_928896040" MODIFIED="1531207575748" TEXT="public void setCurrentPage(int currentPage) {&#xa;        if(currentPage &gt; 0) {&#xa;            this.currentPage = currentPage;&#xa;        }&#xa;    }"/>
</node>
<node CREATED="1531207599423" FOLDED="true" ID="ID_1573313582" MODIFIED="1531207633804" TEXT="getCurrentResult&#xff08;&#xff09;">
<node CREATED="1531207609967" ID="ID_142456429" MODIFIED="1531207623378" TEXT="public int getCurrentResult() {&#xa;        this.currentCount = (this.getCurrentPage() - 1) * this.getPageSize();&#xa;        if(this.currentCount &lt; 0) {&#xa;            this.currentCount = 0;&#xa;        }&#xa;        return this.currentCount;&#xa;    }"/>
</node>
<node CREATED="1531207713398" FOLDED="true" ID="ID_1894067444" MODIFIED="1531207751349" TEXT=" PageEasyUI     ConvertToPageEasyUI&#xff08;&#xff09;">
<node CREATED="1531207735543" ID="ID_331356977" MODIFIED="1531207749661" TEXT="public PageEasyUI ConvertToPageEasyUI() {&#xa;        PageEasyUI page = new PageEasyUI();&#xa;        page.setCurrentCount(this.currentCount);&#xa;        page.setCurrentPage(this.currentPage);&#xa;        page.setCurrentResult(this.currentCount);&#xa;        page.setPageSize(this.pageSize);&#xa;        page.setRows(this.result);&#xa;        page.setSortDir(this.sortDir);&#xa;        page.setSortExp(this.sortExp);&#xa;        page.setTotal(this.totalCount);&#xa;        page.setTotalPage(this.totalPage);&#xa;        return page;&#xa;    }"/>
</node>
</node>
</node>
</node>
<node CREATED="1531193505700" ID="ID_621176922" MODIFIED="1531193509594" TEXT="service"/>
<node CREATED="1531193509957" ID="ID_1344774694" MODIFIED="1531193512570" TEXT="thread"/>
</node>
<node CREATED="1531194779996" FOLDED="true" ID="ID_1016737086" MODIFIED="1531207056325" POSITION="left" TEXT="resources">
<node CREATED="1531194794644" ID="ID_493411572" MODIFIED="1531194803554" TEXT="applicationContext.xml"/>
<node CREATED="1531194803973" ID="ID_463306031" MODIFIED="1531194809834" TEXT="kafka.properties"/>
<node COLOR="#006699" CREATED="1531194810325" ID="ID_270383658" MODIFIED="1531200718337">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <font color="#000000">solr.prooperties</font>
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1531186070524" ID="ID_175962674" MODIFIED="1531189328160" POSITION="right" TEXT="query">
<node CREATED="1531186034557" ID="ID_1256309576" MODIFIED="1531186047315" TEXT="&#x5feb;&#x901f;&#x641c;&#x8f66;"/>
<node CREATED="1531186128243" FOLDED="true" ID="ID_51347388" MODIFIED="1531193809475" TEXT="pojo">
<icon BUILTIN="full-1"/>
<node CREATED="1531186186020" ID="ID_1908921057" MODIFIED="1531189328172" TEXT="&#x8fc7;&#x8f66;&#x8bb0;&#x5f55;&#x4fe1;&#x606f;&#x5c5e;&#x6027;">
<node CREATED="1531186240743" ID="ID_857301802" MODIFIED="1531186246553" TEXT="id"/>
<node CREATED="1531186248756" ID="ID_1669146865" MODIFIED="1531186255337" TEXT="bayonet_id"/>
<node CREATED="1531186265676" ID="ID_706531924" MODIFIED="1531186281433" TEXT="bayonet_name&#x5361;&#x53e3;&#x540d;&#x79f0;"/>
<node CREATED="1531186299379" ID="ID_763181004" MODIFIED="1531186333877" TEXT="direction&#x65b9;&#x5411;&#x7f16;&#x53f7;"/>
<node CREATED="1531186309820" ID="ID_1642190777" MODIFIED="1531186322020" TEXT="dir_name&#x65b9;&#x5411;&#x540d;&#x79f0;"/>
<node CREATED="1531186343172" ID="ID_1344771076" MODIFIED="1531186352257" TEXT="lane_id&#x8f66;&#x9053;&#x53f7;"/>
<node CREATED="1531186366316" ID="ID_1994719849" MODIFIED="1531186379577" TEXT="pass_time&#x8fc7;&#x8f66;&#x65f6;&#x95f4;"/>
<node CREATED="1531186390502" ID="ID_251939889" MODIFIED="1531186399129" TEXT="vehicle_plate&#x53f7;&#x724c;&#x53f7;&#x7801;"/>
<node CREATED="1531186411619" ID="ID_1162512815" MODIFIED="1531186422210" TEXT="vehicle_plate_type&#x53f7;&#x724c;&#x7c7b;&#x578b;"/>
<node CREATED="1531186446660" ID="ID_926082636" MODIFIED="1531186455764" TEXT="vehicle_plate_type_name&#x53f7;&#x724c;&#x7c7b;&#x578b;&#x540d;&#x79f0;"/>
<node CREATED="1531186467988" ID="ID_1940769113" MODIFIED="1531186474451" TEXT="vehicle_plate_color&#x53f7;&#x724c;&#x989c;&#x8272;"/>
<node CREATED="1531186481437" ID="ID_231766065" MODIFIED="1531186488078" TEXT="vehicle_plate_color_name&#x53f7;&#x724c;&#x989c;&#x8272;&#x540d;&#x79f0;"/>
<node CREATED="1531186495709" ID="ID_968858407" MODIFIED="1531186502269" TEXT="vehicle_type&#x8f66;&#x8f86;&#x7c7b;&#x578b;"/>
<node CREATED="1531186542916" ID="ID_129049997" MODIFIED="1531186551069" TEXT="vehicle_type_name&#x8f66;&#x8f86;&#x7c7b;&#x578b;&#x540d;&#x79f0;"/>
<node CREATED="1531186558900" ID="ID_773313761" MODIFIED="1531186564922" TEXT="vehicle_color&#x8f66;&#x8f86;&#x989c;&#x8272;"/>
<node CREATED="1531186572332" ID="ID_1672606915" MODIFIED="1531186578878" TEXT="vehicle_color_name&#x8f66;&#x8f86;&#x989c;&#x8272;&#x540d;&#x79f0;"/>
<node CREATED="1531186586539" ID="ID_769255158" MODIFIED="1531186593237" TEXT="vehicle_brand&#x8f66;&#x8f86;&#x54c1;&#x724c;"/>
<node CREATED="1531186594564" ID="ID_771337757" MODIFIED="1531186608075" TEXT="speed&#x8f66;&#x8f86;&#x901f;&#x5ea6;"/>
<node CREATED="1531186609292" ID="ID_1216022613" MODIFIED="1531186623609" TEXT="min_speed&#x6700;&#x5c0f;&#x884c;&#x9a76;&#x901f;&#x5ea6;"/>
<node CREATED="1531186624076" ID="ID_433407334" MODIFIED="1531186636981" TEXT="max_speed&#x6700;&#x5927;&#x884c;&#x9a76;&#x901f;&#x5ea6;"/>
<node CREATED="1531186650813" ID="ID_1740898172" MODIFIED="1531186656884" TEXT="data_source&#x6570;&#x636e;&#x6765;&#x6e90;"/>
<node CREATED="1531186666748" ID="ID_1650119106" MODIFIED="1531186673340" TEXT="data_source_name&#x6570;&#x636e;&#x6765;&#x6e90;&#x540d;&#x79f0;"/>
<node CREATED="1531186674844" ID="ID_1120767337" MODIFIED="1531186686805" TEXT="collect_type&#x91c7;&#x96c6;&#x65b9;&#x5f0f;"/>
<node CREATED="1531186694379" ID="ID_1816116841" MODIFIED="1531186699820" TEXT="collect_type_name&#x91c7;&#x96c6;&#x65b9;&#x5f0f;&#x540d;&#x79f0;"/>
<node CREATED="1531186706724" ID="ID_427793801" MODIFIED="1531186712134" TEXT="record_time&#x8bb0;&#x5f55;&#x65f6;&#x95f4;"/>
<node CREATED="1531186717940" ID="ID_963216499" MODIFIED="1531186724095" TEXT="pic_url1&#x56fe;&#x7247;&#x8def;&#x5f84;1"/>
<node CREATED="1531186732956" ID="ID_1034468284" MODIFIED="1531186745599" TEXT=" pic_url2&#x56fe;&#x7247;&#x8def;&#x5f84;2"/>
<node CREATED="1531186761636" ID="ID_782405096" MODIFIED="1531186769971" TEXT="pic_url3&#x56fe;&#x7247;&#x8def;&#x5f84;3"/>
<node CREATED="1531186774364" ID="ID_1875890051" MODIFIED="1531186779589" TEXT="pic_url4&#x56fe;&#x7247;&#x8def;&#x5f84;4"/>
<node CREATED="1531186781332" ID="ID_1295072780" MODIFIED="1531186792966" TEXT="video_url&#x89c6;&#x9891;&#x8def;&#x5f84;"/>
<node CREATED="1531186797892" ID="ID_1501238025" MODIFIED="1531186803027" TEXT="laneNumber&#x8f66;&#x9053;&#x6570;"/>
<node CREATED="1531186811363" ID="ID_400094863" MODIFIED="1531186818412" TEXT="vehLength&#x8f66;&#x8f86;&#x957f;&#x5ea6; "/>
</node>
<node CREATED="1531186953955" ID="ID_973529879" MODIFIED="1531186964880" TEXT="get set&#x65b9;&#x6cd5;"/>
</node>
<node CREATED="1531186137276" ID="ID_126002686" MODIFIED="1531193817251" TEXT="dao">
<node CREATED="1531188192268" ID="ID_1253246104" MODIFIED="1531201530992" TEXT="interface   ISolrVehSearDao">
<icon BUILTIN="full-3"/>
<node CREATED="1531188271932" ID="ID_543247572" MODIFIED="1531189328176" TEXT="&#x5bf9;&#x8fc7;&#x8f66;&#x8bb0;&#x5f55;&#x8fdb;&#x884c;&#x67e5;&#x8be2;&#x5e76;&#x83b7;&#x53d6;&#x5206;&#x9875;&#x7ed3;&#x679c;">
<node CREATED="1531188289212" ID="ID_377878289" MODIFIED="1531189328179" TEXT="&#x53c2;&#x6570;">
<node CREATED="1531188294108" ID="ID_1733479105" MODIFIED="1531188351082" TEXT=" @param id          &#x8fc7;&#x8f66;&#x4fe1;&#x606f;ID      &#xa;@param licenseNum  &#x53f7;&#x724c;&#x53f7;&#x7801;      &#xa; @param licenseType &#x53f7;&#x724c;&#x7c7b;&#x578b;      &#xa; @param vehType        &#x8f66;&#x8f86;&#x7c7b;&#x578b;      &#xa; @param startDay    &#x5206;&#x6790;&#x8d77;&#x59cb;&#x65e5;&#x671f;      &#xa; @param endDay      &#x5206;&#x6790;&#x622a;&#x6b62;&#x65e5;&#x671f;      &#xa; @param endDay      &#x5206;&#x6790;&#x622a;&#x6b62;&#x65e5;&#x671f;      &#xa; @param pointId      &#x70b9;&#x4f4d;&#x7f16;&#x7801;      &#xa; @param directionCode &#x5361;&#x53e3;&#x65b9;&#x5411;  String &#x53ef;&#x4ee5;&#x4e3a;null      &#xa; @param minSpeed     &#x901f;&#x5ea6;&#x8303;&#x56f4;&#x4e0b;&#x9650;  Integer &#x53ef;&#x4ee5;&#x4e3a;null     &#xa; @param maxSpeed     &#x901f;&#x5ea6;&#x8303;&#x56f4;&#x4e0a;&#x9650;  Integer &#x53ef;&#x4ee5;&#x4e3a;null      &#xa; @param page        &#x5206;&#x9875;&#x4fe1;&#x606f;      &#xa; @return &#x5305;&#x542b;&#x8fc7;&#x8f66;&#x4fe1;&#x606f;&#x7684;Page"/>
</node>
<node CREATED="1531188363292" ID="ID_1972649248" MODIFIED="1531188375962" TEXT="Page getPageVehSear(String id, String licenseType, String licenseNum, String vehType, String startDay, String endDay, String pointId, String directionCode, Integer minSpeed, Integer maxSpeed, Page page) throws Exception;"/>
</node>
<node CREATED="1531188393932" ID="ID_1403827682" MODIFIED="1531189328181" TEXT="&#x5bf9;&#x8fc7;&#x8f66;&#x8bb0;&#x5f55;&#x8fdb;&#x884c;&#x67e5;&#x8be2;&#x5e76;&#x83b7;&#x53d6;&#x5206;&#x9875;&#x7ed3;&#x679c;">
<node CREATED="1531188289212" ID="ID_1485423650" MODIFIED="1531189328184" TEXT="&#x53c2;&#x6570;">
<node CREATED="1531188294108" ID="ID_1194276194" MODIFIED="1531188447698" TEXT=" @param id          &#x8fc7;&#x8f66;&#x4fe1;&#x606f;ID      &#xa;@param licenseNum  &#x53f7;&#x724c;&#x53f7;&#x7801;      &#xa; @param licenseType &#x53f7;&#x724c;&#x7c7b;&#x578b;      &#xa; @param vehType        &#x8f66;&#x8f86;&#x7c7b;&#x578b;      &#xa; @param startDay    &#x5206;&#x6790;&#x8d77;&#x59cb;&#x65e5;&#x671f;      &#xa; @param endDay      &#x5206;&#x6790;&#x622a;&#x6b62;&#x65e5;&#x671f;      &#xa; @param endDay      &#x5206;&#x6790;&#x622a;&#x6b62;&#x65e5;&#x671f;      &#xa; @param pointId      &#x70b9;&#x4f4d;&#x7f16;&#x7801;      &#xa; @param directionCode &#x5361;&#x53e3;&#x65b9;&#x5411;  String &#x53ef;&#x4ee5;&#x4e3a;null      &#xa; @param minSpeed     &#x901f;&#x5ea6;&#x8303;&#x56f4;&#x4e0b;&#x9650;  Integer &#x53ef;&#x4ee5;&#x4e3a;null     &#xa; @param maxSpeed     &#x901f;&#x5ea6;&#x8303;&#x56f4;&#x4e0a;&#x9650;  Integer &#x53ef;&#x4ee5;&#x4e3a;null      &#xa; @return &#x5305;&#x542b;&#x8fc7;&#x8f66;&#x4fe1;&#x606f;&#x7684;List(&#x9ed8;&#x8ba4;&#x4e0a;&#x9650;10000&#x6761;&#x6570;&#x636e;)"/>
</node>
<node CREATED="1531188474172" ID="ID_899873425" MODIFIED="1531188475412" TEXT="List&lt;PassInfoPojo&gt; getVehSear(String id, String licenseType, String vehType, String licenseNumber, String startDay, String endDay, String pointId, String directionCode, Integer minSpeed, Integer maxSpeed) throws Exception;"/>
</node>
<node CREATED="1531188491604" ID="ID_396042603" MODIFIED="1531189328185" TEXT="&#x5bf9;&#x8fc7;&#x8f66;&#x8bb0;&#x5f55;&#x8fdb;&#x884c;&#x67e5;&#x8be2;&#x5e76;&#x83b7;&#x53d6;&#x5206;&#x9875;&#x7ed3;&#x679c;">
<node CREATED="1531188495453" ID="ID_239760641" MODIFIED="1531189328187" TEXT="&#x53c2;&#x6570;">
<node CREATED="1531188512556" ID="ID_1605766408" MODIFIED="1531188526334">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      @param ids &#36807;&#36710;&#20449;&#24687;ID&#30340;list&#160;&#160;&#160;&#160;&#160;&#160;
    </p>
    <p>
      @return &#21253;&#21547;&#36807;&#36710;&#20449;&#24687;&#30340;List(&#40664;&#35748;&#19978;&#38480;10000&#26465;&#25968;&#25454;)
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1531188531524" ID="ID_1350504432" MODIFIED="1531188538755" TEXT="List&lt;PassInfoPojo&gt; getVehSear(List&lt;String&gt; ids) throws Exception;"/>
</node>
</node>
<node CREATED="1531188204189" ID="ID_1447521099" MODIFIED="1531189328188" TEXT="imp    SolrVehSearDao">
<icon BUILTIN="full-4"/>
<node CREATED="1531188845492" ID="ID_212485989" MODIFIED="1531189328190" TEXT="implements ISolrVehSearDao">
<node CREATED="1531188869348" ID="ID_519102712" MODIFIED="1531189017387">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      @Repository&#160;&#160;&#160;&#29992;&#20110;&#26631;&#27880;&#25968;&#25454;&#35775;&#38382;&#32452;&#20214;&#65292;&#21363;DAO&#32452;&#20214;&#65307;
    </p>
    <p>
      public class SolrVehSearDao implements ISolrVehSearDao {
    </p>
    <p>
      //TODO
    </p>
    <p>
      }
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1531189037348" ID="ID_1481271913" MODIFIED="1531189352249" TEXT="&#x5b9e;&#x73b0;&#x63a5;&#x53e3;&#x4e2d;&#x4e09;&#x4e2a;&#x65b9;&#x6cd5;">
<node CREATED="1531189060780" ID="ID_1220951811" MODIFIED="1531189328202" TEXT="@Override     &#xa;public Page getPageVehSear(String id, String licenseType, String vehType, String licenseNumber, String beginTime, String endTime, String pointId, String directionCode, Integer minSpeed, Integer maxSpeed, Page page) throws Exception {        &#xa;&#xa;}">
<node CREATED="1531189182036" FOLDED="true" ID="ID_1809122410" MODIFIED="1531189602978" TEXT="&#x83b7;&#x53d6;Solr&#x5ba2;&#x6237;&#x7aef;&#x8fde;&#x63a5;">
<icon BUILTIN="full-1"/>
<node CREATED="1531189478012" ID="ID_106449998" MODIFIED="1531189491666" TEXT="org.apache.solr.client.solrj;">
<icon BUILTIN="info"/>
</node>
<node CREATED="1531189404068" ID="ID_1202877856" MODIFIED="1531189420043" TEXT="try {            &#xa; client = SolrClientHelper.getClient();        &#xa; } catch (Exception e) {             &#xa;LoggerHelper.LOG.error(&quot;instantiaze SolrClient failed :&quot; + e.getMessage());           &#xa;  throw new Exception(&quot;instantiaze SolrClient failed :&quot; + e.getMessage());        &#xa; }"/>
</node>
<node CREATED="1531189604404" ID="ID_219385613" MODIFIED="1531194559402" TEXT="&#x67e5;&#x8be2;&#x8f66;&#x724c;">
<node CREATED="1531189610036" ID="ID_483826482" MODIFIED="1531189619412" TEXT="SolrQuery params = new SolrQuery();             System.out.println(&quot;======================query-start==================&quot;);"/>
<node CREATED="1531189629132" ID="ID_1462288424" MODIFIED="1531192471280">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      &#160;&#160;//&#36710;&#29260; &amp; trackId:&#26597;&#35810;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
    </p>
  </body>
</html></richcontent>
<node CREATED="1531190016198" ID="ID_1574865495" MODIFIED="1531190018682" TEXT="&#xa0;&#xa0;licenseNumber = licenseNumber == null ? &quot;*&quot; : (licenseNumber + &quot;*&quot;);">
<node CREATED="1531189642844" ID="ID_1393424111" MODIFIED="1531189768670">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      &#19977;&#30446;&#160;&#160;&#22914;&#26524;&#20256;&#26469;&#30340;license&#20026;null&#23601;&#26159;*&#160;&#160;&#26597;&#20840;&#37096;
    </p>
    <p>
      &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#19981;&#20026;&#31354;&#23601;&#26159;license+*&#160;&#160;&#26681;&#25454;&#20256;&#30340;license+* &#27169;&#31946;&#26597;&#35810;
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1531190024292" ID="ID_931090038" MODIFIED="1531190053442" TEXT="//id &#x8fc7;&#x8f66;&#x4fe1;&#x606f;             &#xa;if (id != null) {                &#xa; params.setQuery(&quot;id:&quot; + id);            &#xa; } else {                 &#xa;//&#x8fc7;&#x8f66;&#x4fe1;&#x606f;id&#x4e3a;&#x7a7a; &#x4f7f;&#x7528;licenseNnumber&#x8f66;&#x724c;&#x53f7;&#x4f5c;&#x4e3a;&#x67e5;&#x8be2;&#x6761;&#x4ef6;                &#xa; params.setQuery(&quot;vehicle_plate:&quot; + licenseNumber);           &#xa;  }"/>
<node CREATED="1531192471304" ID="ID_873229881" MODIFIED="1531192492939" TEXT="if(null != beginTime || null != endTime)">
<node CREATED="1531192496157" ID="ID_1617971880" MODIFIED="1531192588629">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      null&#22312;&#21069;&#22312;&#21518;&#25928;&#26524;&#19968;&#26679; &#20225;&#19994;&#20013; &#19968;&#33324;&#20351;&#29992;null&#65281;=&#36825;&#31181;&#65292;&#36991;&#20813;&#23569;&#20889;&#65281;&#21644;&#31354;&#25351;&#38024;&#24322;&#24120;
    </p>
  </body>
</html></richcontent>
</node>
</node>
</node>
<node CREATED="1531189975556" ID="ID_1522952793" MODIFIED="1531189985321" TEXT=" //&#x65f6;&#x95f4;&#x3001;&#x53f7;&#x724c;&#x7c7b;&#x578b;&#x3001;&#x70b9;&#x4f4d;&#xff1a;&#x6761;&#x4ef6;&#x8fc7;&#x6ee4;"/>
</node>
</node>
<node CREATED="1531189074028" ID="ID_1257150675" MODIFIED="1531189168017" TEXT="@Override     &#xa;public List&lt;PassInfoPojo&gt; getVehSear(String id, String licenseType, String vehType, String licenseNumber, String beginTime, String endTime, String pointId, String directionCode, Integer minSpeed, Integer maxSpeed) throws Exception {      &#xa;&#xa;}"/>
<node CREATED="1531189093484" ID="ID_891713663" MODIFIED="1531189172551">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      @Override&#160;&#160;&#160;&#160;&#160;
    </p>
    <p>
      public List&lt;PassInfoPojo&gt; getVehSear(List&lt;String&gt; ids) throws Exception {&#160;&#160;&#160;&#160;&#160;&#160;
    </p>
    <p>
      
    </p>
    <p>
      }
    </p>
  </body>
</html></richcontent>
</node>
</node>
</node>
</node>
<node CREATED="1531186139379" FOLDED="true" ID="ID_651820202" MODIFIED="1531193812435" TEXT="service">
<node CREATED="1531187039524" ID="ID_72933981" MODIFIED="1531189328210">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      interfaceI&#160;&#160;&#160;SolrVehSearService &#24555;&#36895;&#25628;&#36710;&#25509;&#21475;
    </p>
  </body>
</html></richcontent>
<icon BUILTIN="full-2"/>
<node CREATED="1531187058852" ID="ID_504736906" MODIFIED="1531189328216">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      &#23545;&#36807;&#36710;&#35760;&#24405;&#36827;&#34892;&#26597;&#35810;&#24182;&#33719;&#21462;&#20998;&#39029;&#32467;&#26524;
    </p>
  </body>
</html></richcontent>
<node CREATED="1531187901068" ID="ID_1843067545" MODIFIED="1531189328220" TEXT="&#x53c2;&#x6570;">
<node CREATED="1531187848036" ID="ID_1612264055" MODIFIED="1531187890930" TEXT="@param id          &#x8fc7;&#x8f66;&#x4fe1;&#x606f;ID String &#x53ef;&#x4ee5;&#x4e3a;null      &#xa;@param licenseNum  &#x53f7;&#x724c;&#x53f7;&#x7801;  String &#x53ef;&#x4ee5;&#x4e3a;null&#xff0c;&#x652f;&#x6301;&#x6a21;&#x7cca;&#x67e5;&#x8be2;      &#xa;@param licenseType &#x53f7;&#x724c;&#x7c7b;&#x578b;  String &#x53ef;&#x4e3a;null      &#xa;@param vehType     &#x8f66;&#x724c;&#x7c7b;&#x578b;  String &#x53ef;&#x4e3a;null      &#xa;@param startDay    &#x5206;&#x6790;&#x8d77;&#x59cb;&#x65e5;&#x671f;  String &#x53ef;&#x4e3a;null      &#xa;@param endDay      &#x5206;&#x6790;&#x622a;&#x6b62;&#x65e5;&#x671f;  String &#x53ef;&#x4e3a;null      &#xa;@param pointId     &#x5206;&#x6790;&#x622a;&#x6b62;&#x65e5;&#x671f;  String[] &#x53ef;&#x4ee5;&#x4e3a;null      &#xa;@param directionCode &#x5361;&#x53e3;&#x65b9;&#x5411;  String &#x53ef;&#x4ee5;&#x4e3a;null      &#xa;@param minSpeed    &#x901f;&#x5ea6;&#x8303;&#x56f4;&#x4e0b;&#x9650;  Integer &#x53ef;&#x4ee5;&#x4e3a;null      &#xa;@param maxSpeed    &#x901f;&#x5ea6;&#x8303;&#x56f4;&#x4e0a;&#x9650;  Integer &#x53ef;&#x4ee5;&#x4e3a;null     &#xa;@param page        &#x5206;&#x9875;&#x4fe1;&#x606f; Page &#x4e0d;&#x53ef;&#x4e3a;null,&#x5fc5;&#x987b;&#x4f20;&#x5165;pagesize,currentPage      &#xa;@return &#x5305;&#x542b;&#x8fc7;&#x8f66;&#x4fe1;&#x606f;&#x7684;Page"/>
</node>
<node CREATED="1531187495861" ID="ID_1726118614" MODIFIED="1531187899874" TEXT="Page getPageVehSear(String id, String licenseType, String vehType, String licenseNum, String startDay, String endDay, String[] pointId, String directionCode, Integer minSpeed, Integer maxSpeed, Page page)throws Exception;&#xa;"/>
</node>
<node CREATED="1531187676436" ID="ID_747093643" MODIFIED="1531189328222" TEXT="&#x5bf9;&#x8fc7;&#x8f66;&#x8bb0;&#x5f55;&#x8fdb;&#x884c;&#x67e5;&#x8be2;&#x5e76;&#x83b7;&#x53d6;&#x5206;&#x9875;&#x7ed3;&#x679c;">
<node CREATED="1531187686188" ID="ID_1220603266" MODIFIED="1531189328223" TEXT="&#x4f18;&#x5316;">
<icon BUILTIN="button_ok"/>
<node CREATED="1531187691436" ID="ID_1224130119" MODIFIED="1531187702594" TEXT="&#x4e3a;&#x4e86;&#x63d0;&#x9ad8;&#x67e5;&#x8be2;&#x901f;&#x5ea6;&#xff0c;&#x5efa;&#x8bae;&#x5c3d;&#x53ef;&#x80fd;&#x8f93;&#x5165;&#x8f66;&#x724c;&#xff1b; &#x65f6;&#x95f4;&#x8303;&#x56f4;&#x4e0d;&#x786e;&#x5b9a;&#x65f6;&#x4f20;null,&#x6bd4;&#x4f20;&#x5177;&#x4f53;&#x65f6;&#x95f4;&#x8303;&#x56f4;&#x6027;&#x80fd;&#x66f4;&#x597d;"/>
</node>
<node CREATED="1531187752308" ID="ID_24518221" MODIFIED="1531189328226" TEXT="&#x53c2;&#x6570;">
<node CREATED="1531187711572" ID="ID_33369098" MODIFIED="1531187827697" TEXT=" @param id          &#x8fc7;&#x8f66;&#x4fe1;&#x606f;ID String  &#x53ef;&#x4ee5;&#x4e3a;null     &#xa; @param licenseNum  &#x53f7;&#x724c;&#x53f7;&#x7801; String  &#x53ef;&#x4ee5;&#x4e3a;null&#xff0c;&#x652f;&#x6301;&#x6a21;&#x7cca;&#x67e5;&#x8be2;      &#xa; @param licenseType &#x53f7;&#x724c;&#x7c7b;&#x578b;  String &#x53ef;&#x4e3a;null      &#xa; @param vehType     &#x8f66;&#x724c;&#x7c7b;&#x578b;  String &#x53ef;&#x4e3a;null      &#xa; @param startDay    &#x5206;&#x6790;&#x8d77;&#x59cb;&#x65e5;&#x671f;  String &#x53ef;&#x4e3a;null     &#xa; @param endDay      &#x5206;&#x6790;&#x622a;&#x6b62;&#x65e5;&#x671f; String  &#x53ef;&#x4e3a;null      &#xa; @param pointId     &#x5206;&#x6790;&#x622a;&#x6b62;&#x65e5;&#x671f;  String[] &#x53ef;&#x4ee5;&#x4e3a;null      &#xa; @param directionCode &#x5361;&#x53e3;&#x65b9;&#x5411;  String &#x53ef;&#x4ee5;&#x4e3a;null      &#xa; @param minSpeed    &#x901f;&#x5ea6;&#x8303;&#x56f4;&#x4e0b;&#x9650;  Integer &#x53ef;&#x4ee5;&#x4e3a;null      &#xa; @param maxSpeed    &#x901f;&#x5ea6;&#x8303;&#x56f4;&#x4e0a;&#x9650;  Integer &#x53ef;&#x4ee5;&#x4e3a;null      &#xa; @return &#x5305;&#x542b;&#x8fc7;&#x8f66;&#x4fe1;&#x606f;&#x7684;List (&#x9ed8;&#x8ba4;&#x4e0a;&#x9650;1000&#x6761;&#x6570;&#x636e;)">
<arrowlink DESTINATION="ID_33369098" ENDARROW="Default" ENDINCLINATION="0;0;" ID="Arrow_ID_1272280691" STARTARROW="None" STARTINCLINATION="0;0;"/>
</node>
</node>
<node CREATED="1531187762092" ID="ID_156517439" MODIFIED="1531187768772" TEXT="List&lt;PassInfoPojo&gt; getVehSear(String id, String licenseType, String vehType, String licenseNum, String startDay, String endDay, String[] pointId, String directionCode, Integer minSpeed, Integer maxSpeed) throws Exception; "/>
</node>
<node CREATED="1531187954508" ID="ID_907910763" MODIFIED="1531189328227" TEXT="&#x5bf9;&#x8fc7;&#x8f66;&#x8bb0;&#x5f55;&#x8fdb;&#x884c;&#x67e5;&#x8be2;&#x5e76;&#x83b7;&#x53d6;&#x5206;&#x9875;&#x7ed3;&#x679c;">
<node CREATED="1531187957212" ID="ID_686615864" MODIFIED="1531189328229" TEXT="&#x53c2;&#x6570;">
<node CREATED="1531187968764" ID="ID_1932950872" MODIFIED="1531187989522">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      &#160;@param ids &#36807;&#36710;&#20449;&#24687;ID&#30340;list&lt;String&gt;&#160;&#160;&#160;&#160;&#160;
    </p>
    <p>
      &#160;@return &#21253;&#21547;&#36807;&#36710;&#20449;&#24687;&#30340;Lists(&#40664;&#35748;&#19978;&#38480;1000&#26465;&#25968;&#25454;)
    </p>
  </body>
</html></richcontent>
</node>
</node>
<node CREATED="1531188022116" ID="ID_1069397322" MODIFIED="1531188024485" TEXT="List&lt;PassInfoPojo&gt; getVehSear(List&lt;String&gt; ids) throws Exception;"/>
</node>
</node>
<node CREATED="1531187048059" ID="ID_1055287790" MODIFIED="1531189328230">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      impl&#160;&#160;&#160;SolrVehSearService &#24555;&#36895;&#25628;&#36710;&#25509;&#21475;&#23454;&#29616;&#31867;
    </p>
  </body>
</html></richcontent>
<icon BUILTIN="full-5"/>
<node CREATED="1531188106996" ID="ID_1636733032" MODIFIED="1531188113184" TEXT="&#x6570;&#x636e;&#x6765;&#x6e90;&#xff1a;Solr"/>
</node>
</node>
<node CREATED="1531186166644" ID="ID_645826980" MODIFIED="1531186168066" TEXT="util"/>
</node>
</node>
</map>
