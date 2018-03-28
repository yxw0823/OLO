package com.fh.controller.olo.olopdshoppingcart;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.Jurisdiction;
import com.fh.service.olo.olopdshoppingcart.OlopdshoppingcartService;

/** 
 * 类名称：OlopdshoppingcartController
 * 创建人：FH 
 * 创建时间：2018-03-21
 */
@Controller
@RequestMapping(value="/olopdshoppingcart")
public class OlopdshoppingcartController extends BaseController {
	
	String menuUrl = "olopdshoppingcart/list.do"; //菜单地址(权限用)
	@Resource(name="olopdshoppingcartService")
	private OlopdshoppingcartService olopdshoppingcartService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Olopdshoppingcart");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CART_ID", this.get32UUID());	//主键
		olopdshoppingcartService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Olopdshoppingcart");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			olopdshoppingcartService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Olopdshoppingcart");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		olopdshoppingcartService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Olopdshoppingcart");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = olopdshoppingcartService.list(page);	//列出Olopdshoppingcart列表
			mv.setViewName("olo/olopdshoppingcart/olopdshoppingcart_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Olopdshoppingcart页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("olo/olopdshoppingcart/olopdshoppingcart_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Olopdshoppingcart页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = olopdshoppingcartService.findById(pd);	//根据ID读取
			mv.setViewName("olo/olopdshoppingcart/olopdshoppingcart_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Olopdshoppingcart");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				olopdshoppingcartService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Olopdshoppingcart到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("人员ID");	//1
			titles.add("物品ID");	//2
			titles.add("商品编码");	//3
			titles.add("商品名称");	//4
			titles.add("数量");	//5
			titles.add("价格");	//6
			titles.add("创建时间");	//7
			titles.add("创建人ID");	//8
			titles.add("更新时间");	//9
			titles.add("更新人ID");	//10
			titles.add("Spread1");	//11
			titles.add("Spread2");	//12
			titles.add("Spread3");	//13
			titles.add("Spread4");	//14
			titles.add("Spread5");	//15
			dataMap.put("titles", titles);
			List<PageData> varOList = olopdshoppingcartService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("USER_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("SKU_ID"));	//2
				vpd.put("var3", varOList.get(i).getString("CODE"));	//3
				vpd.put("var4", varOList.get(i).getString("TITLE"));	//4
				vpd.put("var5", varOList.get(i).getString("NUMBER"));	//5
				vpd.put("var6", varOList.get(i).getString("PRICE"));	//6
				vpd.put("var7", varOList.get(i).getString("CREATE_TIME"));	//7
				vpd.put("var8", varOList.get(i).getString("CREATION_PEOPLE_ID"));	//8
				vpd.put("var9", varOList.get(i).getString("UPDATE_TIME"));	//9
				vpd.put("var10", varOList.get(i).getString("UPDATE_PEOPLE_ID"));	//10
				vpd.put("var11", varOList.get(i).getString("SPREAD1"));	//11
				vpd.put("var12", varOList.get(i).getString("SPREAD2"));	//12
				vpd.put("var13", varOList.get(i).getString("SPREAD3"));	//13
				vpd.put("var14", varOList.get(i).getString("SPREAD4"));	//14
				vpd.put("var15", varOList.get(i).getString("SPREAD5"));	//15
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
