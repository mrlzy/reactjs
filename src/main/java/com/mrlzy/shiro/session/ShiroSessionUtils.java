package com.mrlzy.shiro.session;


import com.mrlzy.shiro.app.Constants;
import com.mrlzy.shiro.entity.User;
import com.mrlzy.shiro.weixin.bean.WeiXinUser;
import com.mrlzy.shiro.weixin.builder.WeiXinClientBuilder;
import com.mrlzy.shiro.weixin.client.WeiXinAppClient;
import com.mrlzy.shiro.weixin.client.WeiXinWebClient;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShiroSessionUtils {

    public static  Subject getSubject(){
        return  SecurityUtils.getSubject();
    }


    public static boolean login(HttpServletRequest request ) {
        Subject subject=getSubject();
        if(subject.isAuthenticated()) return true;
        if("get".equalsIgnoreCase(request.getMethod())) return  false;

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            return true;
        } catch (Exception e) {
            request.setAttribute(Constants.DEFAULT_ERROR_KEY_ATTRIBUTE_MSG,e.getMessage());
            return false;
        }
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

    public static  void setUserTelValiCode(String code){
        getSession().setAttribute(Constants.CURRENT_USER_TELVALICODE,code);
    }

    public static  String  getUserTelValiCode(){
        return   (String)getSession().removeAttribute(Constants.CURRENT_USER_TELVALICODE) ;
    }

    public static  void setUserValiCode(String code){
        getSession().setAttribute(Constants.DEFAULT_VALIDCODE_ATTRIBUTE,code);
    }

    public static  String  getUserValiCode(){
        return   (String)getSession().removeAttribute(Constants.DEFAULT_VALIDCODE_ATTRIBUTE) ;
    }


    public static WeiXinWebClient getWeiXinWebClient(){
          Object client=getSession().getAttribute(Constants.USER_WEI_XIN_WEB_CLIENT);
         return (WeiXinWebClient)client;
    }

    public static WeiXinWebClient setWeiXinWebClient(){
        WeiXinWebClient  client= WeiXinClientBuilder.createWeiXinWebClient();
        getSession().setAttribute(Constants.USER_WEI_XIN_WEB_CLIENT,client);
        return (WeiXinWebClient)client;
    }

    public static WeiXinAppClient getWeiXinAppClient(){
        Object client=getSession().getAttribute(Constants.USER_WEI_XIN_APP_CLIENT);
        if(client==null){
            client=WeiXinClientBuilder.createWeiXinAppClient();
            getSession().setAttribute(Constants.USER_WEI_XIN_APP_CLIENT,client);
        }
        return (WeiXinAppClient)client;
    }


    public static Integer incWeiXinRequestCount(){
        Integer n=(Integer)getSession().getAttribute(Constants.USER_WEI_XIN_REQUEST_COUNT);
        if(n==null){
            n=0;
        }
        n++;
        getSession().setAttribute(Constants.USER_WEI_XIN_REQUEST_COUNT,n);
        return n;
    }
    public static void clearWeiXinRequestCount(){
        getSession().setAttribute(Constants.USER_WEI_XIN_REQUEST_COUNT,0);
    }

    public static Integer getWeiXinRequestCount(){
        Integer n=(Integer)getSession().getAttribute(Constants.USER_WEI_XIN_REQUEST_COUNT);
        if(n==null){
            n=0;
        }
        return n;
    }


}
