package com.fh.service.olo.npinformationsubject;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("npinformationsubjectService")
public class NpinformationsubjectService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("NpinformationsubjectMapper.save", pd);
	}
	public void batchSaves(List<PageData> list)throws Exception{
        dao.batchSaves("NpinformationsubjectMapper.save", list);
    }
	
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("NpinformationsubjectMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("NpinformationsubjectMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("NpinformationsubjectMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NpinformationsubjectMapper.listAll", pd);
	}
    /*
    *列表(全部)
    */
    public List<PageData> listAllById(List<PageData> list)throws Exception{
        return (List<PageData>)dao.findForList("NpinformationsubjectMapper.listAllById", list  );
    }
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("NpinformationsubjectMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("NpinformationsubjectMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

