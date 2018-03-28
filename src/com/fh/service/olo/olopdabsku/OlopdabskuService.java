package com.fh.service.olo.olopdabsku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.DateUtil;
import com.fh.util.JsonUtils;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.StringUtils;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service("olopdabskuService")
public class OlopdabskuService {
    protected Logger logger = Logger.getLogger(this.getClass());
    final BASE64Encoder encoder = new BASE64Encoder();
    final BASE64Decoder decoder = new BASE64Decoder();
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
	    dao.save("OlopdabskuMapper.save", pd);
	}
	/*
	    * 新增
	    */
	    public void batchSave(List<PageData> pd)throws Exception{
	        dao.batchSave("OlopdabskuMapper.batchSave", pd);
	    }
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OlopdabskuMapper.delete", pd);
	}
	/*
	    * 删除
	    */
	    public void deleteGoodsId(PageData pd)throws Exception{
	        dao.delete("OlopdabskuMapper.deleteGoodsId", pd);
	    }
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OlopdabskuMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OlopdabskuMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OlopdabskuMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OlopdabskuMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OlopdabskuMapper.deleteAll", ArrayDATA_IDS);
	}
	/*
	    * 批量新增
	    */
    public void saveAllSku(PageData pd)throws Exception{
        //获取
        String SKU_ID =pd.getString("SKU_ID");
        List<PageData> list=new ArrayList<PageData>();
        if(StringUtils.isEmpty(SKU_ID) ){
            throw new Exception("缺少必要的属性 必要的属性有SKU_ID");
        }
        try {
           
            PageData temppd1 =  new PageData();
            String temp =   pd.getString("SPREAD1");
            String[] tempStrs = temp.split(",");
            //删除相关的
            temppd1.put("SKU_ID", SKU_ID);
            this.delete(temppd1);
            for(String tempStr :tempStrs){
                String  tempOtherAttributes =  new String(decoder.decodeBuffer(tempStr), "UTF-8");
                JSONObject tempJson = JSONObject.fromObject(tempOtherAttributes);
                PageData temppd =  new PageData();
                temppd.put("SKU_ID", pd.getString("SKU_ID"));
                temppd.put("A_ID", tempJson.getString("oid"));
                temppd.put("A_NAME", tempJson.getString("oname"));
                temppd.put("PROPERTY_ID", tempJson.getString("value"));
                temppd.put("PROPERTY_VALUE", tempJson.getString("c_name"));
                temppd.put("CREATE_TIME", java.sql.Timestamp.valueOf(DateUtil.getTime().toString()));
                temppd.put("SEALED", "0");//默认 0 不封存  1封存
                temppd.put("SORTING", "0");//默认 0  排序
                list.add(temppd);
               
            }
            this.batchSave(list);
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            throw new Exception("组装OlopdabskuMapper对象异常,异常原因"+e.getMessage());
        }
        //
    }
    
    /*
     * 通过goods_id查询出商品的所有SKU属性
     * 
     * 
     */
     public List<PageData> findSKUAtribute(PageData pd)throws Exception{
       
      if(StringUtils.isEmpty(pd.getString("GOODS_ID"))){
          throw new Exception("查询接口findSKUAtribute缺少必要的查询条件[GOODS_ID]");
      }
         //查询出所有SKU
       List<PageData> list=  (List<PageData>)dao.findForList("OlopdabskuMapper.findByGOODSID", pd);
       
       Map<String,PageData> map = new HashMap<String,PageData>();
       List<PageData> allList = new ArrayList<PageData>();
       for(PageData  temppd:list){
           String aId =temppd.getString("A_ID");
           PageData mapPd=map.get(aId);
           if(!StringUtils.isEmpty(mapPd)){
             List<PageData> childrenList=  (List<PageData>) mapPd.get("children");
             boolean flage =false;
             for(PageData childrenPd:childrenList){
                 if(childrenPd.getString("PROPERTY_ID").equals(temppd.getString("PROPERTY_ID"))){
                     flage=true;
                     break;
                 }
             }
             if(!flage){
                 childrenList.add(temppd); 
             }
             continue;
           }
           PageData father = new PageData();
           father.put("A_ID", aId);
           father.put("A_NAME", temppd.getString("A_NAME"));
           father.put("children", new ArrayList<PageData>());
           ((List<PageData>)father.get("children")).add(temppd);
           allList.add(father);
           map.put(aId, father);
           
       }
       return allList;
     }
    
}

