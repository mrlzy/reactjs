package com.mrlzy.shiro.service;

import com.mrlzy.shiro.dao.local.mapper.SmsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("localManager")
public class SmsService {

    @Autowired
    private SmsMapper smsMapper;


    public void sendWeiXinMsg(String tel,String msg){
           smsMapper.sendMsg(tel,msg,"微信验证码");
    }

}
