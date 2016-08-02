package com.mrlzy.shiro.session;


import com.mrlzy.app.Constants;
import com.mrlzy.shiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroSessionUtils {

    public static  Subject getSubject(){
        return  SecurityUtils.getSubject();
    }

    public static Session getSession(){
        return getSubject().getSession();
    }

    public static User getCurrUser(){
        Subject subject=getSubject();
        Session session=subject.getSession();
        if(!isExsitUser()){
            subject.hasRole((String)subject.getPrincipal());
        }
        return (User)getSession().getAttribute(Constants.CURRENT_USER);
    }

   public static boolean isExsitUser(){
      return  getSession().getAttribute(Constants.CURRENT_USER)!=null;
   }

   public static  void setCurrUser(User user){
       getSession().setAttribute(Constants.CURRENT_USER,user);
   }

}
