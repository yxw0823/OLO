package com.fh.util;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.alibaba.druid.proxy.jdbc.ClobProxy;

import oracle.sql.CLOB;
 
public class OracleClobTypeHandler implements TypeHandler<Object> {
     
    public Object valueOf(String param) {
        return null;
    }
 
    @Override
    public Object getResult(ResultSet arg0, String arg1) throws SQLException {
        CLOB clob  =null;
        if  (  arg0.getClob(arg1)   instanceof ClobProxy)
        {
            ClobProxy clobProxy  = (ClobProxy) arg0.getClob(arg1);
            clob = (CLOB) clobProxy.getRawClob ();
                
        }else{
            clob = (CLOB) arg0.getClob(arg1);
        }
         
       
        return (clob == null || clob.length() == 0) ? null : clob.getSubString((long) 1, (int) clob.length());
    }
 
    @Override
    public Object getResult(ResultSet arg0, int arg1) throws SQLException {
        return null;
    }
 
    @Override
    public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
        return null;
    }
 
    @Override
    public void setParameter(PreparedStatement arg0, int arg1, Object arg2, JdbcType arg3) throws SQLException {
        CLOB clob = CLOB.empty_lob();
        clob.setString(1, (String) arg2);
        arg0.setClob(arg1, clob);
    }
}