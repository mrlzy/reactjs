package com.mrlzy.shiro.filter;


import com.mrlzy.shiro.session.ShiroSessionUtils;

import com.mrlzy.shiro.weixin.client.WeiXinWebClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WeiXinClientFilter extends AccessControlFilter {

    private static Log log = LogFactory.getLog(WeiXinClientFilter.class);

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if(isAccessAllowed(request, response, mappedValue)){
               return  onAccessDeniedRedirect(request,response);
        }
        return  onAccessDenied(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        WeiXinWebClient client= ShiroSessionUtils.getWeiXinWebClient();
        return  client!=null&&!client.isCloseWeiXin();
    }

    protected boolean onAccessDeniedRedirect(ServletRequest request, ServletResponse response) throws Exception {
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            if(ShiroSessionUtils.getWeiXinRequestCount()>0){
                WeiXinWebClient client= ShiroSessionUtils.getWeiXinWebClient();
                String code=request.getParameter("code");
                client.setCode(code);
                ShiroSessionUtils.clearWeiXinRequestCount();
                WebUtils.redirectToSavedRequest(request,response,"/weixin/error");
                return false;
            }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        WeiXinWebClient  client=ShiroSessionUtils.setWeiXinWebClient();
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        int  n= ShiroSessionUtils.incWeiXinRequestCount();
        if(n<3){
            saveRequestAndRedirectToAuthor(httpServletRequest,httpServletResponse);
        }
        return false;
    }





    public  void saveRequestAndRedirectToAuthor(HttpServletRequest request,HttpServletResponse  response)throws  Exception{
          WebUtils.saveRequest(request);
          WeiXinWebClient client= ShiroSessionUtils.getWeiXinWebClient();
          String url=  request.getRequestURL().toString();
          String query=request.getQueryString();
          WebUtils.issueRedirect(request,response,client.getAuthorizeUrl(url,query));
    }


}
