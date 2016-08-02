package com.mrlzy.shiro.dao.local.mapper;

import com.mrlzy.shiro.plugin.dto.CaseIgnoreDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PermMapper {


    @Select("select b.* from sys_menu_perm a,sys_perm b  where a.perm_id=b.perm_id  and menu_id=#{menu_id} order by ${orderby} ")
    public     List<CaseIgnoreDto>    listPermByMenuId(@Param("menu_id") String menu_id,@Param("orderby") String orderby);


    @Select("select menu_id  id,menu_name text, 'folder' type ,1 n  from sys_menu where parent_id = #{parent_id} "+
            " union " +
            "select a.perm_id id,perm_code||'['||remark||']' text, 'item' type ,2 n " +
            " from sys_perm a left join sys_menu_perm  b on a.perm_id=b.perm_id where menu_id = #{parent_id} " +
            " order by n,id asc ")
    public List<Map<String,String>> treeComponentMenuPerm(@Param("parent_id") String parent_id);



    @Select("select menu_id  id,menu_name text, 'folder'  type  from sys_menu where parent_id is null order by sortno asc ")
    public List<Map<String,String>> treeComponentRootMenuPerm();




}
