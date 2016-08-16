package com.mrlzy.shiro.test;


import com.mrlzy.shiro.test.base.BaseSpringTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

public class WeiXinTest extends BaseSpringTest{

     @Test
     public  void testCache(){
         EhCacheCacheManager ehCacheCacheManager=(EhCacheCacheManager)getBean("ehCacheManager");
         Cache cache= ehCacheCacheManager.getCache("weiXinCache");
         cache.put("yy","gg");
         String gg=(String)cache.get("yy").get();
         Assert.assertTrue(gg.equals("gg"));
     }


}
