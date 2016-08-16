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

public abstract class   AbsWeixinClient {
    public Log log = LogFactory.getLog(getClass());

    private CloseableHttpClient client;

    private  void openHttpClient(){
        if(client==null){
            client= HttpClients.createDefault();
        }
    }


    protected    Object doExecute(HttpRequestBase httpMethod, WeiXinCallBack callBack) throws WeiXinException {
        return  doExecute(5000,2000,httpMethod,callBack);
    }

    protected   Object doExecute(int socketTimeout, int connectTimeout, HttpRequestBase httpMethod, WeiXinCallBack callBack) throws WeiXinException{
        openHttpClient();
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpMethod.setConfig(requestConfig);
            HttpResponse response =client.execute(httpMethod);
            HttpEntity entity=response.getEntity();
            return  callBack.doSuccess(entity);
        } catch (Exception e) {
            callBack.doFailed(new WeiXinException(e));
        }finally {
            closeHttpClient();
        }
        return null;
    }




    private    void closeHttpClient(){
        if(client!=null){
            try {
                client.close();
            } catch (IOException e) {
                log.warn(e.getMessage(),e);
            }finally {
                client=null;
            }
        }
    }

}
