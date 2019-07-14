package com.jlau.live.config;

import com.jlau.live.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by cxr1205628673 on 2019/7/5.
 */
@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer{
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/live").setViewName("liveapp");
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //要排除registry页面和login页面(也就是你要跳转的页面),不然回循环请求你跳转的页面
        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/registry").excludePathPatterns("/login");
    }
    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }
}
