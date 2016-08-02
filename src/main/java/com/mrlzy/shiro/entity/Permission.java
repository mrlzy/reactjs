package com.mrlzy.shiro.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_perm")
public class Permission implements Serializable {

    @Id
    @Column(length = 32)
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private  String perm_id;

    private  PermType permType=PermType.MENU;


    @Column(length = 50,nullable = false)
    private String perm_code;

    private String remark;


    @ManyToMany(mappedBy = "perms")
    private Set<Role> roles=new HashSet<Role>();



    @ManyToMany(mappedBy = "perms")
    private Set<Menu> menus=new HashSet<Menu>();


    public String getPerm_code() {
        return perm_code;
    }

    public void setPerm_code(String perm_code) {
        this.perm_code = perm_code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPerm_id() {
        return perm_id;
    }

    public void setPerm_id(String perm_id) {
        this.perm_id = perm_id;
    }

    public PermType getPermType() {
        return permType;
    }

    public void setPermType(PermType permType) {
        this.permType = permType;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }




    public static enum PermType{
         MENU,PAGE,FILE
    }



}
