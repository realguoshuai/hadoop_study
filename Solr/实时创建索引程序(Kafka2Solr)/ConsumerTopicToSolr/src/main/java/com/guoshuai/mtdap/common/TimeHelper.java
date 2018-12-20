package com.guoshuai.mtdap.common;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeHelper {

    private static Logger logger = Logger.getLogger(TimeHelper.class.getName());

    //0, 10, 20, 30, 40, 50, 60
    //private static LocalTime time;
    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 返回用秒计数的当前时间
     *
     * @return
     */
    //public static int getDaySeconds() {
        //time = LocalTime.now();
        //return (time.getHour() - 1) * 60 * 60 + (time.getMinute() - 1) * 60 + time.getSecond();

   // }

    public static Date getSQLDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        java.util.Date utilDate = null;

        try {
            utilDate = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new Date(utilDate.getTime());
    }

    public static Timestamp getSQLTimestamp(String tsStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);

        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        java.util.Date utilDate = null;

        try {
            utilDate = sdf.parse(tsStr);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new Timestamp(utilDate.getTime());
    }

    /**
     * 将字符串类型的日期转换为一个timestamp（时间戳记java.sql.Timestamp）
     *
     * @param dateString
     *            需要转换为timestamp的字符串
     * @return
     * @throws ParseException
     */
    public final static Timestamp stringToTimestamp(String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT, Locale.CHINA);// 设定格式
        // dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
        dateFormat.setLenient(false);
        java.util.Date timeDate = dateFormat.parse(dateString);// util类型
        Timestamp dateTime = new Timestamp(timeDate.getTime());// Timestamp类型,timeDate.getTime()返回一个long型
        return dateTime;
    }

    /**
     * 将一个java.sql.Timestamp类型转换为字符串
     * @param timestamp
     * @return
     */
    public static String timestampToString(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);
        String timestampStr = sdf.format(timestamp);
        //这里减掉8小时是为了解决读取数据库之后时间多了8小时的问题
        String minus8Hour = getCalenderDateString(getTimeMillis(timestampStr) - 8 * 60 * 60 * 1000);
        return minus8Hour;
    }

    /**
     * 返回当前日期是星期几
     * @param date
     * @return
     */
    public static String getWeekOfDate(java.util.Date date) {
        String[] weekDays = { "2018-05-20", "2018-05-14", "2018-05-15", "2018-05-16", "2018-05-17", "2018-05-18", "2018-05-19" };
                //{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 将表示当前时间的时间戳对象转换成日期时间字符串
     * @param currentTime
     * @return
     */
    public static String getCalenderDateString(long currentTime) {
        java.util.Date date = new Date(currentTime);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
        String dateStr = dateFormat.format(calendar.getTime());
        return dateStr;
    }

    /**
     * 将表示当前时间的时间戳对象转换成日期时间对象
     * @param currentTime
     * @return
     */
    public static java.util.Date getCalenderDate(long currentTime) {
        java.util.Date date = new Date(currentTime);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.getTime();
    }

    /**
     * 返回日期时间中的时间部分
     * @param date
     * @return
     */
    public static String getTimePart(java.util.Date date) {
        String dateTimeStr = new SimpleDateFormat(TIMESTAMP_FORMAT).format(date);
        return dateTimeStr.substring(11, 19);
    }

    /**
     * 返回日期时间中的日期部分
     * @param date
     * @return
     */
    public static String getDatePart(java.util.Date date) {
        String dateTimeStr = new SimpleDateFormat(TIMESTAMP_FORMAT).format(date);
        return dateTimeStr.substring(0, 10);
    }

    /**
     * 将一个日期时间字符串转换为用毫秒表示的时间戳
     * @param dateStr
     * @return
     */
    public static long getTimeMillis(String dateStr) {
        java.util.Date date = null;
        try {
            date = new SimpleDateFormat(TIMESTAMP_FORMAT).parse(dateStr);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        return date.getTime();
    }

    /**
     * 将一个date对象转换为日期时间字符串
     * @param date
     * @return
     */
    public static String getDateTimeString(java.util.Date date) {
        String dateTimeStr = new SimpleDateFormat(TIMESTAMP_FORMAT).format(date);
        return dateTimeStr;
    }

    /**
     * 将一个日期时间字符串转换为date对象
     * @param dateStr
     * @return
     */
    public static java.util.Date getDateTime(String dateStr) {
        java.util.Date date = null;
        try {
            date = new SimpleDateFormat(TIMESTAMP_FORMAT).parse(dateStr);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将采集到的卡口过车时间转换成solr中的document时间
     * 2018-10-26 08:26:40-->2018-10-22T10:53:49Z
     * @param recordTime
     * @return
     */
    public static String ChangeRecordTime2DocTime(String recordTime) {
            //System.out.println("record: "+recordTime);//2018-07-29 00:08:57
            String sub1 = recordTime.substring(0, 10);//2018-07-29
            String sub2 = recordTime.substring(11);//00:08:57
            StringBuffer sb =new StringBuffer();
            StringBuffer buffer = sb.append(sub1).append("T").append(sub2).append("Z");
            String concat = buffer.toString();
            //System.out.println("chaned: "+concat);
            return concat;
    }
    public static void main(String[] args) throws ParseException {
        // TODO Auto-generated method stub
        //new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                //format(new java.util.Date()).substring(0, 10) + " 23:59:59").getTime;

        //System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        //System.out.println(new java.util.Date().getTime());
        //System.out.println(System.currentTimeMillis());
        //System.out.println(new java.util.Date().getTime() + 10 * 1000);

        //System.out.println(getWeekOfDate(new java.util.Date()));
        //getCurrentTime(new java.util.Date());
        //System.out.println(stringToTimestamp("2018-07-29 00:08:47"));
       //System.out.println(stringToTimestamp("2018-07-29 00:08:57"));

        /*long currentTime = System.currentTimeMillis();
        long currentTimePlusTenSec = currentTime + 10 * 1000;
        System.out.println(currentTime);
        System.out.println(currentTimePlusTenSec);
        System.out.println(getCalenderDateString(currentTime));
        System.out.println(getCalenderDateString(currentTimePlusTenSec));
        System.out.println(getCalenderDate(currentTime));
        System.out.println(getCalenderDate(currentTimePlusTenSec));*/
        System.out.println(ChangeRecordTime2DocTime("2018-07-29 00:08:57"));

    }

}
