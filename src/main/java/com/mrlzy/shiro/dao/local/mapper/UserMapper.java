package com.mrlzy.shiro.dao.local.mapper;

import com.mrlzy.shiro.plugin.dto.CaseIgnoreDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper {

   @Select("select user_id,account,email,locked,tel,user_name,a.org_id,b.org_name,(select role_name from sys_role where role_id=a.role_id) role_name,role_id " +
           "from sys_user a,sys_org b  where  a.org_id=b.org_id and  ( a.org_id = #{parent_id} or b.weights like '%${parent_id}%'  ) ${wherebuff}   order by ${orderby} ")
   public List<CaseIgnoreDto> listUser(@Param("parent_id") String parent_id, @Param("wherebuff") String wherebuff, @Param("orderby") String orderby);


   @Select("select org_id  id,org_name text, 'folder' type,1 n   from sys_org where parent_id = #{parent_id}  " +
           " union " +
           "select user_id id,user_name text,'item'  type,2 n from sys_user where org_id=#{parent_id}  " +
           "    order by n,id asc ")
   public List<Map<String,String>> treeComponentOrgUser(@Param("parent_id") String parent_id);


   @Select("select org_id  id,org_name text,  'folder'  type  from sys_org where parent_id is null order by sortno asc ")
   public List<Map<String,String>> treeComponentRootOrgUser();

}
