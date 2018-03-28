package com.fh.controller.olo.olopdcarousel;

import java.io.File;
import java.io.PrintWriter;
import java.net.URLEncoder;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.olo.olopdcarousel.e.OlopdcarouselType;
import com.fh.controller.olo.util.OlopdmenuUtils;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.olo.olopdabrelation.OlopdabrelationService;
import com.fh.service.olo.olopdcarousel.OlopdcarouselService;
import com.fh.service.olopdmenu.olopdmenu.OlopdmenuService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.FileUtils;
import com.fh.util.HttpConnectionUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 类名称：OlopdcarouselController 创建人：FH 创建时间：2017-12-17
 */
@Controller
@RequestMapping(value = "/olopdcarousel")
public class OlopdcarouselController extends BaseController {

    String menuUrl = "olopdcarousel/list.do"; // 菜单地址(权限用)

    @Resource(name = "olopdcarouselService")
    private OlopdcarouselService olopdcarouselService;

    @Resource(name = "olopdabrelationService")
    private OlopdabrelationService olopdabrelationService; // 图片商品关系表

    @Resource(name = "dictionariesService")
    private DictionariesService dictionariesService; // 数据字典

    @Resource(name = "olopdmenuService")
    private OlopdmenuService olopdmenuService; // 分类


    
    /**
     * 新增
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        logBefore(logger, "新增Olopdcarousel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } // 校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("ID", this.get32UUID()); // 主键
        olopdcarouselService.save(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) {
        logBefore(logger, "删除Olopdcarousel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } // 校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            olopdcarouselService.delete(pd);
            PageData pandiPData = new PageData();
            pandiPData.put("O_ID1", pd.getString("ID"));
            olopdabrelationService.delete(pandiPData);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit() throws Exception {
        logBefore(logger, "修改Olopdcarousel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } 
       
        
        // 校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        olopdcarouselService.edit(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }
    /**
     * 修改
     */
    @RequestMapping(value = "/relation")
    @ResponseBody
    public Object relation() throws Exception {
        logBefore(logger, "修改Olopdcarousel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } 
       
        
        // 校验权限
        PageData pd = new PageData();
        pd = this.getPageData();
        
        String  relationId=pd.getString("relationId");
        if(StringUtils.isEmpty(relationId)){
            throw new Exception("relationId关联id不能为空！");
        }
        String  ID=pd.getString("ID");
        if(StringUtils.isEmpty(ID)){
            throw new Exception("ID不能为空！");
        }
        pd.put("SPREAD4", relationId);
        
        olopdcarouselService.edit(pd);
        return pd;
    }
    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "列表Olopdcarousel");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        // //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            //设置查询分类
            OlopdmenuUtils.setClassifyToPd(pd);
            page.setPd(pd);
            OlopdmenuUtils.setClassifyToPd(pd);
            List<PageData> varList = olopdcarouselService.list(page); // 列出Olopdcarousel列表
            // 查询出上传图片路径
            PageData uploudImgPd = new PageData();
            uploudImgPd.put("BIANMA", Const.UPLOUADIMGPATH);
            uploudImgPd = dictionariesService.findBmCount(uploudImgPd);
            pd.put("imgBasePath", uploudImgPd.getString("NAME"));
            mv.setViewName("olo/olopdcarousel/olopdcarousel_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() {
        logBefore(logger, "去新增Olopdcarousel页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("olo/olopdcarousel/olopdcarousel_edit");
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
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() {
        logBefore(logger, "去修改Olopdcarousel页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
        
            pd = olopdcarouselService.findById(pd); // 根据ID读取
            // 查询出上传图片路径
            PageData uploudImgPd = new PageData();
            uploudImgPd.put("BIANMA", Const.UPLOUADIMGPATH);
            uploudImgPd = dictionariesService.findBmCount(uploudImgPd);
            pd.put("imgBasePath", uploudImgPd.getString("NAME"));
            mv.setViewName("olo/olopdcarousel/olopdcarousel_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 去分类图片上传页面
     */
    @RequestMapping(value = "/goInputrtImages")
    public ModelAndView goInputrtImages() {
        logBefore(logger, "去修改Olopdcarousel页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            
          
            mv.setViewName("olo/olopdcarousel/olopdproduct_inportimg");
            mv.addObject("msg", "inputrtImages");
            if(!StringUtils.isEmpty(pd.getString("type"))){
                mv.addObject("msg", "inputrtControllerImages");
            }
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() {
        logBefore(logger, "批量删除Olopdcarousel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "dell")) {
            return null;
        } // 校验权限
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if (null != DATA_IDS && !"".equals(DATA_IDS)) {
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                olopdcarouselService.deleteAll(ArrayDATA_IDS);
                
                PageData pandiPData = new PageData();
                pandiPData.put("O_ID1", pd.getString("ID"));
                olopdabrelationService.deleteAllById(ArrayDATA_IDS);
                pd.put("msg", "ok");
            } else {
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
        Map<String, String> mapNams = new HashMap<String, String>();

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
        List<String> list = new ArrayList<String>();
        //遍历map中的键  
        for (String key : uplodImgMap.keySet()) {  
            for (String filepath : uplodImgMap.get(key)) {
                String fileNameEncde = URLEncoder.encode(
                        StringUtils.replaceBlank(  filepath).substring(0,
                                filepath.lastIndexOf(".")),"UTF-8").replaceAll("%", "");
                mapNams.put(fileNameEncde, StringUtils.replaceBlank(  filepath).substring(0,
                                filepath.lastIndexOf(".")));
                list.add(fileDir +"/"+key+ "/"+  filepath);
            }
           
        }  
        String str = HttpConnectionUtil.uploadFile(uploudUrl, list);
        JSONObject json = JSONObject.fromObject(str);
        JSONArray jArray = json.optJSONArray("filePaths");
        List<String> uploadImgReturnList = JSONArray.toList(jArray, new String(),new JsonConfig());
        PageData dp = new PageData();
        List<PageData> listPageDate = new ArrayList<PageData>();
        
     // 循环取出所有商品ID 生成轮播图对象 和商品对象更新商品图片
        List<PageData> carouselList = new ArrayList<PageData>();
        List<PageData> productAndImgList = new ArrayList<PageData>();
        for(String imgPath:uploadImgReturnList){
            String tempPath = imgPath;
            String fenleiName =imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.lastIndexOf("_"));
            fenleiName = mapNams.get(fenleiName);
            fenleiName =fenleiName.replaceAll("~", "\\\\").replaceAll("-", "/").replaceAll("\\$", "-");
            String tuID = this.get32UUID();
            PageData carousePd = new PageData(); // 商品图片
            carousePd.put("SRC", tempPath);// 图片路径
            carousePd.put("LOCATION", 0);// 图片位置 0 列表
            carousePd.put("SORTING", 0);// 排序
            carousePd.put("ID", tuID);// id
            carousePd.put("CREATION_PEOPLE_ID", user.getUSER_ID());
            carousePd.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
            carousePd.put("SPREAD1", "");// 分类名称
            carousePd.put("SPREAD2", "");// 分类名称
            carousePd.put("SPREAD3", fenleiName);// 分类名称
            carousePd.put("SEALED", 0);// 默认不封存
            carouselList.add(carousePd);
            productAndImgList.addAll( generateClassify(fenleiName,tuID, OlopdcarouselType.FENLEI.getIndex()));
        }
        olopdabrelationService.batchSave(productAndImgList);// 图片和商品关系
        olopdcarouselService.batchSave(carouselList);// 保存上传的图片
       

        FileUtils.delFolder(outputDirectory);// 最后删除本地图片
        // 处理分类关系
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }
    
    /**
     * 
       
     * inputrtControllerImages  批量导入图片    导入分类轮播
       
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
    @RequestMapping(value = "/inputrtControllerImages")
    public ModelAndView inputrtControllerImages(@RequestParam(required = false) MultipartFile file,Integer location) throws Exception {
        logBefore(logger, "从zip中导入Olopdproduct的图片");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        if(location ==null){
            location =1;//默认轮播；
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
        Map<String, String> mapNams = new HashMap<String, String>();

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
     
        // 循环取出所有商品ID 生成轮播图对象 和商品对象更新商品图片
        List<PageData> carouselList = new ArrayList<PageData>();
        List<PageData> productAndImgList = new ArrayList<PageData>();
        //遍历map中的键  
        for (String key : uplodImgMap.keySet()) {  
            List<String> list = new ArrayList<String>();
            for (String filepath : uplodImgMap.get(key)) {
                String fileNameEncde = URLEncoder.encode(
                        StringUtils.replaceBlank(  filepath).substring(0,
                                filepath.lastIndexOf(".")),"UTF-8").replaceAll("%", "");
                mapNams.put(fileNameEncde, StringUtils.replaceBlank(  filepath).substring(0,
                                filepath.lastIndexOf(".")));
                list.add(fileDir +"/"+key+ "/"+  filepath);
            }
            String str = HttpConnectionUtil.uploadFile(uploudUrl, list);
            JSONObject json = JSONObject.fromObject(str);
            JSONArray jArray = json.optJSONArray("filePaths");
            List<String> uploadImgReturnList = JSONArray.toList(jArray, new String(),new JsonConfig());
            key =  key.replaceAll("~", "\\\\").replaceAll("-", "/").replaceAll("\\$", "-");
            for(String imgPath:uploadImgReturnList){
                String tempPath = imgPath;
                String fenleiName =imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.lastIndexOf("_"));
                fenleiName = mapNams.get(fenleiName);
                fenleiName =fenleiName.replaceAll("~", "\\\\").replaceAll("-", "/").replaceAll("\\$", "-");
               
                String tuID = this.get32UUID();
                PageData carousePd = new PageData(); // 商品图片
                carousePd.put("SRC", tempPath);// 图片路径
                
                carousePd.put("LOCATION", location);// 图片位置 0 列表
                try {
                    carousePd.put("SORTING", Integer.valueOf(fenleiName));// 排序
                } catch (Exception e) {
                    carousePd.put("SORTING", 1);// 排序
                    
                }
                carousePd.put("ID", tuID);// id
                carousePd.put("CREATION_PEOPLE_ID", user.getUSER_ID());
                carousePd.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                carousePd.put("SPREAD1", "");// 分类名称
                carousePd.put("SPREAD2", "");// 分类名称
                carousePd.put("SPREAD3", key);// 分类名称
                carousePd.put("SEALED", 0);// 默认不封存
                carouselList.add(carousePd);
                productAndImgList.addAll( generateClassify(key,tuID,OlopdcarouselType.FENLEILUNBO.getIndex()));
            }
        }  
        
        olopdabrelationService.batchSave(productAndImgList);// 图片和商品关系
        olopdcarouselService.batchSave(carouselList);// 保存上传的图片
       

        FileUtils.delFolder(outputDirectory);// 最后删除本地图片
        // 处理分类关系
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }
    
    /*
     * 导出到excel
     * 
     * @return
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() {
        logBefore(logger, "导出Olopdcarousel到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("图片路径"); // 1
            titles.add("排序号"); // 2
            titles.add("分类ID"); // 3
            titles.add("0 头部滚动，1页面底部展示"); // 4
            titles.add("描述"); // 5
            titles.add("封存商品 默认 0  不封存 1 封存"); // 6
            titles.add("创建时间"); // 7
            titles.add("创建人ID"); // 8
            titles.add("更新时间"); // 9
            titles.add("更新人ID"); // 10
            titles.add("Spread1"); // 11
            titles.add("Spread2"); // 12
            titles.add("Spread3"); // 13
            titles.add("Spread4"); // 14
            titles.add("Spread5"); // 15
            dataMap.put("titles", titles);
            List<PageData> varOList = olopdcarouselService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("SRC")); // 1
                vpd.put("var2", varOList.get(i).getString("SORTING")); // 2
                vpd.put("var3", varOList.get(i).getString("CLASSIFY_ID")); // 3
                vpd.put("var4", varOList.get(i).getString("LOCATION")); // 4
                vpd.put("var5", varOList.get(i).getString("REMARKS")); // 5
                vpd.put("var6", varOList.get(i).getString("SEALED")); // 6
                vpd.put("var7", varOList.get(i).getString("CREATE_TIME")); // 7
                vpd.put("var8", varOList.get(i).getString("CREATION_PEOPLE_ID")); // 8
                vpd.put("var9", varOList.get(i).getString("UPDATE_TIME")); // 9
                vpd.put("var10", varOList.get(i).getString("UPDATE_PEOPLE_ID")); // 10
                vpd.put("var11", varOList.get(i).getString("SPREAD1")); // 11
                vpd.put("var12", varOList.get(i).getString("SPREAD2")); // 12
                vpd.put("var13", varOList.get(i).getString("SPREAD3")); // 13
                vpd.put("var14", varOList.get(i).getString("SPREAD4")); // 14
                vpd.put("var15", varOList.get(i).getString("SPREAD5")); // 15
                varList.add(vpd);
            }
            dataMap.put("varList", varList);
            ObjectExcelView erv = new ObjectExcelView();
            mv = new ModelAndView(erv, dataMap);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 
       
     * generateClassify(处理分类和图片的关系)    
       
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
    public List<PageData> generateClassify(String classify,String oId,Integer type) throws Exception{
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
                tempData.put("TYPE", type);
                if(classifys.length ==1){
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
                PageData tempData =  new PageData();
                tempData.put("ID", this.get32UUID());
                tempData.put("O_ID2",     OlopdmenuUtils.getHaseCode(pd, "queryMenub"));
                tempData.put("O_ID1", oId);
                tempData.put("TYPE", type);
                if(classifys.length ==2){
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
                PageData tempData =  new PageData();
                tempData.put("ID", this.get32UUID());
                tempData.put("O_ID2",    OlopdmenuUtils.getHaseCode(pd, "queryMenuc"));
                tempData.put("O_ID1", oId);
                tempData.put("TYPE", type);
                if(classifys.length ==3){
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
                PageData tempData =  new PageData();
                tempData.put("ID", this.get32UUID());
                tempData.put("O_ID2",  OlopdmenuUtils.getHaseCode(pd, "queryMenuc"));
                tempData.put("O_ID1", oId);
                tempData.put("TYPE",type);
                if(classifys.length ==4){
                    listClassify.add(tempData);
                }
            }
        }
        return listClassify;
      
    }
    /* ===============================权限================================== */
    public Map<String, String> getHC() {
        Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
    }
    /* ===============================权限================================== */

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}
