package com.enjoyor.mtdap.UDFS;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.phoenix.expression.Expression;
import org.apache.phoenix.expression.function.ScalarFunction;
import org.apache.phoenix.parse.FunctionParseNode.Argument;
import org.apache.phoenix.parse.FunctionParseNode.BuiltInFunction;
import org.apache.phoenix.schema.tuple.Tuple;
import org.apache.phoenix.schema.types.PDataType;
import org.apache.phoenix.schema.types.PVarchar;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description PHOENIX自定义函数
 * 判断(时间,点位|时间,点位)中的时间是否在开始结束时间之间
 * 是 输出1  否 输出0
 * Created with guoshuai
 * date 2019/6/4 13:41
 */
@BuiltInFunction(name = PhoenixUdfDemo.NAME, args = {
        //参数类型
        @Argument(allowedTypes = {PVarchar.class}),
        @Argument(allowedTypes = {PVarchar.class}),
        @Argument(allowedTypes = {PVarchar.class})
})
public class PhoenixUdfDemo extends ScalarFunction {
    public static final String NAME = "udf_demo";

    public PhoenixUdfDemo() { }
    public PhoenixUdfDemo(List<Expression> children) throws SQLException {
        super(children);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public PDataType getDataType() {
        return PVarchar.INSTANCE;
    }


    @Override
    public boolean evaluate(Tuple tuple, ImmutableBytesWritable immutableBytesWritable) {
        //实现类
        Expression arg1 = getChildren().get(0);
        Expression arg2 = getChildren().get(1);
        Expression arg3 = getChildren().get(2);
        if (!arg1.evaluate(tuple, immutableBytesWritable)) {
            return false;
        }
        //将varchar参数转成String
        String mac1 = (String) PVarchar.INSTANCE.toObject(immutableBytesWritable, arg1.getDataType());
        if (mac1 == null) {
            return true;
        }
        if (!arg1.evaluate(tuple, immutableBytesWritable)) {
            return false;
        }
        //将varchar参数转成String
        String mac2 = (String) PVarchar.INSTANCE.toObject(immutableBytesWritable, arg2.getDataType());
        if (mac2 == null) {
            return true;
        }
        if (!arg2.evaluate(tuple, immutableBytesWritable)) {
            return false;
        }

        //将varchar参数转成String
        String mac3 = (String) PVarchar.INSTANCE.toObject(immutableBytesWritable, arg3.getDataType());
        if (mac3 == null) {
            return true;
        }
        if (!arg3.evaluate(tuple, immutableBytesWritable)) {
            return false;
        }

        //编码转换PUnsignedLong  调用自定义执行函数
        immutableBytesWritable.set(PVarchar.INSTANCE.toBytes(judgeTime(mac1,mac2,mac3)));
        return true;
    }



    /*参数1 时间,点位|时间,点位  参数2 开始时间   参数3 结束时间*/
    private String judgeTime(String arg1, String args2, String args3) {
        //. | 转义字符+ \\
        String[] splits = arg1.split("\\|");
        long bTime = Long.parseLong(args2);
        long eTime = Long.parseLong(args3);
        //System.out.println(splits[0]);
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<splits.length;i++){
            //System.out.println(splits[i]);
            if(Long.parseLong(splits[i].split(",")[0]) <= eTime
                    && Long.parseLong(splits[i].split(",")[0])>=bTime){
                list.add(i,1+","+splits[i].split(",")[1]);
            }else{
                list.add(i,0+","+splits[i].split(",")[1]);
            }
        }

        StringBuffer sb = new StringBuffer();
        for(String tmp1:list){
            //System.out.println(tmp1);
            sb.append(tmp1+"|");
        }
        sb.substring(0,sb.lastIndexOf("|"));
        //System.out.println(sb.toString());
        return sb.toString();
    }

    /*
    public static void  main(String args[]){
        PhoenixUdfDemo udfsDemo = new PhoenixUdfDemo();
        udfsDemo.judgeTime("201906041002,001|201906041055,002|201906041100,003|201906041130,004",
                "201906041102",
                "201906041130");
    }*/

}
