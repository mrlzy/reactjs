package com.mrlzy.shiro.weixin.client;


import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.tool.JsonUtil;
import com.mrlzy.shiro.weixin.WeiXinException;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WeiXinJsonCallBack implements WeiXinCallBack {
    @Override
    public void doFailed(WeiXinException e) throws WeiXinException {
            throw new  WeiXinException(e);
    }

    @Override
    public Object doSuccess(HttpEntity entity) throws WeiXinException {
        try {
            String text = EntityUtils.toString(entity);
            System.out.println("json---"+text);
            Dto result=(Dto) JsonUtil.json2ObjWithExption(BaseDto.class,text);
            return result;
        } catch (IOException e) {
             throw new  WeiXinException(e);
        }
    }
}
