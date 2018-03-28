package com.fh.util;

/**
 * Title: JSON帮助类
 * Description: 指定日期格式转换成JSON对象
 * Copyright: Copyright (c) 2011
 * Company: 易象
 * @author 钱嘉
 * @version 1.0 2011-11-22
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

@SuppressWarnings("unchecked")
public class JsonUtils {
	/**
	 * 将java对象转换成json对象,并设定日期格式
	 * 
	 * @param javaObj
	 * @param dataFormat
	 * @return
	 */
	public static JSONObject getJsonObject4JavaPOJO(Object javaObj,String dataFormat) {
		JSONObject json;
		JsonConfig jsonConfig = configJson(dataFormat);
		json = JSONObject.fromObject(javaObj, jsonConfig);
		return json;
	}

	public static JSONArray getJsonArray4JavaPOJO(Object javaObj,String dataFormat) {
		JSONArray json;
		JsonConfig jsonConfig = configJson(dataFormat);
		json = JSONArray.fromObject(javaObj, jsonConfig);
		return json;
	}
	
	/**
	 * JSON 时间解析器具
	 * 
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateProcessor(datePattern));

		return jsonConfig;
	}
	
	/**
	 * 判断是否是JSON
	 * @param key
	 * @param jo
	 * @return
	 */
	public static String typeOfStrIsJSON(String key,JSONObject jo) {
		String type = "String";
		try {
			jo.getJSONArray(key);
			type = "JSONArray";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return type;
	}
	
	/**
	 * 将JSON对象转为Map对象
	 * @param jsonStr
	 * @return HashMap
	 */
	public static Map JsonObjectToMap(JSONObject jsonObj, Map map) {
		//将JSONObject对象转为java对象（Map类型）
		Map<String, Object> objMap = (Map)JSONObject.toBean(jsonObj, Map.class);
		//存放key
		List<String> keyList = new ArrayList<String>();
		for (String key : objMap.keySet()) {
			keyList.add(key);
	    }
		for (String key : keyList) {
			// 判断type是String/JSONArray中的哪一种
			String type = typeOfStrIsJSON(key, jsonObj);
			if (type.equals("String")) {
				map.put(key, jsonObj.getString(key));
			} else if (type.equals("JSONArray")) {
				List childList = JsonArrayToList(jsonObj.getJSONArray(key),new ArrayList());
				map.put(key, childList);
			}
		}
		return map;
	}
	
	public static List JsonArrayToList(JSONArray array, List list) {

		//将JSONArray对象转为java对象（Map类型）
		Object[] obj = (Object[])JSONArray.toArray(array, JSONObject.class);
		//存放key
		if( obj.length > 0 ){
			int len = obj.length;
			for( int i = 0; i<len; i++){
				JSONObject jsonObj = (JSONObject)obj[i];
				Map jsonMap = JsonObjectToMap(jsonObj, new HashMap());
				list.add(jsonMap);
			}
		}
		return list;
	}
}
