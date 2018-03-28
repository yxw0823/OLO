package com.fh.service.olo.olopdabrelation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.PageData;
import com.fh.util.StringUtils;
import com.fh.util.UuidUtil;


@Service("olopdabrelationService")
public class OlopdabrelationService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("OlopdabrelationMapper.save", pd);
	}
    /*
    * 批量新增
    */
    public void batchSave(List<PageData> pd)throws Exception{
        dao.batchSave("OlopdabrelationMapper.batchSave", pd);
    }
    /*
    * 批量新增
    */
    public void savaObjAndProduct(PageData product, List<String> tempList, User user,int type)throws Exception{
        if(tempList==null || tempList.size() ==0){
            return;
        }
         List<PageData> productAndImgList = new ArrayList<PageData>();
         
         String GOODS_ID = product.getString("GOODS_ID");
         
     
         if (StringUtils.isEmpty(GOODS_ID)) {
             return;
         }
         //通过商品ID 和关系类型 删除所有商品和风格的关系
         PageData delProductAndImg = new PageData();
         delProductAndImg.put("O_ID1", GOODS_ID);
         delProductAndImg.put("TYPE", type);
         this.deleteByPd(delProductAndImg);
         
         for (String tempStr : tempList) {
             PageData productAndImg = new PageData();
             productAndImg.put("ID", UuidUtil.get32UUID());
             productAndImg.put("O_ID1", GOODS_ID);
             productAndImg.put("O_ID2", tempStr);
             productAndImg.put("TYPE", type);
             productAndImgList.add(productAndImg);
         }
         this.batchSave(productAndImgList);// 图片和商品关系
    }
    
    
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OlopdabrelationMapper.delete", pd);
	}
   /*
    * 删除
    */
    public void deleteByPd(PageData pd)throws Exception{
        dao.delete("OlopdabrelationMapper.deleteByPd", pd);
    }
	    
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OlopdabrelationMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OlopdabrelationMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OlopdabrelationMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OlopdabrelationMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OlopdabrelationMapper.deleteAll", ArrayDATA_IDS);
	}
	
   /*
    * 批量删除
    */
    public void deleteAllById(String[] ArrayDATA_IDS)throws Exception{
	        dao.delete("OlopdabrelationMapper.deleteID1All", ArrayDATA_IDS);
	}
	
}

