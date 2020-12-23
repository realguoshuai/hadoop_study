package com.main.scala.train

/**
  * Description  Hive  SQL 50道练习题
  * date 2020/12/23 9:53
  **/
object HiveSQL50 {
    def main(args: Array[String]): Unit = {

     //建表
     s"""
        |create table student(s_id string,s_name string,s_birth string,s_sex string) row format delimited fields terminated by '\t';
        |
        |create table course(c_id string,c_name string,t_id string) row format delimited fields terminated by '\t';
        |
        |create table teacher(t_id string,t_name string) row format delimited fields terminated by '\t';
        |
        |create table score(s_id string,c_id string,s_score int) row format delimited fields terminated by '\t';
      """.stripMargin


        //生成数据

        //vi /export/data/student.csv
        s"""
           |01 赵雷 1990-01-01 男
           |02 钱电 1990-12-21 男
           |03 孙风 1990-05-20 男
           |04 李云 1990-08-06 男
           |05 周梅 1991-12-01 女
           |06 吴兰 1992-03-01 女
           |07 郑竹 1989-07-01 女
           |08 王菊 1990-01-20 女
         """.stripMargin


        //vi /export/data/course.csv

        s"""
           |01  语文  02
           |02  数学  01
           |03  英语  03
         """.stripMargin

        //vi /export/data/teacher.csv
        s"""
           |01  张三
           |02  李四
           |03  王五
         """.stripMargin

        //vi /export/data/score.csv
        s"""
           |01  01  80
           |01  02  90
           |01  03  99
           |02  01  70
           |02  02  60
           |02  03  80
           |03  01  80
           |03  02  80
           |03  03  80
           |04  01  50
           |04  02  30
           |04  03  20
           |05  01  76
           |05  02  87
           |06  01  31
           |06  03  34
           |07  02  89
           |07  03  98
         """.stripMargin

        //导数据到hive
        s"""
           |load data local inpath '/export/data/student.csv' into table student;
           |
           |load data local inpath '/export/data/course.csv' into table course;
           |
           |load data local inpath '/export/data/teacher.csv' into table teacher;
           |
           |load data local inpath '/export/data/score.csv' into table score;
         """.stripMargin

        //hive 查询语法
        s"""
           | SELECT [ALL | DISTINCT] select_expr, select_expr, ...
           | FROM table_reference
           | [WHERE where_condition]
           | [GROUP BY col_list [HAVING condition]]
           | [CLUSTER BY col_list      | [DISTRIBUTE BY col_list]
           | [SORT BY | ORDER BY col_list]
         """.stripMargin

        //1、查询"01"课程比"02"课程成绩高的学生的信息及课程分数:
        s"""
           |select student.*,a.s_score as 01_score,b.s_score as 02_score
           |from student  join score a
           |on student.s_id=a.s_id and a.c_id='01'
           |left join score b
           |on student.s_id=b.s_id and b.c_id='02'
           |where  a.s_score>b.s_score;
         """.stripMargin

        s"""
           |select student.*,a.s_score as 01_score,b.s_score as 02_score
           |from studentjoin score a
           |on  a.c_id='01'join scor
         """.stripMargin

        //2、查询"01"课程比"02"课程成绩低的学生的信息及课程分数:
        s"""
           |select student.*,a.s_score as 01_score,b.s_score as 02_score
           |from studentjoin score a on student.s_id=a.s_id and a.c_id='01'
           |left join score b
           |on student.s_id=b.s_id and b.c_id='02'
           |where a.s_score<b.s_score;
         """.stripMargin

        s"""
           |select student.*,a.s_score as 01_score,b.s_score as 02_score
           |from student
           |join score a on  a.c_id='01'
           |join score b on  b.c_id='02'
           |where  a.s_id=student.s_id and b.s_id=student.s_id and a.s_score<b.s_score;
         """.stripMargin

        //3、查询平均成绩大于等于60分的同学的学生编号和学生姓名和平均成绩:
        s"""
           |select  student.s_id,student.s_name,tmp.平均成绩 from student
           |join ( select score.s_id,round(avg(score.s_score),1)as 平均成绩 from score group by s_id)as tmp
           |on tmp.平均成绩>=60where student.s_id = tmp.s_id
         """.stripMargin

        s"""
           |select  student.s_id,student.s_name,round(avg (score.s_score),1) as 平均成绩
           |from studentjoin score
           |on student.s_id = score.s_id
           |group by student.s_id,student.s_name
           |having avg (score.s_score) >= 60;
         """.stripMargin

        //4、查询平均成绩小于60分的同学的学生编号和学生姓名和平均成绩:
        //  (包括有成绩的和无成绩的)
        s"""
           |select  score.s_id,student.s_name,round(avg (score.s_score),1) as avgScore from student
           |inner join score on student.s_id=score.s_id
           |group by score.s_id,student.s_name
           |having avg (score.s_score) < 60
           |union all
           |select  s2.s_id,s2.s_name,0 as avgScore from student s2
           |where s2.s_id not in  (select distinct sc2.s_id from score sc2);
         """.stripMargin

        //5、查询所有同学的学生编号、学生姓名、选课总数、所有课程的总成绩:
        s"""
           |select student.s_id,student.s_name,(count(score.c_id) )as total_count,sum(score.s_score)as total_score
           |from student
           |left join score on student.s_id=score.s_id
           |group by student.s_id,student.s_name ;
         """.stripMargin

        //6、查询"李"姓老师的数量:
        s"""
           |select t_name,count(1) from teacher  where t_name like '李%' group by t_name;
         """.stripMargin

        //7、查询学过"张三"老师授课的同学的信息:
        s"""
           |select student.* from student
           |join score on student.s_id =score.s_id
           |join  course on course.c_id=score.c_id
           |join  teacher on course.t_id=teacher.t_id and t_name='张三';
         """.stripMargin

        //8、查询没学过"张三"老师授课的同学的信息:
        s"""
           |select student.* from student
           |left join (select s_id from score
           |      join  course on course.c_id=score.c_id
           |      join  teacher on course.t_id=teacher.t_id and t_name='张三')tmp
           |on  student.s_id =tmp.s_id
           |where tmp.s_id is null;
         """.stripMargin

        //9、查询学过编号为"01"并且也学过编号为"02"的课程的同学的信息:
        s"""
           |select * from student
           |join (select s_id from score where c_id =1 )tmp1
           |    on student.s_id=tmp1.s_id
           |join (select s_id from score where c_id =2 )tmp2
           |    on student.s_id=tmp2.s_id;
         """.stripMargin

        //10、查询学过编号为"01"但是没有学过编号为"02"的课程的同学的信息:
        s"""
           |select student.* from student
           |join (select s_id from score where c_id =1 )tmp1
           |    on student.s_id=tmp1.s_id
           |left join (select s_id from score where c_id =2 )tmp2
           |    on student.s_id =tmp2.s_id
           |where tmp2.s_id is null;
         """.stripMargin

        //11、查询没有学全所有课程的同学的信息:
        s"""
           |select student.* from student
           |join (select count(c_id)num1 from course)tmp1
           |left join(
           |      select s_id,count(c_id)num2
           |        from score group by s_id)tmp2
           |on student.s_id=tmp2.s_id and tmp1.num1=tmp2.num2
           |where tmp2.s_id is null;
         """.stripMargin

        //12、查询至少有一门课与学号为"01"的同学所学相同的同学的信息
        s"""
           |select student.* from student
           |join (select c_id from score where score.s_id=01)tmp1
           |join (select s_id,c_id from score)tmp2
           |    on tmp1.c_id =tmp2.c_id and student.s_id =tmp2.s_id
           |where student.s_id  not in('01')
           |group by student.s_id,s_name,s_birth,s_sex;
         """.stripMargin

        //13、查询和"01"号的同学学习的课程完全相同的其他同学的信息:
        //  备注:hive不支持group_concat方法,可用 concat_ws(’|’, collect_set(str)) 实现
        s"""
           |select student.*,tmp1.course_id from student
           |join (select s_id ,concat_ws('|', collect_set(c_id)) course_id from score
           |      group by s_id having s_id not in (1))tmp1
           |  on student.s_id = tmp1.s_id
           |join (select concat_ws('|', collect_set(c_id)) course_id2
           |            from score  where s_id=1)tmp2
           |      on tmp1.course_id = tmp2.course_id2;
         """.stripMargin

        //14、查询没学过"张三"老师讲授的任一门课程的学生姓名:
        s"""
           |select student.* from student
           |  left join (select s_id from score
           |          join (select c_id from course join  teacher on course.t_id=teacher.t_id and t_name='张三')tmp2
           |          on score.c_id=tmp2.c_id )tmp
           |  on student.s_id = tmp.s_id
           |  where tmp.s_id is null;
         """.stripMargin

        //15、查询两门及其以上不及格课程的同学的学号，姓名及其平均成绩:
        s"""
           |select student.s_id,student.s_name,tmp.avg_score from student
           |inner join (select s_id from score
           |      where s_score<60
           |        group by score.s_id having count(s_id)>1)tmp2
           |on student.s_id = tmp2.s_id
           |left join (
           |    select s_id,round(AVG (score.s_score)) avg_score
           |      from score group by s_id)tmp
           |      on tmp.s_id=student.s_id;
         """.stripMargin

        //16、检索"01"课程分数小于60，按分数降序排列的学生信息:
        s"""
           |select student.*,s_score from student,score
           |where student.s_id=score.s_id and s_score<60 and c_id='01'
           |order by s_score desc;
         """.stripMargin

        //17、按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩:
        s"""
           |select a.s_id,tmp1.s_score as chinese,tmp2.s_score as math,tmp3.s_score as english,
           |    round(avg (a.s_score),2) as avgScore
           |from score a
           |left join (select s_id,s_score  from score s1 where  c_id='01')tmp1 on  tmp1.s_id=a.s_id
           |left join (select s_id,s_score  from score s2 where  c_id='02')tmp2 on  tmp2.s_id=a.s_id
           |left join (select s_id,s_score  from score s3 where  c_id='03')tmp3 on  tmp3.s_id=a.s_id
           |group by a.s_id,tmp1.s_score,tmp2.s_score,tmp3.s_score order by avgScore desc;
         """.stripMargin

        //18.查询各科成绩最高分、最低分和平均分：以如下形式显示：课程ID，课程name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率:
        //  及格为>=60，中等为：70-80，优良为：80-90，优秀为：>=90
        s"""
           |select course.c_id,course.c_name,tmp.maxScore,tmp.minScore,tmp.avgScore,tmp.passRate,tmp.moderate,tmp.goodRate,tmp.excellentRates from course
           |join(select c_id,max(s_score) as maxScore,min(s_score)as minScore,
           |    round(avg(s_score),2) avgScore,
           |    round(sum(case when s_score>=60 then 1 else 0 end)/count(c_id),2)passRate,
           |    round(sum(case when s_score>=60 and s_score<70 then 1 else 0 end)/count(c_id),2) moderate,
           |    round(sum(case when s_score>=70 and s_score<80 then 1 else 0 end)/count(c_id),2) goodRate,
           |    round(sum(case when s_score>=80 and s_score<90 then 1 else 0 end)/count(c_id),2) excellentRates
           |from score group by c_id)tmp on tmp.c_id=course.c_id;
         """.stripMargin

        //19、按各科成绩进行排序，并显示排名:
        //  row_number() over()分组排序功能(mysql没有该方法)
        s"""
           |select s1.*,row_number()over(order by s1.s_score desc) Ranking
           |    from score s1 where s1.c_id='01'order by noRanking asc
           |union all select s2.*,row_number()over(order by s2.s_score desc) Ranking
           |    from score s2 where s2.c_id='02'order by noRanking asc
           |union all select s3.*,row_number()over(order by s3.s_score desc) Ranking
           |    from score s3 where s3.c_id='03'order by noRanking asc;
         """.stripMargin

        //20、查询学生的总成绩并进行排名:
        s"""
           |select score.s_id,s_name,sum(s_score) sumscore,row_number()over(order by sum(s_score) desc) Ranking
           |  from score ,student
           |    where score.s_id=student.s_id
           |    group by score.s_id,s_name order by sumscore desc;
         """.stripMargin

        //21、查询不同老师所教不同课程平均分从高到低显示:
        s"""
           |select course.c_id,course.t_id,t_name,round(avg(s_score),2)as avgscore from course
           |    join teacher on teacher.t_id=course.t_id
           |    join score on course.c_id=score.c_id
           |    group by course.c_id,course.t_id,t_name order by avgscore desc;
         """.stripMargin

        s"""
           |select course.c_id,course.t_id,t_name,round(avg(s_score),2)as avgscore from course,teacher,score
           |   where teacher.t_id=course.t_id and course.c_id=score.c_id
           |    group by course.c_id,course.t_id,t_name order by avgscore desc;
         """.stripMargin

        //22、查询所有课程的成绩第2名到第3名的学生信息及该课程成绩:
        s"""
           |select tmp1.* from
           |    (select * from score where c_id='01' order by s_score desc limit 3)tmp1
           |    order by s_score asc limit 2
           |union all select tmp2.* from
           |    (select * from score where c_id='02' order by s_score desc limit 3)tmp2
           |    order by s_score asc limit 2
           |union all select tmp3.* from
           |    (select * from score where c_id='03' order by s_score desc limit 3)tmp3
           |    order by s_score asc limit 2;
         """.stripMargin

        //23、统计各科成绩各分数段人数：课程编号,课程名称,[100-85],[85-70],[70-60],[0-60]及所占百分比
        s"""
           |select c.c_id,c.c_name,tmp1.s0_60, tmp1.percentum,tmp2.s60_70, tmp2.percentum,tmp3.s70_85, tmp3.percentum,tmp4.s85_100, tmp4.percentum
           |from course c
           |join(select c_id,sum(case when s_score<60 then 1 else 0 end )as s0_60,
           |               round(100*sum(case when s_score<60 then 1 else 0 end )/count(c_id),2)as percentum
           |     from score group by c_id)tmp1 on tmp1.c_id =c.c_id
           |left join(select c_id,sum(case when s_score<70 and s_score>=60 then 1 else 0 end )as s60_70,
           |               round(100*sum(case when s_score<70 and s_score>=60 then 1 else 0 end )/count(c_id),2)as percentum
           |     from score group by c_id)tmp2 on tmp2.c_id =c.c_id
           |left join(select c_id,sum(case when s_score<85 and s_score>=70 then 1 else 0 end )as s70_85,
           |               round(100*sum(case when s_score<85 and s_score>=70 then 1 else 0 end )/count(c_id),2)as percentum
           |     from score group by c_id)tmp3 on tmp3.c_id =c.c_id
           |left join(select c_id,sum(case when s_score>=85 then 1 else 0 end )as s85_100,
           |               round(100*sum(case when s_score>=85 then 1 else 0 end )/count(c_id),2)as percentum
           |     from score group by c_id)tmp4 on tmp4.c_id =c.c_id;
         """.stripMargin

        //24、查询学生平均成绩及其名次:
        s"""
           |select tmp.*,row_number()over(order by tmp.avgScore desc) Ranking from
           |  (select student.s_id,
           |          student.s_name,
           |          round(avg(score.s_score),2) as avgScore
           |  from student join score
           |  on student.s_id=score.s_id
           |  group by student.s_id,student.s_name)tmp
           |order by avgScore desc;
         """.stripMargin

        //25、查询各科成绩前三名的记录
        s"""
           |–-课程id为01的前三名
           |select score.c_id,course.c_name,student.s_name,s_score from score
           |join student on student.s_id=score.s_id
           |join course on  score.c_id='01' and course.c_id=score.c_id
           |order by s_score desc limit 3;
           |
           |–-课程id为02的前三名
           |select score.c_id,course.c_name,student.s_name,s_score
           |from score
           |join student on student.s_id=score.s_id
           |join course on  score.c_id='02' and course.c_id=score.c_id
           |order by s_score desc limit 3;
           |
           |–-课程id为03的前三名
           |select score.c_id,course.c_name,student.s_name,s_score
           |from score
           |join student on student.s_id=score.s_id
           |join course on  score.c_id='03' and course.c_id=score.c_id
           |order by s_score desc limit 3;
         """.stripMargin

        //26、查询每门课程被选修的学生数:
        s"""
           |select c.c_id,c.c_name,tmp.number from course c
           |    join (select c_id,count(1) as number from score
           |        where score.s_score<60 group by score.c_id)tmp
           |    on tmp.c_id=c.c_id;
         """.stripMargin

        //27、查询出只有两门课程的全部学生的学号和姓名:
        s"""
           |select st.s_id,st.s_name from student st
           |  join (select s_id from score group by s_id having count(c_id) =2)tmp
           |    on st.s_id=tmp.s_id;
         """.stripMargin

        //28、查询男生、女生人数:
        s"""
           |select tmp1.man,tmp2.women from
           |    (select count(1) as man from student where s_sex='男')tmp1,
           |    (select count(1) as women from student where s_sex='女')tmp2;
         """.stripMargin

        //29、查询名字中含有"风"字的学生信息:
        s"""
           |select * from student where s_name like '%风%';
         """.stripMargin

        //30、查询同名同性学生名单，并统计同名人数:
        s"""
           |select s1.s_id,s1.s_name,s1.s_sex,count(*) as sameName
           |from student s1,student s2
           |where s1.s_name=s2.s_name and s1.s_id<>s2.s_id and s1.s_sex=s2.s_sex
           |group by s1.s_id,s1.s_name,s1.s_sex;
         """.stripMargin

        //31、查询1990年出生的学生名单:
        s"""
           |select * from student where s_birth like '1990%';
         """.stripMargin

        //32、查询每门课程的平均成绩，结果按平均成绩降序排列，平均成绩相同时，按课程编号升序排列:
        s"""
           |select score.c_id,c_name,round(avg(s_score),2) as avgScore from score
           |  join course on score.c_id=course.c_id
           |    group by score.c_id,c_name order by avgScore desc,score.c_id asc;
         """.stripMargin

        //33、查询平均成绩大于等于85的所有学生的学号、姓名和平均成绩:
        s"""
           |select score.s_id,s_name,round(avg(s_score),2)as avgScore from score
           |    join student on student.s_id=score.s_id
           |    group by score.s_id,s_name having avg(s_score) >= 85;
         """.stripMargin

        //34、查询课程名称为"数学"，且分数低于60的学生姓名和分数:
        s"""
           |select s_name,s_score as mathScore from student
           |    join (select s_id,s_score
           |            from score,course
           |            where score.c_id=course.c_id and c_name='数学')tmp
           |    on tmp.s_score < 60 and student.s_id=tmp.s_id;
         """.stripMargin

        //35、查询所有学生的课程及分数情况:
        s"""
           |select a.s_name,
           |    SUM(case c.c_name when '语文' then b.s_score else 0 end ) as chainese,
           |    SUM(case c.c_name when '数学' then b.s_score else 0 end ) as math,
           |    SUM(case c.c_name when '英语' then b.s_score else 0 end ) as english,
           |    SUM(b.s_score) as sumScore
           |  from student a
           |    join score b on a.s_id=b.s_id
           |    join course c on b.c_id=c.c_id
           |    group by s_name,a.s_id;
         """.stripMargin

        //36、查询任何一门课程成绩在70分以上的学生姓名、课程名称和分数:
        s"""
           |select student.s_id,s_name,c_name,s_score from student
           |  join (select sc.* from score sc
           |        left join(select s_id from score where s_score < 70 group by s_id)tmp
           |        on sc.s_id=tmp.s_id where tmp.s_id is null)tmp2
           |    on student.s_id=tmp2.s_id
           |  join course on tmp2.c_id=course.c_id
           |order by s_id;
           |
           |
           |
           |-- 查询全部及格的信息
           |select sc.* from score sc
           |  left join(select s_id from score where s_score < 60 group by s_id)tmp
           |    on sc.s_id=tmp.s_id
           |where  tmp.s_id is  null;
         """.stripMargin

        //37、查询课程不及格的学生:
        s"""
           |select s_name,c_name as courseName,tmp.s_score
           |from student
           |join (select s_id,s_score,c_name
           |      from score,course
           |      where score.c_id=course.c_id and s_score < 60)tmp
           |on student.s_id=tmp.s_id;
         """.stripMargin

        //38、查询课程编号为01且课程成绩在80分以上的学生的学号和姓名:
        s"""
           |select student.s_id,s_name,s_score as score_01
           |from student
           |join score on student.s_id=score.s_id
           |where c_id='01' and s_score >= 80;
         """.stripMargin

        //39、求每门课程的学生人数:
        s"""
           |select course.c_id,course.c_name,count(1)as selectNum
           |from course
           |join score on course.c_id=score.c_id
           |group by course.c_id,course.c_name;
         """.stripMargin

        //40、查询选修"张三"老师所授课程的学生中，成绩最高的学生信息及其成绩:
        s"""
           |select student.*,tmp3.c_name,tmp3.maxScore
           |from (select s_id,c_name,max(s_score)as maxScore from score
           |      join (select course.c_id,c_name from course join
           |                  (select t_id,t_name from teacher where t_name='张三')tmp
           |            on course.t_id=tmp.t_id)tmp2
           |      on score.c_id=tmp2.c_id group by score.s_id,c_name
           |      order by maxScore desc limit 1)tmp3
           |join student
           |on student.s_id=tmp3.s_id;
         """.stripMargin

        //41、查询不同课程成绩相同的学生的学生编号、课程编号、学生成绩:
        s"""
           |select distinct a.s_id,a.c_id,a.s_score from score a,score b
           |    where a.c_id <> b.c_id and a.s_score=b.s_score;
         """.stripMargin

        //42、查询每门课程成绩最好的前三名:
        s"""
           |select tmp1.* from
           |  (select *,row_number()over(order by s_score desc) ranking
           |      from score  where c_id ='01')tmp1
           |where tmp1.ranking <= 3
           |union all
           |select tmp2.* from
           |  (select *,row_number()over(order by s_score desc) ranking
           |      from score where c_id ='02')tmp2
           |where tmp2.ranking <= 3
           |union all
           |select tmp3.* from
           |  (select *,row_number()over(order by s_score desc) ranking
           |      from score where c_id ='03')tmp3
           |where tmp3.ranking <= 3;
         """.stripMargin

        //43、统计每门课程的学生选修人数（超过5人的课程才统计）:
        //  要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列
        s"""
           |select distinct course.c_id,tmp.num from course
           |    join (select c_id,count(1) as num from score group by c_id)tmp
           |    where tmp.num>=5 order by tmp.num desc ,course.c_id asc;
         """.stripMargin

        //44、检索至少选修两门课程的学生学号:
        s"""
           |select s_id,count(c_id) as totalCourse
           |from score
           |group by s_id
           |having count(c_id) >= 2;
         """.stripMargin

        //45、查询选修了全部课程的学生信息:
        s"""
           |select student.*
           |from student,
           |     (select s_id,count(c_id) as totalCourse
           |      from score group by s_id)tmp
           |where student.s_id=tmp.s_id and totalCourse=3;
         """.stripMargin

        //46、查询各学生的年龄(周岁):
        //  按照出生日期来算，当前月日 < 出生年月的月日则，年龄减一
        s"""
           |select s_name,s_birth,
           |  floor((datediff(current_date,s_birth) - floor((year(current_date) - year(s_birth))/4))/365) as age
           |from student;
         """.stripMargin

        //47、查询本周过生日的学生:
        s"""
           |select * from student where weekofyear(CURRENT_DATE)+1 =weekofyear(s_birth);
         """.stripMargin

        //48、查询下周过生日的学生:
        s"""
           |select * from student where weekofyear(CURRENT_DATE)+1 =weekofyear(s_birth);
         """.stripMargin

        //49、查询本月过生日的学生:
        s"""
           |select * from student where MONTH(CURRENT_DATE) =MONTH(s_birth);
         """.stripMargin

        //50、查询12月份过生日的学生:
        s"""
           |select s_name,s_sex,s_birth from student where substring(s_birth,6,2)='12';
         """.stripMargin


    }
}
