package com.mrlzy.shiro.weixin.client;


import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.weixin.WeiXinException;
import org.apache.http.HttpEntity;

public interface WeiXinCallBack {

    public  void doFailed(WeiXinException e) throws WeiXinException;

    public  Object doSuccess  (HttpEntity entity) throws WeiXinException;


}
