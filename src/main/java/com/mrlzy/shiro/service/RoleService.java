package com.mrlzy.shiro.service;


import com.mrlzy.shiro.dao.local.jpa.RoleJpa;
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
public class RoleService {
    private static Log log = LogFactory.getLog(RoleService.class);

    @Autowired
    private RoleJpa roleJpa;


    @Autowired
    private RoleMapper roleMapper;

    public String treeComponentRole(String parent_id){
        List<Map<String,String>> maplist=parent_id.equals("0")?roleMapper.treeComponentRootRole():roleMapper.treeComponentRole(parent_id);
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
    public String listRoleForTree(String parent_id,jqGridPlugin jqGrid) {
        String pid =jqGrid.isSearch()?"":parent_id;
        String orderby="sortno asc";
        if(jqGrid.isOrderBy()){
            orderby+=","+jqGrid.getSidx()+" "+jqGrid.getSort();
        }
        String sql=jqGrid.getSql();
        String wherebuff=sql.equals("")?"":" and "+sql;
        log.info("wherebuff---"+  wherebuff);
        jqGrid.setRows( roleMapper.listRoleByWeights(pid,wherebuff,orderby));

        return jqGrid.toJson();
    }

    public void moveRole(String self_id,String id){
        Role p=roleJpa.findOne(self_id);
        Role parent=roleJpa.findOne(id);
        p.setParent_role(parent);
        roleJpa.save(p);
    }


    public List<String> selectPermIds(String role_id){
       return roleMapper.selectPermIds(role_id);
    }


    public void authorizeMenu(Role  u){
        //Role u=roleJpa.findOne(role_id);
        Set<Permission>   ps=   u.getPerms();
        Set<Menu> menus=new HashSet<>();
        for(Permission p:ps){
            Set<Menu>  ms= p.getMenus();
            Iterator<Menu>  it=ms.iterator();
            while(it.hasNext()){
                Menu  m=it.next();
                String[] ids=  ( m.getWeights()+m.getMenu_id()).split("/");
                for(String id:ids){
                    if(id.equals("")) continue;
                    Menu nm=new Menu();
                    nm.setMenu_id(id);
                    menus.add(nm);
                }

            }
        }
        u.setMenus(menus);
        roleJpa.save(u);
    }

    public void authorizePerm(String role_id,String[] perms){
        Role u=roleJpa.findOne(role_id);

        Set<Permission> ps=new HashSet<>();
        for (String perm_id:perms) {
            Permission p=new Permission();
            p.setPerm_id(perm_id);
            ps.add(p);
        }
        u.setPerms(ps);
        roleJpa.save(u);
        authorizeMenu(u);
    }

    public void copyRole(String self_id,String id){
        Role p=roleJpa.findOne(self_id);
        Role parent=roleJpa.findOne(id);
        p.setParent_role(parent);
        p.setRole_id(null);
        roleJpa.save(p);
    }

    public void opRole(jqGridPlugin jqGrid, Role role, String parent_id) {

        Role p=roleJpa.findOne(parent_id);
        role.setParent_role(p);

        jqGrid.doOper(new JqOperFace() {
            @Override
            public void add() {
                roleJpa.save(role);
            }

            @Override
            public void edit() {
                roleJpa.save(role);
            }

            @Override
            public void del() {

            }

            @Override
            public void error(Exception e,String oper) {
                jqGrid.setSuccess(false);
                jqGrid.setErrorMsg(e.getMessage());
                log.info(e.getMessage(),e);
            }


        });
    }
}
