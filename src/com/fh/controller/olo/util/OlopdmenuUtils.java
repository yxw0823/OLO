package com.fh.controller.olo.util;

import java.util.ArrayList;
import java.util.List;

import com.fh.util.PageData;
import com.fh.util.StringUtils;

public class OlopdmenuUtils {
        /**
         *  获取分类组合Id
         * @param pd
         * @return
         */
        public  static String getHaseCode(PageData pd,String key){
            
            return String.valueOf((pd.getString("M_NAME")+pd.getString("CID") +key).hashCode());//通过组合编码获取hashCode
        }
        
        /**
         * 
           
         * getHaseCodeByName(通过分类的 NAME 和 ID组合获取HASECODE)    
           
         * @param   name    (分类名称,ID列：橱柜,1)
         * @param   key    (盐)
           
         * @return String    分类的hashCode        
           
           
         * @since  CodingExample　Ver(编码范例查看) 1.1
         */
        public  static String getHaseCodeByName(String value,String key){
            if(value ==null || "".equals(value) ){
                return "";
            }
            if(value.indexOf(",") ==-1){
                return "";
            }
            PageData pd = new PageData();
            String[] values = value.split(",");
            pd.put("M_NAME", values[0]);
            pd.put("CID", values[1]);
            return getHaseCode(pd,key);//通过组合编码获取hashCode
        }
        
        
        /**
         * 
           
         * getHaseCodeByName(通过分类的 NAME 和 ID组合获取HASECODE)    
           
         * @param   name    (分类名称,ID列：橱柜,1)
           
         * @return String    分类的hashCode        
           
           
         * @since  CodingExample　Ver(编码范例查看) 1.1
         */
        public  static List<String> getHaseCode(String ONE_SELECT,String TWO_SELECT
                ,String THREE_SELECT,String FOUR_SELECT
                ){
            List<String> classify = new ArrayList<String>();
            String temp = "";
            if (!StringUtils.isEmpty(ONE_SELECT)) {
                temp =OlopdmenuUtils.getHaseCodeByName(ONE_SELECT, "queryMenua");
                if(!StringUtils.isEmpty(temp)){
                    classify.add(temp);
                }
            }
            if (!StringUtils.isEmpty(TWO_SELECT)) {
                temp =OlopdmenuUtils.getHaseCodeByName(TWO_SELECT, "queryMenub");
                if(!StringUtils.isEmpty(temp)){
                    classify.add(temp);
                }
            }
            if (!StringUtils.isEmpty(THREE_SELECT)) {
                temp =OlopdmenuUtils.getHaseCodeByName(THREE_SELECT, "queryMenuc");
                if(!StringUtils.isEmpty(temp)){
                    classify.add(temp);
                }
            }
            if (!StringUtils.isEmpty(FOUR_SELECT)) {
                temp =OlopdmenuUtils.getHaseCodeByName(FOUR_SELECT, "queryMenud");
                if(!StringUtils.isEmpty(temp)){
                    classify.add(temp);
                }
            }
            return classify;//通过组合编码获取hashCode
        }
        
        /**
         * 
           
         * getHaseCodeByName(通过分类的 NAME 和 ID组合获取HASECODE)    
           
         * @param   name    (分类名称,ID列：橱柜,1)
           
         * @return String    分类的hashCode        
           
           
         * @since  CodingExample　Ver(编码范例查看) 1.1
         */
        public  static PageData setClassifyToPd(PageData pd){
            String ONE_SELECT = pd.getString("ONE_SELECT");
            String TWO_SELECT = pd.getString("TWO_SELECT");
            String THREE_SELECT = pd.getString("THREE_SELECT");
            String FOUR_SELECT = pd.getString("FOUR_SELECT"); 
            List<String> classify = OlopdmenuUtils.getHaseCode(ONE_SELECT, TWO_SELECT, THREE_SELECT, FOUR_SELECT);
            if(classify.size() >0){
                pd.put("classify", classify);
            }
            return pd;//通过组合编码获取hashCode
        }
}
