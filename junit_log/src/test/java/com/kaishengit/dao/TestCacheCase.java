package com.kaishengit.dao;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.junit.Test;

/**
 * Created by liu on 2016/12/14.
 */

public class TestCacheCase {
    @Test
    public void testCache(){
        //读配置文件
        CacheManager cacheManager = new CacheManager();
        Ehcache ehcache=cacheManager.getEhcache("user");
        //添加
        Element element = new Element("user:1","jack");
        ehcache.put(element);
        //删除
/*//        ehcache.removeAll();//将缓存中的元素全部删除
        ehcache.remove("user:1");//删除特定的键对应的元素*/
        //取值
        Element e = ehcache.get("user:1");
        System.out.println(e.getObjectValue());
    }


}
