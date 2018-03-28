package com.fh.service.olo.olopdmenuproduct;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("olopdmenuproductService")
public class OlopdmenuproductService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("OlopdmenuproductMapper.save", pd);
	}
    /*
    * 批量新增
    */
    public void insertByBatch(List<PageData> list)throws Exception{
        dao.batchSave("OlopdmenuproductMapper.insertByBatch", list);
    }
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OlopdmenuproductMapper.delete", pd);
	}
    /*
    * 删除
    */
    public void deleteGoodsId(PageData pd)throws Exception{
        dao.delete("OlopdmenuproductMapper.deleteGoodsId", pd);
    }
	/*
    * 删除
    */
    public void deleteGoosId(PageData pd)throws Exception{
        dao.delete("OlopdmenuproductMapper.deleteGoosId", pd);
    }
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OlopdmenuproductMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OlopdmenuproductMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OlopdmenuproductMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OlopdmenuproductMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OlopdmenuproductMapper.deleteAll", ArrayDATA_IDS);
	}
    /*
    * 批量删除
    */
    public void deleteGoodsIdAll(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("OlopdmenuproductMapper.deleteGoodsIdAll", ArrayDATA_IDS);
    }
    /*
     * 批量删除
     */
     public void deleteGoodsIdAll(List<PageData> list)throws Exception{
         dao.delete("OlopdmenuproductMapper.deleteListGoodsIdAll", list);
     }
	
}

