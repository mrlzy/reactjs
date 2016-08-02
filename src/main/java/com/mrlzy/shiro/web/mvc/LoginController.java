package com.mrlzy.shiro.web.mvc;

import com.mrlzy.shiro.plugin.mvc.MultiViewResover;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


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





}
