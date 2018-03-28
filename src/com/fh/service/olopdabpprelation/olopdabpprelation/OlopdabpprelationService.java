package com.fh.service.olopdabpprelation.olopdabpprelation;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.StringUtils;


@Service("olopdabpprelationService")
public class OlopdabpprelationService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
	    PageData pData=(PageData) dao.findForObject("OlopdabpprelationMapper.findByMap", pd);
	    if(!StringUtils.isEmpty(pData)){
	        return;
	    }
		dao.save("OlopdabpprelationMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OlopdabpprelationMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OlopdabpprelationMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OlopdabpprelationMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OlopdabpprelationMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OlopdabpprelationMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OlopdabpprelationMapper.deleteAll", ArrayDATA_IDS);
	}
    /*
    * 批量删除
    */
    public void deleteAllByOID2(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("OlopdabpprelationMapper.deleteAllByOID2", ArrayDATA_IDS);
    }
    
    /*
     * 批量删除
     */
     public void deleteAllByOID1(String[] ArrayDATA_IDS)throws Exception{
         dao.delete("OlopdabpprelationMapper.deleteAllByOID1", ArrayDATA_IDS);
     }
}

