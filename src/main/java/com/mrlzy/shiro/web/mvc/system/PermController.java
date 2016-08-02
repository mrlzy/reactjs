package com.mrlzy.shiro.web.mvc.system;

import com.mrlzy.shiro.entity.Permission;
import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.plugin.jqGrid.jqGridPlugin;
import com.mrlzy.shiro.service.PermService;
import com.mrlzy.shiro.tool.RequestUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/perm")
public class PermController {

    private static Log log = LogFactory.getLog(PermController.class);

    @Autowired
    private PermService permService;








    @RequestMapping("/treeComponentMenuPerm")
    @ResponseBody
    public String treeComponentMenuPerm(@RequestParam("parent_id") String parent_id){
        return permService.treeComponentMenuPerm(parent_id).toLowerCase();
    }




    @RequestMapping("/listPermForMenu")
    @ResponseBody
    public String listPermForMenu(HttpServletRequest request,@RequestParam(value = "menu_id") String menu_id) {
        Dto dto=  RequestUtil.getParamAsDto(request);
        return permService.listPermForMenu(menu_id,new jqGridPlugin(dto));
    }


    @RequestMapping("/opPerm")
    @ResponseBody
    public String opPerm(HttpServletRequest request, Permission p, @RequestParam(value = "menu_id") String menu_id){
        Dto dto=  RequestUtil.getParamAsDto(request);
        jqGridPlugin jq=  new jqGridPlugin(dto,false);
        try{
            permService.opPerm(jq,p,menu_id);
        }catch (Exception e){
            jq.setSuccess(false);
            jq.setErrorMsg(e.getMessage());
            log.info(e.getMessage(),e);

        }
        return jq.getReturnMsg();
    }







}
