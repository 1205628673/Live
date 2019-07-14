package com.jlau.live.utils;

import org.aspectj.lang.JoinPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cxr1205628673 on 2019/7/11.
 */
public class RequestUtils {
    public static Map getJoinPointInfoMap(JoinPoint joinPoint){
        Map<String,Object> joinPointInfoMap = new HashMap<>();
        String classPath = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        joinPointInfoMap.put("classPath",classPath);
        joinPointInfoMap.put("methodName",methodName);
        return joinPointInfoMap;
    }
}
