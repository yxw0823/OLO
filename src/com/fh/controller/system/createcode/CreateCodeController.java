package com.fh.controller.system.createcode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fh.controller.base.BaseController;
import com.fh.service.system.sqlfactory.FactoryService;
import com.fh.util.DelAllFile;
import com.fh.util.FileDownload;
import com.fh.util.FileZip;
import com.fh.util.Freemarker;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.StringUtils;

/** 
 * 类名称：FreemarkerController
 * 创建人：FH 
 * 创建时间：2015年1月12日
 * @version
 */
@Controller
@RequestMapping(value="/createCode")
public class CreateCodeController extends BaseController {
    @Resource(name="factoryService")
    private FactoryService factoryService;
	/**
	 * 生成代码
	 */
	@RequestMapping(value="/proCode")
	public void proCode(HttpServletResponse response) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		DelAllFile.delFolder(PathUtil.getClasspath()+"admin/ftl"); //生成代码前,先清空之前生成的代码
		String packageName =pd.getString("packageName").toLowerCase();  //包名                                ========2
		/* ============================================================================================= */
		String tabletops = pd.getString("tabletop").replaceAll(" ", "");	   				 // 表名				========1
		String[] tablelist =  tabletops.split(",");
		for(String tabletop :tablelist){
		tabletop = null == tabletop?"":tabletop.toUpperCase();		 //表前缀转大写
		String tempName  = tabletop.replace("_", "").toLowerCase(); 
		String objectName = tempName.substring(0,1).toUpperCase()+ tempName.substring(1).toLowerCase();   //类名              ========3
		if(StringUtils.isEmpty(packageName)){
		    //如果包名为空，就用表名做包名
		    packageName =tabletop.replace("_", "").toLowerCase();  //包名                                ========2
		}
		
		String zindext = pd.getString("zindex");	   	   			//属性总数
		int zindex = 0;
		if(null != zindext && !"".equals(zindext)){
			zindex = Integer.parseInt(zindext);
		}
		PageData findpd = new PageData();
		findpd.put("TABLE_NAME",tabletop);
		List<PageData> listpd =factoryService.findUserTabColumns(findpd);
		  
		List<List<String>> fieldList = new ArrayList<List<String>>();   	//属性集合			========4
		/*for(int i=0; i< zindex; i++){
			fieldList.add(pd.getString("field"+i).split(",fh,"));	//属性放到集合里面
		}*/
		zindex  = listpd.size();
		 String pk = "";
		for(int i=0; i< listpd.size(); i++){
		    List<String> lists = new  ArrayList<String>();
		    PageData commentsPd = listpd.get(i);
		    pk = commentsPd.getString("PK");
		    if(pk.equals(commentsPd.getString("COLUMN_NAME"))){
		        continue;
		    }
		  
		    lists.add(commentsPd.getString("COLUMN_NAME"));
		    lists.add(commentsPd.getString("DATE_TYPE"));
		    lists.add(commentsPd.getString("COMMENTS") == null ?"":commentsPd.getString("COMMENTS"));
		    if(pk.equals(commentsPd.getString("COLUMN_NAME"))){
		        lists.add("否");
		    }else{
		        lists.add("是");
		    }
		   
		    lists.add("无");
            fieldList.add(lists);   //属性放到集合里面
        }
		Map<String,Object> root = new HashMap<String,Object>();		//创建数据模型
		root.put("fieldList", fieldList);
		root.put("packageName", packageName);						//包名
		root.put("objectName", objectName);							//类名
		root.put("objectNameLower", objectName.toLowerCase());		//类名(全小写)
		root.put("objectNameUpper", objectName.toUpperCase());		//类名(全大写)
		root.put("tabletop", tabletop);		
		root.put("pk", pk);                                         //主键	
		root.put("nowDate", new Date());							//当前日期
		
	
		/* ============================================================================================= */
		
		String filePath = "admin/ftl/code/";						//存放路径
		String ftlPath = "createCode";								//ftl路径
		
		/*生成controller*/
		Freemarker.printFile("controllerTemplate.ftl", root, "controller/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName+"Controller.java", filePath, ftlPath);
		
		/*生成service*/
		Freemarker.printFile("serviceTemplate.ftl", root, "service/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName+"Service.java", filePath, ftlPath);

		/*生成mybatis xml*/
		Freemarker.printFile("mapperMysqlTemplate.ftl", root, "mybatis_mysql/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
		Freemarker.printFile("mapperOracleTemplate.ftl", root, "mybatis_oracle/"+packageName+"/"+objectName+"Mapper.xml", filePath, ftlPath);
		
		/*生成SQL脚本*/
		Freemarker.printFile("mysql_SQL_Template.ftl", root, "mysql数据库脚本/"+tabletop+objectName.toUpperCase()+".sql", filePath, ftlPath);
		Freemarker.printFile("oracle_SQL_Template.ftl", root, "oracle数据库脚本/"+tabletop+objectName.toUpperCase()+".sql", filePath, ftlPath);
		
		/*生成jsp页面*/
		Freemarker.printFile("jsp_list_Template.ftl", root, "jsp/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_list.jsp", filePath, ftlPath);
		Freemarker.printFile("jsp_edit_Template.ftl", root, "jsp/"+packageName+"/"+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_edit.jsp", filePath, ftlPath);
		}
		/*生成说明文档*/
	/*	Freemarker.printFile("docTemplate.ftl", root, "说明.doc", filePath, ftlPath);*/
		
		//this.print("oracle_SQL_Template.ftl", root);  控制台打印
		
		/*生成的全部代码压缩成zip文件*/
		FileZip.zip(PathUtil.getClasspath()+"admin/ftl/code", PathUtil.getClasspath()+"admin/ftl/code.zip");
		
		/*下载代码*/
		FileDownload.fileDownload(response, PathUtil.getClasspath()+"admin/ftl/code.zip", "code.zip");
		
	}
	
}
