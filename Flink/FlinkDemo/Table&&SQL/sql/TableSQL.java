package com.mtdap3.flink.test;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;
/**
 * Description  基于flink1.7.0实现 flink-sql
 * Created with guoshuai
 * date 2021/4/14 9:23
 */
public class TableSQL {
    public static void main(String[] args) throws Exception{

        //1\. 获取上下文环境 table的环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tableEnv = BatchTableEnvironment.getTableEnvironment(env);

        //2\. 读取score.csv
        DataSet<String> input = env.readTextFile("E:\\development\\test\\mtdap-flink\\src\\com\\enjoyor\\mtdap3\\flink\\test\\score.csv");
        input.print();

        //获取球员信息
        DataSet<PlayerData> topInput = input.map(new MapFunction<String, PlayerData>() {
            @Override
            public PlayerData map(String s) throws Exception {
                String[] split = s.split(",");

                return new PlayerData(String.valueOf(split[0]),
                        String.valueOf(split[1]),
                        String.valueOf(split[2]),
                        Integer.valueOf(split[3]),
                        Double.valueOf(split[4]),
                        Double.valueOf(split[5]),
                        Double.valueOf(split[6]),
                        Double.valueOf(split[7]),
                        Double.valueOf(split[8])
                );
            }
        });

        //3\. 注册成内存表
        Table topScore = tableEnv.fromDataSet(topInput);
        tableEnv.registerTable("score", topScore);

        //4\. 编写sql 然后提交执行
        //select player, count(season) as num from score group by player order by num desc;
        Table queryResult = tableEnv.sqlQuery("select player, count(season) as num from score group by player order by num desc limit 3");

        //5\. 结果进行打印
        DataSet<Result> result = tableEnv.toDataSet(queryResult, Result.class);
        result.print();

    }

    public static class PlayerData {
        /**
         * 赛季，球员，出场，首发，时间，助攻，抢断，盖帽，得分
         */
        public String season;
        public String player;
        public String play_num;
        public Integer first_court;
        public Double time;
        public Double assists;
        public Double steals;
        public Double blocks;
        public Double scores;

        public PlayerData() {
            super();
        }

        public PlayerData(String season,
                          String player,
                          String play_num,
                          Integer first_court,
                          Double time,
                          Double assists,
                          Double steals,
                          Double blocks,
                          Double scores
        ) {
            this.season = season;
            this.player = player;
            this.play_num = play_num;
            this.first_court = first_court;
            this.time = time;
            this.assists = assists;
            this.steals = steals;
            this.blocks = blocks;
            this.scores = scores;
        }
    }

    public static class Result {
        public String player;
        public Long num;

        public Result() {
            super();
        }
        public Result(String player, Long num) {
            this.player = player;
            this.num = num;
        }
        @Override
        public String toString() {
            return player + ":" + num;
        }
    }
}

