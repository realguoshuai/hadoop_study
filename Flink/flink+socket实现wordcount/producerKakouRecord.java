package com.enjoyor.mtdap3.socketSendMessage;


import com.enjoyor.mtdap3.socketSendMessage.*;
import com.enjoyor.mtdap3.utils.TimeHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description 使用socket模拟kafka发送的实时过车数据
 * Created with guoshuai
 * date 2019/8/2 13:43
 */
public class producerKakouRecord {
    public static String[] WORDS = new String[10];
    public static String s1 = "";
    public static String s2 = "";
    public static String s3 = "";
    public static String s4 = "";
    public static String s5 = "";

    public static String[] BRANDS = {"现代","大众","吉利","奥迪","奇瑞QQ","路虎","特斯拉"};
    // WORDS = new String[]{s1, s2, s3, s4, s5};

    public static void main(String[] args) {
        Integer port = 8001;
        SocketServer server = new SocketServer(port);
        while (true) {
            try {
                String line = null;
                for (int i = 0; i < WORDS.length; i++) {
                    line = WORDS[i];
                    if (WORDS.length == i + 1) {
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
                s1 ="{\"brand\":\""+BRANDS[(int) (Math.random() * 7)]+"\",\"character_pic\":\"\",\"data_source\":\"\",\"direction\":\"01\",\"insert_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+10000)+"\",\"lane_number\":\"2\",\"license_color\":\"PLATECOLOR02\",\"license_number\":\"川R"+(int)((Math.random()*9+1)*10000)+"\",\"license_type\":\"VEHPLATETYPE02\",\"passing_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis())+"\",\"pic_directory\":\"1.pic\",\"point_id\":\"511301010570202\",\"receive_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+9000)+"\",\"record_id\":\""+System.nanoTime()+100+"\",\"record_type\":\"\",\"region_id\":\"\",\"speed\":\""+(int)(Math.random()*9+1)*100+"\",\"speed_limit_max\":0,\"speed_limit_min\":0,\"standby_pic\":\"http://2.jpg\",\"surveil_type\":\"\",\"veh_color\":\"VEHCOLOR05\",\"veh_length\":\"0\",\"veh_pic\":\"1.jpg\",\"veh_type\":\"VEHSUBTYPE164\",\"video_path\":\"\",\"vio_code\":\"0000\"}";
                s2 ="{\"brand\":\""+BRANDS[(int) (Math.random() * 7)]+"\",\"character_pic\":\"\",\"data_source\":\"\",\"direction\":\"01\",\"insert_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+30000)+"\",\"lane_number\":\"2\",\"license_color\":\"PLATECOLOR02\",\"license_number\":\"川R"+(int)((Math.random()*9+1)*10000)+"\",\"license_type\":\"VEHPLATETYPE02\",\"passing_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis())+"\"\",\"pic_directory\":\"2.pic\",\"point_id\":\"511301010570203\",\"receive_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+7000)+"\",\"record_id\":\""+System.nanoTime()+101+"\",\"record_type\":\"\",\"region_id\":\"\",\"speed\""+(Math.random()*9+1)+"\",\"speed_limit_max\":0,\"speed_limit_min\":0,\"standby_pic\":\"http://2.jpg\",\"surveil_type\":\"\",\"veh_color\":\"VEHCOLOR04\",\"veh_length\":\"0\",\"veh_pic\":\"2.jpg\",\"veh_type\":\"VEHSUBTYPE123\",\"video_path\":\"\",\"vio_code\":\"0000\"}";
                s3 ="{\"brand\":\""+BRANDS[(int) (Math.random() * 7)]+"\",\"character_pic\":\"\",\"data_source\":\"\",\"direction\":\"02\",\"insert_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+50000)+"\",\"lane_number\":\"1\",\"license_color\":\"PLATECOLOR01\",\"license_number\":\"川R"+(int)((Math.random()*9+1)*10000)+"\",\"license_type\":\"VEHPLATETYPE99\",\"passing_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis())+"\"\",\"pic_directory\":\"3.pic\",\"point_id\":\"511301010570204\",\"receive_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+5000)+"\",\"record_id\":\""+System.nanoTime()+102+"\",\"record_type\":\"\",\"region_id\":\"\",\"speed\""+(Math.random()*9+1)+"\",\"speed_limit_max\":0,\"speed_limit_min\":0,\"standby_pic\":\"http://2.jpg\",\"surveil_type\":\"\",\"veh_color\":\"VEHCOLOR03\",\"veh_length\":\"0\",\"veh_pic\":\"3.jpg\",\"veh_type\":\"VEHSUBTYPE100\",\"video_path\":\"\",\"vio_code\":\"0000\"}";
                s4 ="{\"brand\":\""+BRANDS[(int) (Math.random() * 7)]+"\",\"character_pic\":\"\",\"data_source\":\"\",\"direction\":\"01\",\"insert_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+10000)+"\",\"lane_number\":\"3\",\"license_color\":\"PLATECOLOR03\",\"license_number\":\"川R"+(int)((Math.random()*9+1)*10000)+"\",\"license_type\":\"VEHPLATETYPE02\",\"passing_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis())+"\",\"pic_directory\":\"4.pic\",\"point_id\":\"511301010570205\",\"receive_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+6000)+"\",\"record_id\":\""+System.nanoTime()+103+"\",\"record_type\":\"\",\"region_id\":\"\",\"speed\":\""+(Math.random()*9+1)+"\",\"speed_limit_max\":0,\"speed_limit_min\":0,\"standby_pic\":\"http://2.jpg\",\"surveil_type\":\"\",\"veh_color\":\"VEHCOLOR02\",\"veh_length\":\"0\",\"veh_pic\":\"4.jpg\",\"veh_type\":\"VEHSUBTYPE164\",\"video_path\":\"\",\"vio_code\":\"0000\"}";
                s5 ="{\"brand\":\""+BRANDS[(int) (Math.random() * 7)]+"\",\"character_pic\":\"\",\"data_source\":\"\",\"direction\":\"02\",\"insert_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+20000)+"\",\"lane_number\":\"1\",\"license_color\":\"PLATECOLOR04\",\"license_number\":\"川R"+(int)((Math.random()*9+1)*10000)+"\",\"license_type\":\"VEHPLATETYPE01\",\"passing_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis())+"\",\"pic_directory\":\"5.pic\",\"point_id\":\"511301010570201\",\"receive_time\":\""+TimeHelper.milliSecondToTimestampString(System.currentTimeMillis()+5000)+"\",\"record_id\":\""+System.nanoTime()+104+"\",\"record_type\":\"\",\"region_id\":\"\",\"speed\":\""+(Math.random()*9+1)+"\",\"speed_limit_max\":0,\"speed_limit_min\":0,\"standby_pic\":\"http://2.jpg\",\"surveil_type\":\"\",\"veh_color\":\"VEHCOLOR01\",\"veh_length\":\"0\",\"veh_pic\":\"5.jpg\",\"veh_type\":\"VEHSUBTYPE164\",\"video_path\":\"\",\"vio_code\":\"0000\"}";

                WORDS = new String[]{s1, s2, s3, s4, s5};
            }
        }, 500);
    }

}