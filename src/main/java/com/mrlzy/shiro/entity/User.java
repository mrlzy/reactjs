package com.mrlzy.shiro.entity;

import com.mrlzy.shiro.tool.StringUtil;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

@Entity
@Table(name = "sys_user")
public class User implements Serializable {

    private static String   algorithmName="md5";
    private static int hashIterations = 2;


    @Id
    @Column(length = 32)
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String user_id;

    @Column(length = 50,nullable = false)
    private String user_name;

    @Column(length = 50,nullable = false)
    private  String account;

    @Column(length = 50,nullable = false)
    private String passwd;

    @Column(length = 50,nullable = false)
    private String salt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Org  org;


    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;


    /**
     *   111 代表有WEB，APP，及微信
     *   11  代表有APP，及微信
     *   1   代表微信
     *   10  代表APP
     */

    private int  user_type=111;


    @Column(length = 50)
    private  String user_custom;//业务扩展ID

    @Column(length = 50)
    private  String user_remark;//业务扩展ID

    private boolean locked=false;

    @Column(length = 20)
    private String tel;

    @Column(length = 50)
    private String email;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    private  static String encryptPassWd(String passwd,String salt){
        return new SimpleHash(
                algorithmName,
                passwd,
                ByteSource.Util.bytes(salt),
                hashIterations).toHex();
    }

    public void setPasswd(String passwd) {
        setSalt(StringUtil.getRandomHexNumber());
        this.passwd= encryptPassWd(passwd,this.getSalt());
    }



    public boolean validPassWd(String passwd){
       return encryptPassWd(passwd,this.getSalt()).equals(this.passwd);
    }



    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUser_custom() {
        return user_custom;
    }

    public void setUser_custom(String user_custom) {
        this.user_custom = user_custom;
    }

    public String getUser_remark() {
        return user_remark;
    }

    public void setUser_remark(String user_remark) {
        this.user_remark = user_remark;
    }

    public  boolean hasWebRole(){
       return  this.user_type/100==1;
    }

    public  boolean hasAppRole(){
        return  (this.user_type%100)/10 ==1;
    }

    public  boolean hasWeiXinRole(){
        return  this.user_type%10 ==1;
    }

    public static void main(String[] args) {
       // System.out.println(encryptPassWd("123","12519f95ddd340e27317a80ecaffcd2c"));
        System.out.println(101%100/10);

    }

}
