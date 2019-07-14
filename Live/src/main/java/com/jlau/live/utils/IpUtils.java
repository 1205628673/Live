package com.jlau.live.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * Created by cxr1205628673 on 2019/7/11.
 */
public class IpUtils {
    public static String getIpAddress(HttpServletRequest request){
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length()==0 || "unknow".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length()==0 || "unknow".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length()==0 || "unknow".equalsIgnoreCase(ipAddress)){//还有我们本机访问的情况
            ipAddress = request.getRemoteHost();
            if("127.0.0.1".equals(ipAddress)){
                InetAddress inet = null;
                try{
                    inet = InetAddress.getLocalHost();
                }catch (Exception e){
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        if(ipAddress != null || ipAddress.length() > 15 ) {//用户通过多个代理 ***.***.***.*** 获得的头部不只一个ip,我们只要第一个代理地址
            if(ipAddress.split(",").length>0){
                ipAddress = ipAddress.split(",")[0];
            }
        }
        return ipAddress;
    }
}
