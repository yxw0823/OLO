package com.fh.controller.olo.olowebInterface;

import javax.servlet.http.HttpServletResponse;

import com.fh.entity.Page;

public interface IOrderInterface {
    
    public void checkUser(HttpServletResponse response);
    public void shoppingCartList(Page page,HttpServletResponse response);
    public void addShoppingCart(HttpServletResponse response);
    /**
     * 
     * editShoppingCart(更新购物车)    
     * @param   name    
     * @param  @return    设定文件    
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void editShoppingCart(HttpServletResponse response);
    
    /**
     * 
     * delShoppingCart(更新购物车)    
     * @param   name    
     * @param  @return    设定文件    
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void delShoppingCart(HttpServletResponse response);
    
    /**
     * 
     * getCustomerInfo(获取经销商名下对应手机号的终端顾客信息)    
     * @param   name    
     * @param  @return    设定文件    
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void getCustomerInfo(HttpServletResponse response);
    
    /**
     * 
     * getCustomerInfo(新建客户商机)    
     * @param   name    
     * @param  @return    设定文件    
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void addIntention(HttpServletResponse response);
    /**
     * 
     * createSalebill(下单)    
     * @param   name    
     * @param  @return    设定文件    
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void createSalebill(HttpServletResponse response);
    
    /**
     * 
     * @Title: getOrderList   
     * @Description: TODO(获取订单列表)   
     * @param: @param response      
     * @return: void      
     * @throws
     */
	public void getOrderList(Page page,HttpServletResponse response);
    
}
