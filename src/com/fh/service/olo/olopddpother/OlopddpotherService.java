package com.fh.service.olo.olopddpother;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.StringUtils;

@Service("olopddpotherService")
public class OlopddpotherService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
     * 新增
     */
    public void save(PageData pd) throws Exception {
        dao.save("OlopddpotherMapper.save", pd);
    }

    /*
     * 新增
     */
    public void insertByBatch(List<PageData> list) throws Exception {
        //先通过ID查询出所有已经存在的
        List<PageData> listPd= (List<PageData>) dao.findForList("OlopddpotherMapper.findByIds", list);
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
            dao.batchSave("OlopddpotherMapper.insertByBatch", insertList);
        }
        if(!StringUtils.isEmpty(updateList) && updateList.size() >0){
            dao.batchUpdate("OlopddpotherMapper.edit", updateList);
        }
    }

    /*
     * 删除
     */
    public void delete(PageData pd) throws Exception {
        dao.delete("OlopddpotherMapper.delete", pd);
    }

    /*
     * 修改
     */
    public void edit(PageData pd) throws Exception {
        dao.update("OlopddpotherMapper.edit", pd);
    }

    /*
     * 修改
     */
    public void updateBatch(List<PageData> pd) throws Exception {
        dao.batchUpdate("OlopddpotherMapper.edit", pd);
    }

    /*
     * 列表
     */
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("OlopddpotherMapper.datalistPage", page);
    }

    /*
     * 列表(全部)
     */
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("OlopddpotherMapper.listAll", pd);
    }

    /*
     * 通过id获取数据
     */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("OlopddpotherMapper.findById", pd);
    }

    /*
     * 通过id获取数据
     */
    public List<PageData> findListById(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("OlopddpotherMapper.findById", pd);
    }

    /*
     * 批量删除
     */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("OlopddpotherMapper.deleteAll", ArrayDATA_IDS);
    }

}
