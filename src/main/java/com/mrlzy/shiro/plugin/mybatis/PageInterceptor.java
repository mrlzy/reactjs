package com.mrlzy.shiro.plugin.mybatis;


import com.mrlzy.shiro.tool.RequestUtil;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;


@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PageInterceptor implements Interceptor {

    private static Logger log = Logger.getLogger(PageInterceptor.class);






/*

    private Object exeSql(String sql,Connection con){
           con.prepareStatement(sql);

    }
*/

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        PageView pv = PageView.getLocalPage();

       if(pv==null){
           return  invocation.proceed();
       }

        Object target = invocation.getTarget();

        if (target instanceof Executor) {
            Executor executor = (Executor) invocation.getTarget();
            Object[] args = invocation.getArgs();

            MappedStatement ms = (MappedStatement) args[0];
            Object parameterObject = args[1];

            BoundSql boundSql = ms.getBoundSql(parameterObject);
            String sql = boundSql.getSql();

            String countSql=" select count(1) from ("+sql+")";

            log.info("countSql:"+countSql);

            Connection conn=ms.getConfiguration().getEnvironment().getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement(countSql);
            BoundSql countBS = copyFromBoundSql(ms, boundSql, countSql);
            DefaultParameterHandler parameterHandler = new DefaultParameterHandler(ms, parameterObject, countBS);
            parameterHandler.setParameters(ps);
            ResultSet rs = ps.executeQuery();
            int total=0;
            if(rs.next()){
                total=rs.getInt(1);
            }
            pv.setRecords(total);
            rs.close();
            ps.close();
            conn.close();

            String dataSql = "select * from ( select pv.*, ROWNUM RN FROM (" + sql + ") pv) where rn>'" +pv.getStartNo() + "' and rn<='" + pv.getEndNo() + "'";
            pv.setBoundSql(copyFromBoundSql(ms,boundSql,dataSql));
            MappedStatement newMs = copyFromMappedStatement(ms,pv);
            invocation.getArgs()[0]= newMs;

        }


        return invocation.proceed();
    }


    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }


    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        String[] keyProperties = ms.getKeyProperties();
        if (keyProperties == null) {
            builder.keyProperty(null);
        } else {
            String key = "";
            for (int i = 0; i < keyProperties.length; i++) {
                key += keyProperties[i];
                if (i != keyProperties.length - 1) {
                    key += ",";
                }
            }
            builder.keyProperty(key);

        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }


    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }

    }

    @Override
    public void setProperties(Properties properties) {
        //log.info("fffffffffff--------" + properties.get("yoyo"));
    }


}
