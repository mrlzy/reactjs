package com.mrlzy.shiro.plugin.jqGrid;



public interface FnMap {

    public  String fn(String op,String v);

    public    String equals(String v);

    public    String notEquals(String v);

    public    String less(String v);

    public    String lessOrEquals(String v);

    public    String greater(String v);
    public    String greaterOrEquals(String v);

    public    String contains(String v);

    public    String notContains(String v);

    public    String startsWith(String v);
    public    String notStartsWith(String v);

    public    String endsWith(String v);
    public    String notEndsWith(String v);


    public    String in(String v);
    public    String notIn(String v);

    public    String isNull();

    public    String isNotNull();

}
