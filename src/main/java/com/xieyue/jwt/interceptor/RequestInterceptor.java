package com.xieyue.jwt.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.xieyue.jwt.base.ParamsNotNull;
import com.xieyue.jwt.constants.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
public class RequestInterceptor extends HandlerInterceptorAdapter {
    long start = System.currentTimeMillis();

    //在请求处理之前进行调用（Controller方法调用之前)
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        start = System.currentTimeMillis();


        if (!(handler instanceof HandlerMethod)) {
            log.warn("UnSupport handler");
            return true;
        }
        String methodType = httpServletRequest.getMethod();

        if(Const.HTTP_GET.equals(methodType)){

            List<String> list = getParamsName((HandlerMethod) handler);
            for (String s : list) {
                String parameter = httpServletRequest.getParameter(s);
                if (StringUtils.isEmpty(parameter)){
                    JSONObject jsonObject = new JSONObject();
                    //这个地方是定义缺少参数或者参数为空的时候返回的数据
                    jsonObject.put("status", 203);
                    jsonObject.put("msg", "缺少必要的"+s+"值");
                    httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
                    httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");//跨域
                    httpServletResponse.getWriter().write(jsonObject.toJSONString());
                    return false;
                }
            }

        }else if(Const.HTTP_POST.equals(methodType)){

        }

        return true;
    }

    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Interceptor cost="+(System.currentTimeMillis()-start));
    }

    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) throws Exception {
    }


    /**
     * 获取使用了该注解的参数名称
     */
    private List getParamsName(HandlerMethod handlerMethod) {
        Parameter[] parameters = handlerMethod.getMethod().getParameters();
        List<String> list = new ArrayList<>();
        for (Parameter parameter : parameters) {
            //判断这个参数时候被加入了 ParamsNotNull. 的注解
            //.isAnnotationPresent()  这个方法可以看一下
            if(parameter.isAnnotationPresent(ParamsNotNull.class)){
                list.add(parameter.getName());
            }
        }
        return list;
    }
}
