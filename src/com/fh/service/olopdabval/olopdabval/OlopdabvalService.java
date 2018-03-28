package com.fh.service.olopdabval.olopdabval;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.StringUtils;


@Service("olopdabvalService")
public class OlopdabvalService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public PageData save(PageData pd)throws Exception{
	    //查询是否有存在同名的特性
	    try {
            PageData pData = (PageData)dao.findForObject("OlopdabvalMapper.findByName", pd);
            if(!StringUtils.isEmpty(pData)){
                return pData;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		dao.save("OlopdabvalMapper.save", pd);
		return pd;
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OlopdabvalMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OlopdabvalMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OlopdabvalMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OlopdabvalMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OlopdabvalMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OlopdabvalMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

