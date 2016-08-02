package com.mrlzy.shiro;

import com.mrlzy.shiro.dao.local.jpa.*;
import com.mrlzy.shiro.entity.*;
import com.mrlzy.shiro.plugin.spring.SpringBeanLoaderAware;

import java.util.HashSet;
import java.util.Set;


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
        MenuJpa menuJpa=(MenuJpa)SpringBeanLoaderAware.getSpringBean("menuJpa");


        Menu menu=new Menu();
        menu.setMenu_name("系统管理");
        menu.setIcon_cls("fa-list");
        menu.setLeaf(false);
        menuJpa.save(menu);

        Menu m=new Menu();
        m.setMenu_name("菜单管理");
        m.setUrl("menu/index");
        m.setLeaf(true);
        m.setParent_menu(menu);
        menuJpa.save(m);

        Menu m3=new Menu();
        m3.setMenu_name("角色管理");
        m3.setUrl("role/index");
        m3.setLeaf(true);
        m3.setSortno(1);
        m3.setParent_menu(menu);
        menuJpa.save(m3);


        Menu m4=new Menu();
        m4.setMenu_name("组织管理");
        m4.setUrl("org/index");
        m4.setLeaf(true);
        m4.setSortno(2);
        m4.setParent_menu(menu);
        menuJpa.save(m4);



        Menu m5=new Menu();
        m5.setMenu_name("用户管理");
        m5.setUrl("user/index");
        m5.setLeaf(true);
        m5.setSortno(2);
        m5.setParent_menu(menu);
        menuJpa.save(m5);


       Menu menu2=new Menu();
        menu2.setMenu_name("开发者工具");
        menu2.setIcon_cls("fa-tag");
        menu2.setLeaf(false);
        menu2.setSortno(50);
        menuJpa.save(menu2);


        Menu m2m=new Menu();
        m2m.setMenu_name("自定义控件");
        m2m.setLeaf(false);
        m2m.setParent_menu(menu2);
        menuJpa.save(m2m);


        Menu m2m1=new Menu();
        m2m1.setMenu_name("菜单选择控件");
        m2m1.setUrl("demo/menu");
        m2m1.setLeaf(true);
        m2m1.setParent_menu(menu2);
        menuJpa.save(m2m1);

        Menu m2m2=new Menu();
        m2m2.setMenu_name("角色选择控件");
        m2m2.setUrl("demo/role");
        m2m2.setLeaf(true);
        m2m2.setParent_menu(menu2);
        menuJpa.save(m2m2);

        // 创建组织部门

        OrgJpa orgJpa=(OrgJpa)SpringBeanLoaderAware.getSpringBean("orgJpa");
        Org root=new Org();
        root.setOrg_name("温州移动");
        menu.setLeaf(false);
        orgJpa.save(root);

        Org org2=new Org();
        org2.setOrg_name("市场部");
        org2.setParent_org(root);
        menu.setLeaf(false);
        orgJpa.save(org2);

        Org org3=new Org();
        org3.setOrg_name("业务支撑中心");
        org3.setParent_org(org2);
        menu.setLeaf(true);
        orgJpa.save(org3);


       //  创建admin

        UserJpa userJpa=(UserJpa)SpringBeanLoaderAware.getSpringBean("userJpa");
        RoleJpa roleJpa=(RoleJpa)SpringBeanLoaderAware.getSpringBean("roleJpa");

        User user=new User();
        user.setUser_name("mrlzy");
        user.setAccount("admin");
        user.setPasswd("123");
        user.setOrg(org3);
        userJpa.save(user);

       //创建权限

        Role role=new Role();
        role.setRole_name("超级管理员");
        role.setSortno(0);
        role.setLeaf(false);
        roleJpa.save(role);

       /* Set<Permission> perms=new HashSet<>();
        perms.add(p);
        role.setPerms(perms);


        roleJpa.save(role);

        Set<Role> roles=new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userJpa.save(user);*/

    }
}
