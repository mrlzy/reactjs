package com.mrlzy.shiro.web.mvc;

import com.mrlzy.app.Constants;
import com.mrlzy.shiro.dao.local.jpa.MenuJpa;
import com.mrlzy.shiro.dao.local.mapper.MenuMapper;
import com.mrlzy.shiro.entity.Menu;
import com.mrlzy.shiro.entity.User;
import com.mrlzy.shiro.plugin.dto.CaseIgnoreDto;
import com.mrlzy.shiro.plugin.mvc.UserSelf;
import com.mrlzy.shiro.session.ShiroSessionUtils;
import com.mrlzy.shiro.tool.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class MainController {


    @Autowired
    private MenuMapper menuMapper;

    @RequestMapping("/getMenu")
    @ResponseBody
    public List<CaseIgnoreDto> getMenu(HttpServletRequest request, @RequestParam("menu_id") String menu_id ) {
       User user=ShiroSessionUtils.getCurrUser();
        System.out.println(JsonUtil.obj2Json( menuMapper.getListMenuByParentId(user.getRole().getRole_id(),menu_id)));
        return menuMapper.getListMenuByParentId(user.getRole().getRole_id(),menu_id);
    }

    @RequestMapping("/getRootMenu")
    @ResponseBody
    public List<CaseIgnoreDto> getRootMenu() {
        User user=ShiroSessionUtils.getCurrUser();
        return menuMapper.getRootMenuList(user.getRole().getRole_id());
    }



    @RequestMapping("/template")
    public String toTemplate() {
        return "template";
    }



}
