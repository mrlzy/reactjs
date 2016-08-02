package com.mrlzy.shiro.plugin.dto;


import com.mrlzy.shiro.tool.JsonUtil;
import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class CaseIgnoreDto extends CaseInsensitiveMap  implements  Dto{

    public CaseIgnoreDto(){}

    public CaseIgnoreDto(String key, Object value){
        put(key, value);
    }

    public CaseIgnoreDto(Map map){
        putAll(map);
    }

    public BigDecimal getAsBigDecimal(String key) {
        Object obj = TypeCaseHelper.convert(get(key), "BigDecimal", null);
        if (obj != null)
            return (BigDecimal) obj;
        else
            return null;
    }


    public Date getAsDate(String key) {
        Object obj = TypeCaseHelper.convert(get(key), "Date", "yyyy-MM-dd");
        if (obj != null)
            return (Date) obj;
        else
            return null;
    }


    public Integer getAsInteger(String key) {
        Object obj = TypeCaseHelper.convert(get(key), "Integer", null);
        if (obj != null)
            return (Integer) obj;
        else
            return null;
    }

    public Integer getAsInteger(String key,int def) {
        Object obj = TypeCaseHelper.convert(get(key), "Integer", null);
        if (obj != null)
            return (Integer) obj;
        else
            return def;
    }


    public Long getAsLong(String key) {
        Object obj = TypeCaseHelper.convert(get(key), "Long", null);
        if (obj != null)
            return (Long) obj;
        else
            return null;
    }


    public String getAsString(String key) {
        Object obj = TypeCaseHelper.convert(get(key), "String", null);
        if (obj != null)
            return (String) obj;
        else
            return "";
    }

    public String getAsString(String key,String def) {
        Object obj = TypeCaseHelper.convert(get(key), "String", null);
        if (obj != null)
            return (String) obj;
        else
            return def;
    }


    public List getAsList(String key){
        return (List)get(key);
    }


    public Timestamp getAsTimestamp(String key) {
        Object obj = TypeCaseHelper.convert(get(key), "Timestamp", "yyyy-MM-dd HH:mm:ss");
        if (obj != null)
            return (Timestamp) obj;
        else
            return null;
    }


    public Boolean getAsBoolean(String key){
        Object obj = TypeCaseHelper.convert(get(key), "Boolean", null);
        if (obj != null)
            return (Boolean) obj;
        else
            return null;
    }

    public Boolean getAsBoolean(String key,boolean def){
        Object obj = TypeCaseHelper.convert(get(key), "Boolean", null);
        if (obj != null)
            return (Boolean) obj;
        else
            return def;
    }


    @Override
    public void setSuccess(boolean flag) {
        this.put(DTO_SUCCESS,flag);
    }

    @Override
    public void setMsg(String msg) {
        this.put(DTO_MSG,msg);
    }

    @Override
    public String toJson() {
        return JsonUtil.obj2Json(this);
    }

}
