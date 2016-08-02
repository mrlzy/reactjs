package com.mrlzy.shiro.plugin.jqGrid;


import java.util.Map;
import java.util.Collection;
import java.util.HashMap;



public class OracleFnMap  implements  FnMap{


    @Override
    public String fn(String op, String v) {
        switch (op){
            case  "eq":
                return  equals(v);
            case  "ne":
                return  notEquals(v);
            case  "lt":
                return  less(v);
            case  "le":
                return  lessOrEquals(v);
            case  "gt":
                return  greater(v);
            case  "ge":
                return  greaterOrEquals(v);
            case  "cn":
                return  contains(v);
            case  "nc":
                return  notContains(v);
            case  "bw":
                return  startsWith(v);
            case  "bn":
                return  notStartsWith(v);
            case  "ew":
                return  endsWith(v);
            case  "en":
                return  notEndsWith(v);
            case  "in":
                return  in(v);
            case  "ni":
                return  notIn(v);
            case  "nu":
                return  isNull();
            case  "nn":
                return  isNotNull();
        }
        return equals(v);
    }

    @Override
    public String equals(String v) {
        return " == '"+v+"'";
    }

    @Override
    public String notEquals(String v) {
        return "<> '"+v+"'";
    }

    @Override
    public String less(String v) {
        return "< '"+v+"'";
    }

    @Override
    public String lessOrEquals(String v) {
        return "<= '"+v+"'";
    }

    @Override
    public String greater(String v) {
        return "> '"+v+"'";
    }

    @Override
    public String greaterOrEquals(String v) {
        return ">= '"+v+"'";
    }

    @Override
    public String contains(String v) {
        return "like '%"+v+"%'";
    }

    @Override
    public String notContains(String v) {
        return "not like '%"+v+"%'";
    }

    @Override
    public String startsWith(String v) {
        return "like '"+v+"%'";
    }

    @Override
    public String notStartsWith(String v) {
        return "not like '"+v+"%'";
    }

    @Override
    public String endsWith(String v) {
        return "like '%"+v+"'";
    }

    @Override
    public String notEndsWith(String v) {
        return "not like '%"+v+"'";
    }

    @Override
    public String in(String v) {
        return "in ("+v+")";
    }

    @Override
    public String notIn(String v) {
        return "not in ("+v+")";
    }

    @Override
    public String isNull() {
        return "is null";
    }

    @Override
    public String isNotNull() {
        return "is not null";
    }


}
