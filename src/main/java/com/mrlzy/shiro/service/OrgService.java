package com.mrlzy.shiro.service;


import com.mrlzy.shiro.dao.local.jpa.OrgJpa;
import com.mrlzy.shiro.dao.local.jpa.RoleJpa;
import com.mrlzy.shiro.dao.local.mapper.OrgMapper;
import com.mrlzy.shiro.dao.local.mapper.RoleMapper;
import com.mrlzy.shiro.entity.Org;
import com.mrlzy.shiro.entity.Role;
import com.mrlzy.shiro.plugin.jqGrid.JqOperFace;
import com.mrlzy.shiro.plugin.jqGrid.jqGridPlugin;
import com.mrlzy.shiro.tool.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional("localManager")
public class OrgService {
    private static Log log = LogFactory.getLog(OrgService.class);

    @Autowired
    private OrgJpa orgJpa;


    @Autowired
    private OrgMapper orgMapper;

    public String treeComponentOrg(String parent_id){
        List<Map<String,String>> maplist=parent_id.equals("0")?orgMapper.treeComponentRootOrg():orgMapper.treeComponentOrg(parent_id);
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

    public List<String> selectOrgByName(String name){
        List<String>  list=new ArrayList<>();
        List<String>  weights=orgMapper.listOrgPathByName(name);
        for (String weight:weights){
            String[] ws=weight.split("/");
            String ss="";
            String id="";
            for (int i = 0; i <ws.length ; i++) {
                ss += "'" + ws[i] + "'";
                if (i != ws.length - 1) {
                    ss += ",";
                }else{
                    id=ws[i];
                }
            }
            if(ss.length()!=0){
                list.add(orgMapper.listOrgNamesByIds(ss)+"_"+id);
            }


        }
        return list;
    }

    public String listOrgForTree(String parent_id,jqGridPlugin jqGrid) {
        String pid =jqGrid.isSearch()?"":parent_id;
        String orderby="sortno asc";
        if(jqGrid.isOrderBy()){
            orderby+=","+jqGrid.getSidx()+" "+jqGrid.getSort();
        }
        String sql=jqGrid.getSql();
        String wherebuff=sql.equals("")?"":" and "+sql;
        log.info("wherebuff---"+  wherebuff);
        jqGrid.setRows( orgMapper.listOrgByWeights(pid,wherebuff,orderby));

        return jqGrid.toJson();
    }

    public void moveOrg(String self_id,String id){
        Org p=orgJpa.findOne(self_id);
        Org parent=orgJpa.findOne(id);
        p.setParent_org(parent);
        orgJpa.save(p);
    }


    public void copyOrg(String self_id,String id){
        Org p=orgJpa.findOne(self_id);
        Org parent=orgJpa.findOne(id);
        p.setParent_org(parent);
        p.setOrg_id(null);
        orgJpa.save(p);
    }

    public void opOrg(jqGridPlugin jqGrid, Org org, String parent_id) {

        Org p=orgJpa.findOne(parent_id);
        org.setParent_org(p);

        jqGrid.doOper(new JqOperFace() {
            @Override
            public void add() {
                orgJpa.save(org);
            }

            @Override
            public void edit() {
                orgJpa.save(org);
            }

            @Override
            public void del() {
                orgJpa.delete(org.getOrg_id());
            }

            @Override
            public void error(Exception e,String oper) {
                jqGrid.setSuccess(false);
                if(oper.equals("del")){
                    jqGrid.setErrorMsg("删除失败,此组织在别处引用!");
                }else{
                    jqGrid.setErrorMsg(e.getMessage());
                }
                log.info(e.getMessage(),e);
            }


        });
    }
}
