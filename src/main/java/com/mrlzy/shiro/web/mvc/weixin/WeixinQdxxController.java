package com.mrlzy.shiro.web.mvc.weixin;

import com.mrlzy.shiro.service.WeiXinService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *   Qdxx--渠道信息
 */
@Controller
@RequestMapping("/weixin/qdxx")
public class WeixinQdxxController {

    private static Log log = LogFactory.getLog(WeixinQdxxController.class);


    @Autowired
    private WeiXinService weiXinService;


    //------------酬金查询------------//

    @RequestMapping("/cjdc")
    public String cjdc() {
        return "weixin/qdxx/cjdc_WITH_jsp";
    }

    //------------酬金理财------------//
    @RequestMapping("/cjlc")
    public String cjlc() {
        return "weixin/qdxx/cjlc_WITH_jsp";
    }


    //------------业务达成------------//
    @RequestMapping("/ywdc")
    public String ywdc() {
        return "weixin/qdxx/ywdc_WITH_jsp";
    }

    //------------触点促成------------//
    @RequestMapping("/cdcc")
    public String cdcc() {
        return "weixin/qdxx/cdcc_WITH_jsp";
    }


    //------------星级达标------------//
    @RequestMapping("/xjdb")
    public String xjdb() {
        return "weixin/qdxx/xjdb_WITH_jsp";
    }


}
