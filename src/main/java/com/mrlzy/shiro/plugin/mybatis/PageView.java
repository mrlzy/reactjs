package com.mrlzy.shiro.plugin.mybatis;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mrlzy.shiro.tool.JsonUtil;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

public  class PageView  implements  SqlSource{


    private static final ThreadLocal<PageView> localPage = new ThreadLocal<PageView>();

    public static PageView getLocalPage(){
        PageView pv=localPage.get();
        if(pv!=null)
               localPage.remove();
        return pv;
    }

    private int records=0;//总条数

    private int limit=10;//每页限制数

    private int page=1;//当前页数

    private int total=1;//总页数

    private List rows;//结果集


    public PageView(int limit, int page,boolean autoPage) {
        this.limit = limit;
        this.page = page;
        if(autoPage)
           createLocalPage();
    }




    public  PageView(){
           this(10,1,true);
    }


    public int getRecords() {
        return records;
    }

    public int getLimit() {
        return limit;
    }

    public int getPage() {
        return page;
    }

    public int getTotal() {
        return total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public void setRecords(int records) {
        this.records = records;
        this.total=records/this.limit+records%limit==0?0:1;
    }


    public String toJson(){
        return JsonUtil.obj2Json(this);
    }

    @JsonIgnore
    public int getStartNo() {
        return (this.page-1)*this.limit;
    }
    @JsonIgnore
    public int getEndNo() {
        return this.page*this.limit;
    }


    public void createLocalPage(){
        localPage.set(this);
    }

    private BoundSql boundSql;

    public  void setBoundSql(BoundSql boundSql){
        this.boundSql=boundSql;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return this.boundSql;
    }
}
