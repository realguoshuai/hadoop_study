<map version="1.0.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1514527160749" ID="ID_1953597906" MODIFIED="1514531131938" TEXT="&#x9762;&#x8bd5;&#x9898;&#x53ca;&#x89e3;&#x9898;&#x601d;&#x8def;&#x601d;&#x8def;">
<node CREATED="1514531156579" FOLDED="true" ID="ID_120947914" MODIFIED="1514532289581" POSITION="right" TEXT="1: &#x6d77;&#x91cf;&#x65e5;&#x5fd7;&#x6570;&#x636e;&#xff0c;&#x63d0;&#x53d6;&#x51fa;&#x67d0;&#x65e5;&#x8bbf;&#x95ee;&#x767e;&#x5ea6;&#x6b21;&#x6570;&#x6700;&#x591a;&#x7684;&#x90a3;&#x4e2a;IP">
<node CREATED="1514531780338" ID="ID_839833775" MODIFIED="1514532156471" TEXT="&#x53d6;&#x51fa;&#x8fd9;&#x4e00;&#x5929;&#x65e5;&#x5fd7;&#x4e2d;&#x7684;IP,&#x9010;&#x6b65;&#x5199;&#x5165;&#x4e00;&#x4e2a;&#x5927;&#x6587;&#x4ef6;&#x4e2d;">
<icon BUILTIN="full-1"/>
</node>
<node CREATED="1514531847460" ID="ID_1065991843" MODIFIED="1514532158348" TEXT="ip-&gt;32&#x4f4d;,&#x6700;&#x591a;&#x6709;2^32&#x4e2a;IP =4G&#x79cd;&#x53d6;&#x503c;,&#x4e0d;&#x80fd;&#x653e;&#x5230;&#x5185;&#x5b58;">
<icon BUILTIN="full-2"/>
</node>
<node CREATED="1514531891684" FOLDED="true" ID="ID_1519589065" MODIFIED="1514532164701" TEXT="&#x91c7;&#x7528;&#x6620;&#x5c04;&#x7684;&#x65b9;&#x6cd5;, &#x6bd4;&#x5982;&#x6a21;1000&#xff0c;&#x628a;&#x6574;&#x4e2a;&#x5927;&#x6587;&#x4ef6;&#x6620;&#x5c04;&#x4e3a;1000&#x4e2a;&#x5c0f;&#x6587;&#x4ef6;&#xff0c;&#x518d;&#x627e;&#x51fa;&#x6bcf;&#x4e2a;&#x5c0f;&#x6587;&#x4e2d;&#x51fa;&#x73b0;&#x9891;&#x7387;&#x6700;&#x5927;&#x7684;IP&#xa;">
<icon BUILTIN="full-3"/>
<node CREATED="1514532107760" ID="ID_1635304874" MODIFIED="1514532121998" TEXT="(&#x53ef;&#x4ee5;&#x91c7;&#x7528;hash_map&#x8fdb;&#x884c;&#x9891;&#x7387;&#x7edf;&#x8ba1;&#xff0c;&#x7136;&#x540e;&#x518d;&#x627e;&#x51fa;&#x9891;&#x7387;&#x6700;&#x5927; &#x7684;&#x51e0;&#x4e2a;)"/>
</node>
<node CREATED="1514532147886" ID="ID_60316662" MODIFIED="1514532162159" TEXT="&#x53ca;&#x76f8;&#x5e94;&#x7684;&#x9891;&#x7387;&#x3002;&#x7136;&#x540e;&#x518d;&#x5728;&#x8fd9;1000&#x4e2a;&#x6700;&#x5927;&#x7684;IP&#x4e2d;&#xff0c;&#x627e;&#x51fa;&#x90a3;&#x4e2a;&#x9891;&#x7387;&#x6700;&#x5927;&#x7684;IP&#xff0c;&#x5373;&#x4e3a;&#x6240;&#x6c42;&#x3002;">
<icon BUILTIN="full-4"/>
</node>
<node CREATED="1514532193895" FOLDED="true" ID="ID_412959198" MODIFIED="1514532284859">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      &#31639;&#27861;&#24605;&#24819;:<font color="#990000"><b>&#20998;&#32780;&#27835;&#20043;+Hash</b></font>
    </p>
  </body>
</html>
</richcontent>
<node CREATED="1514532266590" ID="ID_538791943" MODIFIED="1514532270523" TEXT="IP&#x5730;&#x5740;&#x6700;&#x591a;&#x6709;2^32=4G&#x79cd;&#x53d6;&#x503c;&#x60c5;&#x51b5;&#xff0c;&#x6240;&#x4ee5;&#x4e0d;&#x80fd;&#x5b8c;&#x5168;&#x52a0;&#x8f7d;&#x5230;&#x5185;&#x5b58;&#x4e2d;&#x5904;&#x7406;;&#xa;&#xa;&#x53ef;&#x4ee5;&#x8003;&#x8651;&#x91c7;&#x7528;&#x201c;&#x5206;&#x800c;&#x6cbb;&#x4e4b;&#x201d;&#x7684;&#x601d;&#x60f3;&#xff0c;&#x6309;&#x7167;IP&#x5730;&#x5740;&#x7684;Hash(IP)%1024&#x503c;&#xff0c;&#x628a;&#x6d77;&#x91cf;IP&#x65e5;&#x5fd7;&#x5206;&#x522b;&#x5b58;&#x50a8;&#x5230;1024&#x4e2a;&#x5c0f;&#x6587;&#x4ef6;&#x4e2d;&#x3002;&#x8fd9;&#x6837;&#xff0c;&#x6bcf;&#x4e2a;&#x5c0f;&#x6587;&#x4ef6;&#x6700;&#x591a;&#x5305;&#x542b;4MB&#x4e2a;IP&#x5730;&#x5740;;&#xa;&#xa;&#x5bf9;&#x4e8e;&#x6bcf;&#x4e00;&#x4e2a;&#x5c0f;&#x6587;&#x4ef6;&#xff0c;&#x53ef;&#x4ee5;&#x6784;&#x5efa;&#x4e00;&#x4e2a;IP&#x4e3a;key&#xff0c;&#x51fa;&#x73b0;&#x6b21;&#x6570;&#x4e3a;value&#x7684;Hash map&#xff0c;&#x540c;&#x65f6;&#x8bb0;&#x5f55;&#x5f53;&#x524d;&#x51fa;&#x73b0;&#x6b21;&#x6570;&#x6700;&#x591a;&#x7684;&#x90a3;&#x4e2a;IP&#x5730;&#x5740;;&#xa;&#xa;&#x53ef;&#x4ee5;&#x5f97;&#x5230;1024&#x4e2a;&#x5c0f;&#x6587;&#x4ef6;&#x4e2d;&#x7684;&#x51fa;&#x73b0;&#x6b21;&#x6570;&#x6700;&#x591a;&#x7684;IP&#xff0c;&#x518d;&#x4f9d;&#x636e;&#x5e38;&#x89c4;&#x7684;&#x6392;&#x5e8f;&#x7b97;&#x6cd5;&#x5f97;&#x5230;&#x603b;&#x4f53;&#x4e0a;&#x51fa;&#x73b0;&#x6b21;&#x6570;&#x6700;&#x591a;&#x7684;IP;"/>
</node>
</node>
<node CREATED="1514531189130" FOLDED="true" ID="ID_82853275" MODIFIED="1514532462963" POSITION="right" TEXT="2:&#x641c;&#x7d22;&#x5f15;&#x64ce;&#x4f1a;&#x901a;&#x8fc7;&#x65e5;&#x5fd7;&#x6587;&#x4ef6;&#x628a;&#x7528;&#x6237;&#x6bcf;&#x6b21;&#x68c0;&#x7d22;&#x4f7f;&#x7528;&#x7684;&#x6240;&#x6709;&#x68c0;&#x7d22;&#x4e32;&#x90fd;&#x8bb0;&#x5f55;&#x4e0b;&#x6765;&#xff0c;&#xa;&#x6bcf;&#x4e2a;&#x67e5;&#x8be2;&#x4e32;&#x7684;&#x957f;&#x5ea6;&#x4e3a;1-255&#x5b57;&#x8282;&#x3002;">
<node CREATED="1514532305654" FOLDED="true" ID="ID_1782676046" MODIFIED="1514532455755" TEXT="">
<icon BUILTIN="help"/>
<node CREATED="1514531222005" ID="ID_1487259301" MODIFIED="1514531229003" TEXT="&#x5047;&#x8bbe;&#x76ee;&#x524d;&#x6709;&#x4e00;&#x5343;&#x4e07;&#x4e2a;&#x8bb0;&#x5f55;(&#x8fd9;&#x4e9b;&#x67e5;&#x8be2;&#x4e32;&#x7684;&#x91cd;&#x590d;&#x5ea6;&#x6bd4;&#x8f83;&#x9ad8;&#xff0c;&#x867d;&#x7136;&#x603b;&#x6570;&#x662f;1&#x5343;&#x4e07;&#xff0c;&#x4f46;&#x5982;&#x679c;&#x9664;&#x53bb;&#x91cd;&#x590d;&#x540e;&#xff0c;&#x4e0d;&#x8d85;&#x8fc7;3&#x767e;&#x4e07;&#x4e2a;&#x3002;&#x4e00;&#x4e2a;&#x67e5;&#x8be2;&#x4e32;&#x7684;&#x91cd;&#x590d;&#x5ea6;&#x8d8a;&#x9ad8;&#xff0c;&#x8bf4;&#x660e;&#x67e5;&#x8be2;&#x5b83;&#x7684;&#x7528;&#x6237;&#x8d8a;&#x591a;&#xff0c;&#x4e5f;&#x5c31;&#x662f;&#x8d8a;&#x70ed;&#x95e8;&#x3002;)&#xff0c;&#x8bf7;&#x4f60;&#x7edf;&#x8ba1;&#x6700;&#x70ed;&#x95e8;&#x7684;10&#x4e2a;&#x67e5;&#x8be2;&#x4e32;&#xff0c;&#x8981;&#x6c42;&#x4f7f;&#x7528;&#x7684;&#x5185;&#x5b58;&#x4e0d;&#x80fd;&#x8d85;&#x8fc7;1G&#x3002;"/>
</node>
<node CREATED="1514532312084" FOLDED="true" ID="ID_1397766465" MODIFIED="1514532461559" TEXT="&#x95ee;&#x9898;&#x5206;&#x6790;">
<node CREATED="1514532320422" ID="ID_1909831139" MODIFIED="1514532332746" TEXT="&#x5178;&#x578b;&#x7684;Top K&#x7b97;&#x6cd5;"/>
<node CREATED="1514532333510" ID="ID_1594078057" MODIFIED="1514532391322" TEXT="&#x5148;&#x5bf9;&#x8fd9;&#x6279;&#x6d77;&#x91cf;&#x6570;&#x636e;&#x9884;&#x5904;&#x7406;&#xff0c;&#x5728;O(N)&#x7684;&#x65f6;&#x95f4;&#x5185;&#x7528;Hash&#x8868;&#x5b8c;&#x6210;&#x7edf;&#x8ba1;;">
<icon BUILTIN="full-1"/>
</node>
<node CREATED="1514532344277" ID="ID_101984534" MODIFIED="1514532393322" TEXT="&#x501f;&#x52a9;&#x5806;&#x8fd9;&#x4e2a;&#x6570;&#x636e;&#x7ed3;&#x6784;&#xff0c;&#x627e;&#x51fa;Top K&#xff0c;&#x65f6;&#x95f4;&#x590d;&#x6742;&#x5ea6;&#x4e3a;N&#x2018;logK&#x3002;">
<icon BUILTIN="full-2"/>
<node CREATED="1514532360598" ID="ID_843308416" MODIFIED="1514532362124" TEXT="&#x501f;&#x52a9;&#x5806;&#x7ed3;&#x6784;&#xff0c;&#x6211;&#x4eec;&#x53ef;&#x4ee5;&#x5728;log&#x91cf;&#x7ea7;&#x7684;&#x65f6;&#x95f4;&#x5185;&#x67e5;&#x627e;&#x548c;&#x8c03;&#x6574;/&#x79fb;&#x52a8;&#x3002;&#x56e0;&#x6b64;&#xff0c;&#x7ef4;&#x62a4;&#x4e00;&#x4e2a;K(&#x8be5;&#x9898;&#x76ee;&#x4e2d;&#x662f;10)&#x5927;&#x5c0f;&#x7684;&#x5c0f;&#x6839;&#x5806;&#xff0c;&#x7136;&#x540e;&#x904d;&#x5386;300&#x4e07;&#x7684;Query&#xff0c;&#x5206;&#x522b; &#x548c;&#x6839;&#x5143;&#x7d20;&#x8fdb;&#x884c;&#x5bf9;&#x6bd4;&#x6240;&#x4ee5;&#xff0c;&#x6211;&#x4eec;&#x6700;&#x7ec8;&#x7684;&#x65f6;&#x95f4;&#x590d;&#x6742;&#x5ea6;&#x662f;&#xff1a;O(N)+ N&#x2019;*O(logK)&#xff0c;(N&#x4e3a;1000&#x4e07;&#xff0c;N&#x2019;&#x4e3a;300&#x4e07;)"/>
</node>
<node CREATED="1514532363565" ID="ID_1883094320" MODIFIED="1514532395538" TEXT="&#x6216;&#x8005;&#x91c7;&#x7528;trie&#x6811;&#xff0c;&#x5173;&#x952e;&#x5b57;&#x57df;&#x5b58;&#x8be5;&#x67e5;&#x8be2;&#x4e32;&#x51fa;&#x73b0;&#x7684;&#x6b21;&#x6570;&#xff0c;&#x6ca1;&#x6709;&#x51fa;&#x73b0;&#x4e3a;0&#x3002;&#x6700;&#x540e;&#x7528;10&#x4e2a;&#x5143;&#x7d20;&#x7684;&#x6700;&#x5c0f;&#x63a8;&#x6765;&#x5bf9;&#x51fa;&#x73b0;&#x9891;&#x7387;&#x8fdb;&#x884c;&#x6392;&#x5e8f;&#x3002;">
<icon BUILTIN="full-2"/>
</node>
</node>
</node>
<node CREATED="1514531261056" FOLDED="true" ID="ID_931889771" MODIFIED="1514532457394" POSITION="right" TEXT="3:&#x6709;&#x4e00;&#x4e2a;1G&#x5927;&#x5c0f;&#x7684;&#x4e00;&#x4e2a;&#x6587;&#x4ef6;&#xff0c;&#x91cc;&#x9762;&#x6bcf;&#x4e00;&#x884c;&#x662f;&#x4e00;&#x4e2a;&#x8bcd;&#xff0c;&#x8bcd;&#x7684;&#x5927;&#x5c0f;&#x4e0d;&#x8d85;&#x8fc7;16&#x5b57;&#x8282;&#xff0c;&#xa;&#x5185;&#x5b58;&#x9650;&#x5236;&#x5927;&#x5c0f;&#x662f;1M&#x3002;&#x8fd4;&#x56de;&#x9891;&#x6570;&#x6700;&#x9ad8;&#x7684;100&#x4e2a;&#x8bcd;&#x3002;">
<node CREATED="1514532411814" ID="ID_438427272" MODIFIED="1514532416108" TEXT="&#x987a;&#x5e8f;&#x8bfb;&#x6587;&#x4ef6;&#x4e2d;&#xff0c;&#x5bf9;&#x4e8e;&#x6bcf;&#x4e2a;&#x8bcd;x&#xff0c;&#x53d6;hash(x)%5000&#xff0c;&#x7136;&#x540e;&#x6309;&#x7167;&#x8be5;&#x503c;&#x5b58;&#x5230;5000&#x4e2a;&#x5c0f;&#x6587;&#x4ef6;(&#x8bb0;&#x4e3a;x0,x1,&#x2026;x4999)&#x4e2d;&#x3002;&#x8fd9;&#x6837;&#x6bcf;&#x4e2a;&#x6587;&#x4ef6;&#x5927;&#x6982;&#x662f;200k&#x5de6;&#x53f3;&#x3002;">
<icon BUILTIN="full-1"/>
</node>
<node CREATED="1514532417674" ID="ID_1535013178" MODIFIED="1514532438034" TEXT="&#x5982;&#x679c;&#x5176;&#x4e2d;&#x7684;&#x6709;&#x7684;&#x6587;&#x4ef6;&#x8d85;&#x8fc7;&#x4e86;1M&#x5927;&#x5c0f;&#xff0c;&#x8fd8;&#x53ef;&#x4ee5;&#x6309;&#x7167;&#x7c7b;&#x4f3c;&#x7684;&#x65b9;&#x6cd5;&#x7ee7;&#x7eed;&#x5f80;&#x4e0b;&#x5206;&#xff0c;&#x76f4;&#x5230;&#x5206;&#x89e3;&#x5f97;&#x5230;&#x7684;&#x5c0f;&#x6587;&#x4ef6;&#x7684;&#x5927;&#x5c0f;&#x90fd;&#x4e0d;&#x8d85;&#x8fc7;1M&#x3002;">
<icon BUILTIN="full-2"/>
</node>
<node CREATED="1514532427669" ID="ID_829608882" MODIFIED="1514532439826" TEXT="&#x5bf9;&#x6bcf;&#x4e2a;&#x5c0f;&#x6587;&#x4ef6;&#xff0c;&#x7edf;&#x8ba1;&#x6bcf;&#x4e2a;&#x6587;&#x4ef6;&#x4e2d;&#x51fa;&#x73b0;&#x7684;&#x8bcd;&#x4ee5;&#x53ca;&#x76f8;&#x5e94;&#x7684;&#x9891;&#x7387;(&#x53ef;&#x4ee5;&#x91c7;&#x7528;trie&#x6811;/hash_map&#x7b49;)&#xff0c;&#x5e76;&#x53d6;&#x51fa;&#x51fa;&#x73b0;&#x9891;&#x7387;&#x6700;&#x5927;&#x7684;100&#x4e2a;&#x8bcd;(&#x53ef;&#x4ee5;&#x7528;&#x542b;100&#x4e2a;&#x7ed3; &#x70b9;&#x7684;&#x6700;&#x5c0f;&#x5806;)&#xff0c;&#x5e76;&#x628a;100&#x4e2a;&#x8bcd;&#x53ca;&#x76f8;&#x5e94;&#x7684;&#x9891;&#x7387;&#x5b58;&#x5165;&#x6587;&#x4ef6;&#xff0c;&#x8fd9;&#x6837;&#x53c8;&#x5f97;&#x5230;&#x4e86;5000&#x4e2a;&#x6587;&#x4ef6;&#x3002;&#x4e0b;&#x4e00;&#x6b65;&#x5c31;&#x662f;&#x628a;&#x8fd9;5000&#x4e2a;&#x6587;&#x4ef6;&#x8fdb;&#x884c;&#x5f52;&#x5e76;(&#x7c7b;&#x4f3c;&#x4e0e;&#x5f52;&#x5e76;&#x6392;&#x5e8f;)&#x7684;&#x8fc7;&#x7a0b;&#x4e86;&#x3002;">
<icon BUILTIN="full-3"/>
</node>
</node>
<node CREATED="1514531279837" ID="ID_1337142019" MODIFIED="1514532842357" POSITION="right" TEXT="4:)&#x6709;10&#x4e2a;&#x6587;&#x4ef6;&#xff0c;&#x6bcf;&#x4e2a;&#x6587;&#x4ef6;1G&#xff0c;&#x6bcf;&#x4e2a;&#x6587;&#x4ef6;&#x7684;&#x6bcf;&#x4e00;&#x884c;&#x5b58;&#x653e;&#x7684;&#x90fd;&#x662f;&#x7528;&#x6237;&#x7684;query&#xff0c;&#x6bcf;&#x4e2a;&#x6587;&#x4ef6;&#x7684;query&#x90fd;&#x53ef;&#x80fd;&#x91cd;&#x590d;&#x3002;&#x8981;&#x6c42;&#x4f60;&#x6309;&#x7167;query&#x7684;&#x9891;&#x5ea6;&#x6392;&#x5e8f;&#x3002;">
<node CREATED="1514532320422" ID="ID_1128300465" MODIFIED="1514532332746" TEXT="&#x5178;&#x578b;&#x7684;Top K&#x7b97;&#x6cd5;"/>
<node CREATED="1514532468348" FOLDED="true" ID="ID_829650823" MODIFIED="1514532547778" TEXT="&#x65b9;&#x6848;1&#xff1a;">
<node CREATED="1514532522339" ID="ID_567128374" MODIFIED="1514532540258" TEXT="&#x987a;&#x5e8f;&#x8bfb;&#x53d6;10&#x4e2a;&#x6587;&#x4ef6;&#xff0c;&#x6309;&#x7167;hash(query)%10&#x7684;&#x7ed3;&#x679c;&#x5c06;query&#x5199;&#x5165;&#x5230;&#x53e6;&#x5916;10&#x4e2a;&#x6587;&#x4ef6;(&#x8bb0;&#x4e3a;)&#x4e2d;&#x3002;&#x8fd9;&#x6837;&#x65b0;&#x751f;&#x6210;&#x7684;&#x6587;&#x4ef6;&#x6bcf;&#x4e2a;&#x7684;&#x5927;&#x5c0f;&#x5927;&#x7ea6;&#x4e5f;1G(&#x5047;&#x8bbe;hash&#x51fd;&#x6570;&#x662f;&#x968f;&#x673a;&#x7684;)&#x3002;">
<icon BUILTIN="full-1"/>
</node>
<node CREATED="1514532529636" ID="ID_226413467" MODIFIED="1514532542425" TEXT="&#x627e;&#x4e00;&#x53f0;&#x5185;&#x5b58;&#x5728;2G&#x5de6;&#x53f3;&#x7684;&#x673a;&#x5668;&#xff0c;&#x4f9d;&#x6b21;&#x5bf9;&#x7528;hash_map(query, query_count)&#x6765;&#x7edf;&#x8ba1;&#x6bcf;&#x4e2a;query&#x51fa;&#x73b0;&#x7684;&#x6b21;&#x6570;&#x3002;&#x5229;&#x7528;&#x5feb;&#x901f;/&#x5806;/&#x5f52;&#x5e76;&#x6392;&#x5e8f;&#x6309;&#x7167;&#x51fa;&#x73b0;&#x6b21;&#x6570;&#x8fdb;&#x884c;&#x6392;&#x5e8f;&#x3002;&#x5c06;&#x6392;&#x5e8f;&#x597d;&#x7684;query&#x548c;&#x5bf9;&#x5e94;&#x7684;query_cout&#x8f93;&#x51fa;&#x5230;&#x6587;&#x4ef6;&#x4e2d;&#x3002;&#x8fd9;&#x6837;&#x5f97;&#x5230;&#x4e86;10&#x4e2a;&#x6392;&#x597d;&#x5e8f;&#x7684;&#x6587;&#x4ef6;(&#x8bb0;&#x4e3a;)&#x3002;">
<icon BUILTIN="full-2"/>
</node>
<node CREATED="1514532532244" ID="ID_137499399" MODIFIED="1514532544665" TEXT="&#x5bf9;&#x8fd9;10&#x4e2a;&#x6587;&#x4ef6;&#x8fdb;&#x884c;&#x5f52;&#x5e76;&#x6392;&#x5e8f;(&#x5185;&#x6392;&#x5e8f;&#x4e0e;&#x5916;&#x6392;&#x5e8f;&#x76f8;&#x7ed3;&#x5408;)&#x3002;">
<icon BUILTIN="full-3"/>
</node>
</node>
<node CREATED="1514532476176" FOLDED="true" ID="ID_1733147689" MODIFIED="1514532549848" TEXT="&#x65b9;&#x6848;2&#xff1a;">
<node CREATED="1514532506802" ID="ID_551381383" MODIFIED="1514532508314" TEXT="&#x4e00;&#x822c;query&#x7684;&#x603b;&#x91cf;&#x662f;&#x6709;&#x9650;&#x7684;&#xff0c;&#x53ea;&#x662f;&#x91cd;&#x590d;&#x7684;&#x6b21;&#x6570;&#x6bd4;&#x8f83;&#x591a;&#x800c;&#x5df2;&#xff0c;&#x53ef;&#x80fd;&#x5bf9;&#x4e8e;&#x6240;&#x6709;&#x7684;query&#xff0c;&#x4e00;&#x6b21;&#x6027;&#x5c31;&#x53ef;&#x4ee5;&#x52a0;&#x5165;&#x5230;&#x5185;&#x5b58;&#x4e86;&#x3002;&#x8fd9;&#x6837;&#xff0c;&#x6211;&#x4eec;&#x5c31;&#x53ef;&#x4ee5;&#x91c7;&#x7528;trie&#x6811;/hash_map&#x7b49;&#x76f4;&#x63a5;&#x6765;&#x7edf;&#x8ba1;&#x6bcf;&#x4e2a;query&#x51fa;&#x73b0;&#x7684;&#x6b21;&#x6570;&#xff0c;&#x7136;&#x540e;&#x6309;&#x51fa;&#x73b0;&#x6b21;&#x6570;&#x505a;&#x5feb;&#x901f;/&#x5806;/&#x5f52;&#x5e76;&#x6392;&#x5e8f;&#x5c31;&#x53ef;&#x4ee5;&#x4e86;&#x3002;"/>
</node>
<node CREATED="1514532490075" FOLDED="true" ID="ID_501565229" MODIFIED="1514532501186" TEXT="&#x65b9;&#x6848;3&#xff1a;">
<node CREATED="1514532498157" ID="ID_358969661" MODIFIED="1514532499906" TEXT="&#x4e0e;&#x65b9;&#x6848;1&#x7c7b;&#x4f3c;&#xff0c;&#x4f46;&#x5728;&#x505a;&#x5b8c;hash&#xff0c;&#x5206;&#x6210;&#x591a;&#x4e2a;&#x6587;&#x4ef6;&#x540e;&#xff0c;&#x53ef;&#x4ee5;&#x4ea4;&#x7ed9;&#x591a;&#x4e2a;&#x6587;&#x4ef6;&#x6765;&#x5904;&#x7406;&#xff0c;&#x91c7;&#x7528;&#x5206;&#x5e03;&#x5f0f;&#x7684;&#x67b6;&#x6784;&#x6765;&#x5904;&#x7406;(&#x6bd4;&#x5982;MapReduce)&#xff0c;&#x6700;&#x540e;&#x518d;&#x8fdb;&#x884c;&#x5408;&#x5e76;&#x3002;"/>
</node>
</node>
<node CREATED="1514531297774" FOLDED="true" ID="ID_1324585774" MODIFIED="1514532631874" POSITION="right" TEXT="5:&#x7ed9;&#x5b9a;a&#x3001;b&#x4e24;&#x4e2a;&#x6587;&#x4ef6;&#xff0c;&#x5404;&#x5b58;&#x653e;50&#x4ebf;&#x4e2a;url&#xff0c;&#x6bcf;&#x4e2a;url&#x5404;&#x5360;64&#x5b57;&#x8282;&#xff0c;&#x5185;&#x5b58;&#x9650;&#x5236;&#x662f;4G&#xff0c;&#x8ba9;&#x4f60;&#x627e;&#x51fa;a&#x3001;b&#x6587;&#x4ef6;&#x5171;&#x540c;&#x7684;url?">
<node CREATED="1514532562797" FOLDED="true" ID="ID_1144106553" MODIFIED="1514532609956" TEXT="&#x65b9;&#x6848;1">
<node CREATED="1514532575724" ID="ID_103508629" MODIFIED="1514532603504" TEXT="&#x53ef;&#x4ee5;&#x4f30;&#x8ba1;&#x6bcf;&#x4e2a;&#x6587;&#x4ef6;&#x5b89;&#x7684;&#x5927;&#x5c0f;&#x4e3a;5G&#xd7;64=320G&#xff0c;&#x8fdc;&#x8fdc;&#x5927;&#x4e8e;&#x5185;&#x5b58;&#x9650;&#x5236;&#x7684;4G&#x3002;&#x6240;&#x4ee5;&#x4e0d;&#x53ef;&#x80fd;&#x5c06;&#x5176;&#x5b8c;&#x5168;&#x52a0;&#x8f7d;&#x5230;&#x5185;&#x5b58;&#x4e2d;&#x5904;&#x7406;&#x3002;&#x8003;&#x8651;&#x91c7;&#x53d6;&#x5206;&#x800c;&#x6cbb;&#x4e4b;&#x7684;&#x65b9;&#x6cd5;&#x3002;">
<icon BUILTIN="full-1"/>
</node>
<node CREATED="1514532576916" ID="ID_1921587757" MODIFIED="1514532604888" TEXT="&#x904d;&#x5386;&#x6587;&#x4ef6;a&#xff0c;&#x5bf9;&#x6bcf;&#x4e2a;url&#x6c42;&#x53d6;hash(url)%1000&#xff0c;&#x7136;&#x540e;&#x6839;&#x636e;&#x6240;&#x53d6;&#x5f97;&#x7684;&#x503c;&#x5c06;url&#x5206;&#x522b;&#x5b58;&#x50a8;&#x5230;1000&#x4e2a;&#x5c0f;&#x6587;&#x4ef6;(&#x8bb0;&#x4e3a;a0,a1,&#x2026;,a999)&#x4e2d;&#x3002;&#x8fd9;&#x6837;&#x6bcf;&#x4e2a;&#x5c0f;&#x6587;&#x4ef6;&#x7684;&#x5927;&#x7ea6;&#x4e3a;300M">
<icon BUILTIN="full-2"/>
</node>
<node CREATED="1514532585716" ID="ID_217179652" MODIFIED="1514532606707" TEXT="&#x904d;&#x5386;&#x6587;&#x4ef6;b&#xff0c;&#x91c7;&#x53d6;&#x548c;a&#x76f8;&#x540c;&#x7684;&#x65b9;&#x5f0f;&#x5c06;url&#x5206;&#x522b;&#x5b58;&#x50a8;&#x5230;1000&#x5c0f;&#x6587;&#x4ef6;(&#x8bb0;&#x4e3a;b0,b1,&#x2026;,b999)&#x3002;&#x8fd9;&#x6837;&#x5904;&#x7406;&#x540e;&#xff0c;&#x6240;&#x6709;&#x53ef;&#x80fd;&#x76f8;&#x540c;&#x7684;url&#x90fd;&#x5728;&#x5bf9;&#x5e94;&#x7684;&#x5c0f; &#x6587;&#x4ef6;(a0vsb0,a1vsb1,&#x2026;,a999vsb999)&#x4e2d;&#xff0c;&#x4e0d;&#x5bf9;&#x5e94;&#x7684;&#x5c0f;&#x6587;&#x4ef6;&#x4e0d;&#x53ef;&#x80fd;&#x6709;&#x76f8;&#x540c;&#x7684;url&#x3002;&#x7136;&#x540e;&#x6211;&#x4eec;&#x53ea;&#x8981;&#x6c42;&#x51fa;1000&#x5bf9;&#x5c0f;&#x6587;&#x4ef6;&#x4e2d;&#x76f8;&#x540c;&#x7684;url&#x5373;&#x53ef;&#x3002;">
<icon BUILTIN="full-3"/>
</node>
<node CREATED="1514532592844" ID="ID_1013035226" MODIFIED="1514532608674" TEXT="&#x6c42;&#x6bcf;&#x5bf9;&#x5c0f;&#x6587;&#x4ef6;&#x4e2d;&#x76f8;&#x540c;&#x7684;url&#x65f6;&#xff0c;&#x53ef;&#x4ee5;&#x628a;&#x5176;&#x4e2d;&#x4e00;&#x4e2a;&#x5c0f;&#x6587;&#x4ef6;&#x7684;url&#x5b58;&#x50a8;&#x5230;hash_set&#x4e2d;&#x3002;&#x7136;&#x540e;&#x904d;&#x5386;&#x53e6;&#x4e00;&#x4e2a;&#x5c0f;&#x6587;&#x4ef6;&#x7684;&#x6bcf;&#x4e2a;url&#xff0c;&#x770b;&#x5176;&#x662f;&#x5426;&#x5728;&#x521a;&#x624d;&#x6784;&#x5efa;&#x7684;hash_set&#x4e2d;&#xff0c;&#x5982;&#x679c;&#x662f;&#xff0c;&#x90a3;&#x4e48;&#x5c31;&#x662f;&#x5171;&#x540c;&#x7684;url&#xff0c;&#x5b58;&#x5230;&#x6587;&#x4ef6;&#x91cc;&#x9762;&#x5c31;&#x53ef;&#x4ee5;&#x4e86;&#x3002;">
<icon BUILTIN="full-4"/>
</node>
</node>
<node CREATED="1514532563973" FOLDED="true" ID="ID_1387200847" MODIFIED="1514532626155" TEXT="&#x65b9;&#x6848;2">
<node CREATED="1514532617532" ID="ID_1399074158" MODIFIED="1514532618305" TEXT="&#x5982;&#x679c;&#x5141;&#x8bb8;&#x6709;&#x4e00;&#x5b9a;&#x7684;&#x9519;&#x8bef;&#x7387;&#xff0c;&#x53ef;&#x4ee5;&#x4f7f;&#x7528;Bloom filter&#xff0c;4G&#x5185;&#x5b58;&#x5927;&#x6982;&#x53ef;&#x4ee5;&#x8868;&#x793a;340&#x4ebf;bit&#x3002;&#x5c06;&#x5176;&#x4e2d;&#x4e00;&#x4e2a;&#x6587;&#x4ef6;&#x4e2d;&#x7684;url&#x4f7f;&#x7528;Bloom filter&#x6620;&#x5c04;&#x4e3a;&#x8fd9;340&#x4ebf;bit&#xff0c;&#x7136;&#x540e;&#x6328;&#x4e2a;&#x8bfb;&#x53d6;&#x53e6;&#x5916;&#x4e00;&#x4e2a;&#x6587;&#x4ef6;&#x7684;url&#xff0c;&#x68c0;&#x67e5;&#x662f;&#x5426;&#x4e0e;Bloom filter&#xff0c;&#x5982;&#x679c;&#x662f;&#xff0c;&#x90a3;&#x4e48;&#x8be5;url&#x5e94;&#x8be5;&#x662f;&#x5171;&#x540c;&#x7684;url(&#x6ce8;&#x610f;&#x4f1a;&#x6709;&#x4e00;&#x5b9a;&#x7684;&#x9519;&#x8bef;&#x7387;)&#x3002;"/>
</node>
</node>
<node CREATED="1514531311741" FOLDED="true" ID="ID_1408632317" MODIFIED="1514532755072" POSITION="left" TEXT="6:&#x5728;2.5&#x4ebf;&#x4e2a;&#x6574;&#x6570;&#x4e2d;&#x627e;&#x51fa;&#x4e0d;&#x91cd;&#x590d;&#x7684;&#x6574;&#x6570;&#xff0c;&#x6ce8;&#xff0c;&#x5185;&#x5b58;&#x4e0d;&#x8db3;&#x4ee5;&#x5bb9;&#x7eb3;&#x8fd9;2.5&#x4ebf;&#x4e2a;&#x6574;&#x6570;&#x3002;">
<node CREATED="1514532646331" ID="ID_967591994" MODIFIED="1514532647009" TEXT="&#x65b9;&#x6848;1">
<node CREATED="1514532664003" ID="ID_934464162" MODIFIED="1514532664785" TEXT="&#x91c7;&#x7528;2-Bitmap(&#x6bcf;&#x4e2a;&#x6570;&#x5206;&#x914d;2bit&#xff0c;00&#x8868;&#x793a;&#x4e0d;&#x5b58;&#x5728;&#xff0c;01&#x8868;&#x793a;&#x51fa;&#x73b0;&#x4e00;&#x6b21;&#xff0c;10&#x8868;&#x793a;&#x591a;&#x6b21;&#xff0c;11&#x65e0;&#x610f;&#x4e49;)&#x8fdb;&#x884c;&#xff0c;&#x5171;&#x9700;&#x5185;&#x5b58;2^32 * 2 bit=1 GB&#x5185;&#x5b58;&#xff0c;&#x8fd8;&#x53ef;&#x4ee5;&#x63a5;&#x53d7;&#x3002;&#x7136;&#x540e;&#x626b;&#x63cf;&#x8fd9;2.5&#x4ebf;&#x4e2a;&#x6574;&#x6570;&#xff0c;&#x67e5;&#x770b;Bitmap&#x4e2d;&#x76f8;&#x5bf9;&#x5e94;&#x4f4d;&#xff0c;&#x5982;&#x679c;&#x662f;00&#x53d8;01&#xff0c;01&#x53d8;10&#xff0c;10&#x4fdd;&#x6301;&#x4e0d;&#x53d8;&#x3002;&#x6240;&#x63cf;&#x5b8c;&#x4e8b;&#x540e;&#xff0c;&#x67e5;&#x770b;bitmap&#xff0c;&#x628a;&#x5bf9;&#x5e94;&#x4f4d;&#x662f;01&#x7684;&#x6574;&#x6570;&#x8f93;&#x51fa;&#x5373;&#x53ef;&#x3002;"/>
</node>
<node CREATED="1514532647435" ID="ID_622022773" MODIFIED="1514532649129" TEXT="&#x65b9;&#x6848;2">
<node CREATED="1514532657014" ID="ID_73234443" MODIFIED="1514532657747" TEXT="&#x4e5f;&#x53ef;&#x91c7;&#x7528;&#x4e0e;&#x7b2c;1&#x9898;&#x7c7b;&#x4f3c;&#x7684;&#x65b9;&#x6cd5;&#xff0c;&#x8fdb;&#x884c;&#x5212;&#x5206;&#x5c0f;&#x6587;&#x4ef6;&#x7684;&#x65b9;&#x6cd5;&#x3002;&#x7136;&#x540e;&#x5728;&#x5c0f;&#x6587;&#x4ef6;&#x4e2d;&#x627e;&#x51fa;&#x4e0d;&#x91cd;&#x590d;&#x7684;&#x6574;&#x6570;&#xff0c;&#x5e76;&#x6392;&#x5e8f;&#x3002;&#x7136;&#x540e;&#x518d;&#x8fdb;&#x884c;&#x5f52;&#x5e76;&#xff0c;&#x6ce8;&#x610f;&#x53bb;&#x9664;&#x91cd;&#x590d;&#x7684;&#x5143;&#x7d20;&#x3002;"/>
</node>
</node>
<node CREATED="1514532765522" FOLDED="true" ID="ID_1439953278" MODIFIED="1514532791024" POSITION="left" TEXT="7:&#x7ed9;40&#x4ebf;&#x4e2a;&#x4e0d;&#x91cd;&#x590d;&#x7684;unsigned int&#x7684;&#x6574;&#x6570;&#xff0c;&#x6ca1;&#x6392;&#x8fc7;&#x5e8f;&#x7684;&#xff0c;&#x7136;&#x540e;&#x518d;&#x7ed9;&#x4e00;&#x4e2a;&#x6570;&#xff0c;&#x5982;&#x4f55;&#x5feb;&#x901f;&#x5224;&#x65ad;&#x8fd9;&#x4e2a;&#x6570;&#x662f;&#x5426;&#x5728;&#x90a3;40&#x4ebf;&#x4e2a;&#x6570;&#x5f53;&#x4e2d;?">
<node CREATED="1514532687971" FOLDED="true" ID="ID_1195521036" MODIFIED="1514532703905" TEXT="&#x89e3;&#x9898;&#x601d;&#x8def;&#xa;">
<node CREATED="1514531327829" ID="ID_284290977" MODIFIED="1514532679736" TEXT="&#x5feb;&#x901f;&#x6392;&#x5e8f;+&#x4e8c;&#x5206;&#x67e5;&#x627e;&#xa;"/>
</node>
<node CREATED="1514532704875" ID="ID_136556340" MODIFIED="1514532711257" TEXT="&#x65b9;&#x6848;1">
<node CREATED="1514532724931" ID="ID_1453362927" MODIFIED="1514532725536" TEXT="&#x7533;&#x8bf7;512M&#x7684;&#x5185;&#x5b58;&#xff0c;&#x4e00;&#x4e2a;bit&#x4f4d;&#x4ee3;&#x8868;&#x4e00;&#x4e2a;unsigned int&#x503c;&#x3002;&#x8bfb;&#x5165;40&#x4ebf;&#x4e2a;&#x6570;&#xff0c;&#x8bbe;&#x7f6e;&#x76f8;&#x5e94;&#x7684;bit&#x4f4d;&#xff0c;&#x8bfb;&#x5165;&#x8981;&#x67e5;&#x8be2;&#x7684;&#x6570;&#xff0c;&#x67e5;&#x770b;&#x76f8;&#x5e94;bit&#x4f4d;&#x662f;&#x5426;&#x4e3a;1&#xff0c;&#x4e3a;1&#x8868;&#x793a;&#x5b58;&#x5728;&#xff0c;&#x4e3a;0&#x8868;&#x793a;&#x4e0d;&#x5b58;&#x5728;&#x3002;"/>
</node>
<node CREATED="1514532711571" ID="ID_1087835143" MODIFIED="1514532744796" TEXT="&#x65b9;&#x6848;2"/>
</node>
<node CREATED="1514531352209" FOLDED="true" ID="ID_1476887609" MODIFIED="1514532808328" POSITION="left" TEXT="8:&#x600e;&#x4e48;&#x5728;&#x6d77;&#x91cf;&#x6570;&#x636e;&#x4e2d;&#x627e;&#x51fa;&#x91cd;&#x590d;&#x6b21;&#x6570;&#x6700;&#x591a;&#x7684;&#x4e00;&#x4e2a;?">
<node CREATED="1514532806273" ID="ID_1240128144" MODIFIED="1514532806727" TEXT="&#x5148;&#x505a;hash&#xff0c;&#x7136;&#x540e;&#x6c42;&#x6a21;&#x6620;&#x5c04;&#x4e3a;&#x5c0f;&#x6587;&#x4ef6;&#xff0c;&#x6c42;&#x51fa;&#x6bcf;&#x4e2a;&#x5c0f;&#x6587;&#x4ef6;&#x4e2d;&#x91cd;&#x590d;&#x6b21;&#x6570;&#x6700;&#x591a;&#x7684;&#x4e00;&#x4e2a;&#xff0c;&#x5e76;&#x8bb0;&#x5f55;&#x91cd;&#x590d;&#x6b21;&#x6570;&#x3002;&#x7136;&#x540e;&#x627e;&#x51fa;&#x4e0a;&#x4e00;&#x6b65;&#x6c42;&#x51fa;&#x7684;&#x6570;&#x636e;&#x4e2d;&#x91cd;&#x590d;&#x6b21;&#x6570;&#x6700;&#x591a;&#x7684;&#x4e00;&#x4e2a;&#x5c31;&#x662f;&#x6240;&#x6c42;(&#x5177;&#x4f53;&#x53c2;&#x8003;&#x524d;&#x9762;&#x7684;&#x9898;)&#x3002;"/>
</node>
<node CREATED="1514531357813" FOLDED="true" ID="ID_1024783247" MODIFIED="1514532818288" POSITION="left" TEXT="9:&#x4e0a;&#x5343;&#x4e07;&#x6216;&#x4e0a;&#x4ebf;&#x6570;&#x636e;(&#x6709;&#x91cd;&#x590d;)&#xff0c;&#x7edf;&#x8ba1;&#x5176;&#x4e2d;&#x51fa;&#x73b0;&#x6b21;&#x6570;&#x6700;&#x591a;&#x7684;&#x524d;N&#x4e2a;&#x6570;&#x636e;&#x3002;">
<node CREATED="1514532816227" ID="ID_166766027" MODIFIED="1514532816736" TEXT="&#x4e0a;&#x5343;&#x4e07;&#x6216;&#x4e0a;&#x4ebf;&#x7684;&#x6570;&#x636e;&#xff0c;&#x73b0;&#x5728;&#x7684;&#x673a;&#x5668;&#x7684;&#x5185;&#x5b58;&#x5e94;&#x8be5;&#x80fd;&#x5b58;&#x4e0b;&#x3002;&#x6240;&#x4ee5;&#x8003;&#x8651;&#x91c7;&#x7528;hash_map/&#x641c;&#x7d22;&#x4e8c;&#x53c9;&#x6811;/&#x7ea2;&#x9ed1;&#x6811;&#x7b49;&#x6765;&#x8fdb;&#x884c;&#x7edf;&#x8ba1;&#x6b21;&#x6570;&#x3002;&#x7136;&#x540e;&#x5c31;&#x662f;&#x53d6;&#x51fa;&#x524d;N&#x4e2a;&#x51fa;&#x73b0;&#x6b21;&#x6570;&#x6700;&#x591a;&#x7684;&#x6570;&#x636e;&#x4e86;&#xff0c;&#x53ef;&#x4ee5;&#x7528;&#x7b2c;2&#x9898;&#x63d0;&#x5230;&#x7684;&#x5806;&#x673a;&#x5236;&#x5b8c;&#x6210;&#x3002;"/>
</node>
<node CREATED="1514531370917" FOLDED="true" ID="ID_1076607667" MODIFIED="1514532827140" POSITION="left" TEXT="10:&#x4e00;&#x4e2a;&#x6587;&#x672c;&#x6587;&#x4ef6;&#xff0c;&#x5927;&#x7ea6;&#x6709;&#x4e00;&#x4e07;&#x884c;&#xff0c;&#x6bcf;&#x884c;&#x4e00;&#x4e2a;&#x8bcd;&#xff0c;&#x8981;&#x6c42;&#x7edf;&#x8ba1;&#x51fa;&#x5176;&#x4e2d;&#x6700;&#x9891;&#x7e41;&#x51fa;&#x73b0;&#x7684;&#x524d;10&#x4e2a;&#x8bcd;&#xff0c;&#x8bf7;&#x7ed9;&#x51fa;&#x601d;&#x60f3;&#xff0c;&#x7ed9;&#x51fa;&#x65f6;&#x95f4;&#x590d;&#x6742;&#x5ea6;&#x5206;&#x6790;&#x3002;">
<node CREATED="1514532825235" ID="ID_1686658205" MODIFIED="1514532825736" TEXT="&#x8fd9;&#x9898;&#x662f;&#x8003;&#x8651;&#x65f6;&#x95f4;&#x6548;&#x7387;&#x3002;&#x7528;trie&#x6811;&#x7edf;&#x8ba1;&#x6bcf;&#x4e2a;&#x8bcd;&#x51fa;&#x73b0;&#x7684;&#x6b21;&#x6570;&#xff0c;&#x65f6;&#x95f4;&#x590d;&#x6742;&#x5ea6;&#x662f;O(n*le)(le&#x8868;&#x793a;&#x5355;&#x8bcd;&#x7684;&#x5e73;&#x51c6;&#x957f;&#x5ea6;)&#x3002;&#x7136;&#x540e;&#x662f;&#x627e;&#x51fa;&#x51fa;&#x73b0;&#x6700;&#x9891;&#x7e41;&#x7684;&#x524d;10&#x4e2a;&#x8bcd;&#xff0c;&#x53ef;&#x4ee5;&#x7528;&#x5806;&#x6765;&#x5b9e;&#x73b0;&#xff0c;&#x524d;&#x9762;&#x7684;&#x9898;&#x4e2d;&#x5df2;&#x7ecf;&#x8bb2;&#x5230;&#x4e86;&#xff0c;&#x65f6;&#x95f4;&#x590d;&#x6742;&#x5ea6;&#x662f;O(n*lg10)&#x3002;&#x6240;&#x4ee5;&#x603b;&#x7684;&#x65f6;&#x95f4;&#x590d;&#x6742;&#x5ea6;&#xff0c;&#x662f;O(n*le)&#x4e0e;O(n*lg10)&#x4e2d;&#x8f83;&#x5927;&#x7684;&#x54ea;&#x4e00;&#x4e2a;&#x3002;"/>
</node>
<node CREATED="1514531474651" ID="ID_1011998249" MODIFIED="1514531478114" POSITION="left" TEXT="11:100w&#x4e2a;&#x6570;&#x4e2d;&#x627e;&#x51fa;&#x6700;&#x5927;&#x7684;100&#x4e2a;&#x6570;&#x3002;"/>
</node>
</map>
