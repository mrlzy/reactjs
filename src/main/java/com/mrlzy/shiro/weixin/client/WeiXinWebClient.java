package com.mrlzy.shiro.weixin.client;


import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.tool.JsonUtil;
import com.mrlzy.shiro.tool.StringUtil;
import com.mrlzy.shiro.weixin.WeiXinConfig;
import com.mrlzy.shiro.weixin.WeiXinException;
import com.mrlzy.shiro.weixin.bean.WeiXinUser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;


public class WeiXinWebClient  extends WeiXinClient {


    private  static  String authorize_url="https://open.weixin.qq.com/connect/oauth2/authorize";
    private  static  String api_url="https://api.weixin.qq.com/sns";

    private  String code;

    private  String refresh_token;

    private  String op_id;




    @Override
    protected void setToken() throws WeiXinException {
          if(this.code==null||this.code.equals("")){
              if(this.refresh_token!=null&&!this.refresh_token.equals("")){
                  refreshToken();
              }else
                  throw  new WeiXinException("refresh_token及code为空");
          }else{
              setTokenUseCode();
          }

    }





    public  void setCode(String code) throws WeiXinException {
          this.code=code;
          setTokenUseCode();
    }



    private  void setSuccessToken(Dto dto){
        if(!dto.getAsString("errcode").equals("")){
            closeWeiXin=true;
            closeMsg=dto.getAsString("errcode")+":"+dto.getAsString("errmsg");
        }else{
            token=dto.getAsString("access_token");
            token_expires_time=dto.getAsLong("expires_in")!=null?((dto.getAsLong("expires_in")-180)*1000+new Date().getTime()):0;//提前三分钟重新获取
            op_id=dto.getAsString("openid");
            refresh_token=dto.getAsString("refresh_token");
        }
    }

    private  void refreshToken()  throws   WeiXinException{
        HttpGet httpGet=new HttpGet(api_url+"/oauth2/refresh_token?appid="+WeiXinConfig.WEIXIN_APPID+"&grant_type=refresh_token&refresh_token="+this.refresh_token);
        Dto dto= (Dto)this.doExecute(httpGet,new WeiXinJsonCallBack());
        setSuccessToken(dto);

    }
    private  void  setTokenUseCode() throws  WeiXinException  {
        if(this.code==null||this.code.equals(""))
            throw  new WeiXinException("you must setCode before setAcessToken,amd code can't be empty");
        HttpGet httpGet=new HttpGet(api_url+"/oauth2/access_token?appid="+WeiXinConfig.WEIXIN_APPID+"&secret="+WeiXinConfig.WEIXIN_APPSECRET+"&code="+this.code+"&grant_type=authorization_code" );
        Dto dto= (Dto)this.doExecute(httpGet,new WeiXinJsonCallBack());
        this.code="";//使用过后清空
        setSuccessToken(dto);
    }


    public String getOp_id() {
        return op_id;
    }

    public String getAuthorizeUrl(String url, String query)throws  WeiXinException {
        String author_url= null;
        String state="&state=123";
        if(query!=null&&!query.equals("")){
            state="&state="+transcoding(query);
        }
        try {
            author_url = authorize_url+"?appid="+ WeiXinConfig.WEIXIN_APPID+"&redirect_uri="+ URLEncoder.encode(url,"utf-8")
                    +"&response_type=code&scope=snsapi_base"+state+"#wechat_redirect";
            log.info("authorize_url--"+author_url);
        } catch (UnsupportedEncodingException e) {
            throw  new WeiXinException(e.getMessage());
        }
        return author_url;
    }

    private static String NOTSHIFTEQU="NOTSHIFTEQU";
    private static String SHIFT8="SHIFT8";

    private    String transcoding(String params){
        return params.replace("=","NOTSHIFTEQU").replaceAll("&",SHIFT8);
    }





}
