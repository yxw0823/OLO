package com.fh.controller.olo.npinformationsubject;

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
import com.fh.service.olo.npinformationsubject.NpinformationsubjectService;

/** 
 * 类名称：NpinformationsubjectController
 * 创建人：FH 
 * 创建时间：2018-03-16
 */
@Controller
@RequestMapping(value="/npinformationsubject")
public class NpinformationsubjectController extends BaseController {
	
	String menuUrl = "npinformationsubject/list.do"; //菜单地址(权限用)
	@Resource(name="npinformationsubjectService")
	private NpinformationsubjectService npinformationsubjectService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Npinformationsubject");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SUBJECT_ID", this.get32UUID());	//主键
		npinformationsubjectService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Npinformationsubject");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			npinformationsubjectService.delete(pd);
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
		logBefore(logger, "修改Npinformationsubject");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		npinformationsubjectService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Npinformationsubject");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = npinformationsubjectService.list(page);	//列出Npinformationsubject列表
			mv.setViewName("olo/npinformationsubject/npinformationsubject_list");
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
		logBefore(logger, "去新增Npinformationsubject页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("olo/npinformationsubject/npinformationsubject_edit");
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
		logBefore(logger, "去修改Npinformationsubject页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = npinformationsubjectService.findById(pd);	//根据ID读取
			mv.setViewName("olo/npinformationsubject/npinformationsubject_edit");
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
		logBefore(logger, "批量删除Npinformationsubject");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				npinformationsubjectService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Npinformationsubject到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("");	//1
			titles.add("");	//2
			titles.add("");	//3
			titles.add("");	//4
			titles.add("");	//5
			titles.add("");	//6
			titles.add("");	//7
			titles.add("");	//8
			titles.add("");	//9
			titles.add("");	//10
			titles.add("");	//11
			titles.add("");	//12
			titles.add("");	//13
			titles.add("");	//14
			titles.add("");	//15
			titles.add("");	//16
			titles.add("");	//17
			titles.add("");	//18
			titles.add("");	//19
			titles.add("");	//20
			titles.add("");	//21
			titles.add("");	//22
			dataMap.put("titles", titles);
			List<PageData> varOList = npinformationsubjectService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("TITLE"));	//1
				vpd.put("var2", varOList.get(i).getString("AUTHOR"));	//2
				vpd.put("var3", varOList.get(i).getString("URL"));	//3
				vpd.put("var4", varOList.get(i).getString("BACKGROUND_IMG"));	//4
				vpd.put("var5", varOList.get(i).getString("CONTENT"));	//5
				vpd.put("var6", varOList.get(i).getString("SEO_KEYWORD"));	//6
				vpd.put("var7", varOList.get(i).getString("SEO_DESC"));	//7
				vpd.put("var8", varOList.get(i).getString("IS_SHOW"));	//8
				vpd.put("var9", varOList.get(i).getString("DELFLAG"));	//9
				vpd.put("var10", varOList.get(i).getString("CREATE_USER_ID"));	//10
				vpd.put("var11", varOList.get(i).getString("CREATE_DATE"));	//11
				vpd.put("var12", varOList.get(i).getString("UPDATE_USER_ID"));	//12
				vpd.put("var13", varOList.get(i).getString("UPDATE_DATE"));	//13
				vpd.put("var14", varOList.get(i).getString("TEMP1"));	//14
				vpd.put("var15", varOList.get(i).getString("TEMP2"));	//15
				vpd.put("var16", varOList.get(i).getString("TEMP3"));	//16
				vpd.put("var17", varOList.get(i).getString("TEMP4"));	//17
				vpd.put("var18", varOList.get(i).getString("TEMP5"));	//18
				vpd.put("var19", varOList.get(i).getString("MOBILE_CONTENT"));	//19
				vpd.put("var20", varOList.get(i).getString("MOBILE_SORT"));	//20
				vpd.put("var21", varOList.get(i).getString("MOBILE_TITLE_IMG"));	//21
				vpd.put("var22", varOList.get(i).getString("RELEASE_TIME"));	//22
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
