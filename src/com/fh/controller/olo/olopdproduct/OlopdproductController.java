package com.fh.controller.olo.olopdproduct;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.fh.controller.olo.olopdproduct.e.OloProductImagOrStyle;
import com.fh.controller.olo.olopdproduct.e.OloProductImageLoaction;
import com.fh.controller.olo.util.OlopdmenuUtils;
import com.fh.entity.Page;
import com.fh.entity.Page.Sort;
import com.fh.entity.system.User;
import com.fh.service.olo.olopdabrelation.OlopdabrelationService;
import com.fh.service.olo.olopdabsku.OlopdabskuService;
import com.fh.service.olo.olopdcarousel.OlopdcarouselService;
import com.fh.service.olo.olopdmenuproduct.OlopdmenuproductService;
import com.fh.service.olo.olopdsku.OlopdskuService;
import com.fh.service.olo.olopdstyle.OlopdstyleService;
import com.fh.service.olopdabval.olopdabval.OlopdabvalService;
import com.fh.service.olopdmenu.olopdmenu.OlopdmenuService;
import com.fh.service.olopdproduct.olopdproduct.OlopdproductService;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 类名称：OlopdproductController 创建人：FH 创建时间：2017-12-04
 */
@Controller
@RequestMapping(value = "/olopdproduct")
public class OlopdproductController extends BaseController {

    String menuUrl = "olopdproduct/list.do"; // 菜单地址(权限用)

    @Resource(name = "olopdabvalService")
    private OlopdabvalService olopdabvalService;

    @Resource(name = "olopdproductService")
    private OlopdproductService olopdproductService;

    @Resource(name = "olopdmenuService")
    private OlopdmenuService olopdmenuService; // 分类

    @Resource(name = "olopdmenuproductService")
    private OlopdmenuproductService olopdmenuproductService;// 分类和商品关系

    @Resource(name = "olopdcarouselService")
    private OlopdcarouselService olopdcarouselService;// 图片管理

    @Resource(name = "olopdabrelationService")
    private OlopdabrelationService olopdabrelationService; // 图片商品关系表

    @Resource(name = "dictionariesService")
    private DictionariesService dictionariesService; // 数据字典

    @Resource(name = "olopdstyleService")
    private OlopdstyleService olopdstyleService;

    @Resource(name = "olopdskuService")
    private OlopdskuService olopdskuService; // SKU管理

    @Resource(name = "olopdabskuService")
    private OlopdabskuService olopdabskuService; // SKU属性关系管理

    /**
     * 新增
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        logBefore(logger, "新增Olopdproduct");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } // 校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("GOODS_ID", this.get32UUID()); // 主键

        List<String> tempList = new ArrayList<String>();
        String IMAGE_URL = pd.getString("IMAGE_URL");// 头部大图
        String IMAGE_URL1 = pd.getString("IMAGE_URL1");// 头部大图
        String IMG_TOP_SMALL = pd.getString("IMG_TOP_SMALL");// 头部小图
        String IMG_BOTTOM = pd.getString("IMG_BOTTOM");// 底部图片
        String IMG_LIST = pd.getString("IMG_LIST");// 底部图片
        if (!StringUtils.isEmpty(IMG_LIST)) {
            String[] listImgs = IMG_LIST.split(",");
            tempList.add(listImgs[listImgs.length - 1] + "," + OloProductImageLoaction.A.getName() + "," + 0);
            pd.put("IMAGE_URL_LIST", listImgs[listImgs.length - 1]);
        }
        int i = 1;
        if (!StringUtils.isEmpty(IMAGE_URL)) {
            for (String imgSrc : IMAGE_URL.split(",")) {
                tempList.add(imgSrc + "," + OloProductImageLoaction.A.getName() + "," + i);
                i++;
            }
        }
        if (!StringUtils.isEmpty(IMAGE_URL1)) {
            for (String imgSrc : IMAGE_URL1.split(",")) {
                tempList.add(imgSrc + "," + OloProductImageLoaction.A.getName() + "," + i);
                i++;
            }
        }
        if (!StringUtils.isEmpty(IMG_TOP_SMALL)) {
            for (String imgSrc : IMG_TOP_SMALL.split(",")) {
                tempList.add(imgSrc + "," + OloProductImageLoaction.B.getName() + "," + i);
                i++;
            }
        }
        if (!StringUtils.isEmpty(IMG_BOTTOM)) {
            for (String imgSrc : IMG_BOTTOM.split(",")) {
                tempList.add(imgSrc + "," + OloProductImageLoaction.C.getName() + "," + i);
                i++;
            }
        }

        String styles = pd.getString("styles");

        List<String> styleList = new ArrayList<String>();
        if (!StringUtils.isEmpty(styles)) {
            for (String id : styles.split(",")) {
                styleList.add(id);
            }
        }
        // shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
        if (user != null) {
            pd.put("LAST_DATE", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
            pd.put("EFFECTIVE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
            pd.put("CREATION_PEOPLE_ID", user.getUSER_ID());
            pd.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
            pd.put("UPDATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
            pd.put("UPDATE_PEOPLE_ID", user.getUSER_ID());
            olopdproductService.save(pd);
            // 保存图片关系
            saveCarouserAndProductAndImg(pd, tempList, user);
            // 保存商品和风格关系
            savaStyleAndProduct(pd, styleList, user, OloProductImagOrStyle.FENGGE.getIndex(),true);

            // 获取推荐的商品list
            //
            mv.addObject("msg", "success");
            mv.setViewName("save_result");
        } else {
            mv.setViewName("system/admin/login");// session失效后跳转登录页面
        }

        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) {
        logBefore(logger, "删除Olopdproduct");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } // 校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            olopdproductService.delete(pd);
            olopdmenuproductService.deleteGoodsId(pd);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }
    
    /**
     * 取推荐商品
     */
    @RequestMapping(value = "/goTjsp")
    public ModelAndView goTjsp(Page page) {
        logBefore(logger, "去新增推荐商品页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
                // //校验权限
                pd = this.getPageData();
                OlopdmenuUtils.setClassifyToPd(pd);
                page.setPd(pd);
                if (StringUtils.isEmpty(page.getSort()) || page.getSort().size() == 0) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", "SORTING");
                    map.put("sortStr", "");
                    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                    list.add(map);
                    page.addListSort(list);
                }
            List<PageData> varList = olopdproductService.list(page); // 列出Olopdproduct列表
            mv.setViewName("olopdproduct/olopdproduct/olopdproduct_listTjdp");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    
    /**
     * 列表
     */
    @RequestMapping(value = "/listTjsp")
    public void listTjsp(HttpServletResponse response) {
        logBefore(logger, "查询推荐商品");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        // //校验权限
        ModelAndView mv = this.getModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
           
            List<PageData> varList = olopdproductService.findTjspById(pd); // 列出Olopdproduct列表
            map.put("varList", varList);
            map.put("pd", pd);
            this.responseJson(response, map);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }
    /**
     * 推荐商品
     */
    @RequestMapping(value = "/relationTjdp")
    public void relationTjdp(PrintWriter out) {
        logBefore(logger, "关联推荐搭配的商品");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } // 校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            
            String DATA_IDS = pd.getString("DATA_IDS");
            List<String> List = new ArrayList<String>();
            if (!StringUtils.isEmpty(DATA_IDS)) {
                for (String id : DATA_IDS.split(",")) {
                    List.add(id);
                }
            }
            // shiro管理的session
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            User user = (User) session.getAttribute(Const.SESSION_USER);
            //商品与推送商品的关系
            savaStyleAndProduct(pd, List, user, OloProductImagOrStyle.TJDPSP.getIndex(),false);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }
    
    
    /**
     * 推荐商品
     */
    @RequestMapping(value = "/delTjdp")
    public void delTjdp(PrintWriter out) {
        logBefore(logger, "删除关联推荐搭配的商品");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } // 校验权限
        PageData pd = new PageData();
        try {
          
            pd = this.getPageData();
            String GOODS_ID = pd.getString("GOODS_ID");
            if (StringUtils.isEmpty(GOODS_ID)) {
                throw new Exception("请传入O_ID1");
            }
            String O_ID2 = pd.getString("O_ID2");

            if (StringUtils.isEmpty(O_ID2)) {
                throw new Exception("请传入O_ID2");
            }
           
            
            // 通过商品ID 和关系类型 删除所有商品和风格的关系
            PageData delProductAndImg = new PageData();
            delProductAndImg.put("O_ID1", GOODS_ID);
            delProductAndImg.put("O_ID2", O_ID2);
            delProductAndImg.put("TYPE", OloProductImagOrStyle.TJDPSP.getIndex());
            olopdabrelationService.deleteByPd(delProductAndImg);  
            //商品与推送商品的关系
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }
    /**
     * 
     * 
     * savaStyleAndProduct(保存商品和风格的关系)
     * 
     * @param name
     * 
     * @param @return
     *            设定文件
     * 
     * @return String DOM对象
     * @throws Exception
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public void savaStyleAndProduct(PageData product, List<String> tempList, User user, int type,boolean flage) throws Exception {

        if (tempList == null || tempList.size() == 0) {
            return;
        }
        List<PageData> productAndImgList = new ArrayList<PageData>();

        String GOODS_ID = product.getString("GOODS_ID");

        if (StringUtils.isEmpty(GOODS_ID)) {
            return;
        }
        
        // 通过商品ID 和关系类型 删除所有商品和风格的关系
        PageData delProductAndImg = new PageData();
        delProductAndImg.put("O_ID1", GOODS_ID);
        delProductAndImg.put("TYPE", type);
        if(flage){
            
            //是否删除之前的关系
            olopdabrelationService.deleteByPd(delProductAndImg);  
        }
      

        for (String tempStr : tempList) {
            PageData productAndImg = new PageData();
            productAndImg.put("ID", this.get32UUID());
            productAndImg.put("O_ID1", GOODS_ID);
            productAndImg.put("O_ID2", tempStr);
            productAndImg.put("TYPE", type);
            productAndImgList.add(productAndImg);
        }
        olopdabrelationService.batchSave(productAndImgList);// 图片和商品关系
    }

    // 保存图片和图片和商品关系
    public void saveCarouserAndProductAndImg(PageData product, List<String> tempList, User user) throws Exception {
        if (StringUtils.isEmpty(tempList) || tempList.size() == 0) {
            return;
        }
        List<PageData> carouselList = new ArrayList<PageData>();
        List<PageData> productAndImgList = new ArrayList<PageData>();
        String codeStr = product.getString("CODE");
        String GOODS_ID = product.getString("GOODS_ID");
        if (StringUtils.isEmpty(codeStr)) {
            return;
        }

        for (String tempStr : tempList) {
            String tuID = this.get32UUID();
            PageData carousePd = new PageData(); // 商品图片
            String[] strs = tempStr.split(",");
            carousePd.put("SRC", strs[0]);// 图片路径
            carousePd.put("LOCATION", OloProductImageLoaction.getValue(strs[1].toUpperCase()));// 图片位置
            carousePd.put("SORTING", strs[2]);// 图片路径
            carousePd.put("ID", tuID);// id
            carousePd.put("CREATION_PEOPLE_ID", user.getUSER_ID());
            carousePd.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
            carousePd.put("SPREAD1", codeStr);// 商品编码
            carousePd.put("SPREAD2", GOODS_ID);// 商品ID
            carousePd.put("SPREAD3", "");// 商品ID
            carousePd.put("SEALED", 0);// 默认不封存
            carouselList.add(carousePd);
            PageData productAndImg = new PageData();
            productAndImg.put("ID", this.get32UUID());
            productAndImg.put("O_ID1", GOODS_ID);
            productAndImg.put("O_ID2", tuID);
            productAndImg.put("TYPE", OloProductImagOrStyle.TUBPIAN.getIndex());
            productAndImgList.add(productAndImg);
        }

        olopdabrelationService.batchSave(productAndImgList);// 图片和商品关系
        olopdcarouselService.batchSave(carouselList);// 保存上传的图片
    }

    /**
     * 
     * 
     * editSorting(更新排序号)
     * 
     * @param name
     * 
     * @param @return
     *            设定文件
     * 
     * @return String DOM对象
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    @RequestMapping(value = "/editSorting")
    public void editSorting(HttpServletResponse response) throws Exception {
        logBefore(logger, "修改Olopdproduct");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return;
        } // 校验权限
        PageData pd = new PageData();
        pd = this.getPageData();

        if (StringUtils.isEmpty(pd.getString("GOODS_ID"))) {
            throw new Exception("请提交GOODS_ID");
        }
        if (StringUtils.isEmpty(pd.getString("SORTING"))) {
            throw new Exception("请提交SORTING");
        }
        olopdproductService.edit(pd);
        this.responseText(response, "true");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit() throws Exception {
        logBefore(logger, "修改Olopdproduct");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } // 校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        // 处理sku

        // 处理图片
        List<String> tempList = new ArrayList<String>();

        String IMAGE_URL = pd.getString("IMAGE_URL");// 头部大图
        String IMAGE_URL1 = pd.getString("IMAGE_URL1");// 头部大图
        String IMG_TOP_SMALL = pd.getString("IMG_TOP_SMALL");// 头部小图
        String IMG_BOTTOM = pd.getString("IMG_BOTTOM");// 底部图片
        String IMG_LIST = pd.getString("IMG_LIST");// 底部图片
        if (!StringUtils.isEmpty(IMG_LIST)) {
            String[] listImgs = IMG_LIST.split(",");
            tempList.add(listImgs[listImgs.length - 1] + "," + OloProductImageLoaction.A.getName() + "," + 0);
            pd.put("IMAGE_URL_LIST", listImgs[listImgs.length - 1]);
        }
        int i = 1;
        if (!StringUtils.isEmpty(IMAGE_URL)) {
            for (String imgSrc : IMAGE_URL.split(",")) {
                tempList.add(imgSrc + "," + OloProductImageLoaction.A.getName() + "," + i);
                i++;
            }
        }
        if (!StringUtils.isEmpty(IMAGE_URL1)) {
            for (String imgSrc : IMAGE_URL1.split(",")) {
                tempList.add(imgSrc + "," + OloProductImageLoaction.A.getName() + "," + i);
                i++;
            }
        }
        if (!StringUtils.isEmpty(IMG_TOP_SMALL)) {
            for (String imgSrc : IMG_TOP_SMALL.split(",")) {
                tempList.add(imgSrc + "," + OloProductImageLoaction.B.getName() + "," + i);
                i++;
            }
        }
        if (!StringUtils.isEmpty(IMG_BOTTOM)) {
            for (String imgSrc : IMG_BOTTOM.split(",")) {
                tempList.add(imgSrc + "," + OloProductImageLoaction.C.getName() + "," + i);
                i++;
            }
        }
        String styles = pd.getString("styles");
        pd.put("SPREAD5", styles);
        List<String> styleList = new ArrayList<String>();
        if (!StringUtils.isEmpty(styles)) {
            for (String id : styles.split(",")) {
                styleList.add(id);
            }
        }
        // 推荐搭配的商品
        String recommend = pd.getString("recommend");

        List<String> recommendList = new ArrayList<String>();
        if (!StringUtils.isEmpty(recommend)) {
            for (String id : recommend.split(",")) {
                recommendList.add(id);
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
            pd.put("LAST_DATE", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
            if (!StringUtils.isEmpty(pd.getString("EFFECTIVE_TIME"))) {
                pd.put("EFFECTIVE_TIME", java.sql.Timestamp
                        .valueOf(DateUtil.getTime(DateUtil.fomatDate(pd.getString("EFFECTIVE_TIME")))));
            } else {
                pd.put("EFFECTIVE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
            }
            pd.put("UPDATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
            pd.put("UPDATE_PEOPLE_ID", user.getUSER_ID());
            if (!StringUtils.isEmpty(ONE_SELECT) && !"请选择".equals(ONE_SELECT)) {
                pd.put("CATEGORY", CATEGORY);
            }
            olopdproductService.edit(pd);
            // 保存图片关系
            saveCarouserAndProductAndImg(pd, tempList, user);

            savaStyleAndProduct(pd, styleList, user, OloProductImagOrStyle.FENGGE.getIndex(),true);
            // 推荐搭配商品
            savaStyleAndProduct(pd, recommendList, user, OloProductImagOrStyle.TJDPSP.getIndex(),true);

            PageData temppData = new PageData();
            temppData.put("GOODS_ID", pd.getString("GOODS_ID"));
            olopdmenuproductService.deleteGoosId(temppData);
            if (!StringUtils.isEmpty(ONE_SELECT) && !"请选择".equals(ONE_SELECT)) {
                // 保存商品和分类关系
                List<PageData> listClassify = generateClassify(CATEGORY, pd.getString("GOODS_ID"));
                olopdmenuproductService.insertByBatch(listClassify);
            }
            mv.addObject("msg", "success");
            mv.setViewName("save_result");
        } else {
            mv.setViewName("system/admin/login");// session失效后跳转登录页面
        }
        return mv;
    }

    public ModelAndView recommendSave(Page page) throws Exception {
        logBefore(logger, "列表Olopdproduct");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        if (StringUtils.isEmpty(pd.getString("GOODS_ID"))) {
            throw new Exception("请提交GOODS_ID");
        }

        // 推荐搭配的商品
        String recommend = pd.getString("recommend");

        List<String> recommendList = new ArrayList<String>();
        if (!StringUtils.isEmpty(recommend)) {
            for (String id : recommend.split(",")) {
                recommendList.add(id);
            }
        }
        // shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
        if (user != null) {

            // 推荐搭配商品
            savaStyleAndProduct(pd, recommendList, user, OloProductImagOrStyle.TJDPSP.getIndex(),false);
            mv.addObject("msg", "success");
            mv.setViewName("save_result");
        } else {
            mv.setViewName("system/admin/login");// session失效后跳转登录页面
        }
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "列表Olopdproduct");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        // //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            OlopdmenuUtils.setClassifyToPd(pd);
            page.setPd(pd);
            if (StringUtils.isEmpty(page.getSort()) || page.getSort().size() == 0) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", "SORTING");
                map.put("sortStr", "");
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                list.add(map);
                page.addListSort(list);
            }
            List<PageData> varList = olopdproductService.list(page); // 列出Olopdproduct列表
            mv.setViewName("olopdproduct/olopdproduct/olopdproduct_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    /**
     * 列表
     */
    @RequestMapping(value = "/relationList")
    public ModelAndView relationList(Page page) {
        logBefore(logger, "列表Olopdproduct");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        // //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            OlopdmenuUtils.setClassifyToPd(pd);
            page.setPd(pd);
            if (StringUtils.isEmpty(page.getSort()) || page.getSort().size() == 0) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", "SORTING");
                map.put("sortStr", "");
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                list.add(map);
                page.addListSort(list);
            }
            List<PageData> varList = olopdproductService.list(page); // 列出Olopdproduct列表
            mv.setViewName("olopdproduct/olopdproduct/olopdproduct_listRelation");
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
        logBefore(logger, "去新增Olopdproduct页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            // 查询出标签
            PageData RXPd = new PageData();
            RXPd.put("P_BM", Const.SPMBTMBQ);
            
            
            //查询出相关分类
            PageData SPXQLM = new PageData();
            SPXQLM.put("P_BM", Const.SPMBTMSPXQLM);
            pd.put("SPXQLM", dictionariesService.dictlist(SPXQLM));
            
            pd.put("bq", dictionariesService.dictlist(RXPd));
            pd.put("styles", olopdstyleService.listAll(new PageData()));
            mv.setViewName("olopdproduct/olopdproduct/olopdproduct_edit");
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
    @RequestMapping(value = "/goInportExcel")
    public ModelAndView goInportExcel() {
        logBefore(logger, "去批量上传Olopdproduct页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("olopdproduct/olopdproduct/olopdproduct_inport");
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
    @RequestMapping(value = "/goInputrtImages")
    public ModelAndView goInputrtImages() {
        logBefore(logger, "去图片批量上传Olopdproduct页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("olopdproduct/olopdproduct/olopdproduct_inportimg");
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
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() {
        logBefore(logger, "去修改Olopdproduct页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = olopdproductService.findById(pd); // 根据ID读取

            generateClassifys(pd.getString("CATEGORY"), pd);
            // 查询出和商品关联的图片
            PageData imgPd = new PageData();
            imgPd.put("GOODS_ID", pd.getString("GOODS_ID"));
            pd.put("imgs", olopdproductService.findImgsById(imgPd));
            // 查询出标签
            PageData RXPd = new PageData();
            RXPd.put("P_BM", Const.SPMBTMBQ);
            pd.put("bq", dictionariesService.dictlist(RXPd));
            // 查询出上传图片路径
            PageData uploudImgPd = new PageData();
            uploudImgPd.put("BIANMA", Const.UPLOUADIMGPATH);
            uploudImgPd = dictionariesService.findBmCount(uploudImgPd);
            pd.put("imgBasePath", uploudImgPd.getString("NAME"));
            // 查询出风格
            pd.put("styles", olopdstyleService.listAll(new PageData()));
            // 查询出可以新增的属性
            pd.put("pdabval", olopdabvalService.listAll(new PageData()));
            JsonArray jsonArray = new JsonArray();
            Gson gson = new Gson();
            // 查询出商品已经选择好的属性
            List tempSkuPdab = olopdabskuService.findSKUAtribute(pd);
            pd.put("skuAtribute", tempSkuPdab);
            pd.put("skuAtributeJson", gson.toJson(tempSkuPdab));
            pd.put("listSku", gson.toJson(olopdskuService.listAll(pd)));
            
            
            //查询出相关分类
            PageData SPXQLM = new PageData();
            SPXQLM.put("P_BM", Const.SPMBTMSPXQLM);
            pd.put("SPXQLM", dictionariesService.dictlist(SPXQLM));
            
            
            mv.setViewName("olopdproduct/olopdproduct/olopdproduct_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);

        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/getPdabval")
    @ResponseBody
    public Object getPdabval() {
        logBefore(logger, "去修改Olopdproduct页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            Gson gson = new Gson();
            // 查询出商品已经选择好的属性
            List tempSkuPdab = olopdabskuService.findSKUAtribute(pd);
            map.put("skuAtributeJson", tempSkuPdab);
            // 查询出可以新增的属性
            map.put("pdabval", olopdabvalService.listAll(new PageData()));

            pd.put("msg", "ok");

        } catch (Exception e) {
            pd.put("msg", "no");
            logger.error(e.toString(), e);
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() {
        logBefore(logger, "批量删除Olopdproduct");
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
                olopdproductService.deleteAll(ArrayDATA_IDS);
                olopdmenuproductService.deleteGoodsIdAll(ArrayDATA_IDS);
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

   
    
    
    /*
     * 导出到excel
     * 
     * @return
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() {
        logBefore(logger, "导出Olopdproduct到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("商品标题"); // 1
            titles.add("短标题"); // 2
            titles.add("标签"); // 3
            titles.add("尺寸"); // 4
            titles.add("编码"); // 5
            titles.add("价格"); // 6
            titles.add("单位"); // 7
            titles.add("底价"); // 8
            titles.add("颜色"); // 9
            titles.add("商品分类(橱柜/款式/颜色)"); // 10
            titles.add("生效时间（商品展示生效日期格式 2017-12-10）"); // 11
            titles.add("封存商品 默认 0  不封存 1 封存"); // 12
            titles.add("商品详情"); // 13
            titles.add("排序号"); // 14
            titles.add("说明"); // 13
            dataMap.put("titles", titles);
            List<PageData> varOList = olopdproductService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {

                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("TITLE")); // 1
                vpd.put("var2", varOList.get(i).getString("SHORT_TITLE")); // 2
                vpd.put("var3", varOList.get(i).getString("LABEL")); // 3
                vpd.put("var4", varOList.get(i).getString("DIMENSION")); // 4
                vpd.put("var5", varOList.get(i).getString("CODE")); // 5
                vpd.put("var6", varOList.get(i).getString("PRICE")); // 6
                vpd.put("var7", varOList.get(i).getString("COMPANY")); // 7
                vpd.put("var8", varOList.get(i).getString("FLOOR_PRICE")); // 8
                vpd.put("var9", varOList.get(i).getString("COLOUR")); // 9
                vpd.put("var10", varOList.get(i).getString("CATEGORY")); // 10
                vpd.put("var11", varOList.get(i).getString("EFFECTIVE_TIME")); // 11
                vpd.put("var12", varOList.get(i).getString("SEALED")); // 12
                vpd.put("var13", varOList.get(i).getString("DETAIL")); // 13
                vpd.put("var14", varOList.get(i).getString("SORTING")); // 14
                vpd.put("var15", varOList.get(i).getString("REMARKS")); // 14
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

    /*
     * 导入
     * 
     * @return
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
        Map<String, List<String>> uplodImgMap = new HashMap<String, List<String>>();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
                tempfileDir = fs.getPath();
                uplodImgMap.put(fs.getName(), FileUtils.getFileName(fs.getPath()));
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
        for (String key : uplodImgMap.keySet()) {
            for (String filepath : uplodImgMap.get(key)) {

                list.add(fileDir + "/" + key + "/" + filepath);
            }
        }
        String str = HttpConnectionUtil.uploadFile(uploudUrl, list);

        JSONObject json = JSONObject.fromObject(str);

        JSONArray jArray = json.optJSONArray("filePaths");
        String uplodImgName = "";// 上传图片路径
        String productCode = "";// 上传图片编码
        String locationAndSort = "";// 位置和排序
        String sort = ""; // 排序
        String location = "";// 图片位置
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        String productCodes = "";// 所有商品编码
        List<String> listCodes = new ArrayList<String>();
        // 循环所有的返回路径
        for (int i = 0; i < jArray.size(); i++) {
            String imgPath = (String) jArray.get(i);
            // 取出已最后一个/和最后_之间的字符串
            uplodImgName = imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.lastIndexOf("_"));
            // 取出字符串最后一个-号之前的字符串，（商品编码）
            productCode = uplodImgName.substring(0, uplodImgName.lastIndexOf("-"));
            locationAndSort = uplodImgName.substring(uplodImgName.lastIndexOf("-") + 1);
            location = locationAndSort.substring(0, 1);
            sort = locationAndSort.replace(location, "");
            if (StringUtils.isEmpty(map.get(productCode))) {
                /**
                 * 把商品编码和商品图片的远程服务器路径放入以个map中 key为商品的编码， value为商品的远程服务器路径List结合
                 * List 为远程服务器路径加，号图片位置加，号图片的排序号
                 * 最后以个-之后和最后一个.号之前的字符串为图片展示显示位置规则。
                 * A0代表列表图片，A0~An（n代表一个整数）代表商品上部轮播大图， B1~Bn 代表商品上部轮播小图 C1~Cn
                 * 代表商品底部图片展示 数字代表图片展示顺序
                 **/
                listCodes.add(productCode);
                List<String> imgLists = new ArrayList<String>();
                imgLists.add(imgPath + "," + location + "," + sort);
                map.put(productCode, imgLists);
            } else {
                List<String> imgLists = map.get(productCode);
                imgLists.add(imgPath + "," + location + "," + sort);
                map.put(productCode, imgLists);
            }

        }
        // 一次通过商品唯一编码查询出所有对应的商品。
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

                    // 封面图片
                    if ("A".equals(strs[1].toUpperCase()) && "0".equals(strs[2])) {
                        cPageData.put("IMAGE_URL_LIST", strs[0]);
                        continue;
                    }
                    carousePd.put("LOCATION", OloProductImageLoaction.getValue(strs[1].toUpperCase()));// 图片路径

                    carousePd.put("SORTING", strs[2]);// 图片路径
                    carousePd.put("ID", tuID);// id
                    carousePd.put("CREATION_PEOPLE_ID", user.getUSER_ID());
                    carousePd.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                    carousePd.put("SPREAD1", codeStr);// 商品编码
                    carousePd.put("SPREAD2", GOODS_ID);// 商品ID
                    carousePd.put("SPREAD3", "");// 商品ID
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
            olopdproductService.edit(producctList);// 更新商品
        }

        FileUtils.delFolder(outputDirectory);// 最后删除本地图片
        // 处理分类关系
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /*
     * 导入
     * 
     * @return
     */
    @RequestMapping(value = "/inportExcel")
    public ModelAndView inportExcel(@RequestParam(required = false) MultipartFile file) throws Exception {
        logBefore(logger, "从excel导入Olopdproduct");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();

        String ffile = DateUtil.getDays(), fileName = "";
        PageData pd = new PageData();
        pd = this.getPageData();
        String filePath = "";// 文件上传路径
        if (null != file && !file.isEmpty()) {
            filePath = PathUtil.getClasspath() + Const.FILEPATHFILE + ffile; // 文件上传路径
            fileName = FileUpload.fileUp(file, filePath, this.get32UUID()); // 执行上传
        } else {
            throw new Exception("上传失败");
        }
        // shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
        // 解析xsl
        ObjectExcelRead err = new ObjectExcelRead();
        String title = "TITLE,SHORT_TITLE,LABEL,DIMENSION,CODE,PRICE:jdbcType=NUMERIC,COMPANY,FLOOR_PRICE:jdbcType=NUMERIC,COLOUR,CATEGORY,EFFECTIVE_TIME:jdbcType=TIMESTAMP,SEALED:jdbcType=NUMERIC,DETAIL,SORTING:jdbcType=NUMERIC,REMARKS";
        List<PageData> list = err.readExcel(filePath, fileName, title.split(","), 1, 0, 0);// 解析
        Iterator<PageData> it2 = list.iterator();
        while (it2.hasNext()) {
            PageData temp = it2.next();
            if (StringUtils.isEmpty(temp.getString("CATEGORY")) && StringUtils.isEmpty(temp.getString("CODE"))) {
                it2.remove();
                continue;
            }
        }
        List<PageData> listClassify = new ArrayList<PageData>();
        /*
         * for (PageData pageData : list) { pageData.put("GOODS_ID",
         * this.get32UUID()); // 主键 pageData.put("CREATION_PEOPLE_ID",
         * user.getUSER_ID()); pageData.put("CREATE_TIME",
         * java.sql.Timestamp.valueOf(DateUtil.getTime().toString())); String
         * CATEGORY = pageData.getString("CATEGORY");// 分类
         * listClassify.addAll(generateClassify(CATEGORY,
         * pageData.getString("GOODS_ID")));
         * 
         * }
         */

        Map<String, List<PageData>> map = new HashMap<String, List<PageData>>();
        for (PageData pageData : list) {
            if (StringUtils.isEmpty(pageData.getString("GOODS_ID"))) {
                pageData.put("GOODS_ID", this.get32UUID()); // 主键
            }
            pageData.put("CREATION_PEOPLE_ID", user.getUSER_ID());
            pageData.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
            String CATEGORY = pageData.getString("CATEGORY");// 分类
            if (!StringUtils.isEmpty(map.get(CATEGORY))) {
                map.get(CATEGORY).add(pageData);
                continue;
            }
            map.put(CATEGORY, new ArrayList<PageData>());
            map.get(CATEGORY).add(pageData);
        }

        for (String key : map.keySet()) {

            listClassify.addAll(generateClassify(key, map.get(key)));
        }
        if (!StringUtils.isEmpty(list) && list.size() > 0) {
            olopdproductService.insertByBatch(list);
        }
        if (!StringUtils.isEmpty(listClassify) && listClassify.size() > 0) {
            olopdmenuproductService.deleteGoodsIdAll(listClassify);
            olopdmenuproductService.insertByBatch(listClassify);
        }
        // 处理分类关系
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;

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
                classifyOne = pd;
                oldpd.put("ONE_SELECT", pd.getString("M_NAME") + "," + pd.getString("CID"));
            }
        }
        PageData classifyTwo = new PageData();
        if (classifys.length >= 2) {
            // 查询第二级分类
            pData.put("M_NAME", classifys[1]);
            pData.put("M_FID", classifyOne.getString("CID"));

            List<PageData> list = olopdmenuService.queryMenub(pData);

            if (list != null && list.size() > 0) {
                PageData pd = list.get(0);
                classifyTwo = pd;
                oldpd.put("TWO_SELECT", pd.getString("M_NAME") + "," + pd.getString("CID"));
            }
        }
        PageData classifyTHREE = new PageData();
        if (classifys.length >= 3) {
            // 查询第三级分类
            pData.put("M_NAME", classifys[2]);
            pData.put("M_FID", classifyTwo.getString("CID"));
            List<PageData> list = olopdmenuService.queryMenuc(pData);

            if (list != null && list.size() > 0) {
                PageData pd = list.get(0);
                classifyTHREE = pd;
                oldpd.put("THREE_SELECT", pd.getString("M_NAME") + "," + pd.getString("CID"));
            }
        }
        if (classifys.length >= 4) {
            // 查询第四级分类
            pData.put("M_NAME", classifys[3]);
            pData.put("M_FID", classifyTHREE.getString("CID"));
            List<PageData> list = olopdmenuService.queryMenud(pData);
            if (list != null && list.size() > 0) {
                PageData pd = list.get(0);
                oldpd.put("FOUR_SELECT", pd.getString("M_NAME") + "," + pd.getString("CID"));
            }
        }
        return oldpd;

    }

    public List<PageData> generateClassify(String classify, String oId) throws Exception {
        String[] classifys = classify.split("/");
        PageData pData = new PageData();
        List<PageData> listClassify = new ArrayList<PageData>();
        PageData classifyOne = new PageData();
        if (classifys.length >= 1) {
            // 查询第一级分类
            pData.put("M_NAME", classifys[0]);
            List<PageData> list = olopdmenuService.queryMenua(pData);

            if (list != null && list.size() > 0) {
                PageData pd = list.get(0);
                classifyOne = pd;
                PageData tempData = new PageData();
                tempData.put("ID", this.get32UUID());
                tempData.put("T_ID", OlopdmenuUtils.getHaseCode(pd, "queryMenua"));
                tempData.put("GOODS_ID", oId);
                tempData.put("SKU_ID", "");
                tempData.put("INSERT_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                listClassify.add(tempData);
            }
        }
        PageData classifyTwo = new PageData();
        if (classifys.length >= 2) {
            // 查询第二级分类
            pData.put("M_NAME", classifys[1]);
            pData.put("M_FID", classifyOne.getString("CID"));

            List<PageData> list = olopdmenuService.queryMenub(pData);

            if (list != null && list.size() > 0) {
                PageData pd = list.get(0);
                classifyTwo = pd;
                PageData tempData = new PageData();
                tempData.put("ID", this.get32UUID());
                tempData.put("T_ID", OlopdmenuUtils.getHaseCode(pd, "queryMenub"));
                tempData.put("GOODS_ID", oId);
                tempData.put("SKU_ID", "");
                tempData.put("INSERT_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                listClassify.add(tempData);
            }
        }
        PageData classifyTHREE = new PageData();
        if (classifys.length >= 3) {
            // 查询第三级分类
            pData.put("M_NAME", classifys[2]);
            pData.put("M_FID", classifyTwo.getString("CID"));
            List<PageData> list = olopdmenuService.queryMenuc(pData);

            if (list != null && list.size() > 0) {
                PageData pd = list.get(0);
                classifyTHREE = pd;
                PageData tempData = new PageData();
                tempData.put("ID", this.get32UUID());
                tempData.put("T_ID", OlopdmenuUtils.getHaseCode(pd, "queryMenuc"));
                tempData.put("GOODS_ID", oId);
                tempData.put("SKU_ID", "");
                tempData.put("INSERT_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                listClassify.add(tempData);
            }
        }
        if (classifys.length >= 4) {
            // 查询第四级分类
            pData.put("M_NAME", classifys[3]);
            pData.put("M_FID", classifyTHREE.getString("CID"));
            List<PageData> list = olopdmenuService.queryMenud(pData);
            if (list != null && list.size() > 0) {
                PageData pd = list.get(0);
                PageData tempData = new PageData();
                tempData.put("ID", this.get32UUID());
                tempData.put("T_ID", OlopdmenuUtils.getHaseCode(pd, "queryMenud"));
                tempData.put("GOODS_ID", oId);
                tempData.put("SKU_ID", "");
                tempData.put("INSERT_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                listClassify.add(tempData);
            }
        }
        return listClassify;

    }

    public List<PageData> generateClassify(String classify, List<PageData> pageData) throws Exception {
        String[] classifys = classify.split("/");
        PageData pData = new PageData();
        List<PageData> listClassify = new ArrayList<PageData>();
        PageData classifyOne = new PageData();
        if (classifys.length >= 1) {
            // 查询第一级分类
            pData.put("M_NAME", classifys[0]);
            List<PageData> list = olopdmenuService.queryMenua(pData);

            if (list != null && list.size() > 0) {
                PageData pd = list.get(0);
                classifyOne = pd;
                for (PageData tempd : pageData) {
                    PageData tempData = new PageData();
                    tempData.put("ID", this.get32UUID());
                    tempData.put("T_ID", OlopdmenuUtils.getHaseCode(pd, "queryMenua"));
                    tempData.put("GOODS_ID", tempd.getString("GOODS_ID"));
                    tempData.put("SKU_ID", "");
                    tempData.put("INSERT_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                    listClassify.add(tempData);
                }
            }
        }
        PageData classifyTwo = new PageData();
        if (classifys.length >= 2) {
            // 查询第二级分类
            pData.put("M_NAME", classifys[1]);
            pData.put("M_FID", classifyOne.getString("CID"));

            List<PageData> list = olopdmenuService.queryMenub(pData);

            if (list != null && list.size() > 0) {
                PageData pd = list.get(0);
                classifyTwo = pd;
                for (PageData tempd : pageData) {
                    PageData tempData = new PageData();
                    tempData.put("ID", this.get32UUID());
                    tempData.put("T_ID", OlopdmenuUtils.getHaseCode(pd, "queryMenub"));
                    tempData.put("GOODS_ID", tempd.getString("GOODS_ID"));
                    tempData.put("SKU_ID", "");
                    tempData.put("INSERT_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                    listClassify.add(tempData);
                }
            }
        }
        PageData classifyTHREE = new PageData();
        if (classifys.length >= 3) {
            // 查询第三级分类
            pData.put("M_NAME", classifys[2]);
            pData.put("M_FID", classifyTwo.getString("CID"));
            List<PageData> list = olopdmenuService.queryMenuc(pData);

            if (list != null && list.size() > 0) {
                PageData pd = list.get(0);
                classifyTHREE = pd;
                for (PageData tempd : pageData) {
                    PageData tempData = new PageData();
                    tempData.put("ID", this.get32UUID());
                    tempData.put("T_ID", OlopdmenuUtils.getHaseCode(pd, "queryMenuc"));
                    tempData.put("GOODS_ID", tempd.getString("GOODS_ID"));
                    tempData.put("SKU_ID", "");
                    tempData.put("INSERT_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                    listClassify.add(tempData);
                }
            }
        }
        if (classifys.length >= 4) {
            // 查询第四级分类
            pData.put("M_NAME", classifys[3]);
            pData.put("M_FID", classifyTHREE.getString("CID"));
            List<PageData> list = olopdmenuService.queryMenud(pData);
            if (list != null && list.size() > 0) {
                PageData pd = list.get(0);
                for (PageData tempd : pageData) {
                    PageData tempData = new PageData();
                    tempData.put("ID", this.get32UUID());
                    tempData.put("T_ID", OlopdmenuUtils.getHaseCode(pd, "queryMenud"));
                    tempData.put("GOODS_ID", tempd.getString("GOODS_ID"));
                    tempData.put("SKU_ID", "");
                    tempData.put("INSERT_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
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
