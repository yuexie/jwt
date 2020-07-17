package com.xieyue.jwt.config;

import com.xieyue.jwt.interceptor.CheckParamsInterceptor;
import com.xieyue.jwt.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: yyyangyang@didichuxing.com
 * @date: 2019-11-04
 */
@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Autowired
    public RequestInterceptor requestInterceptor;

    @Autowired
    public CheckParamsInterceptor checkParamsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(requestInterceptor).addPathPatterns("/**").excludePathPatterns("/login/**","/error");

        registry.addInterceptor(checkParamsInterceptor).addPathPatterns("/**");
    }

}
