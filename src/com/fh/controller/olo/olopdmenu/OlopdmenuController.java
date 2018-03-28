package com.fh.controller.olo.olopdmenu;

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
import com.fh.service.olopdmenu.olopdmenu.OlopdmenuService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtils;

/**
 * 类名称：OlopdmenuController 创建人：FH 创建时间：2017-12-05
 */
@Controller
@RequestMapping(value = "/olopdmenu")
public class OlopdmenuController extends BaseController {

    String menuUrl = "olopdmenu/list.do"; // 菜单地址(权限用)

    @Resource(name = "olopdmenuService")
    private OlopdmenuService olopdmenuService;

    @Resource(name = "dictionariesService")
    private DictionariesService dictionariesService; // 数据字典

    /**
     * 查询
     */
    @RequestMapping(value = "/queryMenu")
    @ResponseBody
    public Object queryMenu() throws Exception {
        logBefore(logger, "查询分类");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        map.put("state", "ok");
        String queryID = pd.getString("queryID");
        String M_FID = pd.getString("M_FID");

        if (!StringUtils.isEmpty(M_FID)) {
            String queryIDMFid = pd.getString("queryIDMFid");
            if(!StringUtils.isEmpty( queryIDMFid)){
                queryID =queryIDMFid;
            }
            if (M_FID.indexOf(",") != -1) {
                M_FID = M_FID.substring(M_FID.indexOf(",") + 1);
            }
            pd.put("M_FID", M_FID);
        }
        if ("queryMenua".equals(queryID)) {
            map.put("result", olopdmenuService.queryMenua(pd));
            return AppUtil.returnObject(pd, map);
        }
        if ("queryMenub".equals(queryID)) {

            if (StringUtils.isEmpty(M_FID)) {
                map.put("result", new ArrayList<PageData>());
                return AppUtil.returnObject(pd, map);
            }
            map.put("result", olopdmenuService.queryMenub(pd));
            return AppUtil.returnObject(pd, map);
        }
        if ("queryMenuc".equals(queryID)) {
            if (StringUtils.isEmpty(M_FID)) {
                map.put("result", new ArrayList<PageData>());
                return AppUtil.returnObject(pd, map);
            }
            map.put("result", olopdmenuService.queryMenuc(pd));
            return AppUtil.returnObject(pd, map);
        }
        if (StringUtils.isEmpty(M_FID)) {
            map.put("result", new ArrayList<PageData>());
            return AppUtil.returnObject(pd, map);
        }
        map.put("result", olopdmenuService.queryMenud(pd));
        return AppUtil.returnObject(pd, map);

    }

    /**
     * 查询
     */
    @RequestMapping(value = "/queryMenua")
    @ResponseBody
    public Object queryMenua() throws Exception {
        logBefore(logger, "新增Pictures");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        map.put("state", "ok");
        map.put("result", olopdmenuService.queryMenua(pd));
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 查询
     */
    @RequestMapping(value = "/queryMenub")
    @ResponseBody
    public Object queryMenub() throws Exception {
        logBefore(logger, "查询二级菜单");
        Map<String, Object> map = new HashMap<String, Object>();
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        map.put("state", "ok");
        map.put("result", olopdmenuService.queryMenub(pd));
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 查询
     */
    @RequestMapping(value = "/queryMenuc")
    @ResponseBody
    public Object queryMenuc() throws Exception {
        logBefore(logger, "查询三级菜单");
        Map<String, Object> map = new HashMap<String, Object>();
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        map.put("state", "ok");
        map.put("result", olopdmenuService.queryMenuc(pd));
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 查询
     */
    @RequestMapping(value = "/queryMenud")
    @ResponseBody
    public Object queryMenud() throws Exception {
        logBefore(logger, "查询四级菜单");
        Map<String, Object> map = new HashMap<String, Object>();
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        map.put("state", "ok");
        map.put("result", olopdmenuService.queryMenud(pd));
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        logBefore(logger, "新增Olopdmenu");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } // 校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("ID", this.get32UUID()); // 主键
        olopdmenuService.save(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) {
        logBefore(logger, "删除Olopdmenu");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } // 校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            olopdmenuService.delete(pd);
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
        logBefore(logger, "修改Olopdmenu");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } // 校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        olopdmenuService.edit(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "列表Olopdmenu");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        // //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            page.setPd(pd);
            List<PageData> varList = olopdmenuService.list(page); // 列出Olopdmenu列表
            // 查询出上传图片路径
            PageData uploudImgPd = new PageData();
            uploudImgPd.put("BIANMA", Const.UPLOUADIMGPATH);
            uploudImgPd = dictionariesService.findBmCount(uploudImgPd);
            pd.put("imgBasePath", uploudImgPd.getString("NAME"));
            mv.setViewName("olopdmenu/olopdmenu/olopdmenu_list");
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
        logBefore(logger, "去新增Olopdmenu页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("olopdmenu/olopdmenu/olopdmenu_edit");
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
        logBefore(logger, "去修改Olopdmenu页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = olopdmenuService.findById(pd); // 根据ID读取
            mv.setViewName("olopdmenu/olopdmenu/olopdmenu_edit");
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
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() {
        logBefore(logger, "批量删除Olopdmenu");
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
                olopdmenuService.deleteAll(ArrayDATA_IDS);
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
        logBefore(logger, "导出Olopdmenu到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("记录父节点"); // 1
            titles.add("节点全路径(1/2/3)"); // 2
            titles.add("节点类型"); // 3
            titles.add("封存商品 默认 0  不封存 1 封存"); // 4
            titles.add("节点名称"); // 5
            titles.add("节点英文名"); // 6
            titles.add("图片路径"); // 7
            titles.add("说明"); // 8
            dataMap.put("titles", titles);
            List<PageData> varOList = olopdmenuService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("PARENT_ID")); // 1
                vpd.put("var2", varOList.get(i).getString("PATH")); // 2
                vpd.put("var3", varOList.get(i).getString("TYPE")); // 3
                vpd.put("var4", varOList.get(i).getString("SEALED")); // 4
                vpd.put("var5", varOList.get(i).getString("NAME")); // 5
                vpd.put("var6", varOList.get(i).getString("ENGLISHNAME")); // 6
                vpd.put("var7", varOList.get(i).getString("SRC")); // 7
                vpd.put("var8", varOList.get(i).getString("REMARKS")); // 8
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
