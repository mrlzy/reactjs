package com.mrlzy.shiro.test;


import com.mrlzy.shiro.dao.local.mapper.MenuMapper;
import com.mrlzy.shiro.plugin.mybatis.PageInterceptor;
import com.mrlzy.shiro.plugin.mybatis.PageView;
import com.mrlzy.shiro.plugin.spring.SpringBeanLoaderAware;
import org.junit.Assert;
import org.junit.Test;

public class MybatisPageTest extends  BaseSpringTest {

    @Test
    public void test(){
        PageView pv=new PageView();
        MenuMapper menuMapper=(MenuMapper)SpringBeanLoaderAware.getSpringBean("menuMapper");
        pv.setRows(menuMapper.listMenuByWeights("","","sortno asc"));
        System.out.println(pv.toJson());
        Assert.assertTrue(true);
    }

}
