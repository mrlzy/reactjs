package com.mrlzy.shiro.tool;


import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestUtil {
    public static Logger log = Logger.getLogger(RequestUtil.class);


    public static Map<?, ?> getParamAsMap(HttpServletRequest request)
    {
        Map dto = new HashMap();
        Map<?, ?> map = request.getParameterMap();
        Iterator<?> keyIterator = map.keySet().iterator();
        String debugStr = "";
        while (keyIterator.hasNext()) {
            String key = (String) keyIterator.next();
            String[] sv= (String[]) map.get(key);
            String value="";
            if(sv!=null&&sv.length>0) {
                value=sv[0];
                if(sv.length>1){
                    dto.put(key+"[]", sv);
                }
            }
            dto.put(key, value);
            debugStr += "[\"" + key + "\":\"" + value + "\"]";
        }
        log.info(debugStr);
        return dto;
    }



    public static Dto getParamAsDto(HttpServletRequest request)
    {
         return new BaseDto(getParamAsMap(request));
    }



}
