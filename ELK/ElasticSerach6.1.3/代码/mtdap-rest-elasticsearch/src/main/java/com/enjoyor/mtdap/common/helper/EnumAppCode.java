package com.enjoyor.mtdap.common.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zn
 * @version 创建时间：2015-1-27 下午1:58:51
 * @类说明
 */

public enum EnumAppCode {

	SUCCESS(0, "成功"), 
	PARAM_ERR(1, "参数错误"), 
	NULL_POINTER(2, "空指针"), 
	PARAM_NULL(3, "参数为空"), 
	PARAM_FMT_ERROR(4, "数据格式错误"), 
	PARAM_LACK(5, "缺少参数"), 
	PARAM_INSERT_ERROR(6, "参数插入错误"), 
	DB_ERR(7, "数据库错误"), 
	REQUEST_TIMEOUT(8, "请求超时"), 
	UNKNOW(9, "未知"), 
	OPERATOR_FAILURE(10, "操作失败"), 
	WORK_FLOW_ERROR(11,"工作流引擎错误"),
	DICRIONARY_NOT_EXIST(12,"文件目录不存在"),
	EXPORT_FAILURE(13,"导出文件失败"),
	EXPORT_TOOMUCH(14,"导出文件失败,由于导出的行数大于Excel所能承载量(65535行)！"),
	EXPORT_NULL(15,"导出文件失败，因为导出的文件内容为空！"),
	TIME_ERROR(16,"时间格式错误"),
	REQUEST_FAILURE(17,"请求失败"),
	IMPORT_FAIL(18,"导入失败,请选择有效的EXCEL文件！"),
	DEVIALTE_UNIQUE(101, "重复数据"), 
	COMMON_CLASS_NOT_FOUND(801, "未找到类"), 
	COMMON_BEAN_NULL(802, "bean未进行实例化"), 
	DEVICE_OFFLINE(901, "匝道设备不在线"), 
	DEVICE_ERROR_UNDER_CONTROLLER(902, "匝道设备处于现场控制"), 
	DEVICE_ERROR_NOT_EXIST(903, "设备编号不存在"), 
	DEVICE_ERROR_SAMESTATUS(904, "设备状态一致，无需操作"), 
	DEVICE_ERROR_ONOPERATE(905, "匝道设备当前正操作控制"), 
	DEVICE_ERROR_CONFIGFAIL(906, "匝道设备配置失败"), 
	DEVICE_ERROR_CONFIG(907, "匝道数据配置错误"), 
	SELECT_ERR(1001, "查询出错"), 
	UPLOAD_FAIL(1002,"附件上传失败"), 
	UPDATE_FAIL(1003, "更新出错"), 
	IMAGE_ERROR(1501, "图片内容格式错误"), 
	JSON_TO_BEAN_ERROR(1601, "JSON字符串转bean错误"), 
	JSON_FIELD_ERROR(1602, "JSON格式参数错误"), 
	XML_FORMAT_ERROR(1701, "XML格式错误"), 
	USER_NO_EXISTS(2001, "用户不存在"), 
	USER_NAMEORPWD_ERR(2002, "用户名或密码错误"), 
	USER_NO_RIGHTS(2003, "用户无权限"), 
	USER_ERROR_MAC(2004,"MAC地址错误"), 
	USER_PWD_ERR(2005, "密码错误"), 
	SESSION_TIMEOUT(2006,"session过期"), 
	BLOCK_NOT_EXIST(2007, "路块不存在"),
	USER_NO_LOGIN(2008, "用户未登录"),
	DATA_NULL(2009,"无数据"),
	USER_ERROR_IP(2010,"IP地址错误"), 
	USER_ERROR_IP_MAC(2011,"IP或者MAC地址错误"),
	ID_NO_EXISTS(2012, "身份证号不存在"),
	USER_DISABLE(2013, "用户已禁用"),
	USER_DISCHECK(2014, "用户待审核"),
	USER_NAME_EXISTS(2015, "用户名重复"),
	COMMON_NAME_EXISTS(2016, "名称重复"),
	// token
	TOKEN_MISSING(2101, "令牌缺失"), 
	TOKEN_INVALID(2102, "令牌无效"), 
	PARAM_NAME_EXISTS(2103, "重复数据，请仔细检查！"),
	//redis
	REDIS_DISCONNECT(2201,"redis连接异常"),
	//dubbo服务
	DUBBO_DISCONNECT(2301,"dubbo连接异常"),
	NO_DUBBO_SERVICE(2302,"dubbo服务无法获取"),
	//UBMS
	NO_SUCH_METHOD(2401,"无对应方法，检查方法名和方法参数是否正确"),
	ILLEGAL_ACCESS(2402,"安全权限异常,检查所调用方法是否有权限限制"),
	ILLEGAL_ARGUMEN(2403,"参数不合法"),
	NUMBER_FORMAT(2404,"String转成int数字的转换出错,检查字符串内容是否符合数字格式"),
	
	// system
	SYSTEM_NO_EXISTS(8001, "系统不存在,请联系管理员"), 
	SYSTEM_NO_WORK(8002, "系统已停用,请联系管理员"), 
	SYSTEM_NO_EFFECT(8002, "系统已无效,请联系管理员"),
	/**
	 * BPMS
	 */
	BMPS_LACK_PARAM(3001, "缺少参数"), 
	BPMS_NULL_RES(3101, "请求资源不存在"), 
	BPMS_NULL_HISTPROINST(3102, "请求历史流程实例不存在"), 
	BPMS_NULL_PRODEF(3103, "请求流程定义不存在"), 
	BPMS_NULL_TASK(3014, "请求的任务不存在"), 
	BPMS_NULL_PROINST(3105, "请求流程实例不存在"), 
	BPMS_HAVE_PROINST(3201, "存在活动的流程实例"),

	/**
	 * VMS
	 */
	ERROR_CONNECTION_SERVER(5001, "诱导服务器连接失败"), 
	ERROR_REQUEST_TIMEOUT(5002,"诱导服务器请求超时"), 
	ERROR_MOTHOD_PARAMETER(5003, "调用方法传参错误"), 
	ERROR_FILE_NOTFOUND(5004, "文件无法找到"), 
	ERROR_FILE_UPLOAD(5005, "文件上传失败"), 
	ERROR_XML_FORMAT(5006, "返回XML格式错误"), 
	ON_OPERATE_SUCCESS(5007, "操作成功"), 
	ON_OPERATE_FAILURE(5008, "操作失败"), 
	ERROR_PIC_UPLOAD(5009, "节目图片上传失败"), 
	ERROR_PROG_SEND(5010, "节目发送失败"), 
	ERROR_PROG_ID_DUP(5011, "节目ID重复"),
	ERROR_PROG_DEL(5011, "节目删除失败"), 
	ERROR_PROG_RECYCLE(5012, "节目移至回收站失败"), 
	ERROR_PROG_RELATE_DEL(5013, "节目删除失败"), 
	ERROR_PROG_UPDATE(5014, "节目修改失败"), 
	ERROR_PROG_UPDATE_ORDER_PARAM(5015, "调整节目顺序参数错误"), 
	ERROR_PROG_ENABLE(5016, "节目是否启用错误"),

	LOCATIONID_EXISTS(5017, "定位标识已存在"),
	ERROR_READ_EXCEl(5018, "EXCEL读取失败"),
	/**
	 * FMS
	 */
	CONTAINS_SENSITIVE_WORDS(6001, "资料名称或关键字包含敏感词汇"), 
	
	/**
	 * 
	 */
	ERROR_NO_EXIST_REPAIRID(12001,"报修单不存在"),
	/**
	 * 调用外部系统接口时发生的异常
	 */
	SME_SEND_INTER_ERR(4001, "调用短信接口出错"),
	
	
	//代码 7000~7999     用于数据库错误 
	
	SYS_EXPT(10000, "系统异常"),
	OBJ_EXISTS(10001, "操作对象已存在"),
	OBJ_NO_EXISTS(10002, "操作对象不存在"),
	
	//流程引擎
	PROCESS_NO_OPR(8001, "无法执行下一步该操作");
    

	//******************************************
	//* 数据库错误  (7000~7999)，见于配置文件
	//******************************************
	static final Map<Integer,Integer> dbErrCodeMap = new HashMap<Integer,Integer>();
	static final Map<Integer,String> dbErrMessageMap = new HashMap<Integer,String>();
	
	static public Integer getDbErrorCode(Integer oraCode){
		if(dbErrCodeMap.size()==0) initDbErrorMap();
		Integer ret = dbErrCodeMap.get(oraCode);
		return ret==null?DB_ERR.code:ret;
	}
	static public String getDbErrorMessage(Integer oraCode){
		if(dbErrMessageMap.size()==0) initDbErrorMap();
		return dbErrMessageMap.get(oraCode);
	}
	
	private final int code;
	private final String message;

	private EnumAppCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public String toString() {
		return this.name();
	}
	
	static private void initDbErrorMap(){
		if(dbErrCodeMap.size()==0||dbErrMessageMap.size()==0){
			synchronized (EnumAppCode.class){ if(dbErrCodeMap.size()==0||dbErrMessageMap.size()==0){
				dbErrCodeMap.put(Integer.parseInt("00001",10), Integer.parseInt("7001",10));
				dbErrMessageMap.put(Integer.parseInt("00001",10), "违反唯一性约束，检查数据是否跟原数据重复");
				
				dbErrCodeMap.put(Integer.parseInt("00054",10), Integer.parseInt("7002",10));
				dbErrMessageMap.put(Integer.parseInt("00054",10), "资源正忙;表或资源正在被修改，等待修改完成");

				dbErrCodeMap.put(Integer.parseInt("00060",10), Integer.parseInt("7003",10));
				dbErrMessageMap.put(Integer.parseInt("00060",10), "等待资源时检测到死锁；其他应用在编辑该表");

				dbErrCodeMap.put(Integer.parseInt("00257",10), Integer.parseInt("7004",10));
				dbErrMessageMap.put(Integer.parseInt("00257",10), "归档日志写满；删除部分归档日志");

				dbErrCodeMap.put(Integer.parseInt("00904",10), Integer.parseInt("7005",10));
				dbErrMessageMap.put(Integer.parseInt("00904",10), "无效的列名；检查表列名是否有改动");

				dbErrCodeMap.put(Integer.parseInt("00913",10), Integer.parseInt("7006",10));
				dbErrMessageMap.put(Integer.parseInt("00913",10), "数据插入值过多；检查数据插入语句");

				dbErrCodeMap.put(Integer.parseInt("00933",10), Integer.parseInt("7007",10));
				dbErrMessageMap.put(Integer.parseInt("00933",10), "sql未正确结束；检查sql语法");

				dbErrCodeMap.put(Integer.parseInt("00936",10), Integer.parseInt("7008",10));
				dbErrMessageMap.put(Integer.parseInt("00936",10), "sql错误；检查sql语法");

				dbErrCodeMap.put(Integer.parseInt("00947",10), Integer.parseInt("7009",10));
				dbErrMessageMap.put(Integer.parseInt("00947",10), "数据插入值太少；检查数据插入语句");

				dbErrCodeMap.put(Integer.parseInt("00972",10), Integer.parseInt("7010",10));
				dbErrMessageMap.put(Integer.parseInt("00972",10), "标识符过长；列命名不规范超过数据库规定长度");

				dbErrCodeMap.put(Integer.parseInt("01017",10), Integer.parseInt("7011",10));
				dbErrMessageMap.put(Integer.parseInt("01017",10), "账号/密码错误，请确认账号/密码");

				dbErrCodeMap.put(Integer.parseInt("01400",10), Integer.parseInt("7012",10));
				dbErrMessageMap.put(Integer.parseInt("01400",10), "无法将 NULL 插入表；检查数据，或者修改不能为空字段。");

				dbErrCodeMap.put(Integer.parseInt("01403",10), Integer.parseInt("7013",10));
				dbErrMessageMap.put(Integer.parseInt("01403",10), "未找到任何数据；检查查询的表是否空");

				dbErrCodeMap.put(Integer.parseInt("01407",10), Integer.parseInt("7014",10));
				dbErrMessageMap.put(Integer.parseInt("01407",10), "无法更新字段为空；检查输入数据");

				dbErrCodeMap.put(Integer.parseInt("01422",10), Integer.parseInt("7015",10));
				dbErrMessageMap.put(Integer.parseInt("01422",10), "返回多行；检查是否有重复数据");

				dbErrCodeMap.put(Integer.parseInt("01427",10), Integer.parseInt("7016",10));
				dbErrMessageMap.put(Integer.parseInt("01427",10), "单行子查询返回多个行；更新操作查询的表有重复数据。");

				dbErrCodeMap.put(Integer.parseInt("01438",10), Integer.parseInt("7017",10));
				dbErrMessageMap.put(Integer.parseInt("01438",10), "值大于为此列指定的允许精度；数字小数点过多");

				dbErrCodeMap.put(Integer.parseInt("01502",10), Integer.parseInt("7018",10));
				dbErrMessageMap.put(Integer.parseInt("01502",10), "索引损坏；重建索引");

				dbErrCodeMap.put(Integer.parseInt("01653",10), Integer.parseInt("7019",10));
				dbErrMessageMap.put(Integer.parseInt("01653",10), "表空间写满，需要增加表空间数据文件");

				dbErrCodeMap.put(Integer.parseInt("01722",10), Integer.parseInt("7020",10));
				dbErrMessageMap.put(Integer.parseInt("01722",10), "无效数字；检查字符串数字转换是否有问题");

				dbErrCodeMap.put(Integer.parseInt("01745",10), Integer.parseInt("7021",10));
				dbErrMessageMap.put(Integer.parseInt("01745",10), "无效的绑定变量名；检查绑定变量是否占用数据库变量");

				dbErrCodeMap.put(Integer.parseInt("01747",10), Integer.parseInt("7022",10));
				dbErrMessageMap.put(Integer.parseInt("01747",10), "列名或表名占用了数据库变量；检查修改列明或表名");

				dbErrCodeMap.put(Integer.parseInt("01858",10), Integer.parseInt("7023",10));
				dbErrMessageMap.put(Integer.parseInt("01858",10), "要求输入数字处找到字符；检查输入数字是否是字符");

				dbErrCodeMap.put(Integer.parseInt("02049",10), Integer.parseInt("7024",10));
				dbErrMessageMap.put(Integer.parseInt("02049",10), "表被其他进程锁住；检查锁住资源并且释放锁");

				dbErrCodeMap.put(Integer.parseInt("02068",10), Integer.parseInt("7025",10));
				dbErrMessageMap.put(Integer.parseInt("02068",10), "连接超时；检查数据库配置，修改数据库连接时长");

				dbErrCodeMap.put(Integer.parseInt("02291",10), Integer.parseInt("7026",10));
				dbErrMessageMap.put(Integer.parseInt("02291",10), "违法完整约束条件；检查数据关联数据是否已经配置");

				dbErrCodeMap.put(Integer.parseInt("02292",10), Integer.parseInt("7027",10));
				dbErrMessageMap.put(Integer.parseInt("02292",10), "违法完整约束条件；检查数据关联数据是否已经配置");

				dbErrCodeMap.put(Integer.parseInt("04098",10), Integer.parseInt("7028",10));
				dbErrMessageMap.put(Integer.parseInt("04098",10), "触发器损坏；检查维护触发器");

				dbErrCodeMap.put(Integer.parseInt("06502",10), Integer.parseInt("7029",10));
				dbErrMessageMap.put(Integer.parseInt("06502",10), "字符串缓冲区太小；PL/sql定义变量长度太小");

				dbErrCodeMap.put(Integer.parseInt("08103",10), Integer.parseInt("7030",10));
				dbErrMessageMap.put(Integer.parseInt("08103",10), "对象不存在；检查表是否存在（查询物化视图时会出现这种情况）");

				dbErrCodeMap.put(Integer.parseInt("12170",10), Integer.parseInt("7031",10));
				dbErrMessageMap.put(Integer.parseInt("12170",10), "连接超时；检查数据库网络");

				dbErrCodeMap.put(Integer.parseInt("12514",10), Integer.parseInt("7032",10));
				dbErrMessageMap.put(Integer.parseInt("12514",10), "连接无法被识别；检查连接配置（客户端）");

				dbErrCodeMap.put(Integer.parseInt("12516",10), Integer.parseInt("7033",10));
				dbErrMessageMap.put(Integer.parseInt("12516",10), "无法匹配协议栈；连接过多，检查程序或增加会话数");

				dbErrCodeMap.put(Integer.parseInt("12518",10), Integer.parseInt("7034",10));
				dbErrMessageMap.put(Integer.parseInt("12518",10), "监听程序无法分发客户机连接；连接过多检查程序或增加会话数");

				dbErrCodeMap.put(Integer.parseInt("12528",10), Integer.parseInt("7035",10));
				dbErrMessageMap.put(Integer.parseInt("12528",10), "所有适用例程都无法建立新连；检查数据库监听");

				dbErrCodeMap.put(Integer.parseInt("12541",10), Integer.parseInt("7036",10));
				dbErrMessageMap.put(Integer.parseInt("12541",10), "无监听程序；检查数据库监听是否启动");

				dbErrCodeMap.put(Integer.parseInt("12543",10), Integer.parseInt("7037",10));
				dbErrMessageMap.put(Integer.parseInt("12543",10), "无法连接到目标主机；检查网络");

				dbErrCodeMap.put(Integer.parseInt("12547",10), Integer.parseInt("7038",10));
				dbErrMessageMap.put(Integer.parseInt("12547",10), "丢失连接；检查网络");

				dbErrCodeMap.put(Integer.parseInt("12570",10), Integer.parseInt("7039",10));
				dbErrMessageMap.put(Integer.parseInt("12570",10), "包阅读程序失败；检查网络；检查监听配置");

				dbErrCodeMap.put(Integer.parseInt("12805",10), Integer.parseInt("7040",10));
				dbErrMessageMap.put(Integer.parseInt("12805",10), "并行查服务意外终止；联系DBA");

				dbErrCodeMap.put(Integer.parseInt("12899",10), Integer.parseInt("7041",10));
				dbErrMessageMap.put(Integer.parseInt("12899",10), "字段长度超过设定值;检查数据长度是否有问题");

				dbErrCodeMap.put(Integer.parseInt("14019",10), Integer.parseInt("7042",10));
				dbErrMessageMap.put(Integer.parseInt("14019",10), "表分区创建错误；检查创建分区语句");

				dbErrCodeMap.put(Integer.parseInt("14020",10), Integer.parseInt("7043",10));
				dbErrMessageMap.put(Integer.parseInt("14020",10), "不能指定表分区；检查创建分区语句");

				dbErrCodeMap.put(Integer.parseInt("14074",10), Integer.parseInt("7044",10));
				dbErrMessageMap.put(Integer.parseInt("14074",10), "表分区界限低于最后一个分区；检查创建分区语句");
				//读配置文件...
			}}
		}
	}
}