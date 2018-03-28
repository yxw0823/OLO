package com.fh.service.olo.olopdcarousel;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service("olopdcarouselService")
public class OlopdcarouselService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
     * 新增
     */
    public void save(PageData pd) throws Exception {
        dao.save("OlopdcarouselMapper.save", pd);
    }

    /*
     * 新增
     */
    public void batchSave(List<PageData> pd) throws Exception {
        dao.batchSave("OlopdcarouselMapper.batchSave", pd);
    }

    /*
     * 删除
     */
    public void delete(PageData pd) throws Exception {
        dao.delete("OlopdcarouselMapper.delete", pd);
    }

    /*
     * 修改
     */
    public void edit(PageData pd) throws Exception {
        dao.update("OlopdcarouselMapper.edit", pd);
    }

    /*
     * 列表
     */
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("OlopdcarouselMapper.datalistPage", page);
    }
    /*
     * 列表
     */
    public List<PageData> findBymenuId(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("OlopdcarouselMapper.findBymenuId", pd);
    }
    
    /*
     * 列表(全部)
     */
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("OlopdcarouselMapper.listAll", pd);
    }

    /*
     * 通过id获取数据
     */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("OlopdcarouselMapper.findById", pd);
    }

    /*
     * 批量删除
     */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("OlopdcarouselMapper.deleteAll", ArrayDATA_IDS);
    }

}
