package com.fh.controller.olo.olowebInterface.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fh.cache.Cache;
import com.fh.cache.CacheManager;
import com.fh.controller.base.BaseController;
import com.fh.controller.olo.olopdcarousel.e.OlopdcarouselType;
import com.fh.controller.olo.olowebInterface.IwebInterface;
import com.fh.controller.olo.olowebInterface.e.WeblInterFaceQueryType;
import com.fh.controller.olo.util.OlopdmenuUtils;
import com.fh.ecology.ConFirmAutoCreate;
import com.fh.entity.Page;
import com.fh.service.olo.npinformationsubject.NpinformationsubjectService;
import com.fh.service.olo.olopdabrelation.OlopdabrelationService;
import com.fh.service.olo.olopdabsku.OlopdabskuService;
import com.fh.service.olo.olopdcarousel.OlopdcarouselService;
import com.fh.service.olo.olopddp.OlopddpService;
import com.fh.service.olo.olopddpother.OlopddpotherService;
import com.fh.service.olo.olopdfile.OlopdfileService;
import com.fh.service.olo.olopdflfile.OlopdflfileService;
import com.fh.service.olo.olopdprfile.OlopdprfileService;
import com.fh.service.olo.olopdsku.OlopdskuService;
import com.fh.service.olo.olopdstyle.OlopdstyleService;
import com.fh.service.olo.viewolouserinfo.ViewolouserinfoService;
import com.fh.service.olopdmenu.olopdmenu.OlopdmenuService;
import com.fh.service.olopdproduct.olopdproduct.OlopdproductService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.FileUpload;
import com.fh.util.HttpClientMapUtils;
import com.fh.util.HttpConnectionUtil;
import com.fh.util.MD5;
import com.fh.util.MD5Util;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/appweb")
public class WebInterFace extends BaseController implements IwebInterface {

    @Resource(name = "viewolouserinfoService")
    private ViewolouserinfoService viewolouserinfoService;

    @Resource(name = "olopdmenuService")
    private OlopdmenuService olopdmenuService;

    @Resource(name = "dictionariesService")
    private DictionariesService dictionariesService; // 数据字典

    @Resource(name = "olopdabrelationService")
    private OlopdabrelationService olopdabrelationService; // 图片商品关系表

    @Resource(name = "olopddpotherService")
    private OlopddpotherService olopddpotherService; // 台面

    @Resource(name = "olopddpService")
    private OlopddpService olopddpService; // 门板

    @Resource(name = "olopdproductService")
    private OlopdproductService olopdproductService;// 商品

    @Resource(name = "olopdcarouselService")
    private OlopdcarouselService olopdcarouselService;

    @Resource(name = "olopdstyleService")
    private OlopdstyleService olopdstyleService;// 风格
    
    
    @Resource(name="olopdskuService")
    private OlopdskuService olopdskuService; //SKU管理
    
    @Resource(name = "olopdabskuService")
    private OlopdabskuService olopdabskuService; //SKU属性关系管理
    
    @Resource(name="olopdfileService")
    private OlopdfileService olopdfileService; //下载中心

    /**
     * 验证码保存
     */
    private String CACHE_CODE = "CACHE_CODE";

    /**
     * 获取二维码
     */
    @RequestMapping(value = "/getCodeKey")
    @ResponseBody
    @Override
    public Object getVerificationCode() {

        logBefore(logger, "获取验证码KEY");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");

        String requestSign = pd.getString("sing");

        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            if (AppUtil.checkParam("registered", pd) || true) // 接口参数验证，暂时不加
            {

                try {
                    String code = "";
                    for (int i = 0; i < 4; i++) {
                        code += randomChar();
                    }
                    String token = this.get32UUID();
                    CacheManager.putCacheInfo(token, code, 1000 * 60 * 10);
                    map.put("token", token);
                    map.put("code", code);
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    map.put("result", result);
                    logAfter(logger);
                }
            }
        } else {
            result = "05";
        }
        if (!StringUtils.isEmpty(callback)) {
            pd.put("callback", callback);
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/code")
    public void Object(HttpServletResponse response, String token) {
        logBefore(logger, "获取验证码");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
            if (AppUtil.checkParam("registered", pd) || true) // 接口参数验证，暂时不加
            {
                try {
                    if (!StringUtils.isEmpty(callback)) {
                        pd.put("callback", callback);
                    }
                    String code = CacheManager.getCacheInfoString(token);
                    if (StringUtils.isEmpty(code)) {
                        map.put("result", "99");
                        map.put("msg", "验证码失效!");
                        this.responseJson(response, map);
                        return;
                    }
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    drawImg(output, code);
                    try {

                        ServletOutputStream out = response.getOutputStream();
                        output.writeTo(out);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    map.put("result", result);
                    logAfter(logger);
                }
            }
        } else {
            result = "05";
        }
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/validateCode")
    @Override
    @ResponseBody
    public Object validateCode() {
        // TODO Auto-generated method stub

        logBefore(logger, "获取验证码");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8")) {
            if (AppUtil.checkParam("registered", pd) || true) // 接口参数验证，暂时不加
            {
                try {
                    if (!StringUtils.isEmpty(callback)) {
                        pd.put("callback", callback);
                    }
                    String webCode = pd.getString("code");
                    String token = pd.getString("token");
                    String code = CacheManager.getCacheInfoString(token);
                    if (StringUtils.isEmpty(code)) {
                        map.put("result", "99");
                        map.put("msg", "服务器验证码已经失效!");
                        // this.responseJson(this.getResponse(), map);
                        return AppUtil.returnObject(pd, map);
                    }
                    if (StringUtils.isEmpty(webCode)) {
                        map.put("result", "99");
                        map.put("msg", "请输入验证码");
                        return AppUtil.returnObject(pd, map);
                    }
                    if (webCode.equals(code)) {
                        map.put("result", "01");
                        map.put("msg", true);
                        return AppUtil.returnObject(pd, map);
                    }
                    map.put("result", "99");
                    map.put("msg", false);
                    return AppUtil.returnObject(pd, map);
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    map.put("result", result);
                    logAfter(logger);
                }
            }
        } else {
            result = "05";
        }

        return AppUtil.returnObject(pd, map);
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login")
    @Override
    @ResponseBody
    public Object login() {
        // TODO Auto-generated method stub

        logBefore(logger, "登陆");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
            if (AppUtil.checkParam("webappLogin", pd)) {
                try {
                    if (!StringUtils.isEmpty(callback)) {
                        pd.put("callback", callback);
                    }
                    String webCode = pd.getString("code");
                    String token = pd.getString("token");
                    String code = "";
                    try {
                        code = CacheManager.getCacheInfoString(token);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        // e.printStackTrace();
                    }

                    if (StringUtils.isEmpty(code)) {
                        map.put("result", "99");
                        map.put("msg", "服务器验证码已经失效!");
                        // this.responseJson(this.getResponse(), map);
                        return AppUtil.returnObject(pd, map);
                    }
                    if (StringUtils.isEmpty(webCode)) {
                        map.put("result", "99");
                        map.put("msg", "请输入验证码");
                        return AppUtil.returnObject(pd, map);
                    }
                    if (webCode.equals(code)) {

                        String PASSWORD = pd.getString("PASSWORD");
                        PASSWORD = MD5.md5(PASSWORD);
                        pd.put("PASSWORD", PASSWORD.toUpperCase());

                        PageData pdOloUser = this.viewolouserinfoService.findById(pd);
                        if (StringUtils.isEmpty(pdOloUser)) {
                            map.put("result", "99");
                            map.put("msg", "用户名密码不正确");
                            return AppUtil.returnObject(pd, map);
                        }
                        if (!StringUtils.isEmpty(pdOloUser)) {
                            CacheManager.putCacheInfo(token, pdOloUser, 1000 * 60 * 60 * 24 * 3);// 缓存3天
                            map.put("result", "01");
                            map.put("token", token);
                            map.put("LOGINID", pdOloUser.getString("LOGINID"));
                            map.put("JZ", pdOloUser.getString("JZ"));
                            map.put("JXSPOST", pdOloUser.getString("JXSPOST"));
                            map.put("ROLE", pdOloUser.getString("ROLE"));
                            map.put("SUBCOMPANYID1", pdOloUser.getString("SUBCOMPANYID1"));
                            map.put("ISINVESTOR", pdOloUser.getString("ISINVESTOR"));
                            map.put("SUBCOMPANYNAME", pdOloUser.getString("SUBCOMPANYNAME"));
                            map.put("LASTNAME", pdOloUser.getString("LASTNAME"));
                            // map.put("user", CacheManager.getCacheInfo(key));
                            return AppUtil.returnObject(pd, map);
                        }

                    }
                    map.put("result", "99");
                    map.put("msg", false);
                    return AppUtil.returnObject(pd, map);
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    // map.put("result", result);
                    logAfter(logger);
                }
            } else {
                map.put("result", "99");
                map.put("msg", "缺少必须的提交参数");
                return AppUtil.returnObject(pd, map);
            }
        } else {
            result = "05";
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 
     * 
     * loginOut(退出)
     * 
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
    @RequestMapping(value = "/loginOut")
    @Override
    @ResponseBody
    public Object loginOut() {
        // TODO Auto-generated method stub

        logBefore(logger, "退出");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
            try {
                if (!StringUtils.isEmpty(callback)) {
                    pd.put("callback", callback);
                }
                String token = pd.getString("token");
                if (!StringUtils.isEmpty(token)) {
                    CacheManager.clearOnly(token);
                    map.put("result", "01");
                    map.put("msg", true);
                    return AppUtil.returnObject(pd, map);
                }
                map.put("result", "99");
                map.put("msg", false);
                return AppUtil.returnObject(pd, map);
            } catch (Exception e) {
                logger.error(e.toString(), e);
            } finally {
                // map.put("result", result);
                logAfter(logger);
            }
        } else {
            map.put("result", "99");
            map.put("msg", "缺少必须的提交参数");
            return AppUtil.returnObject(pd, map);
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 获取主页
     */
    @RequestMapping(value = "/mainPage")
    @Override
    @ResponseBody
    public Object mainPage() {

        logBefore(logger, "获取首页");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        String token = pd.getString("token");
        if (StringUtils.isEmpty(token)) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "缺少必须参数key");
            return AppUtil.returnObject(pd, map);
        }

        Cache cache = CacheManager.getCacheInfo(token);
        if (StringUtils.isEmpty(cache)) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            return AppUtil.returnObject(pd, map);
        }
        Object o = cache.getValue();
        if (o instanceof String) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            return AppUtil.returnObject(pd, map);
        }
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
            if (AppUtil.checkParam("webappLogin", pd) || true) // 接口参数验证，暂时不加
            {
                try {
                    if (!StringUtils.isEmpty(callback)) {
                        pd.put("callback", callback);
                    }
                    map.put("result", "01");
                    map.put("data", getClassifyTree(olopdmenuService.queryMenua(pd), "queryMenua"));
                    // 查询出所有分类下的封面图片和轮播图片
                    return AppUtil.returnObject(pd, map);

                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    // map.put("result", result);
                    logAfter(logger);
                }
            } else {
                map.put("result", "99");
                map.put("msg", "缺少必须的提交参数");
                return AppUtil.returnObject(pd, map);
            }
        } else {
            result = "05";
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 获取分类
     */
    @RequestMapping(value = "/getClassify")
    @Override
    @ResponseBody
    public Object getClassify() {
        logBefore(logger, "获取分类");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        String token = pd.getString("token");
        if (StringUtils.isEmpty(token)) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "缺少必须参数key");
            return AppUtil.returnObject(pd, map);
        }

        Cache cache = CacheManager.getCacheInfo(token);
        if (StringUtils.isEmpty(cache)) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            return AppUtil.returnObject(pd, map);
        }
        Object o = cache.getValue();
        if (o instanceof String) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            return AppUtil.returnObject(pd, map);
        }
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
            if (AppUtil.checkParam("webappLogin", pd) || true) // 接口参数验证，暂时不加
            {
                try {
                    if (!StringUtils.isEmpty(callback)) {
                        pd.put("callback", callback);
                    }

                    String queryID = pd.getString("queryID");
                    String M_FID = pd.getString("M_FID");
                    map.put("result", "01");
                    if (!StringUtils.isEmpty(M_FID)) {
                        String queryIDMFid = pd.getString("queryIDMFid");
                        if (!StringUtils.isEmpty(queryIDMFid)) {
                            queryID = queryIDMFid;
                        }
                        if (M_FID.indexOf(",") != -1) {
                            M_FID = M_FID.substring(M_FID.indexOf(",") + 1);
                        }
                        pd.put("M_FID", M_FID);
                    }
                    if ("queryMenua".equals(queryID)) {
                        map.put("data", getClassifyTree(olopdmenuService.queryMenua(pd), "queryMenua"));
                        return AppUtil.returnObject(pd, map);
                    }
                    if ("queryMenub".equals(queryID)) {

                        if (StringUtils.isEmpty(M_FID)) {
                            map.put("data", new ArrayList<PageData>());
                            return AppUtil.returnObject(pd, map);
                        }
                        map.put("data", getClassifyTree(olopdmenuService.queryMenub(pd), "queryMenub"));
                        return AppUtil.returnObject(pd, map);
                    }
                    if ("queryMenuc".equals(queryID)) {
                        if (StringUtils.isEmpty(M_FID)) {
                            map.put("data", new ArrayList<PageData>());
                            return AppUtil.returnObject(pd, map);
                        }
                        map.put("data", getClassifyTree(olopdmenuService.queryMenuc(pd), "queryMenuc"));
                        return AppUtil.returnObject(pd, map);
                    }

                    if (StringUtils.isEmpty(M_FID)) {
                        map.put("data", new ArrayList<PageData>());
                        return AppUtil.returnObject(pd, map);
                    }
                    map.put("data", getClassifyTree(olopdmenuService.queryMenud(pd), "queryMenud"));

                    // 查询出所有分类下的封面图片和轮播图片
                    return AppUtil.returnObject(pd, map);

                } catch (Exception e) {
                    map.put("result", "00");
                    map.put("msg", e.getMessage());
                    logger.error(e.toString(), e);
                } finally {
                    logAfter(logger);
                }
            } else {
                map.put("result", "99");
                map.put("msg", "缺少必须的提交参数");
                return AppUtil.returnObject(pd, map);
            }
        } else {
            result = "05";
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 获取分类
     */
    @RequestMapping(value = "/getClassifyList")
    @Override
    @ResponseBody
    public Object getClassifyList() {
        // TODO Auto-generated method stub

        logBefore(logger, "获取分类");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        String token = pd.getString("token");
        if (StringUtils.isEmpty(token)) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "缺少必须参数key");
            return AppUtil.returnObject(pd, map);
        }

        Cache cache = CacheManager.getCacheInfo(token);
        if (StringUtils.isEmpty(cache)) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            return AppUtil.returnObject(pd, map);
        }
        Object o = cache.getValue();
        if (o instanceof String) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            return AppUtil.returnObject(pd, map);
        }
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
            if (AppUtil.checkParam("webappLogin", pd) || true) // 接口参数验证，暂时不加
            {
                try {
                    if (!StringUtils.isEmpty(callback)) {
                        pd.put("callback", callback);
                    }
                    List<PageData> listClassify = new ArrayList<PageData>();

                    /*
                     * String queryID = pd.getString("queryID"); String M_FID =
                     * pd.getString("M_FID");
                     */
                    String queryType = pd.getString("queryType");
                    Integer qType = WeblInterFaceQueryType.TM.getIndex();
                    if (!StringUtils.isEmpty(queryType)) {
                        try {
                            qType = Integer.valueOf(queryType);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            // e.printStackTrace();
                            logger.info("查询类型queryType传入的不是一个数字，执行默认查询台面!");
                        }
                    }
                    map.put("result", "01");
                    List<PageData> list = null;
                    map.put("EFFECTIVE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                    pd.put("SEALED", "0"); // 正常状态
                    String classify = pd.getString("classify");
                    List<String> cify = new ArrayList<String>();
                    if (!StringUtils.isEmpty(classify)) {
                        for (String temp : classify.split(",")) {
                            cify.add(temp);
                        }
                        pd.put("classify", cify);
                    }
                    if (StringUtils.isEmpty(pd.getString("sort"))) {
                        pd.put("sort", " SORTING  DESC ");
                    }
                    // 台面
                    if (qType == WeblInterFaceQueryType.TM.getIndex()) {

                        list = this.olopddpotherService.listAll(pd);
                    }
                    // 门板
                    if (qType == WeblInterFaceQueryType.MB.getIndex()) {
                        list = this.olopddpService.listAll(pd);
                    }
                    // 商品
                    if (qType == WeblInterFaceQueryType.SP.getIndex()) {
                        list = olopdproductService.findlist(pd); // 列出Olopdproduct列表
                    }

                    map.put("result", "01");
                    map.put("data", list);

                    // 查询出所有分类下的封面图片和轮播图片
                    return AppUtil.returnObject(pd, map);

                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    map.put("pd", pd);
                    // map.put("result", result);
                    logAfter(logger);
                }
            } else {
                map.put("result", "99");
                map.put("msg", "缺少必须的提交参数");
                return AppUtil.returnObject(pd, map);
            }
        } else {
            result = "05";
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 获取商品详情
     */
    @RequestMapping(value = "/detail")
    @Override
    @ResponseBody
    public Object getGoodsDetails() {

        logBefore(logger, "获取详情");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        String token = pd.getString("token");
        if (StringUtils.isEmpty(token)) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "缺少必须参数key");
            return AppUtil.returnObject(pd, map);
        }

        Cache cache = CacheManager.getCacheInfo(token);
        if (StringUtils.isEmpty(cache)) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            return AppUtil.returnObject(pd, map);
        }
        Object o = cache.getValue();
        if (o instanceof String) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            return AppUtil.returnObject(pd, map);
        }
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
            if (AppUtil.checkParam("webappLogin", pd) || true) // 接口参数验证，暂时不加
            {
                try {
                    if (!StringUtils.isEmpty(callback)) {
                        pd.put("callback", callback);
                    }
                    map.put("result", "01");
                    PageData pdProduct = olopdproductService.findById(pd); // 根据ID或者code

                    generateClassifys(pdProduct.getString("CATEGORY"), pd);
                    // 查询出和商品关联的图片
                    PageData imgPd = new PageData();
                    imgPd.put("GOODS_ID", pdProduct.getString("GOODS_ID"));
                    pdProduct.put("imgs", olopdproductService.findImgsById(imgPd));
                    // 查询出和商品关联的风格
                    PageData stylePd = new PageData();
                    List<String> ids = new ArrayList<String>();
                    if (!StringUtils.isEmpty(pdProduct.getString("SPREAD5"))) {
                        String[] tempstrs = pdProduct.getString("SPREAD5").split(",");
                        for (String temp : tempstrs) {

                            ids.add(temp);
                        }
                        stylePd.put("ids", ids);
                        pdProduct.put("styles", olopdstyleService.listAll(stylePd));
                    }

                    //查询出商品已经选择好的属性
                    List tempSkuPdab =  olopdabskuService.findSKUAtribute(pdProduct);
                    pdProduct.put("skuAtribute",   tempSkuPdab);
                    /*pd.put("skuAtributeJson", gson.toJson(tempSkuPdab) );*/
                    pdProduct.put("listSku",  olopdskuService.listAll(pdProduct));
                    
                    //查询出推荐商品
                    pdProduct.put("TJSP",  olopdproductService.findTjspById(pdProduct));
                    
                    // 查询出商品详情目录
                    PageData RXPd = new PageData();
                    RXPd.put("P_BM", Const.SPMBTMSPXQLM);
                    pdProduct.put("SPXQLM", dictionariesService.dictlist(RXPd));
                    /*
                     * // 查询出上传图片路径 PageData uploudImgPd = new PageData();
                     * uploudImgPd.put("BIANMA", Const.UPLOUADIMGPATH);
                     * uploudImgPd =
                     * dictionariesService.findBmCount(uploudImgPd);
                     * pd.put("imgBasePath", uploudImgPd.getString("NAME"));
                     */
                    map.put("result", "01");
                    map.put("data", pdProduct);

                    // 查询出所有分类下的封面图片和轮播图片
                    return AppUtil.returnObject(pd, map);

                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    // map.put("result", result);
                    logAfter(logger);
                }
            } else {
                map.put("result", "99");
                map.put("msg", "缺少必须的提交参数");
                return AppUtil.returnObject(pd, map);
            }
        } else {
            result = "05";
        }
        return AppUtil.returnObject(pd, map);
        // TODO Auto-generated method stub
    }

    /**
     * 获取分类
     */
    @RequestMapping(value = "/createWorkflow")
    @Override
    public void createWorkflow(@RequestParam Map<String, String> map,
            @RequestParam(required = false, value = "files") MultipartFile[] files,HttpServletResponse response) {

        logBefore(logger, "总经理信箱，经销商投诉与建议");
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = map.get("callback");
        if (!StringUtils.isEmpty(callback)) {
            pd.put("callback", callback);
        }
        String result = "00";
        String token = map.get("token");
        if (StringUtils.isEmpty(token)) {
            map.put("result", result);
            map.put("msg", "缺少必须参数token");
            this.responseJson(response, map);
            return ;
        }

        Cache cache = CacheManager.getCacheInfo(token);
        if (StringUtils.isEmpty(cache)) {
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            this.responseJson(response, map);
            return ;
        }
        Object o = cache.getValue();
        if (o instanceof String) {
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            this.responseJson(response, map);
            return ;
        }
        try {
            PageData pdOloUser = (PageData) o;
            map.put("ID", pdOloUser.getString("ID"));
            map.put("jxs", pdOloUser.getString("SUBCOMPANYID1"));
            
            String ffile = DateUtil.getDays(), fileName = "";
            String filePath = "";// 文件上传路径
            String fileNameUUid = this.get32UUID();
            filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile; // 文件上传路径
            List<String> list = new ArrayList<String>();
            for(MultipartFile file:files ){
                // 校验权限
                if (null != file && !file.isEmpty()) {
                    fileName = FileUpload.fileUp(file, filePath, fileNameUUid); // 执行上传
                    list.add(filePath+ "/" + fileName);
                } 
            }
            //上传到远程服务器
            PageData findBmPd  = new PageData();
            findBmPd.put("BIANMA", "PATH"); //图片上传路径
            PageData bmData=dictionariesService.findBmCount(findBmPd);
            String upPath=bmData.getString("NAME");
            String imgpath ="";
            if(!StringUtils.isEmpty(upPath) && list.size() >0 ){
                
                PageData findBmREMOTEPd  = new PageData();
                findBmREMOTEPd.put("BIANMA", "REMOTE_PATH"); //图片上传路径
                PageData bmREMOTEData=dictionariesService.findBmCount(findBmREMOTEPd);
                String basePath=bmREMOTEData.getString("NAME");
                
                String str = HttpConnectionUtil.uploadFile(upPath,list);
                JSONObject jsonObject = JSONObject.fromObject(str);
                JSONArray jArray= jsonObject.optJSONArray("filePaths");
                for(int i=0;i<jArray.size();i++){
                      String imgPath = basePath+"/"+(String)jArray.get(i);
                      imgpath +=imgPath+",";
                      //路径
                }
            }
            map.put("tpfj", imgpath);  
            ConFirmAutoCreate.uploudWorkFlow(map);
            
            map.put("result", "01");
            // 查询出所有分类下的封面图片和轮播图片
            this.responseJson(response, map);
            return ;

        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            // map.put("result", result);
            logAfter(logger);
        }
        return ;
        // TODO Auto-generated method stub
    }

    private String drawImg(ByteArrayOutputStream output) {
        String code = "";
        for (int i = 0; i < 4; i++) {
            code += randomChar();
        }
        int width = 70;
        int height = 25;
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Color color = new Color(66, 2, 82);
        g.setColor(color);
        g.setBackground(new Color(226, 226, 240));
        g.clearRect(0, 0, width, height);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int) x, (int) baseY);
        g.dispose();
        try {
            ImageIO.write(bi, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    private String drawImg(ByteArrayOutputStream output, String code) {
        int width = 70;
        int height = 25;
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Color color = new Color(66, 2, 82);
        g.setColor(color);
        g.setBackground(new Color(226, 226, 240));
        g.clearRect(0, 0, width, height);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int) x, (int) baseY);
        g.dispose();
        try {
            ImageIO.write(bi, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    private char randomChar() {
        Random r = new Random();
        String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
        return s.charAt(r.nextInt(s.length()));
    }
    @Resource(name = "olopdflfileService")
    private OlopdflfileService olopdflfileService;
    /**
     * 
     * 
     * getClassifyTree(获取分类的轮播图和封面)
     * 
     * @param name
     * 
     * @param @return
     *            分类集合
     * 
     * @return String DOM对象
     * @throws Exception
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    private List<PageData> getClassifyTree(List<PageData> list, String key) throws Exception {

        if (!StringUtils.isEmpty(list) && list.size() > 0) {

            List<String> classIfyIds = new ArrayList<String>();
            Map<String, PageData> map = new HashMap<String, PageData>();
            PageData RXPd = new PageData();
            RXPd.put("P_BM", Const.SPMBTMFLML);
            List<PageData> lmlist=  dictionariesService.dictlist(RXPd);
            for (PageData pd : list) {
                List<PageData> tempList =deepCopy(lmlist);
                pd.put("classIfyId", OlopdmenuUtils.getHaseCode(pd, key));
                classIfyIds.add(pd.getString("classIfyId"));
                map.put(pd.getString("classIfyId"), pd);
                pd.put("flml", tempList);
                pd.remove("classIfy");
                pd.remove("carousel");
            }
            // 查询出标签
          
           //查询出分类下所有的文件
           List<PageData>  flwenj= olopdflfileService.listAllByFlId(list);
           for(PageData pd : list){
               for(PageData pdflwenj:flwenj){
                   if(pd.getString("classIfyId").equals(pdflwenj.getString("FL_ID"))){
                       if(pd.getString("classIfyId").equals("-181129604")){
                        System.out.println(pd.getString("classIfyId"));   
                       }
                       List<PageData> temlmlist = (List<PageData>)pd.get("flml");
                       for(PageData tempPd:temlmlist){
                           if(tempPd.getString("ZD_ID").equals(pdflwenj.getString("ZD_ID"))){
                               if(tempPd.get("children")!=null){
                                   List<PageData> tlist = (List<PageData>) tempPd.get("children");
                                   tlist.add(pdflwenj);
                                   continue;
                               }
                               List<PageData> tlist = new ArrayList<PageData>();
                               tlist.add(pdflwenj);
                               tempPd.put("children", tlist);
                           }
                       }
                   }
               }
           }
            //查询分类关联的
            
            // 分类封面()
            PageData tempData = new PageData();
            tempData.put("O_ID2", classIfyIds);
            tempData.put("TYPE", OlopdcarouselType.FENLEI.getIndex());

            // 如果封面图片大于一张，说明是最初的轮播，设置到轮播图里面去。
            List<PageData> listPage = olopdcarouselService.findBymenuId(tempData);
            for (PageData pd : listPage) {
                String Oid2 = pd.getString("O_ID2");
                if (StringUtils.isEmpty(pd.getString("O_ID2"))) {
                    continue;
                }
                PageData t = map.get(Oid2);// 获取分类
                if (StringUtils.isEmpty(t)) {
                    continue;
                }
                if (t.get("classIfy") != null) {
                    List<PageData> tlist = (List<PageData>) t.get("classIfy");
                    tlist.add(pd);
                    continue;
                }
                List<PageData> tlist = new ArrayList<PageData>();
                tlist.add(pd);
                t.put("classIfy", tlist);
            }
            // 分类轮播
            PageData tempDataLunBo = new PageData();
            tempDataLunBo.put("O_ID2", classIfyIds);
            tempDataLunBo.put("TYPE", OlopdcarouselType.FENLEILUNBO.getIndex());
            List<PageData> listCarousePage = olopdcarouselService.findBymenuId(tempDataLunBo);
            for (PageData pd : listCarousePage) {
                String Oid2 = pd.getString("O_ID2");
                if (StringUtils.isEmpty(pd.getString("O_ID2"))) {
                    continue;
                }
                PageData t = map.get(Oid2);// 获取分类
                if (StringUtils.isEmpty(t)) {
                    continue;
                }

                /*
                 * if(pd.get){
                 * 
                 * }
                 */
                if (t.get("carousel") != null) {
                    List<PageData> tlist = (List<PageData>) t.get("carousel");
                    tlist.add(pd);
                    continue;
                }
                List<PageData> tlist = new ArrayList<PageData>();
                tlist.add(pd);
                t.put("carousel", tlist);
            }
        }
        return list;

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
                oldpd.put("ONE_SELECT", pd.getString("M_NAME") + "," + pd.getString("ID"));
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
                oldpd.put("TWO_SELECT", pd.getString("M_NAME") + "," + pd.getString("ID"));
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
                oldpd.put("THREE_SELECT", pd.getString("M_NAME") + "," + pd.getString("ID"));
            }
        }
        if (classifys.length >= 4) {
            // 查询第四级分类
            pData.put("M_NAME", classifys[3]);
            pData.put("M_FID", classifyTHREE.getString("CID"));
            List<PageData> list = olopdmenuService.queryMenud(pData);
            if (list != null && list.size() > 0) {
                PageData pd = list.get(0);
                oldpd.put("FOUR_SELECT", pd.getString("M_NAME") + "," + pd.getString("ID"));
            }
        }
        return oldpd;

    }

    /**
     * 获取下载中心目录
     */
    @RequestMapping(value = "/getDownloadDir")
    @ResponseBody
    @Override
    public java.lang.Object getDownloadDir() {
        // TODO Auto-generated method stub

        logBefore(logger, "获取下载中心目录");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
                try {
                    if (!StringUtils.isEmpty(callback)) {
                        pd.put("callback", callback);
                    }
                    // 查询出标签
                    PageData RXPd = new PageData();
                    RXPd.put("P_BM", Const.SPMBTMMJML);
                    pd.put("wjml", dictionariesService.dictlist(RXPd));
                   
                    map.put("result", "01");
                    map.put("data", pd);
                    return AppUtil.returnObject(pd, map);
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    // map.put("result", result);
                    logAfter(logger);
                }
            } else {
                map.put("result", "99");
                map.put("msg", "缺少必须的提交参数");
                return AppUtil.returnObject(pd, map);
            }
        return AppUtil.returnObject(pd, map);
    }
    /**
     * 获取下载中心目录附件
     */
    @RequestMapping(value = "/getDownloadFilePageList")
    @ResponseBody
    @Override
    public java.lang.Object getDownloadFilePageList(Page page) {
        logBefore(logger, " 获取下载中心目录附件");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        page.setPd(pd);
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        String token = pd.getString("token");
        Cache cache = CacheManager.getCacheInfo(token);
        if (StringUtils.isEmpty(cache)) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            return AppUtil.returnObject(pd, map);
        }
        Object o = cache.getValue();
        if (o instanceof String) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            return AppUtil.returnObject(pd, map);
        }
        if(StringUtils.isEmpty(pd.get("ZD_ID"))){
            //必传字段
            map.put("result", result);
            map.put("msg", "SPREAD2是分类目录ZD_ID，必传!");
            return AppUtil.returnObject(pd, map);
        }
        pd.put("SPREAD2", pd.get("ZD_ID"));
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
                try {
                    if (!StringUtils.isEmpty(callback)) {
                        pd.put("callback", callback);
                    }
                    if(StringUtils.isEmpty(page.getSort())|| page.getSort().size() ==0){
                        Map<String, String> map1 = new HashMap<String, String>();
                        map1.put("name", "SORTING");
                        map1.put("sortStr", "desc");  
                        List< Map<String, String> > list = new ArrayList<Map<String,String>>();
                        list.add(map1);
                        page.addListSort(list);
                  }
                    
                    if(StringUtils.isEmpty(pd.get("SEALED"))){
                        pd.put("SEALED", "0");
                    }
                    
                    PageData findBmREMOTEPd  = new PageData();
                    findBmREMOTEPd.put("BIANMA", "REMOTE_PATH"); //图片上传路径
                    PageData bmREMOTEData=dictionariesService.findBmCount(findBmREMOTEPd);
                    String basePath=bmREMOTEData.getString("NAME");
                    // 查询出标签
                    List<PageData>  varList = olopdfileService.list(page);  //列出Olopdfile列表
                    map.put("result", "01");
                    map.put("data", varList);
                    map.put("page", page);
                    map.put("basePath", basePath);
                    
                    return AppUtil.returnObject(pd, map);
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    // map.put("result", result);
                    logAfter(logger);
                }
            } else {
                map.put("result", "99");
                map.put("msg", "缺少必须的提交参数");
                return AppUtil.returnObject(pd, map);
            }
        return AppUtil.returnObject(pd, map);
    }


    
    @Resource(name="npinformationsubjectService")
    private NpinformationsubjectService npinformationsubjectService;

    /**
     * 获取新闻列表
     */
    @RequestMapping(value = "/getNews")
    @Override
    public  void getNews(Page page,HttpServletResponse response) {
        logBefore(logger, "获取新闻列表");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        page.setPd(pd);
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        String token = pd.getString("token");
        Cache cache = CacheManager.getCacheInfo(token);
        if (StringUtils.isEmpty(cache)) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            this.responseJson(response, AppUtil.returnObject(pd, map)); 
            return;
        }
        Object o = cache.getValue();
        if (o instanceof String) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            this.responseJson(response, AppUtil.returnObject(pd, map)); 
            return;
        }

        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
                try {
                    if (!StringUtils.isEmpty(callback)) {
                        pd.put("callback", callback);
                    }
                    if(StringUtils.isEmpty(page.getSort())|| page.getSort().size() ==0){
                        Map<String, String> map1 = new HashMap<String, String>();
                        map1.put("name", "SORT ,CREATE_DATE");
                        map1.put("sortStr", "desc");  
                        List< Map<String, String> > list = new ArrayList<Map<String,String>>();
                        list.add(map1);
                        page.addListSort(list);
                  }
                    
                    if(StringUtils.isEmpty(pd.get("SEALED"))){
                        pd.put("SEALED", "0");
                    }
                    
                    if(!StringUtils.isEmpty(pd.get("TYPE")) && "1".equals(pd.get("TYPE"))){
                        pd.put("TYPE", "CPGGLE_CPGG");
                    }
                    // 查询出标签
                    map.put("result", "01");
                    List<PageData>  varList = npinformationsubjectService.list(page);  //列出Olopdfile列表
                    map.put("data", varList);
                    map.put("page", page);
                    
                    this.responseJson(response, AppUtil.returnObject(pd, map)); 
                    return;
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    // map.put("result", result);
                    logAfter(logger);
                }
            } else {
                map.put("result", "99");
                map.put("msg", "缺少必须的提交参数");
                this.responseJson(response, AppUtil.returnObject(pd, map));
            }
        this.responseJson(response, AppUtil.returnObject(pd, map));
    }

    /**
     * 获取新闻详情
     */
    @RequestMapping(value = "/getNewsDet")
    @Override
    public void getNewsDet(HttpServletResponse response) {
        logBefore(logger, "获取新闻详情");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        String token = pd.getString("token");
        Cache cache = CacheManager.getCacheInfo(token);
        if (StringUtils.isEmpty(cache)) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            this.responseJson(response, AppUtil.returnObject(pd, map)); 
            return;
        }
        Object o = cache.getValue();
        if (o instanceof String) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            this.responseJson(response, AppUtil.returnObject(pd, map)); 
            return;
        }
        if(StringUtils.isEmpty(pd.get("SUBJECT_ID"))){
            //必传字段
            map.put("result", result);
            map.put("msg", "SUBJECT_ID新闻详情ID，必传!");
            this.responseJson(response, AppUtil.returnObject(pd, map)); 
            return;
        }
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
                try {
                    if (!StringUtils.isEmpty(callback)) {
                        pd.put("callback", callback);
                    }

                    
                    if(StringUtils.isEmpty(pd.get("SEALED"))){
                        pd.put("SEALED", "0");
                    }
                    // 查询出标签
                    map.put("result", "01");
                    PageData  newData = npinformationsubjectService.findById(pd);  //列出Olopdfile列表
                    map.put("data", newData);
                    
                    this.responseJson(response, AppUtil.returnObject(pd, map)); 
                    return;
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    // map.put("result", result);
                    logAfter(logger);
                }
            } else {
                map.put("result", "99");
                map.put("msg", "缺少必须的提交参数");
                this.responseJson(response, AppUtil.returnObject(pd, map)); 
                return;
            }
        this.responseJson(response, AppUtil.returnObject(pd, map)); 
        return;
    }
    
    @Resource(name="olopdprfileService")
    private OlopdprfileService olopdprfileService; 
    /**
     * 获取新闻详情
     */
    @RequestMapping(value = "/getGoodsFile")
    @Override
    public void getGoodsFile(HttpServletResponse response) {
        logBefore(logger, "获取新闻详情");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");
        String requestSign = pd.getString("sing");
        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        String token = pd.getString("token");
        Cache cache = CacheManager.getCacheInfo(token);
        if (StringUtils.isEmpty(cache)) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            this.responseJson(response, AppUtil.returnObject(pd, map)); 
            return;
        }
        Object o = cache.getValue();
        if (o instanceof String) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            map.put("result", result);
            map.put("msg", "没有相关用户信息!");
            this.responseJson(response, AppUtil.returnObject(pd, map)); 
            return;
        }
        if(StringUtils.isEmpty(pd.getString("GOODS_ID"))){
            //必传字段
            map.put("result", result);
            map.put("msg", "GOODS_ID必传!");
            this.responseJson(response, AppUtil.returnObject(pd, map)); 
            return;
        }
        if(StringUtils.isEmpty(pd.getString("ZD_ID"))){
            //必传字段
            map.put("result", result);
            map.put("msg", "ZD_ID必传!");
            this.responseJson(response, AppUtil.returnObject(pd, map)); 
            return;
        }
        pd.put("MENU", pd.getString("ZD_ID"));
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
                try {
                    if (!StringUtils.isEmpty(callback)) {
                        pd.put("callback", callback);
                    }

                    
                    if(StringUtils.isEmpty(pd.get("SEALED"))){
                        pd.put("SEALED", "0");
                    }
                    // 查询出标签
                    map.put("result", "01");
                    map.put("data",  olopdprfileService.listAll(pd));
                    
                    this.responseJson(response, AppUtil.returnObject(pd, map)); 
                    return;
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    // map.put("result", result);
                    logAfter(logger);
                }
            } else {
                map.put("result", "99");
                map.put("msg", "缺少必须的提交参数");
                this.responseJson(response, AppUtil.returnObject(pd, map)); 
                return;
            }
        this.responseJson(response, AppUtil.returnObject(pd, map)); 
        return;
    }
    
    
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {  
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream out = new ObjectOutputStream(byteOut);  
        out.writeObject(src);  

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
        ObjectInputStream in = new ObjectInputStream(byteIn);  
        @SuppressWarnings("unchecked")  
        List<T> dest = (List<T>) in.readObject();  
        return dest;  
    }  
    @RequestMapping(value = "/sendVerificationCode")
    @Override
    @ResponseBody
	public java.lang.Object sendVerificationCode() {

        logBefore(logger, "发送验证码");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String callback = pd.getString("callback");
        String _ = pd.getString("_");
        pd.remove("callback");
        pd.remove("_");

        String requestSign = pd.getString("sing");

        pd.remove("sing");
        TreeMap treemap = new TreeMap(pd);
        JSONObject json = JSONObject.fromObject(treemap);
        String result = "00";
        if(StringUtils.isEmpty(pd.getString("LOGINID"))) {
        	 if (!StringUtils.isEmpty(callback)) {
                 pd.put("callback", callback);
             }
             map.put("result", result);
             map.put("msg", "请传入LOGINID");
             return AppUtil.returnObject(pd, map);
        }
        if (MD5Util.checkTimeSign(requestSign, json.toString(), Const.KEY, "UTF-8") || true) {
            if (!StringUtils.isEmpty(callback)) {
                pd.put("callback", callback);
            }
            if (AppUtil.checkParam("registered", pd) || true) // 接口参数验证，暂时不加
            {
                
                try {

                    PageData pdOloUser = this.viewolouserinfoService.findById(pd);
                    if (StringUtils.isEmpty(pdOloUser)) {
                        map.put("result", "99");
                        map.put("msg", "用户名不存在");
                        return AppUtil.returnObject(pd, map);
                    }
                	
                    String code = "";
                    for (int i = 0; i < 4; i++) {
                        code += randomChar();
                    }
                    String token = this.get32UUID();
                    CacheManager.putCacheInfo(token, code, 1000 * 60 * 10);
                    map.put("token", token);
                    map.put("code", code);
                    String parameters="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sen=\"http://localhost/services/sendwxService\">\r\n" + 
                    		"   <soapenv:Header/>\r\n" + 
                    		"   <soapenv:Body>\r\n" + 
                    		"      <sen:sendwxMethod>\r\n" + 
                    		"         <sen:in0>"+ pdOloUser.getString("ID")+"</sen:in0>\r\n" + 
                    		"         <sen:in1>"+code+"</sen:in1>\r\n" + 
                    		"         <sen:in2>664255BAD4B27290E053A202A8C0FF89</sen:in2>\r\n" + 
                    		"      </sen:sendwxMethod>\r\n" + 
                    		"   </soapenv:Body>\r\n" + 
                    		"</soapenv:Envelope>";
                    HttpClientMapUtils.sendRequest("http://service.olokitchen.com/services/sendwxService",parameters, HttpClientMapUtils.CONTENT_TYPE_TEXT_HTML, HttpClientMapUtils.METHOD_POST_STRING);
                    result ="01";
                } catch (Exception e) {
                    logger.error(e.toString(), e);
                } finally {
                    map.put("result", result);
                    logAfter(logger);
                }
            }
        } else {
            result = "05";
        }
        if (!StringUtils.isEmpty(callback)) {
            pd.put("callback", callback);
        }
        return AppUtil.returnObject(pd, map);
	}

}
