package com.mrlzy.shiro.weixin;


import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.tool.JsonUtil;
import com.mrlzy.shiro.web.mvc.weixin.WeixinController;
import com.mrlzy.shiro.weixin.bean.WeiXinMenu;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

public class WeiXinConfig {

    private static final String WEIXIN_APPID="wx715cff5129be58b9";
    private static final String WEIXIN_APPSECRET="9c5f3b58750edf2ffe14e5454d5e3a5f";

    public static final String WEIXIN_TOKEN = "mrlzyDHL20160803";
    private static final String WEIXIN_ENCODINGAESKEY="kVkvWWJ3fAQLXcXUvlnx1NfrZzAtlehMsdF2iku5ji9";


    private static final int WEIXIN_ERORR_COUNT=10;



    private static Log log = LogFactory.getLog(WeiXinConfig.class);


    private static  String acess_token="";
    private static  long expires_time=0;
    private static  int error_count=0;
    private static  boolean closeWeiXin=false;
    private static  String closeMsg="";


    public static  void configMenu(List<WeiXinMenu> menus)throws  WeiXinException{
          Dto dto=new BaseDto("button",menus);
          CloseableHttpClient client= HttpClients.createDefault();
          RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(2000).build();
          HttpPost httpPost=new HttpPost("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+getAcessToken());
          httpPost.setConfig(requestConfig);
         try {
               httpPost.setEntity(new StringEntity(dto.toJson()));
               HttpResponse response = client.execute(httpPost);
               HttpEntity entity = response.getEntity();
               String text =  EntityUtils.toString(entity);
               Dto result=(Dto)JsonUtil.json2ObjWithExption(BaseDto.class,text);
               String errcode= result.getAsString("errcode");
               if(!errcode.equals("0")){
                     throw  new WeiXinException(errcode+":"+result.getAsString("errmsg"));
               }
         } catch (IOException e) {
             throw  new WeiXinException(e);
         }

    }







    public static  String getAcessToken()throws  WeiXinException{
        if(closeWeiXin){
                 throw  new WeiXinException(closeMsg);
        }
        if(new Date().getTime()>expires_time) {
              synchronized (acess_token) {
                  if(new Date().getTime()>expires_time) {
                        try {
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            setAcessToken();
                        } catch (IOException e) {
                            error_count++;
                            if(error_count>WEIXIN_ERORR_COUNT){
                                   closeMsg="获取失败:"+e.getMessage();
                                   closeWeiXin=true;
                            }
                        }
                        return getAcessToken();

                    }
                    error_count=0;//设置成功清0
              }

        }
        return acess_token;
    }


    private static void setAcessToken() throws  IOException{
        CloseableHttpClient client= HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(2000).build();//设置请求和传输超时时间
        HttpGet httpGet=new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WEIXIN_APPID+"&secret="+WEIXIN_APPSECRET);
        httpGet.setConfig(requestConfig);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String text =  EntityUtils.toString(entity);
        Dto dto=(Dto)JsonUtil.json2ObjWithExption(BaseDto.class,text);
        if(!dto.getAsString("errcode").equals("")){
            closeWeiXin=true;
            closeMsg=dto.getAsString("errcode")+":"+dto.getAsString("errmsg");
        }else{
            acess_token=dto.getAsString("access_token");
            expires_time=dto.getAsLong("expires_in")!=null?((dto.getAsLong("expires_in")-180)*1000+new Date().getTime()):0;//提前三分钟重新获取
            if(new Date().getTime()>expires_time||acess_token.equals("")){
                closeWeiXin=true;
                closeMsg="返回格式验证失败:"+text;
            }
        }
        client.close();
    }

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(getAcessToken());
                } catch (WeiXinException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(getAcessToken());
                } catch (WeiXinException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
