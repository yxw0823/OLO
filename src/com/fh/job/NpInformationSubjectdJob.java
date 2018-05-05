package com.fh.job;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.fh.dao.MultipleDataSource;
import com.fh.service.olo.npinformationsubject.NpinformationsubjectService;
import com.fh.util.Logger;
import com.fh.util.PageData;

public class NpInformationSubjectdJob {
    protected Logger logger = Logger.getLogger(NpInformationSubjectdJob.class);

    @Resource(name="npinformationsubjectService")
    private NpinformationsubjectService npinformationsubjectService;
    

    /**
     * 
       
     * execute(定时同步新闻数据到本地库)    
       
   
       
     * @param   name    
       
     * @param  @return    设定文件    
       
     * @return String    DOM对象    
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public void execute() {
        logger.info("》》》》》》》》》》》》》》》》》》》》》》》》新闻同步      开始");
        //从mysql数据库中读取数据
       
        try {
          MultipleDataSource.setDataSourceKey("mysqlDataSource");
          List<PageData> list =  npinformationsubjectService.listMySqlAll(new PageData());
          logger.info("》》》》》》》》》》》》》》》》》》》》》》》》新闻同步    官网新闻库中存在   "+list.size()+"条新闻！");
          //查询出新闻不包含
          MultipleDataSource.setDataSourceKey("oracleDataSource");
          List<PageData> listLocal = npinformationsubjectService.listAllById(list);
          for(PageData pd : listLocal){
             removeList(list,pd);
          }
          logger.info("》》》》》》》》》》》》》》》》》》》》》》》》新闻同步    去掉已经落地到本地的新闻还剩  "+list.size()+"条新闻！");
          for(PageData pd : list){
              npinformationsubjectService.save(pd); 
          }
             
       
         logger.info("》》》》》》》》》》》》》》》》》》》》》》》》新闻同步   本次同步成功"+list.size()+"条新闻！      同步结束");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        
    }
    
    /*
     * 正确
     */
    public static void removeList(List<PageData> list, PageData pd){
        Iterator<PageData> iter = list.iterator();
        while (iter.hasNext()) {
            PageData item = iter.next();
            String SUBJECT_ID = item.getString("SUBJECT_ID");
            if (SUBJECT_ID.equals(pd.getString("SUBJECT_ID"))) {
                iter.remove();
            }
        }
    }
}
