package com.mrlzy.shiro.service;

import com.mrlzy.shiro.dao.local.jpa.UserJpa;
import com.mrlzy.shiro.entity.User;
import com.mrlzy.shiro.session.ShiroSessionUtils;
import com.mrlzy.shiro.tool.StringUtil;
import com.mrlzy.shiro.weixin.WeiXinException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("localManager")
public class WeiXinService {

    @Autowired
    private EhCacheCacheManager ehCacheManager;

    public Cache getCache(){
        return ehCacheManager.getCache("weiXinCache");
    }


    @Autowired
    private  SmsService smsService;
    @Autowired
    private UserJpa userJpa;


    public  String sendUserValiCode(String username) throws  WeiXinException{
          User user= userJpa.findByAccount(username);
          if(user==null||!user.hasWeiXinRole()){
              throw new WeiXinException("帐号不存在");
          }
          String tel=user.getTel();
          if(tel==null|| tel.length()!=11){
              throw new WeiXinException("手机号码异常");
          }
        String code= StringUtil.getRandomStr(6,"0123456789");
        try {
            smsService.sendWeiXinMsg(user.getTel(),"尊敬的用户您好，您本次业务受理登记验证码为:" + code + "，使用后失效[渠道微信平台]");
        }catch (Exception e){
            throw new WeiXinException("系统短信库异常！");
        }
        ShiroSessionUtils.setUserTelValiCode(code);
        String t=tel.substring(0,3)+"******"+tel.substring(9,11);
        return t;
    }

    public static void main(String[] args) {
        String tel="12345678901";
        String t=tel.substring(0,3)+"******"+tel.substring(9,11);

        System.out.println(t);
    }



}
