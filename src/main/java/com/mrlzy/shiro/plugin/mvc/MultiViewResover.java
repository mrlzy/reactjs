package com.mrlzy.shiro.plugin.mvc;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;
import java.util.Map;

public class MultiViewResover implements ViewResolver {

    private static String DEFAULT_RESOLVER="jsp";
    public static String NAME_SEPARATOR="_WITH_";


    private Map<String, ViewResolver> resolvers;

    private String defaultResolver=DEFAULT_RESOLVER;
    private String defaultSeparator=NAME_SEPARATOR;

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {

        ViewResolver resolver=null;

        if(!resolvers.containsKey(this.defaultResolver)) {
                throw new Exception("can't find resolver["+this.defaultResolver+"]");
        }

        resolver=resolvers.get(this.defaultResolver);

        if(viewName.indexOf(this.defaultSeparator)==-1){
                return resolver.resolveViewName(viewName,locale);
        }


        String[] vs= viewName.split(this.defaultSeparator);

        if(vs==null||vs.length!=2){
               throw new Exception("viewName["+viewName+"] don't conform to the rules[XXX"+this.defaultSeparator+"XXX]");
        }

        if(!resolvers.containsKey(vs[1])) {
            throw new Exception("can't find resolver["+vs[1]+"]");
        }
        resolver=resolvers.get(vs[1]);

        return resolver.resolveViewName(vs[0],locale);
    }





    public Map<String, ViewResolver> getResolvers() {
        return resolvers;
    }

    public void setResolvers(Map<String, ViewResolver> resolvers) {
        this.resolvers = resolvers;
    }

    public String getDefaultResolver() {
        return defaultResolver;
    }

    public void setDefaultResolver(String defaultResolver) {
        this.defaultResolver = defaultResolver;
    }

    public String getDefaultSeparator() {
        return defaultSeparator;
    }

    public void setDefaultSeparator(String defaultSeparator) {
        this.defaultSeparator = defaultSeparator;
    }
}
