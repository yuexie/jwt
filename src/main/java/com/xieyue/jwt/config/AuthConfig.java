package com.xieyue.jwt.config;

import com.xieyue.jwt.interceptor.RequestInterceptor;
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

    @Bean
    public RequestInterceptor initAuthInterceptor(){
        return new RequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initAuthInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**","/error");
    }

}
