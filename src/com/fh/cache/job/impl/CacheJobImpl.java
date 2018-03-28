package com.fh.cache.job.impl;

import com.fh.cache.job.IcacheJob;
import com.fh.controller.base.BaseController;

public class CacheJobImpl extends BaseController implements IcacheJob  {

    @Override
    public void execute() throws Exception {
        // TODO Auto-generated method stub
        logBefore(logger, "定时清理缓存");
        
        
        
        
        logAfter(logger);
    }

}
