package com.mrlzy.shiro.web.mvc.weixin;

import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.service.WeiXinService;
import com.mrlzy.shiro.session.ShiroSessionUtils;
import com.mrlzy.shiro.tool.RandomUtil;
import com.mrlzy.shiro.weixin.WeiXinConfig;
import com.mrlzy.shiro.weixin.client.WeiXinWebClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 *   Ywzd--业务指导
 */
@Controller
@RequestMapping("/weixin/ywzd")
public class WeixinYwzdController {

    private static Log log = LogFactory.getLog(WeixinYwzdController.class);


    @Autowired
    private WeiXinService weiXinService;


    //------------主要政策------------//

    @RequestMapping("/zyzc")
    public String zyzc() {
        return "weixin/ywzd/zyzc_WITH_jsp";
    }

    @RequestMapping("/toGgl")
    public String toGgl(){
        WeiXinWebClient client = ShiroSessionUtils.getWeiXinWebClient();

        int giftId = RandomUtil.getInstance().getGiftId(client.getOp_id());
        return "weixin/ywzd/ggl_WITH_jsp";
    }


    //------------公告通知------------//
    @RequestMapping("/ggtz")
    public String ggtz() {
        return "weixin/ywzd/ggtz_WITH_jsp";
    }


    //------------故障指南------------//
    @RequestMapping("/kzzn")
    public String kzzn() {
        return "weixin/ywzd/kzzn_WITH_jsp";
    }

    //------------操作指南------------//
    @RequestMapping("/czzn")
    public String czzn() {
        return "weixin/ywzd/czzn_WITH_jsp";
    }


    //------------宣传规范------------//
    @RequestMapping("/xckf")
    public String xckf() {
        return "weixin/ywzd/xckf_WITH_jsp";
    }


}
