package com.mrlzy.shiro.weixin.client;

import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.tool.JsonUtil;
import com.mrlzy.shiro.weixin.WeiXinConfig;
import com.mrlzy.shiro.weixin.WeiXinException;
import com.mrlzy.shiro.weixin.bean.WeiXinMenu;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.util.Date;
import java.util.List;

public class WeiXinAppClient extends WeiXinClient {


    private static String base_url="https://api.weixin.qq.com/cgi-bin/";

  //  https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi


    private  String   ticket;
    private  long    ticket_expires_time=0;



    public  String getTicket() throws WeiXinException {
           if(new Date().getTime()>ticket_expires_time){
                              setTicket();
           }
        return  ticket;

    };


    private  synchronized void setTicket() throws WeiXinException {
        if(new Date().getTime() > ticket_expires_time){
            HttpGet httpGet=new HttpGet(base_url+"ticket/getticket?access_token="+getToken()+"&type=jsapi");
            Dto dto= (Dto)this.doExecute(httpGet,new WeiXinJsonCallBack());
            ticket=dto.getAsString("ticket");
            ticket_expires_time=dto.getAsLong("expires_in")!=null?((dto.getAsLong("expires_in")-180)*1000+new Date().getTime()):0;//提前       三分钟重新获取

        }
    }

    @Override
    protected void setToken() throws WeiXinException {
        HttpGet httpGet=new HttpGet(base_url+"token?grant_type=client_credential&appid="+WeiXinConfig.WEIXIN_APPID+"&secret="+WeiXinConfig.WEIXIN_APPSECRET);
         Dto dto= (Dto)this.doExecute(httpGet,new WeiXinJsonCallBack());
         if(!dto.getAsString("errcode").equals("")){
            closeWeiXin=true;
            closeMsg=dto.getAsString("errcode")+":"+dto.getAsString("errmsg");
         }else{
             token=dto.getAsString("access_token");
             token_expires_time=dto.getAsLong("expires_in")!=null?((dto.getAsLong("expires_in")-180)*1000+new Date().getTime()):0;//提前三分钟重新获取
         }
    }




   public   void configMenu(List<WeiXinMenu> menus)throws  WeiXinException{
        Dto dto=new BaseDto("button",menus);
        HttpPost httpPost=new HttpPost(base_url+"menu/create?access_token="+getToken());
        httpPost.setEntity(new StringEntity(dto.toJson(), ContentType.APPLICATION_JSON));
        Dto result= (Dto)this.doExecute(httpPost,new WeiXinJsonCallBack());
        String errcode= result.getAsString("errcode");
        if(!errcode.equals("0")){
            throw  new WeiXinException(errcode+":"+result.getAsString("errmsg"));
        }
    }


    public static void main(String[] args) throws  Exception {
        System.out.println(new WeiXinAppClient().getToken());
    }




}
