package com.mrlzy.shiro.tool;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public  class SpellUtil {
    //将中文转换为英文
    public static String getEname(String name) throws BadHanyuPinyinOutputFormatCombination {
        HanyuPinyinOutputFormat pyFormat = new        HanyuPinyinOutputFormat();
        pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        return PinyinHelper.toHanyuPinyinString(name, pyFormat, "");
    }

    //姓、名的第一个字母需要为大写
    public static String getUpEname(String name) throws BadHanyuPinyinOutputFormatCombination {
        char[] strs = name.toCharArray();
        String newname = null;

        //名字的长度
        if (strs.length == 2) {
            newname = toUpCase(getEname("" + strs[0])) + " "
                    + toUpCase(getEname("" + strs[1]));
        } else if (strs.length == 3)
        {
            newname = toUpCase(getEname("" + strs[0])) + " "
                    + toUpCase(getEname("" + strs[1] + strs[2]));
        }
        else if (strs.length == 4)
        {
            newname = toUpCase(getEname("" + strs[0] + strs[1])) + " "
                    + toUpCase(getEname("" + strs[2] + strs[3]));
        } else
        {
            newname = toUpCase(getEname(name));
        }
        return newname;
    }

    //首字母大写
    private static String toUpCase(String str) {
        StringBuffer newstr = new StringBuffer();
        newstr.append((str.substring(0, 1)).toUpperCase()).append(
                str.substring(1, str.length()));

        return newstr.toString();
    }
    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
        System.out.println(getUpEname("李宇春"));

    }

}