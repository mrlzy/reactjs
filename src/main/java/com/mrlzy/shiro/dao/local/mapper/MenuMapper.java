package com.mrlzy.shiro.dao.local.mapper;

import com.mrlzy.shiro.plugin.dto.CaseIgnoreDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

public interface MenuMapper {

    @Select("select menu_id  id,menu_name text, (case when is_leaf=1 then 'item' else 'folder' end) type  from sys_menu where parent_id = #{parent_id} order by sortno asc ")
    public List<Map<String,String>> treeComponentMenu(@Param("parent_id") String parent_id);


    @Select("select menu_id  id,menu_name text, (case when is_leaf=1 then 'item' else 'folder' end) type  from sys_menu where parent_id is null order by sortno asc ")
    public List<Map<String,String>> treeComponentRootMenu();

    @Select("select menu_id,is_leaf leaf ,menu_name,sortno,statu,url,icon_cls,parent_id,(select  menu_name from sys_menu where t.parent_id=menu_id) parent_name " +
            " from sys_menu t where  menu_id=#{parent_id}  or weights like '%${parent_id}%'  ${wherebuff}  order by ${orderby} ")
    public     List<CaseIgnoreDto>    listMenuByWeights(@Param("parent_id") String parent_id,@Param("wherebuff") String wherebuff, @Param("orderby") String orderby);



    @Select("select menu_id,is_leaf leaf,menu_name,url,icon_cls from sys_menu where parent_id is null and menu_id in (" +
            "   select menu_id from sys_role_menu where role_id in ( " +
            "        select role_id from sys_role where  role_id=#{role_id} or weights like '%${role_id}%'" +
            "   )" +
            ") order by sortno asc ")
    public List<CaseIgnoreDto> getRootMenuList(@Param("role_id") String role_id);

    @Select("select menu_id,is_leaf leaf,menu_name,url,icon_cls from sys_menu where  parent_id=#{parent_id}  and menu_id in (" +
            "   select menu_id from sys_role_menu where role_id in ( " +
            "        select role_id from sys_role where  role_id=#{role_id} or  weights like '%${role_id}%'" +
            "   )" +
            ") order by sortno asc ")
    public List<CaseIgnoreDto> getListMenuByParentId(@Param("role_id") String role_id,@Param("parent_id") String parent_id);



}
