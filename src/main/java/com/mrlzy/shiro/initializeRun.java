package com.mrlzy.shiro;

import com.mrlzy.shiro.dao.local.jpa.*;
import com.mrlzy.shiro.entity.*;
import com.mrlzy.shiro.plugin.spring.SpringBeanLoaderAware;

import java.util.*;


public class initializeRun {

    /**
     *
     * 初始化系统,系统初始设置
     * 1.把spring.xml关于web的属性注释
     * 2。把jpa  hibernate.hbm2ddl.auto设置为create
     * @param args
     */




    public static void main(String[] args) {

        /**
         * 创建菜单资源
         */

        Map<String,Menu> map=new HashMap<>();
        MenuJpa menuJpa=(MenuJpa)SpringBeanLoaderAware.getSpringBean("menuJpa");
        Menu m01=new Menu();
        m01.setMenu_name("系统管理");
        m01.setIcon_cls("fa-list");
        m01.setLeaf(false);
        menuJpa.save(m01);
        map.put(m01.getMenu_id(),m01);

        Menu  m0101=new Menu();
        m0101.setMenu_name("菜单管理");
        m0101.setUrl("menu/index");
        m0101.setLeaf(true);
        m0101.setParent_menu(m01);
        menuJpa.save(m0101);
        map.put(m0101.getMenu_id(),m0101);

        Menu m0102=new Menu();
        m0102.setMenu_name("角色管理");
        m0102.setUrl("role/index");
        m0102.setLeaf(true);
        m0102.setSortno(1);
        m0102.setParent_menu(m01);
        menuJpa.save(m0102);
        map.put(m0102.getMenu_id(),m0102);

        Menu m0103=new Menu();
        m0103.setMenu_name("组织管理");
        m0103.setUrl("org/index");
        m0103.setLeaf(true);
        m0103.setSortno(2);
        m0103.setParent_menu(m0103);
        menuJpa.save(m01);
        map.put(m0103.getMenu_id(),m0103);



        Menu m0104=new Menu();
        m0104.setMenu_name("用户管理");
        m0104.setUrl("user/index");
        m0104.setLeaf(true);
        m0104.setSortno(2);
        m0104.setParent_menu(m01);
        menuJpa.save(m0104);
        map.put(m0104.getMenu_id(),m0104);

        Menu menu02=new Menu();
        menu02.setMenu_name("开发者工具");
        menu02.setIcon_cls("fa-tag");
        menu02.setLeaf(false);
        menu02.setSortno(50);
        menuJpa.save(menu02);
        map.put(menu02.getMenu_id(),menu02);


        Menu menu0201=new Menu();
        menu0201.setMenu_name("自定义控件");
        menu0201.setLeaf(false);
        menu0201.setParent_menu(menu02);
        menuJpa.save(menu0201);
        map.put(menu0201.getMenu_id(),menu0201);


        Menu menu020101=new Menu();
        menu020101.setMenu_name("菜单选择控件");
        menu020101.setUrl("demo/menu");
        menu020101.setLeaf(true);
        menu020101.setParent_menu(menu0201);
        menuJpa.save(menu020101);
        map.put(menu020101.getMenu_id(),menu020101);

        Menu menu020102=new Menu();
        menu020102.setMenu_name("角色选择控件");
        menu020102.setUrl("demo/role");
        menu020102.setLeaf(true);
        menu020102.setParent_menu(menu0201);
        menuJpa.save(menu020102);
        map.put(menu020102.getMenu_id(),menu020102);


        //(菜单,角色)两菜单创建操作
        PermJpa permJpa=(PermJpa)SpringBeanLoaderAware.getSpringBean("permJpa");

        Permission p0102=new Permission();
        p0102.setPerm_code("*");
        p0102.setRemark("root");
        Set<Menu> pm0102=new HashSet<Menu>();
        pm0102.add(m0102);
        p0102.setMenus(pm0102);
        permJpa.save(p0102);

        Permission p0101=new Permission();
        p0101.setPerm_code("*");
        p0101.setRemark("root");
        Set<Menu> pm0101=new HashSet<Menu>();
        pm0101.add(m0101);
        p0101.setMenus(pm0101);
        permJpa.save(p0101);

        //创建角色
        RoleJpa roleJpa=(RoleJpa)SpringBeanLoaderAware.getSpringBean("roleJpa");

        Role role=new Role();
        role.setRole_name("超级管理员");
        role.setSortno(0);
        role.setLeaf(false);
        roleJpa.save(role);

        //给角色赋权

        Set<Permission> rp=new HashSet<Permission>();
        rp.add(p0101);
        rp.add(p0102);

        role.setPerms(rp);

        //从操作权限中提取菜单权限
        Set<Permission>   ps=  role.getPerms();
        Set<Menu> menus=new HashSet<>();
        for(Permission p:ps){
            Set<Menu>  ms= p.getMenus();
            Iterator<Menu> it=ms.iterator();
            while(it.hasNext()){
                Menu  tt=it.next();
                String[] ids=  ( tt.getWeights()+tt.getMenu_id()).split("/");
                for(String id:ids){
                   if(map.containsKey(id)){
                       menus.add(map.get(id));
                   }
                }
            }
        }

        role.setMenus(menus);
        roleJpa.save(role);



        // 创建组织部门
        OrgJpa orgJpa=(OrgJpa)SpringBeanLoaderAware.getSpringBean("orgJpa");
        Org root=new Org();
        root.setOrg_name("温州移动");
        root.setLeaf(false);
        orgJpa.save(root);

        Org org2=new Org();
        org2.setOrg_name("市场部");
        org2.setParent_org(root);
        org2.setLeaf(false);
        orgJpa.save(org2);

        Org org3=new Org();
        org3.setOrg_name("业务支撑中心");
        org3.setParent_org(org2);
        org3.setLeaf(true);
        orgJpa.save(org3);


       //  创建admin

       UserJpa userJpa=(UserJpa)SpringBeanLoaderAware.getSpringBean("userJpa");
        User user=new User();
        user.setUser_name("mrlzy");
        user.setAccount("admin");
        user.setPasswd("123");
        user.setOrg(org3);
        userJpa.save(user);

        //赋角色
        user.setRole(role);
        userJpa.save(user);




    }



}
