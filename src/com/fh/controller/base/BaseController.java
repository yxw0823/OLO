package com.fh.controller.base;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fh.entity.Page;
import com.fh.util.JsonUtils;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		/*String enctype = request.getContentType();  
	        if (!StringUtils.isEmpty(enctype) && enctype.contains("multipart/form-data")){ 
	            ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;  
	            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();  
	            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());  
	            // 返回 MultipartHttpServletRequest 用于获取 multipart/form-data 方式提交的请求中 上传的参数  
	            return multipartRequest;  
	        }else{   
	            return request;
	        }*/
	        return request;
	}
	/**
     * 得到request对象
     */
    public HttpServletResponse getResponse() {
        
        HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }
	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		
		return UuidUtil.get32UUID();
	}
	
	/**
	 * 得到分页列表的信息 
	 */
	public Page getPage(){
		
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	

    /**
     * 向客户端输出JSONObject
     * 
     * @param response
     * @param object
     */
    public void responseJson(HttpServletResponse response, Object object)
    {
        String jsonStr = JSONObject.fromObject(object).toString();
        try
        {
            response.setContentType("text/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonStr);
            response.getWriter().flush();
            response.getWriter().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 向客户端输出JSONArray
     * 
     * @param response
     * @param object
     */
    public void responseJsonArray(HttpServletResponse response, Object object)
    {
        String jsonStr = JSONArray.fromObject(object).toString();
        try
        {
            response.setContentType("text/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonStr);
            response.getWriter().flush();
            response.getWriter().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 向客户端输出日期格式化后的JSONObject
     * 
     * @param response
     * @param object
     *            被转化的JAVA对象
     * @param dataFormat
     *            日期格式
     */
    public void responseJsonDateFormate(HttpServletResponse response, Object object, String dataFormat)
    {
        String jsonStr = JsonUtils.getJsonObject4JavaPOJO(object, dataFormat).toString();
        try
        {
            response.setContentType("text/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonStr);
            response.getWriter().flush();
            response.getWriter().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 向客户端输出日期格式化后的JSONArray
     * 
     * @param response
     * @param object
     *            被转化的JAVA对象
     * @param dataFormat
     *            日期格式
     */
    public void responseJsonArrayDateFormate(HttpServletResponse response, Object object, String dataFormat)
    {
        String jsonStr = JsonUtils.getJsonArray4JavaPOJO(object, dataFormat).toString();
        try
        {
            response.setContentType("text/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonStr);
            response.getWriter().flush();
            response.getWriter().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 向客户端输出普通文本
     * 
     * @param response
     * @param object
     */
    public void responseText(HttpServletResponse response, String text)
    {
        try
        {
            response.setContentType("text/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);
            response.getWriter().flush();
            response.getWriter().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 向客户端输出xml格式文本
     * 
     * @param response
     * @param object
     */
    public void responseXml(HttpServletResponse response, String xml)
    {
        try
        {
            response.setContentType("text/xml");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(xml);
            response.getWriter().flush();
            response.getWriter().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 向客户端输出Html格式文本
     * 
     * @param response
     * @param object
     */
    public void responseHtml(HttpServletResponse response, String html)
    {
        try
        {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            response.getWriter().write(html);
            response.getWriter().flush();
            response.getWriter().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
	
}
