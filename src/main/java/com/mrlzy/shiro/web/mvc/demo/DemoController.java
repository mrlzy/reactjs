package com.mrlzy.shiro.web.mvc.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/demo")
public class DemoController {


    @RequestMapping("/menu")
    public String menu() {
        return "demo/menu";
    }

    @RequestMapping("/role")
    public String role() {
        return "demo/role";
    }

    @RequestMapping("/menuPerm")
    public String menuPerm() {
        return "demo/menuPerm";
    }



}
