package com.mrlzy.shiro.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_org")
public class Org implements Serializable {

    @Id
    @Column(length = 32)
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String org_id;

    @Column(length = 50,nullable = false)
    private String org_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",referencedColumnName="org_id")
    private Org  parent_org;

    @OneToMany(mappedBy = "parent_org")
    private Set<Org> orgs=new HashSet<Org>();


    @Column(nullable = false)
    private String weights="/";

    public Set<Org> getOrgs() {
        return orgs;
    }

    public void setOrgs(Set<Org> orgs) {
        this.orgs = orgs;
    }

    private boolean statu=true;

    private int sortno=0;

    private boolean isLeaf=false;

    private  String remark;

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSortno() {
        return sortno;
    }

    public void setSortno(int sortno) {
        this.sortno = sortno;
    }

    public boolean isStatu() {
        return statu;
    }

    public void setStatu(boolean statu) {
        this.statu = statu;
    }

    public Org getParent_org() {
        return parent_org;
    }

    public void setParent_org(Org parent_org) {
        if(parent_org==null) return;
        this.parent_org = parent_org;
        setWeights(parent_org.getWeights()+parent_org.getOrg_id()+"/");

    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }
}
