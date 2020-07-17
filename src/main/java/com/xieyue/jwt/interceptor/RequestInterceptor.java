package com.xieyue.jwt.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.xieyue.jwt.base.ParamsNotNull;
import com.xieyue.jwt.constants.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
    long start = System.currentTimeMillis();

    //在请求处理之前进行调用（Controller方法调用之前)
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        start = System.currentTimeMillis();
        return true;
    }

    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("RequestInterceptor cost="+(System.currentTimeMillis()-start));
    }

    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) throws Exception {
    }
}
