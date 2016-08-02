package com.mrlzy.shiro.web.mvc.system;

import com.mrlzy.shiro.entity.Menu;
import com.mrlzy.shiro.entity.User;
import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.plugin.jqGrid.jqGridPlugin;
import com.mrlzy.shiro.service.RoleService;
import com.mrlzy.shiro.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    private static Log log = LogFactory.getLog(UserController.class);

    @Autowired
    private UserService userService;


    @RequestMapping("/index")
    public String toUserPage() {
        return "system/user";
    }



    @RequestMapping("/treeComponentOrgUser")
    @ResponseBody
    public String treeComponentOrgUser(@RequestParam("parent_id") String parent_id){
        return userService.treeComponentOrgUser(parent_id).toLowerCase();
    }

    @RequestMapping("/authorizeRole")
    @ResponseBody
    public String authorizeRole(@RequestParam("user_id") String user_id,@RequestParam("role_id") String role_id){
        Dto dto=new BaseDto(true,"");
        try{
            userService.authorizeRole(user_id,role_id);
        }catch (Exception e){
            dto.setSuccess(false);
            dto.setMsg("授权失败");
        }
        return dto.toJson();
    }



    @RequestMapping("/listUserForTree")
    @ResponseBody
    public String listUserForTree(HttpServletRequest request, @RequestParam(value = "parent_id",defaultValue = "",required = false) String parent_id){
        Dto dto=  RequestUtil.getParamAsDto(request);
        return userService.listUserForTree(parent_id,new jqGridPlugin(dto));
    }


    @RequestMapping("/opUser")
    @ResponseBody
    public String opUser(HttpServletRequest request, User u,@RequestParam(value = "org_id")  String org_id){
        Dto dto=  RequestUtil.getParamAsDto(request);
        jqGridPlugin jq=  new jqGridPlugin(dto,false);
        try{
            userService.opUser(jq,u,org_id);
        }catch (Exception e){
            jq.setSuccess(false);
            jq.setErrorMsg(e.getMessage());
            log.info(e.getMessage(),e);

        }
        return jq.getReturnMsg();
    }

    @RequestMapping("/paste")
    @ResponseBody
    public String paste(@RequestParam(value = "temp_id") String self_id,@RequestParam(value = "cmd") String cmd,@RequestParam(value = "id") String id){

        Dto dto=new BaseDto(true,"操作成功");
        try{
            if(cmd.equals("cut")){
                userService.moveUser(self_id.replace("itme_",""), id.replace("itme_",""));
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
