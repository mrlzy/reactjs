package com.mrlzy.shiro.dao.local.mapper;


import com.mrlzy.shiro.plugin.dto.CaseIgnoreDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface OrgMapper {

    @Select("select org_id  id,org_name text, (case when is_leaf=1 then 'item' else 'folder' end) type  from sys_org  where   parent_id = #{parent_id} order by sortno asc ")
    public List<Map<String,String>> treeComponentOrg(@Param("parent_id") String parent_id);


    @Select("select org_id  id,org_name text, (case when is_leaf=1 then 'item' else 'folder' end) type  from sys_org  where   parent_id is null order by sortno asc ")
    public List<Map<String,String>> treeComponentRootOrg();

    @Select("select org_id,is_leaf leaf ,org_name,sortno,statu,parent_id,(select  org_name from sys_org where t.parent_id=org_id) parent_name " +
            " from sys_org t where  org_id=#{parent_id}  or weights like '%${parent_id}%'  ${wherebuff}  order by ${orderby} ")
    public     List<CaseIgnoreDto>    listOrgByWeights(@Param("parent_id") String parent_id, @Param("wherebuff") String wherebuff, @Param("orderby") String orderby);


    @Select("select  weights||org_id from  sys_org where org_name like '%${name}%'")
    public List<String> listOrgPathByName(@Param("name")String name);

    @Select("select  replace(WMSYS.WM_CONCAT(org_name),',','=>') from sys_org where org_id in (${ids})")
    public String  listOrgNamesByIds(@Param("ids")String ids);

}
