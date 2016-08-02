package com.mrlzy.shiro.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

     private static Map<String,SimpleDateFormat> formats=new HashMap<String,SimpleDateFormat>();

    public   static SimpleDateFormat getSimpleDateFormat(String pattern){
             if(formats.containsKey(pattern)){
                 return formats.get(pattern);
             }
            SimpleDateFormat format=new SimpleDateFormat(pattern);
            formats.put(pattern,format);
            return  format;
    }

    public static String  getDate2FormatString(Date date,String pattern){
        SimpleDateFormat format=   getSimpleDateFormat(pattern);
        return  format.format(date);
    }

    public static void main(String[] args) {
        SimpleDateFormat format=new SimpleDateFormat("oo");
        System.out.println(format);

    }
}
