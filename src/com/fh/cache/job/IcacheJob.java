package com.fh.cache.job;
/**
 * 
 *     
 * 项目名称：FHORACLE    
 * 类名称：IcacheJob
 * 
 * 类描述：定时清理缓存已经过期
 * 创建人：yanxuewen    
 * 创建时间：2017年12月26日 上午10:25:37    
 * 修改人：yanxuewen    
 * 修改时间：2017年12月26日 上午10:25:37    
 * 修改备注：    
 * @version     
 *
 */
public interface IcacheJob {
    /**
     * 
       
     * execute(定时清理缓存)    
       
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void execute() throws Exception;
}
