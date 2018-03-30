package com.fh.controller.olo.olowebInterface.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fh.cache.Cache;
import com.fh.cache.CacheManager;
import com.fh.controller.base.BaseController;
import com.fh.controller.olo.olopdorder.e.OloOrderState;
import com.fh.controller.olo.olowebInterface.IOrderInterface;
import com.fh.controller.olo.olowebInterface.exception.NoUserExcetion;
import com.fh.controller.olo.olowebInterface.exception.SignException;
import com.fh.entity.Page;
import com.fh.service.olo.olopdorder.OlopdorderService;
import com.fh.service.olo.olopdorderdetails.OlopdorderdetailsService;
import com.fh.service.olo.olopdshoppingcart.OlopdshoppingcartService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.MD5Util;
import com.fh.util.PageData;
import com.fh.util.StringUtils;
import com.fh.util.UuidUtil;
import com.fh.util.Xml2JsonUtil;

import localhost.services.Services2020.Services2020PortTypeProxy;
import net.sf.json.JSON;

@Controller
@RequestMapping(value = "/apporder")
public class OrderInterface extends BaseController implements IOrderInterface
{
	@Resource(name = "olopdshoppingcartService")
	private OlopdshoppingcartService olopdshoppingcartService; // 购物车

	@Resource(name = "olopdorderService")
	private OlopdorderService olopdorderService; // 订单

	@Resource(name = "olopdorderdetailsService")
	private OlopdorderdetailsService olopdorderdetailsService; // 订单 详情

	Services2020PortTypeProxy proxy = new Services2020PortTypeProxy();

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
			pdOloUser.getString("LOGINID");
			pdOloUser.getString("PASSWORD");
			String cont = proxy.checkUser(pdOloUser.getString("LOGINID"), pdOloUser.getString("PASSWORD"));

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

	/**
	 * 获取当前人员 购物车列表
	 */
	@RequestMapping(value = "/shoppingCartList", method = RequestMethod.POST)
	@Override
	public void shoppingCartList(Page page, HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		logBefore(logger, "获取我的购物车");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "00";
		try
		{
			check();
			result = "01";
			String token = pd.getString("token");
			Cache cache = CacheManager.getCacheInfo(token);
			Object o = cache.getValue();
			PageData pdOloUser = (PageData) o;
			/*
			 * pdOloUser.getString("LOGINID"); pdOloUser.getString("PASSWORD");
			 */
			if (StringUtils.isEmpty(page.getSort()) || page.getSort().size() == 0)
			{
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("name", "CREATE_TIME");
				map1.put("sortStr", "desc");
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				list.add(map1);
				page.addListSort(list);
			}
			pd.put("USER_ID", pdOloUser.getString("LOGINID"));
			page.setPd(pd);

			map.put("data", olopdshoppingcartService.list(page));
			map.put("result", result);
			map.put("page", page);

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

	}

	@RequestMapping(value = "/addShoppingCart", method = RequestMethod.POST)
	@Override
	public void addShoppingCart(HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		logBefore(logger, "新增购物车");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "00";
		try
		{
			check();
			result = "01";
			String token = pd.getString("token");
			Cache cache = CacheManager.getCacheInfo(token);
			Object o = cache.getValue();
			PageData pdOloUser = (PageData) o;
			String CART_ID = this.get32UUID();
			pd.put("CART_ID", CART_ID); // 主键
			pd.put("USER_ID", pdOloUser.getString("LOGINID"));
			pd.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
			pd.put("CREATION_PEOPLE_ID", pdOloUser.getString("LOGINID"));
			olopdshoppingcartService.save(pd);
			map.put("result", result);
			map.put("data", "true");
			map.put("pd", pd);
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
	}

	@RequestMapping(value = "/editShoppingCart", method = RequestMethod.POST)
	@Override
	public void editShoppingCart(HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		logBefore(logger, "更新购物车");
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
			if (StringUtils.isEmpty(pd.getString("CART_ID")))
			{
				map.put("result", result);
				map.put("msg", "更新购物车是CART_ID为必传字段");
				this.responseJson(response, AppUtil.returnObject(pd, map));
				return;
			}
			result = "01";
			pd.put("UPDATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
			pd.put("UPDATE_PEOPLE_ID", pdOloUser.getString("LOGINID"));
			olopdshoppingcartService.edit(pd);
			map.put("result", result);
			map.put("data", "true");
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
	}

	@RequestMapping(value = "/delShoppingCart", method = RequestMethod.POST)
	@Override
	public void delShoppingCart(HttpServletResponse response)
	{

		// TODO Auto-generated method stub
		logBefore(logger, "删除购物车");
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
			String DATA_IDS = pd.getString("DATA_IDS");
			if (StringUtils.isEmpty(pd.getString("DATA_IDS")))
			{
				map.put("result", result);
				map.put("msg", "删除购物车DATA_IDS为必传字段 DATA_IDS是多个CART_ID以逗号分隔");
				this.responseJson(response, AppUtil.returnObject(pd, map));
				return;
			}
			result = "01";
			map.put("result", result);
			// TODO Auto-generated method stub
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			olopdshoppingcartService.deleteAll(ArrayDATA_IDS);
			map.put("data", "ok");
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

	}
	
	
	@RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
	@Override
	public void getOrderList(Page page,HttpServletResponse response)
	{

		// TODO Auto-generated method stub
		logBefore(logger, "获取订单list");
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
			if (StringUtils.isEmpty(page.getSort()) || page.getSort().size() == 0)
			{
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("name", "CREATE_TIME");
				map1.put("sortStr", "desc");
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				list.add(map1);
				page.addListSort(list);
			}
			result = "01";
			map.put("result", result);
			page.setPd(pd);
			pd.put("USER_ID", pdOloUser.getString("LOGINID"));
			// TODO Auto-generated method stub
			List<PageData>  orderList= olopdorderService.list(page);
		
			PageData orderDet = new PageData();
			orderDet.put("list", orderList);
			if(orderList!=null &&orderList.size() >0) {
			List<PageData> orderDetList =  olopdorderdetailsService.listAll(orderDet);
			for(PageData  orderPd:orderList){
				orderPd.put("children", new ArrayList<PageData>());
				for(PageData  orderDetPd: orderDetList){
					if(orderDetPd.getString("ORDER_ID").equals(orderPd.getString("ORDER_ID"))){
						((ArrayList<PageData>) orderPd.get("children")).add(orderDetPd);
					}
				}
			}
			}
			
			map.put("data", orderList);
			map.put("page", page);
			map.put("countOrder",olopdorderService.countOrder(pd));
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

	}
	@RequestMapping(value = "/getCustomerInfo")
	@Override
	public void getCustomerInfo(HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		logBefore(logger, "获取经销商名下对应手机号的终端顾客信息");
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
			String gkmobile = pd.getString("gkmobile");
			if (StringUtils.isEmpty(gkmobile))
			{
				map.put("result", result);
				map.put("msg", "入参：顾客手机号-gkmobile 必传");
				this.responseJson(response, AppUtil.returnObject(pd, map));
				return;
			}
			result = "01";
			// TODO Auto-generated method stub
			map.put("result", result);
			String xml = proxy.getCustomerInfo(gkmobile, pdOloUser.getString("LOGINID"));
			map.put("data", Xml2JsonUtil.xml2Json(xml).toString());
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
	}

	@RequestMapping(value = "/addIntention" , method = RequestMethod.POST)
	@Override
	public void addIntention(HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		logBefore(logger, "新建客户商机");
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
			String NAME = pd.getString("NAME");
			String TEL = pd.getString("TEL");
			String OPPNAME = pd.getString("OPPNAME");
			String OPPADDR = pd.getString("OPPADDR");
			if (StringUtils.isEmpty(NAME))
			{
				map.put("result", result);
				map.put("msg", "入参：顾客姓名-NAME 必传");
				this.responseJson(response, AppUtil.returnObject(pd, map));
				return;
			}
			if (StringUtils.isEmpty(OPPADDR))
			{
				map.put("result", result);
				map.put("msg", "入参： 商机地址-OPPADDR 必传");
				this.responseJson(response, AppUtil.returnObject(pd, map));
				return;
			}
			if (StringUtils.isEmpty(TEL))
			{
				map.put("result", result);
				map.put("msg", "入参：顾客手机号-TEL 必传");
				this.responseJson(response, AppUtil.returnObject(pd, map));
				return;
			}
			/*if (StringUtils.isEmpty(OPPNAME))
			{
				map.put("result", result);
				map.put("msg", "入参：商机名称-OPPNAME 必传");
				this.responseJson(response, AppUtil.returnObject(pd, map));
				return;
			}*/
			if(StringUtils.isEmpty(OPPNAME)) {
				OPPNAME =pdOloUser.getString("LOGINID")+"商机";
			}
			String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
			xml += "<DOCUMENT>";
			xml += "<ROOT>";
			xml += "<NAME>" + NAME + "</NAME> <!-- 顾客姓名 -->";
			xml += "<TEL>" + TEL + "</TEL>    <!-- 顾客手机号 -->";
			xml += "<LOGINID>" + pdOloUser.getString("LOGINID") + "</LOGINID> <!-- 当前操作人登录id -->";
			xml += "<OPPNAME>" + OPPNAME + "</OPPNAME> <!-- 商机名称 -->";
			xml += "<OPPADDR>" + OPPADDR + "</OPPADDR> <!-- 商机地址 -->";
			xml += "</ROOT>";
			xml += "</DOCUMENT>";
			result = "01";
			String data = proxy.addIntention(xml);
			map.put("result", result);
			map.put("data", data);
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
			e.printStackTrace();
			// TODO Auto-generated catch block
			map.put("result", result);
			map.put("msg", "未知错误" + e.getMessage());
			this.responseJson(response, AppUtil.returnObject(pd, map));
		}
	}

	@RequestMapping(value = "/createSalebill")
	@Override
	public void createSalebill(HttpServletResponse response)
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		logBefore(logger, "下单");
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String result = "00";
		try
		{
			check();
			if(StringUtils.isEmpty(pd.getString("PRICE"))){
				
				map.put("msg", "下单   PRICE 总价为必传字段 ");
				this.responseJson(response, AppUtil.returnObject(pd, map));
				return;
			}
			String token = pd.getString("token");
			Cache cache = CacheManager.getCacheInfo(token);
			Object o = cache.getValue();
			PageData pdOloUser = (PageData) o;
			map.put("result", result);
			String xdXml = "<?xml version=\"1.0\" encoding=\"GB2312\"?>";
			xdXml += "<document>";
			xdXml += "<main>";
			xdXml += "<zdr>"+pdOloUser.getString("ID")+"</zdr>";
			xdXml += "<gksjh>"+pd.getString("gksjh")+"</gksjh>";
			xdXml += "<oppid>"+pd.getString("oppid")+"</oppid> ";
			xdXml += "<orderSrc>2</orderSrc>";
			xdXml += "</main>";
			xdXml += "<detail1 />";
			xdXml += "<detail2>";
			xdXml += "DLTAIL2";
			xdXml += "</detail2>";
			xdXml += "</document>";

			String row = "<row>";
			row += "<spbh>CODE</spbh>";
			row += "<sl>SL</sl>";
			row += "<gths></gths> ";
			row += "<mbsj></mbsj>  ";
			row += "<mbhs></mbhs>";
			row += "<mbkx></mbkx>";
			row += "<kd></kd> ";
			row += "<sd></sd> ";
			row += "<gd></gd>";
			row += "<wzls></wzls>";
			row += "<fnls></fnls>";
			row += "<fnlskd></fnlskd>";
			row += "<jlpz></jlpz>";
			row += "<mx></mx>";
			row += "<fbbx></fbbx>";
			row += "<ls></ls>";
			row += "<tsgy></tsgy>";
			row += "<blhs1></blhs1>";
			row += "<dqgd></dqgd>";
			row += "<ajgmzk></ajgmzk>";
			row += "<bz></bz>";
			row += "<mxdm></mxdm>";
			row += "<ssymwl></ssymwl>";
			row += "<key></key>";
			row += "<ZStyle></ZStyle>";
			row += "</row>";

			String DATA_IDS = pd.getString("DATA_IDS");
			if (StringUtils.isEmpty(pd.getString("DATA_IDS")))
			{
				map.put("msg", "下单   DATA_IDS为必传字段 DATA_IDS是多个CART_ID以逗号分隔");
				this.responseJson(response, AppUtil.returnObject(pd, map));
				return;
			}
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			pd.put("IDS", ArrayDATA_IDS);
			// 通过购物车ID 查询出所有需要下单的商品
			List<PageData> list = olopdshoppingcartService.listAll(pd);
			// 循环生成row
			String rows = "";
			for (PageData temppd : list)
			{
				rows += row.replaceAll("CODE", temppd.getString("CODE")).replaceAll("SL", temppd.getString("NUMBER"));
			}
			
			// 组装好xml
			xdXml = xdXml.replaceAll("DLTAIL2", rows);
			System.out.println(xdXml);
			// 调用接口下单
			//String xml="<?xml version=\"1.0\" encoding=\"GB2312\"?><document><main><zdr>8608</zdr><gksjh>18061771111</gksjh><oppid>210197</oppid><orderSrc>2</orderSrc></main><detail1 /><detail2><row><spbh>2</spbh><sl>4</sl><gths></gths>    <mbsj></mbsj>  <mbhs></mbhs>   <mbkx></mbkx>   <kd></kd>    <sd></sd>    <gd></gd>   <wzls></wzls>   <fnls></fnls>   <fnlskd></fnlskd>   <jlpz></jlpz>   <mx></mx>   <fbbx></fbbx>   <ls></ls>   <tsgy></tsgy>   <blhs1></blhs1> <dqgd></dqgd>   <ajgmzk></ajgmzk>   <bz></bz><mxdm></mxdm> <ssymwl></ssymwl>   <key></key>  <ZStyle></ZStyle>   </row><row><spbh>2</spbh> <!-- 商品编号 --><sl>4</sl><gths></gths>    <mbsj></mbsj>  <mbhs></mbhs>   <mbkx></mbkx>   <kd></kd>    <sd></sd>    <gd></gd>   <wzls></wzls>   <fnls></fnls>   <fnlskd></fnlskd>   <jlpz></jlpz>   <mx></mx>   <fbbx></fbbx>   <ls></ls>   <tsgy></tsgy>   <blhs1></blhs1> <dqgd></dqgd>   <ajgmzk></ajgmzk>   <bz></bz><mxdm></mxdm> <ssymwl></ssymwl>   <key></key>  <ZStyle></ZStyle>   </row></detail2></document>";
	           
			String str = proxy.createSalebill(xdXml);
			// 获取订单号
			JSONObject json = Xml2JsonUtil.xml2Json(str);
			String billcode = "";
			if ("S".equals(json.getJSONObject("ROOT").getString("STATUS")))
			{
				billcode = json.getJSONObject("ROOT").getString("BILLCODE");
			}
			if(StringUtils.isEmpty(billcode)){
				
				map.put("msg", "下单失败，订单接口返回"+str);
				this.responseJson(response, AppUtil.returnObject(pd, map));
				return;
			}
			
			
			result ="01";
			// 写入订单表
			PageData orderPd = new PageData();
			orderPd.put("ORDER_ID", billcode);
			orderPd.put("STATE", OloOrderState.DSP.getIndex());
			orderPd.put("SEALED", 0);
			orderPd.put("USER_ID",pdOloUser.getString("LOGINID"));
			orderPd.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
			orderPd.put("CREATION_PEOPLE_ID", pdOloUser.getString("LOGINID"));
			orderPd.put("PRICE", pd.getString("PRICE"));
			olopdorderService.save(orderPd);
			// 生成订单明细
			for (PageData temppd : list)
			{
				temppd.put("ID", UuidUtil.get32UUID());
				temppd.put("ORDER_ID", billcode);
				temppd.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
				temppd.put("CREATION_PEOPLE_ID", pdOloUser.getString("LOGINID"));
				temppd.put("UPDATE_TIME", null);
				temppd.put("UPDATE_PEOPLE_ID", "");
				olopdorderdetailsService.save(temppd);
			}
		
			// 从购物车中删除已经下单的商品v
			olopdshoppingcartService.deleteAll(ArrayDATA_IDS);
			result = "01";
			// TODO Auto-generated method stub
			// olopdshoppingcartService.deleteAll(ArrayDATA_IDS);
			map.put("data", "ok");
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
			e.printStackTrace();
			// TODO Auto-generated catch block
			map.put("result", result);
			map.put("msg", "未知错误" + e.getMessage());
			this.responseJson(response, AppUtil.returnObject(pd, map));
		}
	}

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

}
