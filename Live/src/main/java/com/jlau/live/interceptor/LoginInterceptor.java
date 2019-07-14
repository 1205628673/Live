package com.jlau.live.interceptor;

import com.jlau.live.response.Response;
import com.jlau.live.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cxr1205628673 on 2019/7/11.
 */
public class LoginInterceptor implements HandlerInterceptor{
    @Autowired
    UserAccountService userAccountService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length>0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    Response<String> rsp = userAccountService.checkToken(cookie.getValue());
                    if ("true".equals(rsp.getSuccess())) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        response.sendRedirect("/login");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
