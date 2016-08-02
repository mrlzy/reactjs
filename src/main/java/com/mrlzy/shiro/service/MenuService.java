package com.mrlzy.shiro.service;

import com.mrlzy.shiro.dao.local.jpa.MenuJpa;
import com.mrlzy.shiro.dao.local.mapper.MenuMapper;
import com.mrlzy.shiro.entity.Menu;
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
public class MenuService {

    private static Log log = LogFactory.getLog(MenuService.class);

    @Autowired
    private MenuJpa menuJpa;

    @Autowired
    private MenuMapper menuMapper;




    public String treeComponentMenu(String parent_id){
        List<Map<String,String>> maplist=parent_id.equals("0")?menuMapper.treeComponentRootMenu():menuMapper.treeComponentMenu(parent_id);
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

    public String listMenuForTree(String parent_id,jqGridPlugin jqGrid) {
        String pid =jqGrid.isSearch()?"":parent_id;
        String orderby="sortno asc";
        if(jqGrid.isOrderBy()){
            orderby+=","+jqGrid.getSidx()+" "+jqGrid.getSort();
        }
        String sql=jqGrid.getSql();
        String wherebuff=sql.equals("")?"":" and "+sql;
        log.info("wherebuff---"+  wherebuff);
        jqGrid.setRows( menuMapper.listMenuByWeights(pid,wherebuff,orderby));

        return jqGrid.toJson();
    }

    public void moveMenu(String self_id,String id){
            Menu p=menuJpa.findOne(self_id);
            Menu parent=menuJpa.findOne(id);
            p.setParent_menu(parent);
            menuJpa.save(p);
    }


    public void copyMenu(String self_id,String id){
        Menu p=menuJpa.findOne(self_id);
        Menu parent=menuJpa.findOne(id);
        p.setParent_menu(parent);
        p.setMenu_id(null);
        menuJpa.save(p);
    }

    public void opMenu(jqGridPlugin jqGrid,Menu m,String parent_id) {

        Menu p=menuJpa.findOne(parent_id);
        m.setParent_menu(p);

        jqGrid.doOper(new JqOperFace() {
            @Override
            public void add() {
                    menuJpa.save(m);
            }

            @Override
            public void edit() {
                menuJpa.save(m);
            }

            @Override
            public void del() {
                    menuJpa.delete(m.getMenu_id());
            }

            @Override
            public void error(Exception e,String oper) {
                jqGrid.setSuccess(false);
                if(oper.equals("del")){
                    jqGrid.setErrorMsg("删除失败,此菜单在别处引用!");
                }else{
                    jqGrid.setErrorMsg(e.getMessage());
                }
                log.info(e.getMessage(),e);
            }


        });
    }

}
