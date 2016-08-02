package com.mrlzy.shiro.tool;


import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.util.UUID;

public class StringUtil {


    private static  String  strTable="0123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";



    public static char  getRandomCharInTable(){
        return getRandomCharInTable(strTable);
    }

    public static char getRandomCharInTable(String strTable){
        int len = strTable.length();
        double dblR = Math.random() * len;
        int intR = (int) Math.floor( dblR );
        char c = strTable.charAt( intR );
        return c;
    }

    public  static String getRandomStr(int strlen,String strTable){
          char[] doms=new char[strlen];
           for (int i = 0; i <doms.length ; i++) {
               doms[i]=getRandomCharInTable(strTable);
           }
          return new String(doms);
    }


    public  static String getRandomStr(int strlen){
        return getRandomStr(strlen,strTable);
    }

   private  static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    public static String  getRandomHexNumber(int size){
        return  randomNumberGenerator.nextBytes(size).toHex();
    }

    public static String  getRandomHexNumber(){
        return  randomNumberGenerator.nextBytes(16).toHex();
    }

    public static  String getUUIDFromName(String name){
        UUID uuid= UUID.nameUUIDFromBytes(name.getBytes());
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }





}
