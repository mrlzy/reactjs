package com.mrlzy.shiro.service;

import com.mrlzy.shiro.dao.local.jpa.MenuJpa;
import com.mrlzy.shiro.dao.local.jpa.PermJpa;
import com.mrlzy.shiro.dao.local.jpa.RoleJpa;
import com.mrlzy.shiro.dao.local.mapper.MenuMapper;
import com.mrlzy.shiro.dao.local.mapper.PermMapper;
import com.mrlzy.shiro.dao.local.mapper.RoleMapper;
import com.mrlzy.shiro.entity.Menu;
import com.mrlzy.shiro.entity.Permission;
import com.mrlzy.shiro.entity.Role;
import com.mrlzy.shiro.plugin.jqGrid.JqOperFace;
import com.mrlzy.shiro.plugin.jqGrid.jqGridPlugin;
import com.mrlzy.shiro.tool.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional("localManager")
public class PermService {

    private static Log log = LogFactory.getLog(PermService.class);

    @Autowired
    private PermJpa permJpa;



    @Autowired
    private MenuJpa menuJpa;


    @Autowired
    private PermMapper permMapper;




    public String treeComponentMenuPerm(String parent_id){
        List<Map<String,String>> maplist=parent_id.equals("0")?permMapper.treeComponentRootMenuPerm():permMapper.treeComponentMenuPerm(parent_id);
        int size=maplist.size();
        String json="{";
        for(int i=0;i<size;i++){
            Map<String,String> map=maplist.get(i);
            json+="\""+map.get("ID")+"\""+":"+ JsonUtil.obj2Json(map);
            if(i!=size-1) json+=",";
        }
        json+="}";
        log.info("json-----"+json);
        return json;

    }





    public String listPermForMenu(String  menu_id,jqGridPlugin jqGrid){
        String orderby="perm_code asc";
        if(jqGrid.isOrderBy()){
            orderby+=jqGrid.getSidx()+" "+jqGrid.getSort();
        }

        jqGrid.setRows( permMapper.listPermByMenuId(menu_id,orderby));

        return jqGrid.toJson();
    }



    public void opPerm(jqGridPlugin jqGrid, Permission p, String menu_id) {




        jqGrid.doOper(new JqOperFace() {
            @Override
            public void add() {
                Permission newp=permJpa.save(p);
                Menu m=menuJpa.findOne(menu_id);
                Set<Permission> ps=  m.getPerms();
                ps.add(newp);
                menuJpa.save(m);
            }

            @Override
            public void edit() {
                permJpa.save(p);
            }

            @Override
            public void del() {
                Menu m=menuJpa.findOne(menu_id);
                Set<Permission> ps=  m.getPerms();
                Iterator<Permission> it= ps.iterator();
                while (it.hasNext()){
                    Permission  e=  it.next();
                    if(e.getPerm_id().equals(p.getPerm_id())){
                        ps.remove(e);
                        break;
                    }
                }
                menuJpa.save(m);
                permJpa.delete(p.getPerm_id());
            }

            @Override
            public void error(Exception e,String oper) {
                jqGrid.setSuccess(false);
                if(oper.equals("del")){
                    jqGrid.setErrorMsg("删除失败,此权限在别处引用!");
                }else{
                    jqGrid.setErrorMsg(e.getMessage());
                }
                log.info(e.getMessage(),e);
            }


        });
    }





}
