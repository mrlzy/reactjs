package com.mrlzy.shiro.weixin;


import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.session.ShiroSessionUtils;
import com.mrlzy.shiro.tool.JsonUtil;
import com.mrlzy.shiro.tool.StringUtil;
import com.mrlzy.shiro.weixin.bean.WeiXinMenu;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.List;


/**
 * 微信基本配置信息
 */

public class WeiXinConfig {



    /**
     * 测试微信使用
     */
    public static final String WEIXIN_APPID="wxf237fbab0f838db3";
    public static final String WEIXIN_APPSECRET="618b3b3d66538fecb46cb1fb8c2dbca6";

    /**
     * 正式微信使用
     */
    //public static final String WEIXIN_APPID="wx715cff5129be58b9";
    //public static final String WEIXIN_APPSECRET="9c5f3b58750edf2ffe14e5454d5e3a5f";


     public static final String WEIXIN_TOKEN = "mrlzyDHL20160803";
     public static final String WEIXIN_ENCODINGAESKEY="kVkvWWJ3fAQLXcXUvlnx1NfrZzAtlehMsdF2iku5ji9";






     public  static  String[]  authorCreate(String url) throws WeiXinException {
           String timestamp=Long.toString(System.currentTimeMillis() / 1000);
           String noncestr= StringUtil.getRandomStr(16);
           String  ticket=ShiroSessionUtils.getWeiXinAppClient().getTicket();
           String sum="jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
           String  signature=DigestUtils.sha1Hex(sum);
         return new String[]{timestamp,noncestr,signature};
     }



}
