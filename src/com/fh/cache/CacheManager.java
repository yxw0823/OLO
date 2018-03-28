package com.fh.cache;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
 
 //Description: 管理缓存 
 
 //可扩展的功能：当chche到内存溢出时必须清除掉最早期的一些缓存对象，这就要求对每个缓存对象保存创建时间 
 
public class CacheManager { 
    private volatile static  HashMap cacheMap ;
    //单实例构造方法 
    private CacheManager() { 
        super(); 
    } 
    
    public static HashMap getCache() {  
        if (cacheMap == null) {  
            synchronized (HashMap.class) {  
                if (cacheMap == null) { 
                    
                    cacheMap = new HashMap();  
                }  
            }  
        }  
        return cacheMap;  
    }  
    //获取布尔值的缓存 
    public static boolean getSimpleFlag(String key){ 
        try{ 
            return (Boolean) getCache().get(key); 
        }catch(NullPointerException e){ 
            return false; 
        } 
    } 
    public static long getServerStartdt(String key){ 
        try { 
            return (Long)getCache().get(key); 
        } catch (Exception ex) { 
            return 0; 
        } 
    } 
    //设置布尔值的缓存 
    public synchronized static boolean setSimpleFlag(String key,boolean flag){ 
        if (flag && getSimpleFlag(key)) {//假如为真不允许被覆盖 
            return false; 
        }else{ 
            getCache().put(key, flag); 
            return true; 
        } 
    } 
    public synchronized static boolean setSimpleFlag(String key,long serverbegrundt){ 
        if (getCache().get(key) == null) { 
            getCache().put(key,serverbegrundt); 
            return true; 
        }else{ 
            return false; 
        } 
    } 
 
 
    //得到缓存。同步静态方法 
    private synchronized static Cache getCache(String key) { 
        return (Cache) getCache().get(key); 
    } 
 
    //判断是否存在一个缓存 
    private synchronized static boolean hasCache(String key) { 
        return getCache().containsKey(key); 
    } 
 
    //清除所有缓存 
    public synchronized static void clearAll() { 
        getCache().clear(); 
    } 
 
    //清除某一类特定缓存,通过遍历HASHMAP下的所有对象，来判断它的KEY与传入的TYPE是否匹配 
    public synchronized static void clearAll(String type) { 
        Iterator i = getCache().entrySet().iterator(); 
        String key; 
        ArrayList arr = new ArrayList(); 
        try { 
            while (i.hasNext()) { 
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next(); 
                key = (String) entry.getKey(); 
                if (key.startsWith(type)) { //如果匹配则删除掉 
                    arr.add(key); 
                } 
            } 
            for (int k = 0; k < arr.size(); k++) { 
                clearOnly((String) arr.get(k)); 
            } 
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        } 
    } 
 
    //清除指定的缓存 
    public synchronized static void clearOnly(String key) { 
        getCache().remove(key); 
    } 
 
    //载入缓存 
    public synchronized static void putCache(String key, Cache obj) { 
        getCache().put(key, obj); 
    } 
 
    //获取缓存信息 
    public static Cache getCacheInfo(String key) { 
 
        if (hasCache(key)) { 
            Cache cache = getCache(key); 
            if (cacheExpired(cache)) { //调用判断是否终止方法 
                cache.setExpired(true); 
                return null;
            } 
            return cache; 
        }else 
            return null; 
    } 

    //获取缓存信息 
    public static String getCacheInfoString(String key) { 
 
        if (hasCache(key)) { 
            Cache cache = getCache(key); 
            if (cacheExpired(cache)) { //调用判断是否终止方法 
                cache.setExpired(true); 
               return null;
            } 
            return (String)cache.getValue(); 
        }else 
            return null; 
    } 
    //载入缓存信息 
    public static void putCacheInfo(String key, Cache obj, long dt,boolean expired) { 
        Cache cache = new Cache(); 
        cache.setKey(key); 
        cache.setTimeOut(dt + System.currentTimeMillis()); //设置多久后更新缓存 
        cache.setValue(obj); 
        cache.setExpired(expired); //缓存默认载入时，终止状态为FALSE 
        getCache().put(key, cache); 
    } 
    //载入缓存信息 
    public static void putCacheInfo(String key, String obj, long dt,boolean expired) { 
        Cache cache = new Cache(); 
        cache.setKey(key); 
        cache.setTimeOut(dt + System.currentTimeMillis()); //设置多久后更新缓存 
        cache.setValue(obj); 
        cache.setExpired(expired); //缓存默认载入时，终止状态为FALSE 
        getCache().put(key, cache); 
    } 
    //重写载入缓存信息方法 
    public static void putCacheInfo(String key,String obj,long dt){ 
        Cache cache = new Cache(); 
        cache.setKey(key); 
        cache.setTimeOut(dt+System.currentTimeMillis()); 
        cache.setValue(obj); 
        cache.setExpired(false); 
        getCache().put(key,cache); 
    } 
    //重写载入缓存信息方法 
    public static void putCacheInfo(String key,Object obj,long dt){ 
        Cache cache = new Cache(); 
        cache.setKey(key); 
        cache.setTimeOut(dt+System.currentTimeMillis()); 
        cache.setValue(obj); 
        cache.setExpired(false); 
        getCache().put(key,cache); 
    } 
 
    //判断缓存是否终止 
    public static boolean cacheExpired(Cache cache) { 
        if (null == cache) { //传入的缓存不存在 
            return false; 
        } 
        long nowDt = System.currentTimeMillis(); //系统当前的毫秒数 
        long cacheDt = cache.getTimeOut(); //缓存内的过期毫秒数 
        if (cacheDt <= 0||cacheDt>nowDt) { //过期时间小于等于零时,或者过期时间大于当前时间时，则为FALSE 
            return false; 
        } else { //大于过期时间 即过期 
            return true; 
        } 
    } 
    
    
    
    /**
     * 
       
     * clearAllTiemOut(清理所有已经超时的缓存)    
     * @param  @return    设定文件    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public synchronized static void clearAllTiemOut() { 
        Iterator i = getCache().entrySet().iterator(); 
        String key; 
        ArrayList arr = new ArrayList(); 
        try { 
            while (i.hasNext()) { 
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next(); 
                key = (String) entry.getKey(); 
                Cache cache = getCache(key); 
                if (cacheExpired(cache)) { 
                    arr.add(key); 
                } 
            } 
            for (int k = 0; k < arr.size(); k++) { 
                clearOnly((String) arr.get(k)); 
            } 
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        } 
    } 
    
 
    //获取缓存中的大小 
    public static int getCacheSize() { 
        return getCache().size(); 
    } 
 
    //获取指定的类型的大小 
    public static int getCacheSize(String type) { 
        int k = 0; 
        Iterator i = getCache().entrySet().iterator(); 
        String key; 
        try { 
            while (i.hasNext()) { 
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next(); 
                key = (String) entry.getKey(); 
                if (key.indexOf(type) != -1) { //如果匹配则删除掉 
                    k++; 
                } 
            } 
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        } 
 
        return k; 
    } 
 
    //获取缓存对象中的所有键值名称 
    public static ArrayList getCacheAllkey() { 
        ArrayList a = new ArrayList(); 
        try { 
            Iterator i = getCache().entrySet().iterator(); 
            while (i.hasNext()) { 
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next(); 
                a.add((String) entry.getKey()); 
            } 
        } catch (Exception ex) {} finally { 
            return a; 
        } 
    } 
 
    //获取缓存对象中指定类型 的键值名称 
    public static ArrayList getCacheListkey(String type) { 
        ArrayList a = new ArrayList(); 
        String key; 
        try { 
            Iterator i = getCache().entrySet().iterator(); 
            while (i.hasNext()) { 
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next(); 
                key = (String) entry.getKey(); 
                if (key.indexOf(type) != -1) { 
                    a.add(key); 
                } 
            } 
        } catch (Exception ex) {} finally { 
            return a; 
        } 
    } 
 
} 
 
 
