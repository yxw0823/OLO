package com.fh.service.olopdproduct.olopdproduct;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.olo.olopdabsku.OlopdabskuService;
import com.fh.service.olo.olopdsku.OlopdskuService;
import com.fh.util.PageData;
import com.fh.util.StringUtils;

@Service("olopdproductService")
public class OlopdproductService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Resource(name="olopdskuService")
    private OlopdskuService olopdskuService; //SKU管理
    
    
    /*
     * 新增
     */
    public void save(PageData pd) throws Exception {
        String SKU =pd.getString("SKU");
        if(!StringUtils.isEmpty(SKU)){
            olopdskuService.saveAllSku(pd);
        }
        dao.save("OlopdproductMapper.save", pd);
    }

    /*
     * 批量新增
     */
    public void insertByBatch(List<PageData> list) throws Exception {
        //通过code查询出所有已经存在的商品
        List<PageData> listPd= (List<PageData>) dao.findForList("OlopdproductMapper.findByCodes", list);
        
        //把存在的商品放到更新列表中,不存在的放到新增里面
        List<PageData> updateList = new ArrayList<PageData>();
        List<PageData> insertList = new ArrayList<PageData>();
        boolean flage =false;
        for(PageData temp:list){
            flage =false;
            for(PageData updata:listPd){
                if(updata.getString("CODE").equals(temp.getString("CODE"))){
                    temp.put("GOODS_ID", updata.getString("GOODS_ID"));
                    updateList.add(temp);
                    flage = true;
                    continue;
                }
            }
            if(!flage){
                insertList.add(temp);
            }
        }
        
        if(!StringUtils.isEmpty(insertList) && insertList.size() >0){
            dao.batchSave("OlopdproductMapper.insertByBatch", insertList); 
        }
        if(!StringUtils.isEmpty(updateList) && updateList.size() >0){
            dao.batchUpdate("OlopdproductMapper.edit", updateList);
        }
    }

    /*
     * 删除
     */
    public void delete(PageData pd) throws Exception {
        dao.delete("OlopdproductMapper.delete", pd);
    }

    /*
     * 修改
     */
    public void edit(PageData pd) throws Exception {
        
        
        String SKU =pd.getString("SKU");
        if(!StringUtils.isEmpty(SKU)){
            olopdskuService.saveAllSku(pd);
        }
     
        
        
        dao.update("OlopdproductMapper.edit", pd);
    }
    
    
    /*
     * 修改
     */
    public void edit(List<PageData> pd) throws Exception {
        dao.batchUpdate("OlopdproductMapper.edit", pd);
    }
    /*
     * 列表
     */
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("OlopdproductMapper.datalistPage", page);
    }
    
    /*
     * 列表(全部)
     */
    public List<PageData> findlist(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("OlopdproductMapper.findlist", pd);
    }
    /*
     * 通过GOODS_ID查询推荐商品
     */
    public List<PageData> findTjspById(PageData pd) throws Exception {
        if(StringUtils.isEmpty(pd.getString("GOODS_ID"))){
            throw new Exception("查询推荐商品GOODS_ID是必传字段！");
        }
        return (List<PageData>) dao.findForList("OlopdproductMapper.findTjspById", pd);
    }
    /*
     * 列表(全部)
     */
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("OlopdproductMapper.listAll", pd);
    }
    
    /*
     * 通过id获取数据
     */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("OlopdproductMapper.findById", pd);
    }
    /*
     * 通过id获取数据
     */
    public List<PageData> findImgsById(PageData pd) throws Exception {
        return  (List<PageData>) dao.findForList("OlopdproductMapper.findImgsById", pd);
    }
    /*
     * 通过id获取数据
     */
    public List<PageData> findByCode(List<String> list) throws Exception {
        return (List<PageData>) dao.findForList("OlopdproductMapper.findByCode", list);
    }

    /*
     * 批量删除
     */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("OlopdproductMapper.deleteAll", ArrayDATA_IDS);
    }

}
