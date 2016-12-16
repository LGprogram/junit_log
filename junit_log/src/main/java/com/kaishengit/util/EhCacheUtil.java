package com.kaishengit.util;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import java.io.Serializable;

/**
 * Created by liu on 2016/12/14.
 */

/**
 *Ehcache工具类
 */
public class EhCacheUtil {
    private static CacheManager cacheManager = new CacheManager();

    public Ehcache getEhCache(String cacheName) {
        return cacheManager.getCache(cacheName);
    }
    public void put(Ehcache ehcache, Serializable key,Serializable value){
        Element element = new Element(key,value);
        ehcache.put(element);
    }
    public void put(Ehcache ehcache, Object key,Object value){
        Element element = new Element(key,value);
        ehcache.put(element);
    }
    public void put(String cacheName, Serializable key,Serializable value){
        Element element = new Element(key,value);
        getEhCache(cacheName).put(element);
    }
    public void put(String cacheName, Object key,Object value){
        Element element = new Element(key,value);
        getEhCache(cacheName).put(element);
    }

    public Object get(Ehcache ehcache,Serializable key){
        return ehcache.get(key)==null ? null:ehcache.get(key).getObjectValue();
    }
    public Object get(Ehcache ehcache,Object key){
        return ehcache.get(key)==null ? null:ehcache.get(key).getObjectValue();
    }
    public Object get(String cacheName,Object key){
        return getEhCache(cacheName).get(key)==null?null:getEhCache(cacheName).get(key).getObjectValue();
    }
    public Object get(String cacheName,Serializable key){
        return getEhCache(cacheName).get(key)==null?null:getEhCache(cacheName).get(key).getObjectValue();
    }
    public void removeAll(Ehcache ehcache){
        ehcache.removeAll();
    }
    public void removeAll(String cacheName){
        getEhCache(cacheName).removeAll();
    }
    public void remove(Ehcache ehcache,Serializable key){
        ehcache.remove(key);
    }
    public void remove(Ehcache ehcache,Object key){
        ehcache.remove(key);
    }
    public void remove(String cacheName,Object key){
        getEhCache(cacheName).remove(key);
    }
    public void remove(String cacheName,Serializable key){
        getEhCache(cacheName).remove(key);
    }
}
