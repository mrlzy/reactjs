package com.mrlzy.shiro.plugin.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


public interface Dto extends Map {

    public  static  String DTO_SUCCESS="success";
	public  static  String DTO_MSG="msg";


	public void setSuccess(boolean flag);
	public void setMsg(String msg);


	public Integer getAsInteger(String pStr);
	public Integer getAsInteger(String pStr,int def);

	public Long getAsLong(String pStr);


	public String getAsString(String pStr);
	public String getAsString(String pStr,String def);


	public BigDecimal getAsBigDecimal(String pStr);


	public Date getAsDate(String pStr);

	public List getAsList(String key);


	public Timestamp getAsTimestamp(String pStr);

	public Boolean getAsBoolean(String key);

	public Boolean getAsBoolean(String key,boolean def);

	public String toJson();
	
}
