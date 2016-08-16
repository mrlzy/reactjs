package com.mrlzy.shiro.realm;



import com.mrlzy.shiro.dao.local.jpa.RoleJpa;
import com.mrlzy.shiro.dao.local.jpa.UserJpa;
import com.mrlzy.shiro.entity.Menu;
import com.mrlzy.shiro.entity.Permission;
import com.mrlzy.shiro.entity.Role;
import com.mrlzy.shiro.entity.User;
import com.mrlzy.shiro.session.ShiroSessionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class UserLoginRealm  extends AuthorizingRealm {

    private static Log log = LogFactory.getLog(UserLoginRealm.class);


    @Autowired
    private UserJpa userJpa;


    @Autowired
    private RoleJpa roleJpa;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();  //得到用户名
        String password = new String((char[])token.getCredentials()); //得到密码

        User   user=userJpa.findByAccount(username);

        if(user==null) {
            throw new UnknownAccountException("用户名不存在"); //如果用户名错误
        }

        if(Boolean.TRUE.equals(user.isLocked())) {
            throw new LockedAccountException("帐号锁定"); //帐号锁定
        }

       String telCode= ShiroSessionUtils.getUserTelValiCode();
        if(user.hasWeiXinRole()&&telCode!=null&&telCode.length()>0){
             if(!telCode.equals(password)){
                 throw new IncorrectCredentialsException("验证码错误,请重新获取"); //帐号锁定
             }
        }else{
            if(!user.validPassWd(password)) {
                throw new IncorrectCredentialsException("密码错误"); //帐号锁定
            }
        }
        ShiroSessionUtils.setCurrUser(user);
        return   new SimpleAuthenticationInfo(username, password, getName());

    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String account = (String)principals.getPrimaryPrincipal();
        User  user= null;
        if(ShiroSessionUtils.isExsitUser()){
            user=ShiroSessionUtils.getCurrUser();
        }else{
            user= userJpa.findByAccount(account);
            ShiroSessionUtils.setCurrUser(user);

        }
        Role role_=user.getRole();
        Set<String> roleSet=new HashSet<>();
        roleSet.add("role"+role_.getRole_id());//角色ID
        roleSet.add("user"+user.getUser_id());//用户ID
        roleSet.add("type"+user.getUser_type());//用户类型
        List<Role> roles=roleJpa.findByWeightsLike(role_.getRole_id());
        roles.add(role_);
        for (Role role: roles) {
            roleSet.add("role"+role.getRole_id());
            Set<Permission> perms=  role.getPerms();
            Iterator<Permission> it= perms.iterator();
            while (it.hasNext()){
                Permission p=it.next();
                String permissions_role=role.getRole_id();
                Iterator<Menu>  mit= p.getMenus().iterator();
                while (mit.hasNext()){
                    String  permissions=permissions_role+":"+mit.next().getMenu_id()+"_"+p.getPermType()+":"+p.getPerm_code();
                    log.info("permissions["+permissions+"]");
                    authorizationInfo.addStringPermission(permissions);
                }
            }
        }
        return authorizationInfo;
    }
}
