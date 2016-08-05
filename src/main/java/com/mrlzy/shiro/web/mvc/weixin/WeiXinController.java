package com.mrlzy.shiro.web.mvc.weixin;

import com.mrlzy.shiro.app.Constants;
import com.mrlzy.shiro.weixin.WeiXinConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Controller
@RequestMapping("/weixin")
public class WeixinController {

    private static Log log = LogFactory.getLog(WeixinController.class);


    @RequestMapping(value = {"/author"},method = RequestMethod.GET)
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
