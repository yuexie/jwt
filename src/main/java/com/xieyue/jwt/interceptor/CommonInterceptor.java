package com.xieyue.jwt.interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @program: zkyc-baseframework
 * @description: 拦截器
 * @author: xieyue
 * @create: 2019-10-30 11:18
 **/

@Component
public class CommonInterceptor extends HandlerInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //preHandle在业务处理器处理请求之前被调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 此处配置的是允许任意域名跨域请求，可根据需求指定
       /*
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Headers", "*");


        // 如果是OPTIONS则结束请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.OK.value());
            return true;
        }
        */



        return true;
    }

    //postHandle在业务处理器处理请求执行完成后,生成视图之前执行
    //afterCompletion在DispatcherServlet完全处理完请求后被调用
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        /*
        logger.info("requestIP: =======> " + NetUtils.getIp());
        logger.info("requestMethod: ===> " + request.getMethod());
        logger.info("requestURL: ======> " + request.getRequestURL() + "?" +request.getQueryString());
        */
    }

}
