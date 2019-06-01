package com.enjoyor.soa.traffic.rest.solr.util;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CommonUtils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void addTimeZone() {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	}

	public static Timestamp parseTime(String time) {
		addTimeZone();
		Timestamp date = null;
		try {
			date = new Timestamp(sdf.parse(time).getTime());
		} catch (Exception e) {

		}
		return date;
	}

	static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSSSSSSSS");
	static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMddHHmmss");
	static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy/MM/dd");
	static SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 将val转换为类型toType的数据
	 *
	 * @param val
	 * @param toType
	 * @return
	 */
	public static <T> T castTo(Object val, Class<T> toType) {
		return castTo(val, toType, null);
	}

	public static <T> T castTo(Object val, Class<T> toType, T defVal) {
		Object ret = defVal;
		try {
			if (val != null && !"".equals(val)) {
				if (toType.equals(String.class)) {
					ret = val.toString();
				} else if (toType.equals(Integer.class) || toType.equals(int.class)) {
					ret = Integer.parseInt(val.toString());
				} else if (toType.equals(Long.class) || toType.equals(long.class)) {
					ret = Long.parseLong(val.toString());
				} else if (toType.equals(Short.class) || toType.equals(short.class)) {
					ret = Short.parseShort(val.toString());
				} else if (toType.equals(Byte.class) || toType.equals(byte.class)) {
					ret = Byte.parseByte(val.toString());
				} else if (toType.equals(Double.class) || toType.equals(double.class)) {
					ret = Double.parseDouble(val.toString());
				} else if (toType.equals(Float.class) || toType.equals(float.class)) {
					ret = Float.parseFloat(val.toString());
				} else if (toType.equals(Boolean.class) || toType.equals(boolean.class)) {
					ret = Boolean.parseBoolean(val.toString());
				} else if (toType.equals(Date.class)) {
					try {
						ret = (Date) val;
					} catch (Throwable e7) {
						try {
							ret = new Date(((Timestamp) val).getTime());
						} catch (Throwable e8) {
							try {
								ret = sdf1.parse(val.toString());
							} catch (Throwable e1) {
								try {
									ret = sdf2.parse(val.toString());
								} catch (Throwable e2) {
									try {
										ret = sdf3.parse(val.toString());
									} catch (Throwable e3) {
										try {
											ret = sdf4.parse(val.toString().replace("-", "").replace(":", "").replace("T", "").replace("Z", ""));
										} catch (Throwable e4) {
											try {
												ret = sdf5.parse(val.toString());
											} catch (Throwable e5) {
												try {
													ret = sdf6.parse(val.toString());
												} catch (Throwable e6) {
												}
											}
										}
									}
								}
							}
						}
					}
				} else if (toType.equals(Timestamp.class)) {
					try {
						ret = (Timestamp) val;
					} catch (Throwable e1) {
						try {
							ret = new Timestamp(((Date) val).getTime());
						} catch (Throwable e2) {
							try {
								ret = new Timestamp((castTo(val, Date.class)).getTime());
							} catch (Throwable e3) {
							}
						}
					}
				} else if (toType.equals(java.sql.Date.class)) {
					try {
						ret = new java.sql.Date((castTo(val, Date.class)).getTime());
					} catch (Throwable e1) {
					}
				}
			}
		} catch (Throwable ex) {
		}
		return (T) ret;
	}

	/**
	 * 将SolrDocument转换成Bean
	 *
	 * @param record
	 * @param clazz
	 * @return
	 */
	public static Object toBean(SolrDocument record, Class clazz) {
		Object obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Object value = record.get(field.getName());
			try {
				BeanUtils.setProperty(obj, field.getName(), value);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 将SolrDocumentList转换成BeanList
	 *
	 * @param records
	 * @param clazz
	 * @return
	 */
	public static Object toBeanList(SolrDocumentList records, Class clazz) {
		List list = new ArrayList();
		for (SolrDocument record : records) {
			list.add(toBean(record, clazz));
		}
		return list;
	}

}
