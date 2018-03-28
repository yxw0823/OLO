package com.fh.service.olopdppval.olopdppval;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.StringUtils;


@Service("olopdppvalService")
public class OlopdppvalService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public PageData save(PageData pd)throws Exception{
	    
	   PageData tempPd= (PageData)dao.findForObject("OlopdppvalMapper.findByName", pd);
	   if(!StringUtils.isEmpty(tempPd)){
	       return tempPd;
	   }
		dao.save("OlopdppvalMapper.save", pd);
		return pd;
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OlopdppvalMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OlopdppvalMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OlopdppvalMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OlopdppvalMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OlopdppvalMapper.findById", pd);
	}

	/*
	    *列表(全部)
	    */
	    public List<PageData> listAllByAID(PageData pd)throws Exception{
	        return (List<PageData>)dao.findForList("OlopdppvalMapper.listAllByAID", pd);
	    }
    
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OlopdppvalMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

