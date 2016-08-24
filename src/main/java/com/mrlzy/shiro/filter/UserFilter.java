package com.mrlzy.shiro.filter;


import com.mrlzy.shiro.entity.User;
import com.mrlzy.shiro.session.ShiroSessionUtils;
import com.mrlzy.shiro.tool.RequestUtil;
import org.apache.shiro.web.util.WebUtils;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class UserFilter extends org.apache.shiro.web.filter.authc.UserFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  {
        if (isLoginRequest(request, response)) {
            return true;
        }else{
            User user= ShiroSessionUtils.getCurrUser();
            return user!=null;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if(RequestUtil.isAjaxRequest(WebUtils.toHttp(request))){
                 HttpServletResponse res = WebUtils.toHttp(response);
                 res.sendError(HttpServletResponse.SC_UNAUTHORIZED);

        }else{
            saveRequestAndRedirectToLogin(request, response);
        }
        return false;
    }


}
