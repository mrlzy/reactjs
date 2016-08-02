package com.mrlzy.shiro.dao.local.mapper;

import com.mrlzy.shiro.plugin.dto.CaseIgnoreDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface RoleMapper {

    @Select("select role_id  id,role_name text, (case when is_leaf=1 then 'item' else 'folder' end) type  from sys_role where parent_id = #{parent_id} order by sortno asc ")
    public List<Map<String,String>> treeComponentRole(@Param("parent_id") String parent_id);


    @Select("select role_id  id,role_name text, (case when is_leaf=1 then 'item' else 'folder' end) type  from sys_role where parent_id is null order by sortno asc ")
    public List<Map<String,String>> treeComponentRootRole();

   @Select("select role_id,is_leaf leaf ,role_name,sortno,parent_id,(select  role_name from sys_role where t.parent_id=role_id) parent_name,remark  " +
            " from sys_role t where  role_id=#{parent_id}  or weights like '%${parent_id}%'  ${wherebuff}  order by ${orderby} ")
    public     List<CaseIgnoreDto>    listRoleByWeights(@Param("parent_id") String parent_id, @Param("wherebuff") String wherebuff, @Param("orderby") String orderby);



    @Select(" select perm_id from sys_role_perm where role_id=#{role_id} ")
    public List<String> selectPermIds(@Param("role_id") String role_id);

}
