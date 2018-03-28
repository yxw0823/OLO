package com.fh.controller.olo.olopdsku;

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
import com.fh.service.olo.olopdsku.OlopdskuService;

/** 
 * 类名称：OlopdskuController
 * 创建人：FH 
 * 创建时间：2018-03-10
 */
@Controller
@RequestMapping(value="/olopdsku")
public class OlopdskuController extends BaseController {
	
	String menuUrl = "olopdsku/list.do"; //菜单地址(权限用)
	@Resource(name="olopdskuService")
	private OlopdskuService olopdskuService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Olopdsku");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SKU_ID", this.get32UUID());	//主键
		olopdskuService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Olopdsku");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			olopdskuService.delete(pd);
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
		logBefore(logger, "修改Olopdsku");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		olopdskuService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Olopdsku");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = olopdskuService.list(page);	//列出Olopdsku列表
			mv.setViewName("olo/olopdsku/olopdsku_list");
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
		logBefore(logger, "去新增Olopdsku页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("olo/olopdsku/olopdsku_edit");
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
		logBefore(logger, "去修改Olopdsku页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = olopdskuService.findById(pd);	//根据ID读取
			mv.setViewName("olo/olopdsku/olopdsku_edit");
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
		logBefore(logger, "批量删除Olopdsku");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				olopdskuService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Olopdsku到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("货物ID");	//1
			titles.add("特性ID（颜色，长度等）");	//2
			titles.add("规格方便查询");	//3
			titles.add("价格");	//4
			titles.add("二维码");	//5
			titles.add("产品代号");	//6
			titles.add("状态");	//7
			titles.add("库存");	//8
			titles.add("封存商品 默认 0  不封存 1 封存");	//9
			titles.add("创建时间");	//10
			titles.add("创建人ID");	//11
			titles.add("更新时间");	//12
			titles.add("更新人ID");	//13
			titles.add("Spread1");	//14
			titles.add("Spread2");	//15
			titles.add("Spread3");	//16
			titles.add("Spread4");	//17
			titles.add("Spread5");	//18
			dataMap.put("titles", titles);
			List<PageData> varOList = olopdskuService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("GOODS_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("A_ID"));	//2
				vpd.put("var3", varOList.get(i).getString("SPECIFICATIONS"));	//3
				vpd.put("var4", varOList.get(i).getString("PRICE"));	//4
				vpd.put("var5", varOList.get(i).getString("BARCODE"));	//5
				vpd.put("var6", varOList.get(i).getString("PRODUCT_CODE"));	//6
				vpd.put("var7", varOList.get(i).getString("STATE"));	//7
				vpd.put("var8", varOList.get(i).getString("STORE"));	//8
				vpd.put("var9", varOList.get(i).getString("SEALED"));	//9
				vpd.put("var10", varOList.get(i).getString("CREATE_TIME"));	//10
				vpd.put("var11", varOList.get(i).getString("CREATION_PEOPLE_ID"));	//11
				vpd.put("var12", varOList.get(i).getString("UPDATE_TIME"));	//12
				vpd.put("var13", varOList.get(i).getString("UPDATE_PEOPLE_ID"));	//13
				vpd.put("var14", varOList.get(i).getString("SPREAD1"));	//14
				vpd.put("var15", varOList.get(i).getString("SPREAD2"));	//15
				vpd.put("var16", varOList.get(i).getString("SPREAD3"));	//16
				vpd.put("var17", varOList.get(i).getString("SPREAD4"));	//17
				vpd.put("var18", varOList.get(i).getString("SPREAD5"));	//18
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
