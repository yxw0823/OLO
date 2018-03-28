package com.fh.controller.olo.olopddp;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.olo.util.OlopdmenuUtils;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.olo.olopddp.OlopddpService;
import com.fh.service.olo.olopdmenudp.OlopdmenudpService;
import com.fh.service.olo.olopdmenuproduct.OlopdmenuproductService;
import com.fh.service.olopdmenu.olopdmenu.OlopdmenuService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.FileUtils;
import com.fh.util.HttpConnectionUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelRead;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/** 
 * 类名称：OlopddpController
 * 创建人：FH 
 * 创建时间：2017-12-06
 */
@Controller
@RequestMapping(value="/olopddp")
public class OlopddpController extends BaseController {
	
	String menuUrl = "olopddp/list.do"; //菜单地址(权限用)
	@Resource(name="olopddpService")
	private OlopddpService olopddpService;
	@Resource(name="olopdmenuService")
    private OlopdmenuService olopdmenuService; //分类
    
    @Resource(name="olopdmenuproductService")
    private OlopdmenuproductService olopdmenuproductService;//分类和商品关系
    
    @Resource(name="olopdmenudpService")
    private OlopdmenudpService olopdmenudpService; //分類和門板關係
    
    @Resource(name = "dictionariesService")
    private DictionariesService dictionariesService; // 数据字典
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Olopddp");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ID", this.get32UUID());	//主键
		olopddpService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Olopddp");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			olopddpService.delete(pd);
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
	public ModelAndView edit(String LABEL,@RequestParam Map<String,String> map,@RequestParam(required = false,value="file" ) MultipartFile file) throws Exception{
		logBefore(logger, "修改Olopddp");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		//pd = this.getPageData();
		pd.putAll(map);
		 String ffile = DateUtil.getDays(), fileName = "";
         String filePath = "";// 文件上传路径
         String fileNameUUid = this.get32UUID();
         // 校验权限
         if (null != file && !file.isEmpty()) {
             filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
             fileName = FileUpload.fileUp(file, filePath, fileNameUUid); // 执行上传
             //上传到远程服务器
             PageData findBmPd  = new PageData();
             findBmPd.put("BIANMA", "PATH"); //图片上传路径
             PageData bmData=dictionariesService.findBmCount(findBmPd);
             String upPath=bmData.getString("NAME");
             if(!StringUtils.isEmpty(upPath)){
                 List<String> list = new ArrayList<String>();
                 list.add(filePath+ "/" + fileName);
                 String str = HttpConnectionUtil.uploadFile(upPath,list);
                 JSONObject jsonObject = JSONObject.fromObject(str);
                 JSONArray jArray= jsonObject.optJSONArray("filePaths");
                 for(int i=0;i<jArray.size();i++){
                       String imgPath = (String)jArray.get(i);
                       pd.put("IMAGE_BUG_URL", imgPath);  
                       //路径
                 }
               
             }
         } 
     
         

         String ONE_SELECT = pd.getString("ONE_SELECT");
         String TWO_SELECT = pd.getString("TWO_SELECT");
         String THREE_SELECT = pd.getString("THREE_SELECT");
         String FOUR_SELECT = pd.getString("FOUR_SELECT");
         String CATEGORY = "";
         if (!StringUtils.isEmpty(ONE_SELECT)) {
             CATEGORY += ONE_SELECT.split(",")[0];
         }
         if (!StringUtils.isEmpty(TWO_SELECT)) {
             CATEGORY += "/" + TWO_SELECT.split(",")[0];
         }
         if (!StringUtils.isEmpty(THREE_SELECT)) {
             CATEGORY += "/" + THREE_SELECT.split(",")[0];
         }
         if (!StringUtils.isEmpty(FOUR_SELECT)) {
             CATEGORY += "/" + FOUR_SELECT.split(",")[0];
         }
      
      
		   // shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
        if (user != null) {
            if(StringUtils.isEmpty(pd.getString("EFFECTIVE_TIME"))){
                pd.put("EFFECTIVE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                 
            }else{
                pd.put("EFFECTIVE_TIME", java.sql.Timestamp.valueOf( DateUtil.getTime(DateUtil.fomatDate(pd.getString("EFFECTIVE_TIME")))));
            }
		   pd.put("UPDATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
          pd.put("UPDATE_PEOPLE_ID", user.getUSER_ID());
        }
        if(!StringUtils.isEmpty(LABEL)){
            pd.put("LABEL", LABEL);
        }
        List<PageData> listClassify= null;
        if(!StringUtils.isEmpty(ONE_SELECT)){
            pd.put("SPREAD1", CATEGORY);
            // 保存门板和分类关系
           listClassify=   generateClassify(CATEGORY, pd.getString("ID"));
        }
		olopddpService.edit(pd);
		if(!StringUtils.isEmpty(listClassify)&&!"请选择".equals(ONE_SELECT)){
		        olopdmenudpService.deleteAll(listClassify);
	           olopdmenudpService.insertByBatch(listClassify);
	    }
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Olopddp");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			if(StringUtils.isEmpty(page.getSort())|| page.getSort().size() ==0){
	              Map<String, String> map = new HashMap<String, String>();
	              map.put("name", "SORTING");
	              map.put("sortStr", "desc");  
	              List< Map<String, String> > list = new ArrayList<Map<String,String>>();
	              list.add(map);
	              page.addListSort(list);
	        }
			List<PageData>	varList = olopddpService.list(page);	//列出Olopddp列表
			mv.setViewName("olo/olopddp/olopddp_list");
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
		logBefore(logger, "去新增Olopddp页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("olo/olopddp/olopddp_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	   /**
     * 去新增页面
     */
    @RequestMapping(value="/goInportExcel")
    public ModelAndView goInportExcel(){
        logBefore(logger, "去模板Olopddp页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("olo/olopddp/olopdproduct_inport");
            mv.addObject("msg", "inportExcel");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }                       
        return mv;
    }   
    /**
     * 去新增页面
     */
    @RequestMapping(value="/goInputrtImages")
    public ModelAndView goInputrtImages(){
        logBefore(logger, "去模板Olopddp图片导入页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            PageData RXPd = new PageData();
            RXPd.put("P_BM", Const.SPMBTMBQ);
            pd.put("bq", dictionariesService.dictlist(RXPd));
            mv.setViewName("olo/olopddp/olopdproduct_inportimg");
            mv.addObject("msg", "inputrtImages");
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
		logBefore(logger, "去修改Olopddp页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = olopddpService.findById(pd);	//根据ID读取
			generateClassifys(pd.getString("SPREAD1"),pd);
			   // 查询出上传图片路径
            PageData uploudImgPd = new PageData();
            uploudImgPd.put("BIANMA", Const.UPLOUADIMGPATH);
            uploudImgPd = dictionariesService.findBmCount(uploudImgPd);
            PageData RXPd = new PageData();
            RXPd.put("P_BM", Const.SPMBTMBQ);
            pd.put("bq", dictionariesService.dictlist(RXPd));
            pd.put("imgBasePath", uploudImgPd.getString("NAME"));
            
			mv.setViewName("olo/olopddp/olopddp_edit");
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
		logBefore(logger, "批量删除Olopddp");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				olopddpService.deleteAll(ArrayDATA_IDS);
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
     * 
       
     * inputrtImages  批量导入图片   
       
     * TODO(这里描述这个方法适用条件 – 可选)    
       
     * TODO(这里描述这个方法的执行流程 – 可选)    
       
     * TODO(这里描述这个方法的使用方法 – 可选)    
       
     * TODO(这里描述这个方法的注意事项 – 可选)    
       
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    @RequestMapping(value = "/inputrtImages")
    public ModelAndView inputrtImages(@RequestParam(required = false) MultipartFile file) throws Exception {
        logBefore(logger, "从zip中导入Olopdproduct的图片");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        // shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
        String ffile = DateUtil.getDays(), fileName = "";
        PageData pd = new PageData();
        pd = this.getPageData();
        String filePath = "";// 文件上传路径
        String fileNameUUid = this.get32UUID();
        if (null != file && !file.isEmpty()) {
            filePath = PathUtil.getClasspath() + Const.FILEPATHZIPFILE + ffile; // 文件上传路径
            fileName = FileUpload.fileUp(file, filePath, fileNameUUid); // 执行上传
        } else {
            throw new Exception("上传失败");
        }
        String outputDirectory = filePath + "/" + fileNameUUid;
        FileUtils.unZip(filePath + "/" + fileName, outputDirectory);
        File f = new File(outputDirectory);
        if (!f.exists()) {
            throw new Exception("传入目录不是一个文件！");
            // return null;
        }
        List<String> imagenames = new ArrayList<String>();
        String tempfileDir = ""; // 上传图片解压路径，
        File fa[] = f.listFiles();
        String fileDir = f.getPath();
        Map<String,List<String>> uplodImgMap = new HashMap<String, List<String>>();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
                tempfileDir = fs.getPath();
                uplodImgMap.put(  fs.getName(), FileUtils.getFileName(fs.getPath()));
                imagenames.addAll(uplodImgMap.get(fs.getName()));
            }
        }

        // 查询出上传图片路径
        PageData uploudImgPd = new PageData();
        uploudImgPd.put("BIANMA", Const.UPLOUADIMG);
        uploudImgPd = dictionariesService.findBmCount(uploudImgPd);
        String uploudUrl = uploudImgPd.getString("NAME");
        if (StringUtils.isEmpty(uploudUrl)) {
            throw new Exception("请先配置上传图片远程服务器路径");
        }
     
        
        //遍历map中的键  
        Map<String, Object> map = new HashMap<String, Object>();
        for (String key : uplodImgMap.keySet()) {  
            List<String> list = new ArrayList<String>();
            for (String filepath : uplodImgMap.get(key)) {
              
                list.add(fileDir +"/"+key+ "/"+  filepath);
            }
            String str = HttpConnectionUtil.uploadFile(uploudUrl, list);
            JSONObject json = JSONObject.fromObject(str);
            JSONArray jArray = json.optJSONArray("filePaths");
            map.put(key, JSONArray.toList(jArray, new String(),new JsonConfig()));
        }  
        PageData dp = new PageData();
        List<PageData> listPageDate = new ArrayList<PageData>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {  
                String key = entry.getKey();
                key = key.replaceAll("~", "\\\\").replaceAll("-", "/").replaceAll("\\$", "-");
                dp.put("SPREAD1", key);
                Map<String, Object> tempMap = new HashMap<String, Object>();
                List<String> listAll = (List<String>) entry.getValue();
                List<String> listCode = new ArrayList<String>();
                for(String imgPath :  listAll){
                    listCode.add(imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.lastIndexOf("_")));
                    tempMap.put(imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.lastIndexOf("_")), imgPath);
                }
                dp.put("CODE",listCode);
              List<PageData> tempListPageDate =  olopddpService.findListById(dp);
              
              for(PageData tempPage:tempListPageDate){
                  tempPage.put("IMAGE_BUG_URL", tempMap.get(tempPage.getString("CODE")));
              }
              listPageDate.addAll(tempListPageDate);
        }  
        
        
        olopddpService.updateBatch(listPageDate);
       

      /*  // 一次通过商品唯一编码查询出所有对应的商品。
        List<PageData> producctList = olopdproductService.findByCode(listCodes);
        // 循环取出所有商品ID 生成轮播图对象 和商品对象更新商品图片
        List<PageData> carouselList = new ArrayList<PageData>();
        List<PageData> productAndImgList = new ArrayList<PageData>();
        // 列表图片单独处理 放入一个map中，key为商品ID,value为远程图片路径
        if (!StringUtils.isEmpty(producctList) && producctList.size() > 0) {
            for (PageData cPageData : producctList) {
                String codeStr = cPageData.getString("CODE");
                String GOODS_ID = cPageData.getString("GOODS_ID");
                if (StringUtils.isEmpty(codeStr)) {
                    continue;
                }

                List<String> tempList = map.get(codeStr);
                for (String tempStr : tempList) {
                    String tuID = this.get32UUID();
                    PageData carousePd = new PageData(); // 商品图片
                    String[] strs = tempStr.split(",");
                    carousePd.put("SRC", strs[0]);// 图片路径
                    carousePd.put("LOCATION", OloProductImageLoaction.getValue(strs[1].toUpperCase()));// 图片路径
                    carousePd.put("SORTING", strs[2]);// 图片路径
                    carousePd.put("ID", tuID);// id
                    carousePd.put("CREATION_PEOPLE_ID", user.getUSER_ID());
                    carousePd.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                    carousePd.put("SPREAD1", codeStr);// 商品编码
                    carousePd.put("SPREAD2", GOODS_ID);// 商品ID
                    carousePd.put("SEALED", 0);// 默认不封存
                    carouselList.add(carousePd);
                    PageData productAndImg = new PageData();
                    productAndImg.put("ID", this.get32UUID());
                    productAndImg.put("O_ID1", GOODS_ID);
                    productAndImg.put("O_ID2", tuID);
                    productAndImg.put("TYPE", OloProductImagOrStyle.TUBPIAN.getIndex());
                    productAndImgList.add(productAndImg);
                }

            }
            olopdabrelationService.batchSave(productAndImgList);// 图片和商品关系
            olopdcarouselService.batchSave(carouselList);// 保存上传的图片

        }*/

        FileUtils.delFolder(outputDirectory);// 最后删除本地图片
        // 处理分类关系
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Olopddp到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("名称");	//1
			titles.add("标签");  //2
			titles.add("编码");	//2
			titles.add("尺寸");	//3
			titles.add("价格");	//4
			titles.add("单位");    //4
			titles.add("分类（a/b/c/d）");    //5
			titles.add("说明");	//5
			titles.add("封存商品 默认 0  不封存 1 封存");	//6
			titles.add("是否有详情 0 没有详情，1有详情");	//9
			titles.add("生效时间（商品展示生效时间）");	//10
			titles.add("排序号");	//20
			titles.add("ID");   //20
			dataMap.put("titles", titles);
			List<PageData> varOList = olopddpService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("LABEL")); //2
				vpd.put("var3", varOList.get(i).getString("CODE"));	//2
				vpd.put("var4", varOList.get(i).getString("DIMENSION"));	//3
				vpd.put("var5", varOList.get(i).getString("PRICE"));	//4
				vpd.put("var6", varOList.get(i).getString("COMPANY"));    //4
				vpd.put("var7", varOList.get(i).getString("SPREAD1"));  //5
				vpd.put("var8", varOList.get(i).getString("REMARKS"));	//5
				vpd.put("var9", varOList.get(i).getString("SEALED"));	//6
				vpd.put("var10", varOList.get(i).getString("ISDETAILS"));	//9
				vpd.put("var11", varOList.get(i).getString("EFFECTIVE_TIME"));	//10
				vpd.put("var12", varOList.get(i).getString("SORTING"));	//20
				vpd.put("var13", varOList.get(i).getString("ID")); //20
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
	
	
	/*
     * 导入
     * @return
     */
    @RequestMapping(value="/inportExcel")
    public ModelAndView inportExcel( @RequestParam(required=false) MultipartFile file
            )throws Exception{
        logBefore(logger, "从excel导入Olopdproduct");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = new ModelAndView();
     
        String  ffile = DateUtil.getDays(), fileName = "";
        PageData pd = new PageData();
        pd = this.getPageData();
        String filePath ="";//文件上传路径
        if (null != file && !file.isEmpty()) {
            filePath = PathUtil.getClasspath() + Const.FILEPATHFILE + ffile;     //文件上传路径
            fileName = FileUpload.fileUp(file, filePath, this.get32UUID());             //执行上传
        }else{
            throw   new Exception("上传失败");
        }
        //shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();  
        Session session = currentUser.getSession();
        User user = (User)session.getAttribute(Const.SESSION_USER);
        //解析xsl
       ObjectExcelRead err= new ObjectExcelRead();
       String title = "NAME,LABEL,CODE,DIMENSION,PRICE:jdbcType=NUMERIC,COMPANY,SPREAD1,REMARKS,SEALED,ISDETAILS:jdbcType=NUMERIC,EFFECTIVE_TIME:jdbcType=TIMESTAMP,SORTING:jdbcType=NUMERIC,ID"; 
       List<PageData> list=  err.readExcel(filePath, fileName,title.split(","), 1, 0, 0);//解析
       Iterator<PageData>  it2 = list.iterator();
       while(it2.hasNext()){
           PageData temp= it2.next();
            if(StringUtils.isEmpty(temp.getString("SPREAD1"))){
                   it2.remove();
                   continue;
           }
       }
       List<PageData> listClassify = new ArrayList<PageData>();
      /* for(PageData pageData:list){
           if(StringUtils.isEmpty(pageData.getString("ID"))){
               pageData.put("ID", this.get32UUID());   //主键
           }
           pageData.put("CREATION_PEOPLE_ID", user.getUSER_ID());
           pageData.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
           String CATEGORY = pageData.getString("SPREAD1");//分类
           listClassify.addAll(generateClassify(CATEGORY,pageData.getString("ID")));
       
       }*/
       Map<String,List<PageData>> map = new HashMap<String, List<PageData>>();
       for(PageData pageData:list){
           if(StringUtils.isEmpty(pageData.getString("ID"))){
               pageData.put("ID", this.get32UUID());   //主键
           }
           pageData.put("CREATION_PEOPLE_ID", user.getUSER_ID());
           pageData.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
           String CATEGORY = pageData.getString("SPREAD1");//分类
           if(!StringUtils.isEmpty(map.get(CATEGORY) ) ){
               map.get(CATEGORY).add(pageData);
               continue;
           }
          map.put(CATEGORY, new ArrayList<PageData>());
          map.get(CATEGORY).add(pageData);
       }
       
       for (String key : map.keySet()) {  
           
           listClassify.addAll(generateClassify(key,map.get(key)));
       } 
       olopddpService.insertByBatch(list);
       if(!StringUtils.isEmpty(listClassify)){
           olopdmenudpService.deleteAll(listClassify);
           olopdmenudpService.insertByBatch(listClassify);
       }
       //处理分类关系
       mv.addObject("msg","success");
       mv.setViewName("save_result");
       return mv;
       
    }
    public List<PageData> generateClassify(String classify,String oId) throws Exception{
        if(StringUtils.isEmpty(classify)){
            return null;
        }
        String[] classifys = classify.split("/");
        PageData pData =  new PageData();
        List<PageData> listClassify = new ArrayList<PageData>();
        PageData classifyOne = new PageData();
        if(classifys.length >=1){
            //查询第一级分类
            pData.put("M_NAME", classifys[0]);
            List<PageData> list= olopdmenuService.queryMenua(pData);
          
            if(list !=null &&list.size() >0){
                PageData pd = list.get(0);
                classifyOne =  pd;
                PageData tempData =  new PageData();
                tempData.put("ID", this.get32UUID());
                tempData.put("O_ID2",  OlopdmenuUtils.getHaseCode(pd, "queryMenua"));
                tempData.put("O_ID1", oId);
                listClassify.add(tempData);
            }
        }
        PageData classifyTwo = new PageData();
        if(classifys.length >=2){
            //查询第二级分类
            pData.put("M_NAME", classifys[1]);
            pData.put("M_FID", classifyOne.getString("CID"));
            List<PageData> list= olopdmenuService.queryMenub(pData);
          
            if(list !=null &&list.size() >0){
                PageData pd = list.get(0);
                classifyTwo =  pd;
                PageData tempData =  new PageData();
                tempData.put("ID", this.get32UUID());
                tempData.put("O_ID2",     OlopdmenuUtils.getHaseCode(pd, "queryMenub"));
                tempData.put("O_ID1", oId);
                listClassify.add(tempData);
            }
        }
        PageData classifyTHREE = new PageData();
        if(classifys.length >=3){
            //查询第三级分类
            pData.put("M_NAME", classifys[2]);
            pData.put("M_FID", classifyTwo.getString("CID"));
            List<PageData> list= olopdmenuService.queryMenuc(pData);
          
            if(list !=null &&list.size() >0){
                PageData pd = list.get(0);
                PageData tempData = new PageData();
                classifyTHREE =pd;
                tempData.put("ID", this.get32UUID());
                tempData.put("O_ID2",    OlopdmenuUtils.getHaseCode(pd, "queryMenuc"));
                tempData.put("O_ID1", oId);
                listClassify.add(tempData);
            }
        }
        if(classifys.length >=4){
            //查询第四级分类
            pData.put("M_NAME", classifys[3]);
            pData.put("M_FID", classifyTHREE.getString("CID"));
            List<PageData> list= olopdmenuService.queryMenud(pData);
            if(list !=null &&list.size() >0){
                PageData pd = list.get(0);
                PageData tempData =  new PageData();
                tempData.put("ID", this.get32UUID());
                tempData.put("O_ID2",  OlopdmenuUtils.getHaseCode(pd, "queryMenud"));
                tempData.put("O_ID1", oId);
                listClassify.add(tempData);
            }
        }
        return listClassify;
      
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
	
	  public PageData generateClassifys(String classify, PageData oldpd) throws Exception {
	        String[] classifys = classify.split("/");
	        PageData pData = new PageData();
	        PageData classifyOne = new PageData();
	        if (classifys.length >= 1) {
	            // 查询第一级分类
	            pData.put("M_NAME", classifys[0]);
	            List<PageData> list = olopdmenuService.queryMenua(pData);

	            if (list != null && list.size() > 0) {
	                PageData pd = list.get(0);
	                classifyOne =  pd;
	                oldpd.put("ONE_SELECT",pd.getString("M_NAME")+","+pd.getString("CID"));
	            }
	        }
	        PageData classifyTwo = new PageData();
	        if (classifys.length >= 2) {
	            // 查询第二级分类
	            pData.put("M_NAME", classifys[1]);
	            if(!StringUtils.isEmpty(classifyOne.getString("CID"))){
	                pData.put("M_FID", classifyOne.getString("CID"));
	            }
	            
	            List<PageData> list = olopdmenuService.queryMenub(pData);

	            if (list != null && list.size() > 0) {
	                PageData pd = list.get(0);
	                classifyTwo =  pd;
	                oldpd.put("TWO_SELECT",pd.getString("M_NAME")+","+pd.getString("CID"));
	            }
	        }
	        PageData classifyTHREE = new PageData();
	        if (classifys.length >= 3) {
	            // 查询第三级分类
	            pData.put("M_NAME", classifys[2]);
	            if(!StringUtils.isEmpty(classifyTwo.getString("CID"))){
                    pData.put("M_FID", classifyTwo.getString("CID"));
                }
	            List<PageData> list = olopdmenuService.queryMenuc(pData);

	            if (list != null && list.size() > 0) {
	                PageData pd = list.get(0);
	                classifyTHREE =pd;
	                oldpd.put("THREE_SELECT",pd.getString("M_NAME")+","+pd.getString("CID"));
	            }
	        }
	        if (classifys.length >= 4) {
	            // 查询第四级分类
	            pData.put("M_NAME", classifys[3]);
	            if(!StringUtils.isEmpty(classifyTHREE.getString("CID"))){
                    pData.put("M_FID", classifyTHREE.getString("CID"));
                }
	            List<PageData> list = olopdmenuService.queryMenud(pData);
	            if (list != null && list.size() > 0) {
	                PageData pd = list.get(0);
	                oldpd.put("FOUR_SELECT",pd.getString("M_NAME")+","+pd.getString("CID"));
	            }
	        }
	        return oldpd;

	    }
	  public List<PageData> generateClassify(String classify,List<PageData> pageData) throws Exception{
	        if(StringUtils.isEmpty(classify)){
	            return null;
	        }
	        String[] classifys = classify.split("/");
	        PageData pData =  new PageData();
	        List<PageData> listClassify = new ArrayList<PageData>();
	        PageData classifyOne = new PageData();
	        if(classifys.length >=1){
	            //查询第一级分类
	            pData.put("M_NAME", classifys[0]);
	            List<PageData> list= olopdmenuService.queryMenua(pData);
	          
	            if(list !=null &&list.size() >0){
	                PageData pd = list.get(0);
	                classifyOne =  pd;
	                for(PageData tempd:pageData){
	                    PageData tempData =  new PageData();
	                    tempData.put("ID", this.get32UUID());
	                    tempData.put("O_ID1", tempd.getString("ID"));
	                    tempData.put("O_ID2",  OlopdmenuUtils.getHaseCode(pd, "queryMenua"));
	                    listClassify.add(tempData);
	                }
	               
	            }
	        }
	        PageData classifyTwo = new PageData();
	        if(classifys.length >=2){
	            //查询第二级分类
	            pData.put("M_NAME", classifys[1]);
	            pData.put("M_FID", classifyOne.getString("CID"));
	            List<PageData> list= olopdmenuService.queryMenub(pData);
	          
	            if(list !=null &&list.size() >0){
	                PageData pd = list.get(0);
	                classifyTwo =  pd;
	                for(PageData tempd:pageData){
	                    PageData tempData =  new PageData();
	                    tempData.put("ID", this.get32UUID());
	                    tempData.put("O_ID1", tempd.getString("ID"));
	                    tempData.put("O_ID2",     OlopdmenuUtils.getHaseCode(pd, "queryMenub"));
	                    listClassify.add(tempData);
	                }
	            }
	        }
	        PageData classifyTHREE = new PageData();
	        if(classifys.length >=3){
	            //查询第三级分类
	            pData.put("M_NAME", classifys[2]);
	            pData.put("M_FID", classifyTwo.getString("CID"));
	            List<PageData> list= olopdmenuService.queryMenuc(pData);
	          
	            if(list !=null &&list.size() >0){
	                PageData pd = list.get(0);
	                classifyTHREE =pd;
	                for(PageData tempd:pageData){
	                    PageData tempData =  new PageData();
	                    tempData.put("ID", this.get32UUID());
	                    tempData.put("O_ID1", tempd.getString("ID"));
	                    tempData.put("O_ID2",    OlopdmenuUtils.getHaseCode(pd, "queryMenuc"));
	                    listClassify.add(tempData);
	                }
	            }
	        }
	        if(classifys.length >=4){
	            //查询第四级分类
	            pData.put("M_NAME", classifys[3]);
	            pData.put("M_FID", classifyTHREE.getString("CID"));
	            List<PageData> list= olopdmenuService.queryMenud(pData);
	            if(list !=null &&list.size() >0){
	                PageData pd = list.get(0);
	                for(PageData tempd:pageData){
	                    PageData tempData =  new PageData();
	                    tempData.put("ID", this.get32UUID());
	                    tempData.put("O_ID1", tempd.getString("ID"));
	                    tempData.put("O_ID2",  OlopdmenuUtils.getHaseCode(pd, "queryMenud"));
	                    listClassify.add(tempData);
	                }
	            }
	        }
	        if(listClassify == null){
	            listClassify = new ArrayList<PageData>(); 
	        }
	        return listClassify;
	      
	    }
}
