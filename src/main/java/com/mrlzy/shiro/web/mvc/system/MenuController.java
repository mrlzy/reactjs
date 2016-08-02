package com.mrlzy.shiro.web.mvc.system;

import com.mrlzy.shiro.entity.Menu;
import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.plugin.jqGrid.jqGridPlugin;
import com.mrlzy.shiro.service.MenuService;
import com.mrlzy.shiro.tool.RequestUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 使用tempalate来完成
 */


@Controller
@RequestMapping("/menu")
public class MenuController {

    private static Log log = LogFactory.getLog(MenuController.class);

    @Autowired
    private MenuService menuService;

    @RequestMapping("/index")
    public String toMenuPage(){
        return "system/menu";
    }



    @RequestMapping("/treeComponentMenu")
    @ResponseBody
    public String treeComponentMenu(@RequestParam("parent_id") String parent_id){
        return menuService.treeComponentMenu(parent_id).toLowerCase();
    }


    @RequestMapping("/listMenuForTree")
    @ResponseBody
    public String listMenuForTree(HttpServletRequest request,@RequestParam(value = "parent_id",defaultValue = "",required = false) String parent_id){
        Dto dto=  RequestUtil.getParamAsDto(request);
        return menuService.listMenuForTree(parent_id,new jqGridPlugin(dto));
    }




    @RequestMapping("/opMenu")
    @ResponseBody
    public String opMenu(HttpServletRequest request,Menu m,@RequestParam(value = "parent_id",defaultValue = "",required = false) String parent_id){
        Dto dto=  RequestUtil.getParamAsDto(request);
        jqGridPlugin jq=  new jqGridPlugin(dto,false);
        try{
              menuService.opMenu(jq,m,parent_id);
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
                    menuService.moveMenu(self_id.replace("itme_",""), id.replace("itme_",""));
                }else if(cmd.equals("copy")){
                    menuService.copyMenu(self_id.replace("itme_",""), id.replace("itme_",""));
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
