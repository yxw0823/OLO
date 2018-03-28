package com.fh.service.olo.viewolouserinfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("viewolouserinfoService")
public class ViewolouserinfoService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ViewolouserinfoMapper.findById", pd);
	}
	
}

