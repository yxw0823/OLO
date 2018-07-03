package com.fh.controller.olo.olowebInterface;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.fh.entity.Page;

/**
 * 
 *     
 * 项目名称：线上目录    
 * 类名称：IwebInterface    
 * 类描述：    web页面使用接口定义
 * 创建人：yanxuewen    
 * 创建时间：2017年12月25日 上午9:05:26    
 * 修改人：yanxuewen    
 * 修改时间：2017年12月25日 上午9:05:26    
 * 修改备注：    
 * @version      1.0.0
 *
 */
public interface IwebInterface {
    /**
     * 
       
     * getVerificationCode(获取验证码)     
       
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public Object getVerificationCode();
    
    /**
     * 
       
     * validateCode(验证二维码的)        
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public Object validateCode();
    /**
     * 
       
     * login(   登录接口)    
       
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public Object login();
    
    /**
     * 
       
     * mainPage(主页接口（一级目录+轮播图）)    
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public Object mainPage();
    
    /**
     * 
       
     * getClassify(获取分类接口，加上分类关联的轮播图和分类图片)    
       
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public Object getClassify();
    
    /**
     * 
       
     * getClassifyList(通过三级目录获取列表接口（款式页面、列表项有文字说明、列表项无文字说明）)    
       
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public Object getClassifyList();
    
    /**
     * 
       
     * getGoodsDetails(获取商品详情页面)    
       
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public Object getGoodsDetails();
    /**
     * 
       
     * loginOut(退出登录)    
     * @param   name    
     * @param  @return    设定文件    
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public Object loginOut();
    /**
     * 
     * createWorkflow(总经理信箱和经销商投诉与建议)    
     * @param   name    
     * @param  @return    设定文件    
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public  void createWorkflow(Map<String, String> map, MultipartFile[] file,HttpServletResponse response);

    
    /**
     * 
       
     * getDownloadDir(获取下载中心的目录)    
       
       
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public Object getDownloadDir();
    
    /**
     * 
       
     * getDownloadFilePageList(获取新闻列表)    
       
       
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public Object getDownloadFilePageList(Page page);
    /**
     * 
       
     * getDownloadFilePageList(获取新闻详情)    
       
       
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void getNewsDet(HttpServletResponse response);
    
    /**
     * 
       
     * getDownloadFilePageList(获取目录下的附件分页)    
       
       
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void getNews(Page page,HttpServletResponse response);
    /**
     * 获取新闻分类
     * @return
     */
    public java.lang.Object getNewsClassify() ;
    /**
     * 
       
     * getGoodsFile(获取详情目录附件)    
       
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
    public void getGoodsFile(HttpServletResponse response);
    
    /**
     * 
     * sendVerificationCode(发送验证码)    
     * @param   name    
     * @param  @return    设定文件    
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public Object sendVerificationCode();

}
