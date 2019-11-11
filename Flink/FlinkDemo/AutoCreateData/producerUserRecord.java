package com.guoshuai.mtdap3.socketsource;


import com.guoshuai.mtdap3.utils.TimeHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description 使用socket模拟kafka发送的用户行为数据
 * 提供csv && json 格式数据
 * Created with guoshuai
 * date 2019/10/17 14:21
 */
public class producerUserRecord {
    public static String[] WORDS = new String[5];
    public static String s1 = "";
    public static String s2 = "";
    public static String s3 = "";
    public static String s4 = "";
    public static String s5 = "";
    public static String[] BEHAVIOR = {"pv","pv","pv","pv","pv","pv","buy","cart","fav","fav"}; //用户行为(浏览量,购买,加入购物车,点赞)
    public static String[] ITEMID = {"3760258","3110556","2191348","2123538","1598945","2512167",
                                    "5046581","4719377","3472922","22738","4125511","4000012","25503"
                                    ,"4967749","900305","3259235","59235","764155","12596","66302"};//商品编号
    public  static String[] CATEGORYID={"00","01","02","03","04","05","06","07","08","09"}; //商品类别


    public static void main(String[] args) {
        Integer port = 8001;
        SocketServer server = new SocketServer(port);
        while (true) {
            try {
                String line = null;
                for (int i = 0; i <= WORDS.length; i++) {//4
                    line = WORDS[i];
                    //System.out.println(i);
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
                //文本格式
                s1 =""+(int)((Math.random()*9+1)*10000)+","+ITEMID[(int) (Math.random() * 20)]+","+CATEGORYID[(int) (Math.random() * 10)]+","+BEHAVIOR[(int) (Math.random() * 10)]+","+System.currentTimeMillis()/1000;
                s2 =""+(int)((Math.random()*9+1)*10000)+","+ITEMID[(int) (Math.random() * 20)]+","+CATEGORYID[(int) (Math.random() * 10)]+","+BEHAVIOR[(int) (Math.random() * 10)]+","+System.currentTimeMillis()/1000;
                s3 =""+(int)((Math.random()*9+1)*10000)+","+ITEMID[(int) (Math.random() * 20)]+","+CATEGORYID[(int) (Math.random() * 10)]+","+BEHAVIOR[(int) (Math.random() * 10)]+","+System.currentTimeMillis()/1000;
                s4 =""+(int)((Math.random()*9+1)*10000)+","+ITEMID[(int) (Math.random() * 20)]+","+CATEGORYID[(int) (Math.random() * 10)]+","+BEHAVIOR[(int) (Math.random() * 10)]+","+System.currentTimeMillis()/1000;
                s5 =""+(int)((Math.random()*9+1)*10000)+","+ITEMID[(int) (Math.random() * 20)]+","+CATEGORYID[(int) (Math.random() * 10)]+","+BEHAVIOR[(int) (Math.random() * 10)]+","+System.currentTimeMillis()/1000;

                //json格式
                /*s1 ="{\"behavior\":\""+BEHAVIOR[(int) (Math.random() * 10)]+"\",\"user_id\":\""+(int)((Math.random()*9+1)*10000)+"\",\"item_id\":\""+ITEMID[(int) (Math.random() * 20)]+"\",\"timestamp\":\""+System.currentTimeMillis()+"\",\"category_id\":\""+CATEGORYID[(int) (Math.random() * 10)]+"\"}";
                s2 ="{\"behavior\":\""+BEHAVIOR[(int) (Math.random() * 10)]+"\",\"user_id\":\""+(int)((Math.random()*9+1)*10000)+"\",\"item_id\":\""+ITEMID[(int) (Math.random() * 20)]+"\",\"timestamp\":\""+System.currentTimeMillis()+"\",\"category_id\":\""+CATEGORYID[(int) (Math.random() * 10)]+"\"}";
                s3 ="{\"behavior\":\""+BEHAVIOR[(int) (Math.random() * 10)]+"\",\"user_id\":\""+(int)((Math.random()*9+1)*10000)+"\",\"item_id\":\""+ITEMID[(int) (Math.random() * 20)]+"\",\"timestamp\":\""+System.currentTimeMillis()+"\",\"category_id\":\""+CATEGORYID[(int) (Math.random() * 10)]+"\"}";
                s4 ="{\"behavior\":\""+BEHAVIOR[(int) (Math.random() * 10)]+"\",\"user_id\":\""+(int)((Math.random()*9+1)*10000)+"\",\"item_id\":\""+ITEMID[(int) (Math.random() * 20)]+"\",\"timestamp\":\""+System.currentTimeMillis()+"\",\"category_id\":\""+CATEGORYID[(int) (Math.random() * 10)]+"\"}";
                s5 ="{\"behavior\":\""+BEHAVIOR[(int) (Math.random() * 10)]+"\",\"user_id\":\""+(int)((Math.random()*9+1)*10000)+"\",\"item_id\":\""+ITEMID[(int) (Math.random() * 20)]+"\",\"timestamp\":\""+System.currentTimeMillis()+"\",\"category_id\":\""+CATEGORYID[(int) (Math.random() * 10)]+"\"}";*/

                WORDS = new String[]{s1, s2, s3, s4, s5};
            }
        }, 500);
    }

}