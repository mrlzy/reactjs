package com.mrlzy.shiro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_menu")
public class Menu implements Serializable {


    @Id
    @Column(length = 32)
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private  String menu_id;

    @Column(length = 50,nullable = false)
    private  String menu_name;



    @Column(length = 150)
    private  String url;

    @Column(length = 50)
    private  String icon_cls;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",referencedColumnName="menu_id")
    @JsonIgnore
    private Menu  parent_menu;

    @OneToMany(mappedBy = "parent_menu")
    @JsonIgnore
    private Set<Menu> menus=new HashSet<Menu>();




    @ManyToMany(cascade ={CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.EAGER)
    @JoinTable(name = "sys_menu_perm",
            joinColumns = { @JoinColumn(name = "menu_id", referencedColumnName = "menu_id") },
            inverseJoinColumns = { @JoinColumn(name = "perm_id", referencedColumnName = "perm_id") })
    @JsonIgnore
    private  Set<Permission> perms=new HashSet<Permission>();

    @Column(nullable = false)
    private String weights="/";

    private boolean statu=true;

    private int sortno=0;

    private boolean isLeaf=false;


    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public Menu getParent_menu() {
        return parent_menu;
    }

    public void setParent_menu(Menu parent_menu) {
        if(parent_menu==null) return;
        this.parent_menu = parent_menu;
        setWeights(parent_menu.getWeights()+parent_menu.getMenu_id()+"/");
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }

    public boolean isStatu() {
        return statu;
    }

    public void setStatu(boolean statu) {
        this.statu = statu;
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


    public Set<Permission> getPerms() {
        return perms;
    }

    public void setPerms(Set<Permission> perms) {
        this.perms = perms;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String getIcon_cls() {
        return icon_cls;
    }

    public void setIcon_cls(String icon_cls) {
        this.icon_cls = icon_cls;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
