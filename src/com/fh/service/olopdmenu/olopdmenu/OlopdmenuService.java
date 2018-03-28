package com.fh.service.olopdmenu.olopdmenu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("olopdmenuService")
public class OlopdmenuService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("OlopdmenuMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OlopdmenuMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OlopdmenuMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OlopdmenuMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OlopdmenuMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OlopdmenuMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OlopdmenuMapper.deleteAll", ArrayDATA_IDS);
	}
	
    /*
    * 一级分类
    */
	
    public List<PageData> queryMenua(PageData pd)throws Exception{
        return (List<PageData>) dao.findForList("OlopdmenuMapper.queryMenua", pd);
    }
    /*
     * 二级分类
     */
	 /*
	    * 一级分类
	    */
     public List<PageData> queryMenub(PageData pd)throws Exception{
         return (List<PageData>)dao.findForList("OlopdmenuMapper.queryMenub", pd);
     }
     /*
      * 三级分类
      */
      public List<PageData> queryMenuc(PageData pd)throws Exception{
          return (List<PageData>) dao.findForList("OlopdmenuMapper.queryMenuc", pd);
      }
      /*
       * 四级分类
       */
       public List<PageData> queryMenud(PageData pd)throws Exception{
           return (List<PageData>) dao.findForList("OlopdmenuMapper.queryMenud", pd);
       }
	
}

