package com.fh.service.log.olopdloginlog;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;


@Service("olopdloginlogService")
public class OlopdloginlogService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("OlopdloginlogMapper.save", pd);
	}
	/*
	* 新增
	*/
	public void save(String USER_ID,String ISSUCCEED,String IP,String MSG )throws Exception{
		PageData pd = new PageData();
		pd.put("ID", UuidUtil.get32UUID());
		pd.put("USER_ID", USER_ID);
		pd.put("ISSUCCEED", ISSUCCEED);
		pd.put("IP", IP);
		pd.put("MSG", MSG);
		pd.put("LOGIN_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime()));
		dao.save("OlopdloginlogMapper.save", pd);
	}
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OlopdloginlogMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OlopdloginlogMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OlopdloginlogMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OlopdloginlogMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OlopdloginlogMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OlopdloginlogMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

