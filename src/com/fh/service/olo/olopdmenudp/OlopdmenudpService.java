package com.fh.service.olo.olopdmenudp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("olopdmenudpService")
public class OlopdmenudpService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("OlopdmenudpMapper.save", pd);
	}
	/*
	    * 新增
	    */
	    public void insertByBatch(List<PageData> pd)throws Exception{
	        dao.batchSave("OlopdmenudpMapper.insertByBatch", pd);
	    }
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OlopdmenudpMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OlopdmenudpMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OlopdmenudpMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OlopdmenudpMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OlopdmenudpMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OlopdmenudpMapper.deleteAll", ArrayDATA_IDS);
	}
   /*
    * 批量删除
    */
    public void deleteAll(List<PageData> pd)throws Exception{
        int batchCount = 999;// 每批commit的个数
        List tempObj = new ArrayList();
        for(int index = 0; index < pd.size();index++){
            tempObj.add(pd.get(index));
            if(index % batchCount == 0 && index !=0){
                dao.delete("OlopdmenudpMapper.deleteAllByOID1", tempObj);
                tempObj=new ArrayList();
            }
            
        }
        if(tempObj.size()>0){
            dao.delete("OlopdmenudpMapper.deleteAllByOID1", tempObj);
        }
    }
	
}

