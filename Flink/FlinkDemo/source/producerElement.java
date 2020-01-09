package com.gs.train.table.source;


import com.gs.train.table.source.SocketServer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description 使用socket模拟测试数据  文本格式
 * Created with guoshuai
 * date 2019/10/25 13:58
 */
public class producerElement {
    public static String[] WORDS = new String[5];
    public static String s1 = "";
    public static String s2 = "";
    public static String s3 = "";
    public static String s4 = "";
    public static String s5 = "";

    public static String[] BRANDS = {"Flink","Solr","ES","Hadoop", "Spark", "Scala", "SQL"};

    public static void main(String[] args) {
        Integer port = 8001;
        SocketServer server = new SocketServer(port);
        while (true) {
            try {
                String line = null;
                for (int i = 0; i < WORDS.length; i++) {//4
                    line = WORDS[i];
                    if (WORDS.length == i+1) {
                        Thread.sleep(2000);
                        timer();
                        i = 0;
                    }
                    if (line != null && !"".equals(line) && !line.isEmpty()) {
                        server.sendMessage(line);
                        System.out.println(line);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置定时器 更新数组
     */
    public static void timer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                WORDS=null;
                System.out.println("-----定时器开始执行------");

                s1 =BRANDS[(int) (Math.random() * 7)];
                s2 =BRANDS[(int) (Math.random() * 7)];
                s3 =BRANDS[(int) (Math.random() * 7)];
                s4 =BRANDS[(int) (Math.random() * 7)];
                s5 =BRANDS[(int) (Math.random() * 7)];


                WORDS = new String[]{s1, s2, s3, s4, s5};
            }
        }, 500);
    }

}