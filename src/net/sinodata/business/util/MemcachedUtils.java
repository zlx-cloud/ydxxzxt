package net.sinodata.business.util;
import java.util.Date;

import com.danga.MemCached.MemCachedClient;
public class MemcachedUtils {
	 /**
     * cachedClient.
     */
    private static MemCachedClient cachedClient;
    static {
        if (cachedClient == null) {
            cachedClient = new MemCachedClient("memcachedPool");
        }
    }
    /**
     * 构造函数.
     */
    private MemcachedUtils() {
    }
    /**
     * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换.
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public static boolean set(String key, Object value) {
        return setExp(key, value, null);
    }
    /**
     * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    public static boolean set(String key, Object value, Date expire) {
        return setExp(key, value, expire);
    }
    /**
     * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    private static boolean setExp(String key, Object value, Date expire) {
        boolean flag = false;
        try {
            flag = cachedClient.set(key, value, expire);
        } catch (Exception e) {
        	e.printStackTrace();        }
        return flag;
    }
    /**
     * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对.
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public static boolean add(String key, Object value) {
        return addExp(key, value, null);
    }
    /**
     * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    public static boolean add(String key, Object value, Date expire) {
        return addExp(key, value, expire);
    }
    /**
     * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    private static boolean addExp(String key, Object value, Date expire) {
        boolean flag = false;
        try {
            flag = cachedClient.add(key, value, expire);
        } catch (Exception e) {
        	e.printStackTrace();        }
        return flag;
    }
    /**
     * 仅当键已经存在时，replace 命令才会替换缓存中的键.
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public static boolean replace(String key, Object value) {
        return replaceExp(key, value, null);
    }
    /**
     * 仅当键已经存在时，replace 命令才会替换缓存中的键.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    public static boolean replace(String key, Object value, Date expire) {
        return replaceExp(key, value, expire);
    }
    /**
     * 仅当键已经存在时，replace 命令才会替换缓存中的键.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    private static boolean replaceExp(String key, Object value, Date expire) {
        boolean flag = false;
        try {
            flag = cachedClient.replace(key, value, expire);
        } catch (Exception e) {
        	e.printStackTrace();        }
        return flag;
    }
    /**
     * get 命令用于检索与之前添加的键值对相关的值.
     * @param key 键
     * @return boolean
     */
    public static Object get(String key) {
        Object obj = null;
        try {
            obj = cachedClient.get(key);
        } catch (Exception e) {
        	e.printStackTrace();        }
        return obj;
    }
    /**
     * 删除 memcached 中的任何现有值.
     * @param key 键
     * @return boolean
     */
    public static boolean delete(String key) {
        return deleteExp(key, null);
    }
    /**
     * 删除 memcached 中的任何现有值.
     * @param key 键
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    public static boolean delete(String key, Date expire) {
        return deleteExp(key, expire);
    }
    /**
     * 删除 memcached 中的任何现有值.
     * @param key 键
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    @SuppressWarnings("deprecation")
    private static boolean deleteExp(String key, Date expire) {
        boolean flag = false;
        try {
            flag = cachedClient.delete(key, expire);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return flag;
    }
    /**
     * 清理缓存中的所有键/值对.
     * @return boolean
     */
    public static boolean flashAll() {
        boolean flag = false;
        try {
            flag = cachedClient.flushAll();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return flag;
    }
    public static void main(String[] args) {
    	//System.out.println(MemcachedUtils.set("aa", "bb", new Date(1000 * 60)));
    	Object obj = MemcachedUtils.get("aa");  
    	//System.out.println("***************************");  
    	//System.out.println(obj.toString()); 
    	//System.out.println(MemcachedUtils.set("aa", "bb1", new Date(1000 * 60)));
    	Object objs = MemcachedUtils.get("aa");  
    	//System.out.println("***************************");
    	//System.out.println(objs.toString()); 
    	
    	
    }
}
