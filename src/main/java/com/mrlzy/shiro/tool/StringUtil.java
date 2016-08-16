package com.mrlzy.shiro.tool;


import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    //----------------------正则表达式-------------------//

     public static  boolean testReg(String reg,String source){
         Pattern pat = Pattern.compile(reg);
         Matcher mat  =pat.matcher(source);
         return mat.matches();
     }

    public static void main(String[] args) {
        System.out.println(testReg("[a-zA-Z0-9]{1,128}","a321$11ee"));
    }




    //----------------------正则表达式-------------------//


    //-------------------------随机数--------------------------//
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


    //-------------------------随机数结束--------------------------//



}
