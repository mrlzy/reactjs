package com.mrlzy.shiro.web.mvc.weixin;

import com.mrlzy.shiro.service.WeiXinService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *   Fwzc--服务支撑
 */
@Controller
@RequestMapping("/weixin/fwzc")
public class WeixinFwzcController {

    private static Log log = LogFactory.getLog(WeixinFwzcController.class);


    @Autowired
    private WeiXinService weiXinService;


    //------------投诉建议------------//

    @RequestMapping("/tsjy")
    public String tsjy() {
        return "weixin/fwzc/tsjy_WITH_jsp";
    }

    //------------在线调查------------//
    @RequestMapping("/zxdc")
    public String zxdc() {
        return "weixin/fwzc/zxdc_WITH_jsp";
    }


    //------------搜索服务------------//
    @RequestMapping("/kzzn")
    public String ssfw() {
        return "weixin/fwzc/ssfw_WITH_jsp";
    }

    //------------信息收集------------//
    @RequestMapping("/xxsj")
    public String xxsj() {
        return "weixin/fwzc/xxsj_WITH_jsp";
    }





}
