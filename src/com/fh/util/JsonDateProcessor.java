package com.fh.util;

/**
 * Title: JSON帮助类
 * Description: JSON日期处理器
 * Copyright: Copyright (c) 2011
 * Company: 易象
 * @author 钱嘉
 * @version 1.0 2011-11-22
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateProcessor implements JsonValueProcessor {
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private DateFormat dateFormat;

	public JsonDateProcessor() {
		dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
	}

	/**
	 * 构造方法.
	 * 
	 * @param datePattern 日期格式
	 */
	public JsonDateProcessor(String datePattern) {
		try {
			dateFormat = new SimpleDateFormat(datePattern);
		} catch (Exception ex) {
			dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		}
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		return process(value);
	}

	private Object process(Object value) {
		try {
			return dateFormat.format((Date) value);
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 备注:
	 * 使用JsonConfig转换时如果日期为空，则抛出空指针。问题原因及解决方案
	 */
	/**
	 Json转Java的时候，调用ezmorph-1.0.6.jar中的DateMorpher类有问题:如果date的格式错误或者为空（总之就是转换错误）的时候，没有对错误处理。
	 net.sf.ezmorph.object.DateMorpher中源码如下：
		try{
			return dateParser.parse( strValue.toLowerCase() );  
		} catch( ParseException pe ){   
			// ignore exception, try the next format   
		}   
 	将其改为
		try{
			return dateParser.parse( strValue.toLowerCase() );  
		} catch( ParseException pe ){   
			return null;  
		}
	重新编译ezmorph-1.0.6.jar
	*/

}