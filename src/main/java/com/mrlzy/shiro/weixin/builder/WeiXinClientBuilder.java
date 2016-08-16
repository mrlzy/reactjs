package com.mrlzy.shiro.weixin.builder;

import com.mrlzy.shiro.weixin.client.WeiXinAppClient;
import com.mrlzy.shiro.weixin.client.WeiXinWebClient;

public class WeiXinClientBuilder {

    private  static WeiXinAppClient appClient;





    public  static WeiXinWebClient  createWeiXinWebClient(){
           return new WeiXinWebClient();
    }

    public static WeiXinAppClient  createWeiXinAppClient(){
        if(appClient==null){
            synAppClient();
        }
        return  appClient;
    }

    private  static  synchronized void  synAppClient(){
        if(appClient==null){
            appClient=new WeiXinAppClient();
        }

    }


}
