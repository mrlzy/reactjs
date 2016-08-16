package com.mrlzy.shiro.filter;


import com.mrlzy.shiro.app.Constants;
import com.mrlzy.shiro.session.ShiroSessionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ValidCodeAuthenticationFilter extends AccessControlFilter {

    private String failureMsgAttribute = Constants.DEFAULT_ERROR_KEY_ATTRIBUTE_MSG;
    private String validCodeAttribute = Constants.DEFAULT_VALIDCODE_ATTRIBUTE;



    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureMsgAttribute(), ae.getMessage());
    }




    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpSession session=httpServletRequest.getSession();

        String pvaliCode= request.getParameter(validCodeAttribute);

        if(pvaliCode==null) {
              return true;
        }
        String svaliCode= ShiroSessionUtils.getUserValiCode();

        if(svaliCode==null) {
            return false;
        }

        if(!pvaliCode.equalsIgnoreCase(svaliCode)){
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        request.setAttribute(failureMsgAttribute,"验证码错误!");
        request.getRequestDispatcher(getLoginUrl()).forward(request,response);
        return false;
    }


    public String getValidCodeAttribute() {
        return validCodeAttribute;
    }

    public void setValidCodeAttribute(String validCodeAttribute) {
        this.validCodeAttribute = validCodeAttribute;
    }
    public String getFailureMsgAttribute() {
        return failureMsgAttribute;
    }

    public void setFailureMsgAttribute(String failureMsgAttribute) {
        this.failureMsgAttribute = failureMsgAttribute;
    }
}
