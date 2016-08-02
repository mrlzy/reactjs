package com.mrlzy.shiro.tool;

import java.util.Date;
import java.util.UUID;

public class IdGenerator {






    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }



   private  static  int num=10;
   public static synchronized String getTimeId() {
        String date = DateUtil.getDate2FormatString(new Date(), "yyyyMMddHHmmssS");
        num++;
        if (num >= 99) num = 10;
         date += num;
        return date;
    }


    public static void main(String[] args) {
        System.out.println(getUUID());
        //System.out.println(getUUID("yoyo"));
    }

}
