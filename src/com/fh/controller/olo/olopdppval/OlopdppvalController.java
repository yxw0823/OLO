package com.fh.controller.olo.olopdppval;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.olopdabpprelation.olopdabpprelation.OlopdabpprelationService;
import com.fh.service.olopdabval.olopdabval.OlopdabvalService;
import com.fh.service.olopdppval.olopdppval.OlopdppvalService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtils;

/** 
 * 类名称：OlopdppvalController  属性
 * 创建人：FH 
 * 创建时间：2017-12-06
 */
@Controller
@RequestMapping(value="/olopdppval")
public class OlopdppvalController extends BaseController {
	
	String menuUrl = "olopdppval/list.do"; //菜单地址(权限用)
	@Resource(name="olopdppvalService")
	private OlopdppvalService olopdppvalService;
	@Resource(name="olopdabpprelationService")
	private OlopdabpprelationService olopdabpprelationService;
	@Resource(name="olopdabvalService")
    private OlopdabvalService olopdabvalService;
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	@Transactional
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Olopdppval");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String aNameString = pd.getString("A_NAME");
		pd.put("PROPERTY_ID", this.get32UUID());	//主键
		pd.put("INSERT_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));//插入时间
		pd=olopdppvalService.save(pd);
		
		PageData adpdrelation = new PageData();
		String aIdString = pd.getString("A_ID");
		if(StringUtils.isEmpty(aIdString)){
		    aIdString =this.get32UUID() ;
		    
		    PageData adpd = new PageData();
		    adpd.put("A_ID", aIdString);    //主键
		    adpd.put("INSERT_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));//插入时间
		    adpd.put("A_NAME", aNameString);    //特性名称
		    adpd.put("A_CONTENT", aNameString);    //特性名称
		    adpd.put("SEALED", "0"); // 默认不封存
		    adpd =  olopdabvalService.save(adpd);
		    aIdString =adpd.getString("A_ID");
		}
		adpdrelation.put("ID", this.get32UUID());    //主键
		
		adpdrelation.put("O_ID1", aIdString);    //主键
		adpdrelation.put("O_ID2", pd.getString("PROPERTY_ID"));    //主键
		adpdrelation.put("TYPE", "0");    // 默认不封存
		olopdabpprelationService.save(adpdrelation);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
     * 新增
     */
    @RequestMapping(value="/saveOther")
    @ResponseBody
    public Object saveOther() throws Exception{
        logBefore(logger, "新增Olopdppval");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String aNameString = pd.getString("A_NAME");
        pd.put("PROPERTY_ID", this.get32UUID());    //主键
        pd.put("INSERT_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));//插入时间
        pd=olopdppvalService.save(pd);
        
        PageData adpdrelation = new PageData();
        String aIdString = pd.getString("A_ID");
        if(StringUtils.isEmpty(aIdString)){
            aIdString =this.get32UUID() ;
            PageData adpd = new PageData();
            adpd.put("A_ID", aIdString);    //主键
            adpd.put("INSERT_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));//插入时间
            adpd.put("A_NAME", aNameString);    //特性名称
            adpd.put("A_CONTENT", aNameString);    //特性名称
            adpd.put("SEALED", "0"); // 默认不封存
            adpd =  olopdabvalService.save(adpd);
            aIdString =adpd.getString("A_ID");
        }
        adpdrelation.put("ID", this.get32UUID());    //主键
        
        adpdrelation.put("O_ID1", aIdString);    //主键
        adpdrelation.put("O_ID2", pd.getString("PROPERTY_ID"));    //主键
        adpdrelation.put("TYPE", "0");    // 默认不封存
        olopdabpprelationService.save(adpdrelation);
        return AppUtil.returnObject(pd, adpdrelation);
    }
    
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Olopdppval");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			olopdppvalService.delete(pd);
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
		logBefore(logger, "修改Olopdppval");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		olopdppvalService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Olopdppval");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = olopdppvalService.list(page);	//列出Olopdppval列表
			mv.setViewName("olopdppval/olopdppval/olopdppval_list");
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
		logBefore(logger, "去新增Olopdppval页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("olopdppval/olopdppval/olopdppval_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	/**
     * 去商品页面直接新增页面
     */
    @RequestMapping(value="/goOtherAdd")
    public ModelAndView goOtherAdd(){
        logBefore(logger, "去新增Olopdppval页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("olopdppval/olopdppval/olopdppvalOther_edit");
            mv.addObject("msg", "saveOther");
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
		logBefore(logger, "去修改Olopdppval页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = olopdppvalService.findById(pd);	//根据ID读取
			mv.setViewName("olopdppval/olopdppval/olopdppval_edit");
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
		logBefore(logger, "批量删除Olopdppval");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				olopdppvalService.deleteAll(ArrayDATA_IDS);
				olopdabpprelationService.deleteAllByOID2(ArrayDATA_IDS);//删除关系
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
	   
    /**
     * 通过AID获取全部Olopdppval
     */
    @RequestMapping(value="/listAllByAID")
    @ResponseBody
    public Object listAllByAID() {
        logBefore(logger, "通过AID获取全部Olopdppval");
        PageData pd = new PageData();       
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String O_ID1 = pd.getString("O_ID1");
            if(null != O_ID1 && !"".equals(O_ID1)){
                map.put("listAll",  olopdppvalService.listAllByAID(pd));
                map.put("msg", "ok");
            }else{
                map.put("msg", "no");
            }
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
		logBefore(logger, "导出Olopdppval到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("属性值");	//1
			titles.add("备注");	//2
			titles.add("插入时间");	//3
			titles.add("封存商品 默认 0  不封存 1 封存");	//4
			dataMap.put("titles", titles);
			List<PageData> varOList = olopdppvalService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("PROPERTY_NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("PROPERTY_CONTENT"));	//2
				vpd.put("var3", varOList.get(i).getString("INSERT_TIME"));	//3
				vpd.put("var4", varOList.get(i).getString("SEALED"));	//4
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
