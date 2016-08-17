package com.mrlzy.shiro.weixin.builder;


import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.tool.JsonUtil;
import com.mrlzy.shiro.weixin.WeiXinConfig;
import com.mrlzy.shiro.weixin.WeiXinException;
import com.mrlzy.shiro.weixin.bean.WeiXinMenu;
import com.mrlzy.shiro.weixin.client.WeiXinAppClient;

import java.util.ArrayList;
import java.util.List;

public class WeiXinMenuBuilder {

    public static WeiXinMenu createViewMenu(String name,String url){
        WeiXinMenu m=new WeiXinMenu();
        m.setName(name);
        m.setUrl(url);
        m.setType(WeiXinMenu.MenuType.View);
        return m;
    }

    public static WeiXinMenu createClickMenu(String name,String key){
        WeiXinMenu m=new WeiXinMenu();
        m.setName(name);
         m.setKey(key);
        m.setType(WeiXinMenu.MenuType.Click);
        return m;
    }

    public static WeiXinMenu createMediaIdMenu(String name,String media_id){
        WeiXinMenu m=new WeiXinMenu();
        m.setName(name);
        m.setMedia_id(media_id);
        m.setType(WeiXinMenu.MenuType.MediaId);
        return m;
    }

    public static WeiXinMenu createViewLimitedMenu(String name,String media_id){
        WeiXinMenu m=new WeiXinMenu();
        m.setName(name);
        m.setMedia_id(media_id);
        m.setType(WeiXinMenu.MenuType.ViewLimited);
        return m;
    }
    public static WeiXinMenu createSubMenu(String name){
        WeiXinMenu m=new WeiXinMenu();
        m.setName(name);
        m.setSub_button(new ArrayList<WeiXinMenu>());
        m.setType(null);
        return m;
    }


    public static WeiXinMenu createMenu(String name,WeiXinMenu.MenuType type) throws WeiXinException {
        if(type==WeiXinMenu.MenuType.ViewLimited||type==WeiXinMenu.MenuType.MediaId
                          ||type==WeiXinMenu.MenuType.Click||type==WeiXinMenu.MenuType.View)
                   throw  new WeiXinException("WeiXinMenu["+type+"] create failed");
        WeiXinMenu m=new WeiXinMenu();
        m.setName(name);
        m.setType(type);
        return m;
    }

    public static void main(String[] args) {
        String url="http://10.77.10.194/reactjs/weixin/";


        WeiXinMenu m=createSubMenu("业务指导");
            m.addSubMenu(createViewMenu("主要政策",url+"ywzd/zyzc"));
            m.addSubMenu(createViewMenu("故障指南",url+"ywzd/kzzn"));
            m.addSubMenu(createViewMenu("操作指南",url+"ywzd/czzn"));
            m.addSubMenu(createViewMenu("公告通知",url+"ywzd/ggtz"));
            m.addSubMenu(createViewMenu("宣传规范",url+"ywzd/xckf"));


        WeiXinMenu m2=createSubMenu("服务支撑");
            m2.addSubMenu(createViewMenu("投诉建议",url+"fwzc/tsjy"));
            m2.addSubMenu(createViewMenu("在线调查",url+"fwzc/zxdc"));
            m2.addSubMenu(createViewMenu("搜索服务",url+"fwzc/ssfw"));
            m2.addSubMenu(createViewMenu("信息收集",url+"fwzc/xxsj"));


        WeiXinMenu m3=createSubMenu("渠道信息");
           m3.addSubMenu(createViewMenu("酬金查询",url+"qdxx/cjdc"));
           m3.addSubMenu(createViewMenu("酬金理财",url+"qdxx/cjlc"));
           m3.addSubMenu(createViewMenu("业务达成",url+"qdxx/ywdc"));
           m3.addSubMenu(createViewMenu("触点促成",url+"qdxx/cdcc"));
           m3.addSubMenu(createViewMenu("星级达标",url+"qdxx/xjdb"));

        List<WeiXinMenu> list=new ArrayList<>();
        list.add(m);
        list.add(m2);
        list.add(m3);

        System.out.println(JsonUtil.obj2Json(new BaseDto("button",list)));
        try {
            new WeiXinAppClient().configMenu(list);
        } catch (WeiXinException e) {
            e.printStackTrace();
        }

        // JlI5I5pBvtPBOH89SvgZb6d2W3Z9dWSR3-WMD1axZuYt_8riKnYnDINlnMHaRgLfeA9BYBKRoO169W2vULp7dmgaVgWtYVA75TZ7MGXyXTEQeHkMlbNRlCXrrI7xRcqOBLSbAJAVFM


    }


}
