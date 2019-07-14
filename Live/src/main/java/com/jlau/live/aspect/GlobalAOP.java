package com.jlau.live.aspect;

import com.jlau.live.utils.RequestUtils;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by cxr1205628673 on 2019/7/11.
 */
@Aspect
@Component
public class GlobalAOP {
    @Pointcut(value = "execution(* com.jlau.live.service.*.*(..))")
    public void log(){}

    @AfterThrowing(value = "log()",throwing = "e")
    public void doAfterthrowing(JoinPoint joinPoint, Throwable e){
        long happendTime = System.currentTimeMillis();
        Map<String,Object> map = RequestUtils.getJoinPointInfoMap(joinPoint);
        System.out.println(happendTime);
    }

    @Before(value = "log()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("before");
    }

    @After(value = "log()")
    public void doAfter(JoinPoint joinPoint){

    }
    @AfterReturning(value = "log()",returning = "r")
    public void doReturning(JoinPoint joinPoint,Object r){

    }
}
