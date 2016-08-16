package com.mrlzy.shiro.weixin.client;


import com.mrlzy.shiro.weixin.WeiXinException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Date;

public abstract class WeiXinClient  extends  AbsWeixinClient {

    protected  static  int TOKEN_ERROR_GET=2;


    protected   String token;
    protected   long token_expires_time=0;
    private    int token_error_count=0;

    protected   boolean closeWeiXin=false;
    protected   String closeMsg;



    public String getToken() throws WeiXinException {
        if(closeWeiXin){
               throw  new WeiXinException(closeMsg);
        }
        if(!isBreakLocked()) {
            synGetToken();
            return getToken();
        }
        token_error_count=0;
        return token;
    }


    protected boolean isBreakLocked() {
        return new Date().getTime()<token_expires_time;
    }



    private synchronized  void  synGetToken() {
           if(!isBreakLocked()){
                  try{
                      setToken();
                  }catch (WeiXinException e){
                      token_error_count++;
                      if(token_error_count>TOKEN_ERROR_GET){
                          closeMsg="获取失败:"+e.getMessage();
                          closeWeiXin=true;
                      }
                  }
           }
    }

    public boolean isCloseWeiXin() {
        return closeWeiXin;
    }


    protected  abstract  void setToken() throws WeiXinException;

}
