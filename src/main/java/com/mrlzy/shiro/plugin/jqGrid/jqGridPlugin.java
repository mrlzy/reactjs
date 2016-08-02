package com.mrlzy.shiro.plugin.jqGrid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mrlzy.shiro.plugin.dto.Dto;
import com.mrlzy.shiro.plugin.mybatis.PageView;
import com.mrlzy.shiro.tool.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class jqGridPlugin  extends PageView{

    private static final String JQGRID_LIMIT="rows";
    private static final String JQGRID_PAGE="page";

    private static final String JQGRID_SORT="sord";
    private static final String JQGRID_SORTCOLUMN="sidx";
    private static final String JQGRID_FILTERS="filters";
    private static final String JQGRID_SEARCH="_search";
    private static final String JQGRID_OPER="oper";



    private String sort="asc";

    private String sidx;


    private String filters;


    private boolean search=false;


    private  boolean success=true;

    private  String errorMsg="";

    private  String oper;

    public jqGridPlugin(Dto request,boolean autoPage) {
        super(request.getAsInteger(JQGRID_LIMIT,10),request.getAsInteger(JQGRID_PAGE,1),autoPage);
        this.sort=request.getAsString(JQGRID_SORT,"asc");
        this.sidx=request.getAsString(JQGRID_SORTCOLUMN);
        this.filters=request.getAsString(JQGRID_FILTERS);
        this.search=request.getAsBoolean(JQGRID_SEARCH,false);
        this.oper= request.getAsString(JQGRID_OPER);



    }


    public jqGridPlugin(Dto request) {
         this(request,true);
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @JsonIgnore
    public String getReturnMsg(){
        return "{\"success\":"+this.success+",\"msg\":\""+this.errorMsg+"\" }";
    }


    public boolean isSearch(){
        return this.search;
    }

    public boolean isOrderBy(){
        return !this.sidx.equals("");
    }

    @JsonIgnore
    public String getSidx() {
        return sidx;
    }

    @JsonIgnore
    public String getSort() {
        return sort;
    }


    public void doOper(JqOperFace face){

        try{
            switch (this.oper){
                case "add":
                    face.add();
                    break;

                case "edit":
                    face.edit();
                    break;
                case "del":
                    face.del();
                    break;
                default:
                    throw new Exception("oper["+this.oper+"] is outside the law;");
            }
        }catch (Exception e){
            face.error(e,this.oper);
        }


    }

    @JsonIgnore
    public Filter getFilters() {
        if(filters.equals(""))  return null;
        return  (Filter)JsonUtil.json2Obj(Filter.class,this.filters);
    }


    @JsonIgnore
    public String getSql(String tableName,FnMap fnMap){
        Filter filter=this.getFilters();
        if(filter!=null){
              return filter.groupSql(tableName,fnMap);
        }

        return "";
    }

    @JsonIgnore
    public String getSql(String tableName){
        return getSql(tableName,new  OracleFnMap());
    }

    @JsonIgnore
    public String getSql(){
        return getSql("");
    }

    public static void main(String[] args) {
        //String text="{\"groupOp\":\"AND\",\"rules\":[{\"field\":\"MENU_NAME\",\"op\":\"eq\",\"data\":\"dsdd\"}]}";

       /*  Filter f=new Filter();
         List<Filter.Rule> rules=new ArrayList<Filter.Rule>() ;
        Filter.Rule r1=new Filter.Rule();
        r1.setField("MENU_NAME");
        r1.setOp("eq");
        r1.setData("dsdd");
        rules.add(r1);
        f.setGroupOp("AND");
        f.setRules(rules);*/
       // jqGridPlugin.Filter f=(jqGridPlugin.Filter) JsonUtil.json2Obj(jqGridPlugin.Filter.class,text);
       // System.out.println(JsonUtil.obj2Json(f));
       // System.out.println(f.groupSql("",new  OracleFnMap()));
      //  jqGridPlugin.JqOper y= new JqOper();
       // System.out.println(y==JqOper.NULL);
    }



    static   class  Filter{
         private  String  groupOp;
         private  List<Rule> rules;
         private  List<Filter> groups;



          public  String groupSql(String tableName,FnMap fnMap){
           String sql="";
           List<Rule> rules=getRules();
           for (Rule r: rules) {
               sql+=(sql.equals("")?"": this.groupOp) +r.groupSql(tableName,fnMap);
           }
           List<Filter> filters=getGroups();

           for (Filter f: filters) {
               sql+=(sql.equals("")?"": this.groupOp) + " ( "+f.groupSql(tableName,fnMap)+" ) ";
           }

           return sql;
        }

        public String getGroupOp() {
            return groupOp;
        }

        public void setGroupOp(String groupOp) {
            this.groupOp = groupOp;
        }

        public List<Rule> getRules() {
            if(this.rules==null) return  new ArrayList<Rule>();
            return rules;
        }

        public void setRules(List<Rule> rules) {
            this.rules = rules;
        }

        public List<Filter> getGroups() {
            if(this.groups==null) return  new ArrayList<Filter>();
            return groups;
        }

        public void setGroups(List<Filter> groups) {
            this.groups = groups;
        }



    static class  Rule{
            private  String  field;
            private  String op;
            private  String data;



            public  String groupSql(String tableName,FnMap fnMap){
                String tb=tableName.equals("")?"":tableName+".";
                return " ( "+ tb+this.field +" "+fnMap.fn(this.op,this.data)+" ) " ;
            }

            public String getField() {
                return field;
            }

            public void setField(String field) {
                this.field = field;
            }

            public String getOp() {
                return op;
            }

            public void setOp(String op) {
                this.op = op;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }
        }
    }


}
