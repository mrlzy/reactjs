package com.mrlzy.shiro.web.mvc.system;


import com.mrlzy.shiro.entity.Org;
import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.plugin.jqGrid.jqGridPlugin;
import com.mrlzy.shiro.service.OrgService;
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

@Controller
@RequestMapping("/org")
public class OrgController {

    private static Log log = LogFactory.getLog(OrgController.class);

    @Autowired
    private OrgService orgService;


    @RequestMapping("/index")
    public String toOrgPage(){
        return "system/org";
    }


    @RequestMapping("/treeComponentOrg")
    @ResponseBody
    public String treeComponentOrg(@RequestParam("parent_id") String parent_id){
        return orgService.treeComponentOrg(parent_id).toLowerCase();
    }

    @RequestMapping("/listOrgForTree")
    @ResponseBody
    public String listOrgForTree(HttpServletRequest request, @RequestParam(value = "parent_id",defaultValue = "",required = false) String parent_id){
        Dto dto=  RequestUtil.getParamAsDto(request);
        return orgService.listOrgForTree(parent_id,new jqGridPlugin(dto));
    }


    @RequestMapping("/selectOrgByName")
    @ResponseBody
    public  List<String> selectOrgByName(@RequestParam("name") String name){
        return orgService.selectOrgByName(name);
    }


    @RequestMapping("/opOrg")
    @ResponseBody
    public String opOrg(HttpServletRequest request, Org m, @RequestParam(value = "parent_id",defaultValue = "",required = false) String parent_id){
        Dto dto=  RequestUtil.getParamAsDto(request);
        jqGridPlugin jq=  new jqGridPlugin(dto,false);
        try{
            orgService.opOrg(jq,m,parent_id);
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
                orgService.moveOrg(self_id.replace("itme_",""), id.replace("itme_",""));
            }else if(cmd.equals("copy")){
                orgService.copyOrg(self_id.replace("itme_",""), id.replace("itme_",""));
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
