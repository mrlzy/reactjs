package com.mrlzy.shiro.filter;


import com.mrlzy.shiro.entity.User;
import com.mrlzy.shiro.session.ShiroSessionUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class UserManageFilter extends AccessControlFilter {



    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        User user= ShiroSessionUtils.getCurrUser();
        return user!=null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }


}
