package com.mrlzy.shiro.dao.local.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SmsMapper {

    @Insert("insert into wzportal.DHL_LOCAL_LONG_SM(SRC_TELE_NUM,DEST_TELE_NUM,MSG,MSG_TYPE,insert_date)" +
            " values('106573077570',#{tel},#{msg},#{type},sysdate) ")
    public int sendMsg(@Param("tel")String tel,@Param("msg")String msg,@Param("type") String type);
}
