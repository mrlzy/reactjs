package com.mrlzy.shiro.web.mvc;

import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.plugin.mvc.MultiViewResover;
import com.mrlzy.shiro.service.WeiXinService;
import com.mrlzy.shiro.weixin.WeiXinConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Controller
public class LoginController {


    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        return "login_WITH_jsp";
    }

    @RequestMapping("/image")
    public String image(HttpServletRequest request) {
        return "image_WITH_jsp";
    }




    @Autowired
    private WeiXinService weiXinService;

    @RequestMapping("/weixin/selectCode")
    @ResponseBody
    public String selectCode(@RequestParam("username") String username){
        Dto dto=new BaseDto(true,"");
        try {
            dto.setMsg(weiXinService.sendUserValiCode(username));
        }catch (Exception e){
            dto.setSuccess(false);
            dto.setMsg(e.getMessage());
        }
        //log.info(dto.toJson());
        return dto.toJson();
    }
    @RequestMapping("/weixin/login")
    public String toWeiXin(){
        return "weixin/login_WITH_jsp";
    }

    @RequestMapping(value = {"/weixin/author"},method = RequestMethod.GET)
    @ResponseBody
    public String  author(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
                          @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr){

        String[] values = { WeiXinConfig.WEIXIN_TOKEN, timestamp, nonce };
        Arrays.sort(values);
        String sum=values[0]+values[1]+values[2];
        String sign = DigestUtils.sha1Hex(sum);
        if(sign.equalsIgnoreCase(signature)){
            return  echostr;
        }
        return "error";

    }

}
