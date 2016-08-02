package com.mrlzy.shiro.dao.remote.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface UserRemoteMapper {

   public  String getNameById(@Param("id") String id);

   @Insert("insert into sys_user(userid,username)values(#{id},#{name})")
   public  void saveUser(@Param("id") String id, @Param("name") String name);


}
