package com.fh.controller.olo.olowebInterface.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fh.cache.Cache;
import com.fh.cache.CacheManager;
import com.fh.controller.base.BaseController;
import com.fh.controller.olo.olowebInterface.exception.NoUserExcetion;
import com.fh.controller.olo.olowebInterface.exception.SignException;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.MD5Util;
import com.fh.util.PageData;
import com.fh.util.StringUtils;

@Controller
@RequestMapping(value = "/appssotoken")
public class SSOToken  extends BaseController{
	public boolean check() throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String callback = pd.getString("callback");
		String _ = pd.getString("_");
		pd.remove("callback");
		pd.remove("_");
		String requestSign = pd.getString("sing");
		pd.remove("sing");
		TreeMap treemap = new TreeMap(pd);
		net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(treemap);
		String result = "00";
		String token = pd.getString("token");
		Cache cache = CacheManager.getCacheInfo(token);
		if (StringUtils.isEmpty(cache))
		{
			if (!StringUtils.isEmpty(callback))
			{
				pd.put("callback", callback);
			}
			map.put("result", result);
			map.put("msg", "没有相关用户信息!");

			throw new NoUserExcetion("没有相关用户信息!");
		}
		Object o = cache.getValue();
		if (o instanceof String)
		{
			if (!StringUtils.isEmpty(callback))
			{
				pd.put("callback", callback);
			}
			map.put("result", result);
			map.put("msg", "没有相关用户信息!");
			throw new NoUserExcetion("没有相关用户信息!");
		}
		if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") && false)
		{
			throw new SignException("签名错误");
		}
		return true;

	}

	
	@RequestMapping(value = "/checkuser", method = RequestMethod.POST)
	public void checkUser(HttpServletResponse response)
	{
		logBefore(logger, "");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "00";
		try
		{
			check();
			String token = pd.getString("token");
			Cache cache = CacheManager.getCacheInfo(token);
			Object o = cache.getValue();
			PageData pdOloUser = (PageData) o;
			  map.put("LOGINID", pdOloUser.getString("LOGINID"));
              map.put("JZ", pdOloUser.getString("JZ"));
              map.put("JXSPOST", pdOloUser.getString("JXSPOST"));
              map.put("ROLE", pdOloUser.getString("ROLE"));
              map.put("SUBCOMPANYID1", pdOloUser.getString("SUBCOMPANYID1"));
              map.put("ISINVESTOR", pdOloUser.getString("ISINVESTOR"));
			this.responseJson(response, AppUtil.returnObject(pd, map));
			return;
		}
		catch (NoUserExcetion e)
		{
			map.put("result", result);
			map.put("msg", "没有相关用户信息!");
			this.responseJson(response, AppUtil.returnObject(pd, map));
		}
		catch (SignException e)
		{
			// TODO Auto-generated catch block
			map.put("result", result);
			map.put("msg", "参数签没错误");
			this.responseJson(response, AppUtil.returnObject(pd, map));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			map.put("result", result);
			map.put("msg", "未知错误" + e.getMessage());
			this.responseJson(response, AppUtil.returnObject(pd, map));
		}
		this.responseJson(response, AppUtil.returnObject(pd, map));
		return;
	}
}
