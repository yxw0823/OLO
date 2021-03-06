package com.fh.service.olo.olopddp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.StringUtils;


@Service("olopddpService")
public class OlopddpService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("OlopddpMapper.save", pd);
	}
    /*
    * 新增
    */
    public void insertByBatch(List<PageData> list)throws Exception{
        //先通过ID查询出所有已经存在的
        List<PageData> listPd= (List<PageData>) dao.findForList("OlopddpMapper.findByIds", list);
        //存在的就放入更新list中
        List<PageData> updateList = new ArrayList<PageData>();
        List<PageData> insertList = new ArrayList<PageData>();
        boolean flage =false;
        for(PageData temp:list){
            flage =false;
            for(PageData updata:listPd){
                if(updata.getString("ID").equals(temp.getString("ID"))){
                    updateList.add(temp);
                    flage = true;
                    continue;
                }
            }
            if(!flage){
                //不存在就放入新增list中
                insertList.add(temp);
            }
        }
      
        if(!StringUtils.isEmpty(insertList) && insertList.size() >0){
            dao.batchSave("OlopddpMapper.insertByBatch", insertList);
        }
        if(!StringUtils.isEmpty(updateList) && updateList.size() >0){
            dao.batchUpdate("OlopddpMapper.edit", updateList);
        }
    }
	    
	
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OlopddpMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OlopddpMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OlopddpMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OlopddpMapper.listAll", pd);
	}
	 /*
     * 通过id获取数据
     */
    public List<PageData> findListById(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("OlopddpMapper.findById", pd);
    }
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OlopddpMapper.findById", pd);
	}
	 /*
     * 修改
     */
    public void updateBatch(List<PageData> pd) throws Exception {
        dao.batchUpdate("OlopddpMapper.edit", pd);
    }
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OlopddpMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

