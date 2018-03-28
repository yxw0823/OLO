package com.fh.service.olo.olopdfile;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.olo.olopdfilerelation.OlopdfilerelationService;
import com.fh.util.PageData;
import com.fh.util.StringUtils;
import com.fh.util.UuidUtil;


@Service("olopdfileService")
public class OlopdfileService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource
	private OlopdfilerelationService olopdfilerelationService;
	
	/**
	 * 
	   
	 * saveGoodsFile(保存附件和对象的关系)    
	   
	 * @param   name    
	   
	 * @param  @return    设定文件    
	   
	 * @return String    DOM对象    
	   
	 * @Exception 异常对象    
	   
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public void saveGoodsFile(PageData pd) throws Exception{
	    PageData temp = new PageData();
	    temp.put("ID", UuidUtil.get32UUID());
	    temp.put("O_ID1", pd.getString("GOODS_ID"));
	    temp.put("O_ID2", pd.getString("ID"));
        temp.put("TYPE", pd.getString("FILETYPE"));
        olopdfilerelationService.save(pd);
	}
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
	    //判断是否存在商品ID ，如存在者保存商品和文件的关系
	    String GOODS_ID = pd.getString("GOODS_ID");
	    if(!StringUtils.isEmpty(GOODS_ID)){
	        
	        
	        saveGoodsFile(pd);
	    }
	    
		dao.save("OlopdfileMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OlopdfileMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
	    //判断是否存在商品ID ，如存在者保存商品和文件的关系
        String GOODS_ID = pd.getString("GOODS_ID");
        if(!StringUtils.isEmpty(GOODS_ID)){
            
            
            saveGoodsFile(pd);
        }
		dao.update("OlopdfileMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OlopdfileMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OlopdfileMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OlopdfileMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OlopdfileMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

