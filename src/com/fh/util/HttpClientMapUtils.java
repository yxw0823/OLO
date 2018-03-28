package com.fh.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unchecked")
public class HttpClientMapUtils
{
	public static final String METHOD_POST_STRING = "POST";
	public static final String METHOD_GET_STRING = "GET";
	public static final String METHOD_PUT_STRING = "PUT";
	public static final String METHOD_DELETE_STRING = "DELETE";
	public static final String CONTENT_TYPE_TEXT_XML = "text/xml;charset=UTF-8";
	public static final String CONTENT_TYPE_TEXT_JSON = "application/json;charset=UTF-8";
	public static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=UTF-8";
	public static final String CONTENT_TYPE_TEXT_FORM = "application/x-www-form-urlencoded;charset=UTF-8";

	/***************************************************************************
	 * @param requestURL
	 *            http请求地址
	 * @param parameters
	 *            发送的参数
	 * @param contentType
	 *            消息内容类型
	 * @param method
	 *            请求方式
	 * @throws Exception
	 **************************************************************************/
	public static String sendRequest(String requestURL, String parameters, String contentType, String method)
		throws Exception
	{
		URL url = new URL(requestURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestProperty("Content-Length", String.valueOf(parameters.length()));
		if (StringUtils.isEmpty(contentType))
		{
			contentType = HttpClientMapUtils.CONTENT_TYPE_TEXT_FORM;
		}
		httpConn.setRequestProperty("Content-Type", contentType);
		httpConn.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
		httpConn.setRequestMethod(method);
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		httpConn.setUseCaches(false);
		 if(!"GET".equals(method)){
		     OutputStream o = httpConn.getOutputStream();
	         o.write(parameters.getBytes());
	         o.flush();
	         o.close();
         }
	
		int code = httpConn.getResponseCode();
		InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), "utf-8");
		BufferedReader bufr = new BufferedReader(isr);// 缓冲
		// BufferedReader bufr = new BufferedReader(new
		// InputStreamReader(new
		// FileInputStream("D:\\demo.txt")));可以综合到一句。
		/*
		 * int ch =0; ch = bufr.read(); System.out.println((char)ch);
		 */
		String line = null;
		StringBuffer content = new StringBuffer();
		while ((line = bufr.readLine()) != null)
		{
			content.append(line);
		}
		// System.out.println(content.toString());
		// HttpServletResponse response = getResponse();
		return content.toString();
	}
	/***************************************************************************
	 * @param requestURL
	 *            http请求地址
	 * @param parameters
	 *            发送的参数
	 * @param contentType
	 *            消息内容类型
	 * @param method
	 *            请求方式
	 * @throws Exception
	 **************************************************************************/
	public static byte[] sendRequestGetByte(String requestURL, String parameters, String contentType, String method)
		throws Exception
	{
		URL url = new URL(requestURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestProperty("Content-Length", String.valueOf(parameters.length()));
		if (StringUtils.isEmpty(contentType))
		{
			contentType = HttpClientMapUtils.CONTENT_TYPE_TEXT_FORM;
		}
		httpConn.setRequestProperty("Content-Type", contentType);
		httpConn.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
		httpConn.setRequestMethod(method);
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		httpConn.setUseCaches(false);
		OutputStream o = httpConn.getOutputStream();
		o.write(parameters.getBytes());
		o.flush();
		o.close();
		int code = httpConn.getResponseCode();
		BufferedInputStream bufr = new BufferedInputStream(httpConn.getInputStream());// 缓冲
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final int BUFFER_SIZE =2048;
		final int EOF = -1;
		
		int c;
		byte[] buf = new byte[BUFFER_SIZE] ;
		while(true){
			
			c= bufr.read(buf);
			if(c ==EOF){
				break;
			}
			baos.write(buf, 0, c);
		}
		httpConn.disconnect();
		byte[] data = baos.toByteArray();
		// System.out.println(content.toString());
		// HttpServletResponse response = getResponse();
		return data;
	}
	/***************************************************************************
	 * @param requestURL
	 *            http请求地址
	 * @param parameters
	 *            发送的参数
	 * @param contentType
	 *            消息内容类型
	 * @param method
	 *            请求方式
	 * @throws Exception
	 **************************************************************************/
	public static String sendJsonRequest(String requestURL, String parameters, String contentType, String method)
		throws Exception
	{
		URL url = new URL(requestURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod(method);
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		httpConn.setUseCaches(false);
		httpConn.setRequestProperty("Content-Type", contentType);
		httpConn.setRequestProperty("Charset", "UTF-8");
		httpConn.setRequestProperty("Accept", "application/json");
		httpConn.setRequestProperty("Connection", "Keep-Alive");
		httpConn.setRequestProperty("Content-Length",parameters.getBytes().length+"");
		if(method != HttpClientMapUtils.METHOD_GET_STRING&& !StringUtils.isEmpty(method)){
			OutputStream o = httpConn.getOutputStream();
			o.write(parameters.getBytes());
			o.flush();
			o.close(); 
		}
		
		int resCode =httpConn.getResponseCode() ;
		if(resCode ==200){
			InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), "utf-8");
			BufferedReader bufr = new BufferedReader(isr);// 缓冲
			String line = null;
			StringBuffer content = new StringBuffer();
			while ((line = bufr.readLine()) != null)
			{
				content.append(line);
			}
			// System.out.println(content.toString());
			// HttpServletResponse response = getResponse();
			return content.toString();
		}
		return resCode+"";
	}
	

}
