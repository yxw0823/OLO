package com.fh.service.olo.olopdshoppingcart;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.StringUtils;


@Service("olopdshoppingcartService")
public class OlopdshoppingcartService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public PageData save(PageData pd)throws Exception{
		PageData temp =(PageData) dao.findForObject("OlopdshoppingcartMapper.findCart", pd);
		if(!StringUtils.isEmpty(temp)){
			pd.put("CART_ID", temp.getString("CART_ID"));
			pd.put("NUMBER",Integer.valueOf(pd.getString("NUMBER"))+Integer.valueOf(temp.getString("NUMBER")));
			this.edit(pd);
			return pd;
		}
		dao.save("OlopdshoppingcartMapper.save", pd);
		return pd;
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OlopdshoppingcartMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OlopdshoppingcartMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OlopdshoppingcartMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OlopdshoppingcartMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OlopdshoppingcartMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OlopdshoppingcartMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

