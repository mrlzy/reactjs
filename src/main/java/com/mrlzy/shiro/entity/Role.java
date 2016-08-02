package com.mrlzy.shiro.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_role")
public class Role implements Serializable {

    @Id
    @Column(length = 32)
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private  String role_id;

    @Column(length = 50,nullable = false)
    private  String role_name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",referencedColumnName="role_id")
    private Role  parent_role;

    @OneToMany(mappedBy = "parent_role")
    private Set<Role> roles=new HashSet<Role>();


    @Column(nullable = false)
    private String weights="/";


    private int sortno=0;

    private boolean isLeaf=false;


    @ManyToMany(mappedBy = "role")
    private Set<User> users=new HashSet<User>();


    @ManyToMany(cascade ={CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_perm",
            joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "perm_id", referencedColumnName = "perm_id") })
    private Set<Permission> perms=new HashSet<Permission>();

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_menu",
            joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "menu_id", referencedColumnName = "menu_id") })
    private Set<Menu>  menus =new HashSet<>();


    private  String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Permission> getPerms() {
        return perms;
    }

    public void setPerms(Set<Permission> perms) {
        this.perms = perms;
    }

    public Role getParent_role() {
        return parent_role;
    }

    public void setParent_role(Role parent_role) {
        if(parent_role==null) return;
        this.parent_role = parent_role;
        setWeights(parent_role.getWeights()+parent_role.getRole_id()+"/");
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }

    public int getSortno() {
        return sortno;
    }

    public void setSortno(int sortno) {
        this.sortno = sortno;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }
}
