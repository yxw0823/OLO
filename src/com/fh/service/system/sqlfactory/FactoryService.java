package com.fh.service.system.sqlfactory;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
@Service("factoryService")
public class FactoryService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /*
     *用户列表(全部)
     */
     public List<PageData> findUserTabColumns(PageData pd)throws Exception{
         return (List<PageData>) dao.findForList("FactoryMapper.findUserTabColumns", pd);
     }
}
