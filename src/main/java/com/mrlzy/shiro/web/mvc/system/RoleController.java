package com.mrlzy.shiro.web.mvc.system;


import com.mrlzy.shiro.entity.Permission;
import com.mrlzy.shiro.entity.Role;
import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.plugin.jqGrid.jqGridPlugin;
import com.mrlzy.shiro.service.RoleService;
import com.mrlzy.shiro.tool.RequestUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
@RequestMapping("/role")
public class RoleController {

    private static Log log = LogFactory.getLog(RoleController.class);

    @Autowired
    private RoleService roleService;


    @RequestMapping("/index")
    public String toRolePage(){
        return "system/role";
    }


    @RequestMapping("/treeComponentRole")
    @ResponseBody
    public String treeComponentRole(@RequestParam("parent_id") String parent_id){
        return roleService.treeComponentRole(parent_id).toLowerCase();
    }

    @RequestMapping("/listRoleForTree")
    @ResponseBody
    public String listRoleForTree(HttpServletRequest request, @RequestParam(value = "parent_id",defaultValue = "",required = false) String parent_id){
        Dto dto=  RequestUtil.getParamAsDto(request);
        return roleService.listRoleForTree(parent_id,new jqGridPlugin(dto));
    }


    @RequestMapping("/opRole")
    @ResponseBody
    public String opRole(HttpServletRequest request, Role m, @RequestParam(value = "parent_id",defaultValue = "",required = false) String parent_id){
        Dto dto=  RequestUtil.getParamAsDto(request);
        jqGridPlugin jq=  new jqGridPlugin(dto,false);
        try{
            roleService.opRole(jq,m,parent_id);
        }catch (Exception e){
            jq.setSuccess(false);
            jq.setErrorMsg(e.getMessage());
            log.info(e.getMessage(),e);

        }
        return jq.getReturnMsg();
    }


    @RequestMapping("/selectPerms")
    @ResponseBody
    public String selectPerms(@RequestParam("role_id") String role_id){
        Dto dto=new BaseDto(true,"");
        try{
            if(role_id.indexOf("itme_")>-1){
                role_id=role_id.replace("itme_","");
            }
            if(role_id.indexOf("folder_")>-1){
                role_id=role_id.replace("folder_","");
            }

           dto.put("perms",roleService.selectPermIds(role_id));
        }catch (Exception e){
            log.info(e.getMessage(),e);
            dto.setSuccess(false);
            dto.setMsg("获取失败");
        }
        return dto.toJson();
    }


    @RequestMapping("/authorizePerm")
    @ResponseBody
    public String authorizePerm(@RequestParam("role_id") String role_id,@RequestParam("ids[]") String[] perms){
        Dto dto=new BaseDto(true,"");
        try{
            if(role_id.indexOf("itme_")>-1){
                 role_id=role_id.replace("itme_","");
            }
            if(role_id.indexOf("folder_")>-1){
                role_id=role_id.replace("folder_","");
            }

            roleService.authorizePerm(role_id,perms);
        }catch (Exception e){
            log.info(e.getMessage(),e);
            dto.setSuccess(false);
            dto.setMsg("授权失败");
        }
        return dto.toJson();
    }

    @RequestMapping("/paste")
    @ResponseBody
    public String paste(@RequestParam(value = "temp_id") String self_id,@RequestParam(value = "cmd") String cmd,@RequestParam(value = "id") String id){

        Dto dto=new BaseDto(true,"操作成功");
        try{
            if(cmd.equals("cut")){
                roleService.moveRole(self_id.replace("itme_",""), id.replace("itme_",""));
            }else if(cmd.equals("copy")){
                roleService.copyRole(self_id.replace("itme_",""), id.replace("itme_",""));
            }else{
                dto.setMsg("无效操作");
                dto.setSuccess(false);
            }

        }catch (Exception e){
            dto.setMsg(e.getMessage()==null?"操作异常":e.getMessage());
            dto.setSuccess(false);
            log.info(e.getMessage(),e);
        }
        return dto.toJson();
    }
}
