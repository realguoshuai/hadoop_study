package com.enjoyor.mtdap.common.helper;

/**
 * 用于后台返回JSON对象
 * @author zn
 * @version 创建时间：2015-1-27 下午2:00:12
 * @类说明
 */

import com.alibaba.fastjson.JSONObject;

public class ResultHelper {

	/**
	 * 后台返回json格式 数据
	 *
	 * @param ex
	 *            捕获的错误 enumApp 错误原因枚举 obj 返回结果数据 buffer 返回结果信息
	 * @return JSON
	 */
	public synchronized static JSONObject getResultJson(Exception ex, EnumAppCode enumApp, Object obj, String buffer) {
		return ResultPojo.instance(ex, enumApp, obj, buffer).convertToJson();
	}

	/**
	 * 后台返回ResultPojo格式 数据
	 *
	 * @param ex
	 *            捕获的错误 enumApp 错误原因枚举 obj 返回结果数据 buffer 返回结果信息
	 * @return ResultPojo
	 */
	public synchronized static ResultPojo getResult(Exception ex, EnumAppCode enumApp, Object obj, String buffer) {
		return ResultPojo.instance(ex, enumApp, obj, buffer);
	}

	/**
	 * 后台返回json格式 数据(fail)
	 *
	 * @param ex
	 *            捕获的错误 enumApp 错误原因枚举
	 * @return JSON
	 */
	public synchronized static JSONObject getResultJson(Exception ex, EnumAppCode enumApp) {
		return getResultJson(ex, enumApp, null, null);
	}

	/**
	 * 后台返回ResultPojo格式 数据(fail)
	 *
	 * @param ex
	 *            捕获的错误 enumApp 错误原因枚举
	 * @return ResultPojo
	 */
	public synchronized static ResultPojo getResult(Exception ex, EnumAppCode enumApp) {
		return getResult(ex, enumApp, null, null);
	}

	/**
	 * 后台返回json格式 数据(fail)
	 *
	 * @param ex
	 *            捕获的错误
	 * @return JSON
	 */
	public synchronized static JSONObject getResultJson(Exception ex) {
		return getResultJson(ex, null, null, null);
	}

	/**
	 * 后台返回ResultPojo格式 数据(fail)
	 *
	 * @param ex
	 *            捕获的错误
	 * @return ResultPojo
	 */
	public synchronized static ResultPojo getResult(Exception ex) {
		return getResult(ex, null, null, null);
	}

	/**
	 * 后台返回json格式 数据(fail)
	 *
	 * @param enumApp
	 *            错误原因枚举（定制提示信息）
	 * @param buffer
	 *            定制提示信息
	 * @return JSON
	 */
	public synchronized static JSONObject getResultJson(EnumAppCode enumApp, String buffer) {
		return getResultJson(null, enumApp, null, buffer);
	}

	/**
	 * 后台返回ResultPojo格式 数据(fail)
	 *
	 * @param enumApp
	 *            错误原因枚举（定制提示信息）
	 * @param buffer
	 *            定制提示信息
	 * @return ResultPojo
	 */
	public synchronized static ResultPojo getResult(EnumAppCode enumApp, String buffer) {
		return getResult(null, enumApp, null, buffer);
	}

	/**
	 * 后台返回json格式 数据(fail)
	 *
	 * @param enumApp
	 *            错误原因枚举（提示信息有枚举提供）
	 * @return JSON
	 */
	public synchronized static JSONObject getResultJson(EnumAppCode enumApp) {
		return getResultJson(null, enumApp, null, null);
	}

	/**
	 * 后台返回ResultPojo格式 数据(fail)
	 *
	 * @param enumApp
	 *            错误原因枚举（提示信息有枚举提供）
	 * @return ResultPojo
	 */
	public synchronized static ResultPojo getResult(EnumAppCode enumApp) {
		return getResult(null, enumApp, null, null);
	}

	/**
	 * 后台返回json格式 数据(success)
	 *
	 * @param list
	 *            返回结果数据 buffer 返回结果信息
	 * @return JSON
	 */
	public synchronized static JSONObject getResultJson(Object obj, String buffer) {
		return getResultJson(null, null, obj, buffer);
	}

	/**
	 * 后台返回ResultPojo格式 数据(success)
	 *
	 * @param list
	 *            返回结果数据 buffer 返回结果信息
	 * @return ResultPojo
	 */
	public synchronized static ResultPojo getResult(Object obj, String buffer) {
		return getResult(null, null, obj, buffer);
	}

	/**
	 * 后台返回json格式 数据(success)
	 *
	 * @param obj
	 *            数据结果集
	 * @return JSON
	 */
	public synchronized static JSONObject getResultJson(Object obj) {
		return getResultJson(null, null, obj, "");
	}

	/**
	 * 后台返回ResultPojo格式 数据(success)
	 *
	 * @param obj
	 *            数据结果集
	 * @return ResultPojo
	 */
	public synchronized static ResultPojo getResult(Object obj) {
		return getResult(null, null, obj, "");
	}

	/**
	 * 后台返回json格式 数据(fail)
	 *
	 * @param buffer
	 *            返回结果信息
	 * @return JSON
	 */
	/*
	 * public synchronized static JSONObject getResultJson(String buffer) { return
	 * getResultJson(null, null, null, buffer); }
	 */

	/**
	 * 后台返回ResultPojo格式 数据(fail)
	 *
	 * @param buffer
	 *            返回结果信息
	 * @return ResultPojo
	 */
	/*
	 * public synchronized static ResultPojo getResult(String buffer) { return
	 * getResult(null, null, null, buffer); }
	 */

	/**
	 * 校验返回结果方法
	 *
	 * @param result
	 *            结果json字符串
	 * @return boolean true-返回成功;false-返回失败;
	 */
	public synchronized static boolean validateResult(String result) {
		JSONObject json = JSONObject.parseObject(result);
		int appCode = json.getIntValue("appCode");
		if (appCode == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 校验返回结果方法
	 *
	 * @param result
	 *            JSONObject结果集
	 * @return boolean true-返回成功;false-返回失败;
	 */
	public synchronized static boolean validateResult(JSONObject result) {
		int appCode = result.getIntValue("appCode");
		if (appCode == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取resultList结果集
	 *
	 * @param result
	 *            json格式结果字符串
	 * @return JSONObject 返回resultList对象
	 */
	public synchronized static JSONObject getResultList(String result) {
		JSONObject json = JSONObject.parseObject(result);
		JSONObject object = (JSONObject) json.get("resultList");
		return object;
	}

	/**
	 * 获取resultList结果集
	 *
	 * @param result
	 *            JSONObject对象集
	 * @return JSONObject 返回resultList对象
	 */
	public synchronized static JSONObject getResultList(JSONObject result) {
		JSONObject object = (JSONObject) result.get("resultList");
		return object;
	}

	/**
	 * 校验resultlist是否是success
	 *
	 * @param result
	 * @return boolean true-返回成功;false-返回失败;
	 */
	public static boolean oprValidate(String result) {
		if (ResultHelper.validateResult(result)) {
			if (JSONObject.parseObject(result).getString("resultList").equals("success"))
				return true;
		}
		return false;
	}
}
