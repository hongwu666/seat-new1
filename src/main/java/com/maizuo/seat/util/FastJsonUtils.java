package com.maizuo.seat.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class FastJsonUtils {

	/**
	 * 用fastjson 将json字符串解析为一个 JavaBean
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> T getBean(String jsonString, Class<T> cls) {
		T t = null;
		try {
			t = JSON.parseObject(jsonString, cls);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 把对象转化成字符串 <BR>
	 * time:2015-5-19 上午11:01:17
	 * 
	 * @author: sndy
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return JSON.toJSONString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把List字符串 转化成对象列表<BR>
	 * time:2015-5-19 上午11:01:17
	 * 
	 * @author: sndy
	 * @param obj
	 * @return
	 */
	public static <T> List<T> toList(List<String> jsonList, Class<T> c) {

		if (jsonList == null) {
			return null;
		}

		List<T> results = new ArrayList<T>();

		for (String json : jsonList) {

			if (StringUtils.isEmpty(json)) {
				continue;
			}

			results.add(getBean(json, c));
		}
		return results;
	}

	/**
	 * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getBeans(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			list = JSON.parseArray(jsonString, cls);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * 用fastjson 将jsonString 解析成 List<Map<String,Object>>
	 * 
	 * @param jsonString
	 * @return
	 */
	public static List<Map<String, Object>> getListMap(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map convertBean(Object bean) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new LinkedHashMap<>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				}
			}
		}
		return returnMap;
	}
}