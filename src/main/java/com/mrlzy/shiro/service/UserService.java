package com.mrlzy.shiro.service;

import com.mrlzy.shiro.dao.local.jpa.OrgJpa;
import com.mrlzy.shiro.dao.local.jpa.RoleJpa;
import com.mrlzy.shiro.dao.local.jpa.UserJpa;
import com.mrlzy.shiro.dao.local.mapper.RoleMapper;
import com.mrlzy.shiro.dao.local.mapper.UserMapper;
import com.mrlzy.shiro.entity.Org;
import com.mrlzy.shiro.entity.Role;
import com.mrlzy.shiro.entity.User;
import com.mrlzy.shiro.plugin.jqGrid.JqOperFace;
import com.mrlzy.shiro.plugin.jqGrid.jqGridPlugin;
import com.mrlzy.shiro.tool.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional("localManager")
public class UserService {
    private static Log log = LogFactory.getLog(UserService.class);

    @Autowired
    private UserJpa userJpa;
    @Autowired
    private OrgJpa orgJpa;

    @Autowired
    private RoleJpa roleJpa;
    @Autowired
    private UserMapper userMapper;




    public String treeComponentOrgUser(String parent_id){
        List<Map<String,String>> maplist=parent_id.equals("0")?userMapper.treeComponentRootOrgUser():userMapper.treeComponentOrgUser(parent_id);
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



    public String listUserForTree(String parent_id,jqGridPlugin jqGrid) {
        String pid =jqGrid.isSearch()?"":parent_id;
        String orderby="user_id asc";
        if(jqGrid.isOrderBy()){
            orderby+=","+jqGrid.getSidx()+" "+jqGrid.getSort();
        }
        String sql=jqGrid.getSql();
        String wherebuff=sql.equals("")?"":" and "+sql;
        log.info("wherebuff---"+  wherebuff);
        jqGrid.setRows( userMapper.listUser(pid,wherebuff,orderby));
        return jqGrid.toJson();
    }



    public void moveUser(String self_id,String id){
        User u=userJpa.findOne(self_id);
        Org parent=orgJpa.findOne(id);
        u.setOrg(parent);
        userJpa.save(u);
    }
    public void authorizeRole(String user_id,String role_id){
        User u=userJpa.findOne(user_id);
        Role role=new Role();
        role.setRole_id(role_id);
        u.setRole(role);
        userJpa.save(u);
    }


    public void opUser(jqGridPlugin jqGrid, User u,String org_id) {
        Org parent=orgJpa.findOne(org_id);
        u.setOrg(parent);
        u.setPasswd("888888");


        jqGrid.doOper(new JqOperFace() {
            @Override
            public void add() {
                userJpa.save(u);
            }

            @Override
            public void edit() {
                userJpa.save(u);
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
