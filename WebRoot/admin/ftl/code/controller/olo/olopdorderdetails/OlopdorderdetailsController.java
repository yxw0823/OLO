package com.fh.controller.olo.olopdorderdetails;

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
import com.fh.service.olo.olopdorderdetails.OlopdorderdetailsService;

/** 
 * 类名称：OlopdorderdetailsController
 * 创建人：FH 
 * 创建时间：2018-03-21
 */
@Controller
@RequestMapping(value="/olopdorderdetails")
public class OlopdorderdetailsController extends BaseController {
	
	String menuUrl = "olopdorderdetails/list.do"; //菜单地址(权限用)
	@Resource(name="olopdorderdetailsService")
	private OlopdorderdetailsService olopdorderdetailsService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Olopdorderdetails");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ID", this.get32UUID());	//主键
		olopdorderdetailsService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Olopdorderdetails");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			olopdorderdetailsService.delete(pd);
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
		logBefore(logger, "修改Olopdorderdetails");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		olopdorderdetailsService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Olopdorderdetails");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = olopdorderdetailsService.list(page);	//列出Olopdorderdetails列表
			mv.setViewName("olo/olopdorderdetails/olopdorderdetails_list");
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
		logBefore(logger, "去新增Olopdorderdetails页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("olo/olopdorderdetails/olopdorderdetails_edit");
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
		logBefore(logger, "去修改Olopdorderdetails页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = olopdorderdetailsService.findById(pd);	//根据ID读取
			mv.setViewName("olo/olopdorderdetails/olopdorderdetails_edit");
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
		logBefore(logger, "批量删除Olopdorderdetails");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				olopdorderdetailsService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Olopdorderdetails到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("订单ID");	//1
			titles.add("人员ID");	//2
			titles.add("物品ID");	//3
			titles.add("商品编码");	//4
			titles.add("商品名称");	//5
			titles.add("数量");	//6
			titles.add("价格");	//7
			titles.add("创建时间");	//8
			titles.add("创建人ID");	//9
			titles.add("更新时间");	//10
			titles.add("更新人ID");	//11
			titles.add("Spread1");	//12
			titles.add("Spread2");	//13
			titles.add("Spread3");	//14
			titles.add("Spread4");	//15
			titles.add("Spread5");	//16
			dataMap.put("titles", titles);
			List<PageData> varOList = olopdorderdetailsService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("ORDER_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("USER_ID"));	//2
				vpd.put("var3", varOList.get(i).getString("SKU_ID"));	//3
				vpd.put("var4", varOList.get(i).getString("CODE"));	//4
				vpd.put("var5", varOList.get(i).getString("TITLE"));	//5
				vpd.put("var6", varOList.get(i).getString("NUMBER"));	//6
				vpd.put("var7", varOList.get(i).getString("PRICE"));	//7
				vpd.put("var8", varOList.get(i).getString("CREATE_TIME"));	//8
				vpd.put("var9", varOList.get(i).getString("CREATION_PEOPLE_ID"));	//9
				vpd.put("var10", varOList.get(i).getString("UPDATE_TIME"));	//10
				vpd.put("var11", varOList.get(i).getString("UPDATE_PEOPLE_ID"));	//11
				vpd.put("var12", varOList.get(i).getString("SPREAD1"));	//12
				vpd.put("var13", varOList.get(i).getString("SPREAD2"));	//13
				vpd.put("var14", varOList.get(i).getString("SPREAD3"));	//14
				vpd.put("var15", varOList.get(i).getString("SPREAD4"));	//15
				vpd.put("var16", varOList.get(i).getString("SPREAD5"));	//16
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
