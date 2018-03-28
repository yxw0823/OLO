package com.fh.controller.olo.olopdflfile;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.olo.util.OlopdmenuUtils;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.FileUtil;
import com.fh.util.HttpConnectionUtil;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.PolyvUtil;
import com.fh.util.StringUtils;
import com.fh.util.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fh.util.Jurisdiction;
import com.fh.service.olo.olopdflfile.OlopdflfileService;
import com.fh.service.olopdmenu.olopdmenu.OlopdmenuService;
import com.fh.service.system.dictionaries.DictionariesService;

/**
 * 类名称：OlopdflfileController 创建人：FH 创建时间：2018-03-18
 */
@Controller
@RequestMapping(value = "/olopdflfile")
public class OlopdflfileController extends BaseController {

    String menuUrl = "olopdflfile/list.do"; // 菜单地址(权限用)

    @Resource(name = "olopdflfileService")
    private OlopdflfileService olopdflfileService;

    @Resource(name = "dictionariesService")
    private DictionariesService dictionariesService; // 数据字典

    @Resource(name = "olopdmenuService")
    private OlopdmenuService olopdmenuService; // 分类

    /**
     * 新增
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(@RequestParam Map<String, String> map,
            @RequestParam(required = false, value = "file") MultipartFile file) throws Exception {
        logBefore(logger, "新增Olopdflfile");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } // 校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        // pd = this.getPageData();
        pd.putAll(map);
        String ffile = DateUtil.getDays(), fileName = "";
        String filePath = "";// 文件上传路径
        String fileNameUUid = this.get32UUID();
        // 校验权限
        if (null != file && !file.isEmpty()) {
            filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
            fileName = FileUpload.fileUp(file, filePath, fileNameUUid); // 执行上传
            // 上传到远程服务器
            PageData findBmPd = new PageData();
            findBmPd.put("BIANMA", "PATH"); // 图片上传路径
            PageData bmData = dictionariesService.findBmCount(findBmPd);
            String upPath = bmData.getString("NAME");
            if (!StringUtils.isEmpty(upPath)) {
                List<String> list = new ArrayList<String>();
                list.add(filePath + "/" + fileName);
                if ("1".equals(pd.getString("FILETYPE"))) {
                    // 如果是视频直接上传到保利威视
                    String vid = PolyvUtil.getPolyvUtil().resumableUpload(filePath + "/" + fileName,
                            pd.getString("SPREAD1"), "", "", 1521592877387L);
                    pd.put("FILE_PATH", vid);

                } else {
                    String str = HttpConnectionUtil.uploadFile(upPath, list);
                    JSONObject jsonObject = JSONObject.fromObject(str);
                    JSONArray jArray = jsonObject.optJSONArray("filePaths");
                    for (int i = 0; i < jArray.size(); i++) {
                        String imgPath = (String) jArray.get(i);
                        pd.put("FILE_PATH", imgPath);
                        // 路径
                    }
                }

            }
        }
        FileUtil.delFile(filePath + "/" + fileName);
        pd.put("FILE_ID", this.get32UUID()); // 主键

        // shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
        if (user != null) {
            pd.put("CREATION_PEOPLE_ID", user.getUSER_ID());
            pd.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
        }
        setFlId(pd);
        olopdflfileService.save(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) {
        logBefore(logger, "删除Olopdflfile");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } // 校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            olopdflfileService.delete(pd);
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
    public ModelAndView edit(@RequestParam Map<String, String> map,
            @RequestParam(required = false, value = "file") MultipartFile file) throws Exception {
        logBefore(logger, "修改Olopdflfile");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } // 校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        // pd = this.getPageData();
        pd.putAll(map);
        String ffile = DateUtil.getDays(), fileName = "";
        String filePath = "";// 文件上传路径
        String fileNameUUid = this.get32UUID();
        // 校验权限
        if (null != file && !file.isEmpty()) {
            filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
            fileName = FileUpload.fileUp(file, filePath, fileNameUUid); // 执行上传
            // 上传到远程服务器
            PageData findBmPd = new PageData();
            findBmPd.put("BIANMA", "PATH"); // 图片上传路径
            PageData bmData = dictionariesService.findBmCount(findBmPd);
            String upPath = bmData.getString("NAME");
            if (!StringUtils.isEmpty(upPath)) {
                List<String> list = new ArrayList<String>();
                list.add(filePath + "/" + fileName);
                if ("1".equals(pd.getString("FILETYPE"))) {
                    // 如果是视频直接上传到保利威视
                    String vid = PolyvUtil.getPolyvUtil().resumableUpload(filePath + "/" + fileName,
                            pd.getString("SPREAD1"), "", "", 1521592877387L);
                    pd.put("FILE_PATH", vid);

                } else {
                    String str = HttpConnectionUtil.uploadFile(upPath, list);
                    JSONObject jsonObject = JSONObject.fromObject(str);
                    JSONArray jArray = jsonObject.optJSONArray("filePaths");
                    for (int i = 0; i < jArray.size(); i++) {
                        String imgPath = (String) jArray.get(i);
                        pd.put("FILE_PATH", imgPath);
                        // 路径
                    }
                }

            }
        }
        FileUtil.delFile(filePath + "/" + fileName);
        // shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
        if (user != null) {
            pd.put("UPDATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
            pd.put("UPDATE_PEOPLE_ID", user.getUSER_ID());
        }

        setFlId(pd);
        olopdflfileService.edit(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "列表Olopdflfile");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        // //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            page.setPd(pd);
            List<PageData> varList = olopdflfileService.list(page); // 列出Olopdflfile列表
            mv.setViewName("olo/olopdflfile/olopdflfile_list");
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
        logBefore(logger, "去新增Olopdflfile页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            // 查询出标签
            PageData RXPd = new PageData();
            RXPd.put("P_BM", Const.SPMBTMFLML);
            pd.put("flml", dictionariesService.dictlist(RXPd));
            mv.setViewName("olo/olopdflfile/olopdflfile_edit");
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
        logBefore(logger, "去修改Olopdflfile页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {

            pd = olopdflfileService.findById(pd); // 根据ID读取

            // 查询出标签
            PageData RXPd = new PageData();
            RXPd.put("P_BM", Const.SPMBTMFLML);
            pd.put("flml", dictionariesService.dictlist(RXPd));
            String flid = pd.getString("SPREAD4");
            if (!StringUtils.isEmpty(flid)) {
                String[] flids = flid.split(":");
                pd.put("ONE_SELECT", flids[0]);
                if (flids.length > 1) {
                    pd.put("TWO_SELECT", flids[1]);
                }
                if (flids.length > 2) {
                    pd.put("THREE_SELECT", flids[2]);
                }
                if (flids.length > 3) {
                    pd.put("FOUR_SELECT", flids[3]);
                }
            }

            mv.setViewName("olo/olopdflfile/olopdflfile_edit");
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
        logBefore(logger, "批量删除Olopdflfile");
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
                olopdflfileService.deleteAll(ArrayDATA_IDS);
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

    public void setFlId(PageData pd) throws Exception {

        String ONE_SELECT = pd.getString("ONE_SELECT");
        String TWO_SELECT = pd.getString("TWO_SELECT");
        String THREE_SELECT = pd.getString("THREE_SELECT");
        String FOUR_SELECT = pd.getString("FOUR_SELECT");
        String CATEGORY = "";
        if (!StringUtils.isEmpty(ONE_SELECT) && !"请选择".equals(ONE_SELECT)) {
            CATEGORY += ONE_SELECT.split(",")[0];
            pd.put("SPREAD4", ONE_SELECT);
        }
        if (!StringUtils.isEmpty(TWO_SELECT) && !"请选择".equals(TWO_SELECT)) {
            CATEGORY += "/" + TWO_SELECT.split(",")[0];
            pd.put("SPREAD4", pd.getString("SPREAD4") + ":" + TWO_SELECT);
        }
        if (!StringUtils.isEmpty(THREE_SELECT) && !"请选择".equals(THREE_SELECT)) {
            CATEGORY += "/" + THREE_SELECT.split(",")[0];
            pd.put("SPREAD4", pd.getString("SPREAD4") + ":" + THREE_SELECT);
        }
        if (!StringUtils.isEmpty(FOUR_SELECT) && !"请选择".equals(FOUR_SELECT)) {
            CATEGORY += "/" + FOUR_SELECT.split(",")[0];
            pd.put("SPREAD4", pd.getString("SPREAD4") + ":" + FOUR_SELECT);
        }
        pd.put("SPREAD2", CATEGORY);
        generateClassify(CATEGORY, pd);
    }

    public void generateClassify(String classify, PageData pd) throws Exception {
        String[] classifys = classify.split("/");
        PageData pData = new PageData();
        List<PageData> listClassify = new ArrayList<PageData>();
        PageData classifyOne = new PageData();
        if (classifys.length >= 1) {
            // 查询第一级分类
            pData.put("M_NAME", classifys[0]);
            List<PageData> list = olopdmenuService.queryMenua(pData);
            if (list != null && list.size() > 0) {
                PageData newpd = list.get(0);
                classifyOne = newpd;
                String id = OlopdmenuUtils.getHaseCode(newpd, "queryMenua");
                pd.put("FL_ID", id);
                pd.put("SPREAD3", id);
            }
        }
        PageData classifyTwo = new PageData();
        if (classifys.length >= 2) {
            // 查询第二级分类
            pData.put("M_NAME", classifys[1]);
            pData.put("M_FID", classifyOne.getString("CID"));

            List<PageData> list = olopdmenuService.queryMenub(pData);

            if (list != null && list.size() > 0) {
                PageData newpd = list.get(0);
                classifyTwo = newpd;
                String id = OlopdmenuUtils.getHaseCode(newpd, "queryMenub");
                pd.put("FL_ID", id);
                pd.put("SPREAD3", pd.getString("SPREAD3") + "," + id);
            }
        }
        PageData classifyTHREE = new PageData();
        if (classifys.length >= 3) {
            // 查询第三级分类
            pData.put("M_NAME", classifys[2]);
            pData.put("M_FID", classifyTwo.getString("CID"));
            List<PageData> list = olopdmenuService.queryMenuc(pData);

            if (list != null && list.size() > 0) {
                PageData newpd = list.get(0);
                classifyTHREE = newpd;
                String id = OlopdmenuUtils.getHaseCode(newpd, "queryMenuc");
                pd.put("FL_ID", id);
                pd.put("SPREAD3", pd.getString("SPREAD3") + "," + id);
            }
        }
        if (classifys.length >= 4) {
            // 查询第四级分类
            pData.put("M_NAME", classifys[3]);
            pData.put("M_FID", classifyTHREE.getString("CID"));
            List<PageData> list = olopdmenuService.queryMenud(pData);
            if (list != null && list.size() > 0) {
                PageData newpd = list.get(0);
                String id = OlopdmenuUtils.getHaseCode(newpd, "queryMenud");
                pd.put("FL_ID", id);
                pd.put("SPREAD3", pd.getString("SPREAD3") + "," + id);
            }
        }

    }

    /*
     * 导出到excel
     * 
     * @return
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() {
        logBefore(logger, "导出Olopdflfile到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("文件路径"); // 1
            titles.add("文件类型"); // 2
            titles.add("分类ID"); // 3
            titles.add("目录ID"); // 4
            titles.add("排序"); // 5
            titles.add("封存文件  默认 0  不封存 1 封存"); // 6
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
            List<PageData> varOList = olopdflfileService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("FILE_PATH")); // 1
                vpd.put("var2", varOList.get(i).getString("FILETYPE")); // 2
                vpd.put("var3", varOList.get(i).getString("FL_ID")); // 3
                vpd.put("var4", varOList.get(i).getString("ZD_ID")); // 4
                vpd.put("var5", varOList.get(i).getString("SORTING")); // 5
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
