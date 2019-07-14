package com.jlau.live.utils;

import java.util.regex.Pattern;

/**
 * Created by cxr1205628673 on 2019/7/9.
 */
public class FilterCharUtils {
    static String reg  = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
    static Pattern pattern = Pattern.compile(reg);
    public String filter(String value){
        String target = value.replaceAll(reg,"");
        return target;
    }
    public static boolean checkIllegal(String value){//返回真为检查到非法字符，不允许通过
        if(pattern.matcher(value).find()){
            return true;
        }else{
            return false;
        }
    }
}
