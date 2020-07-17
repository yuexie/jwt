package com.zkyc.interceptor;

/**
 * @program: zkyc-show
 * @description: 参数检查注解拦截器
 * @author: xieyue
 * @create: 2020-06-19 11:15
 **/

import com.alibaba.fastjson.JSON;
import com.zkyc.base.ParamsNotNull;
import com.zkyc.baseframework.common.lang.PageResult;
import com.zkyc.constants.Const;
import com.zkyc.enums.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 参数检查注解拦截器
 */

@Slf4j
@Component
public class CheckParamsInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            log.warn("UnSupport handler");
            return true;
        }

        List<String> list = getParamsName((HandlerMethod) handler);
        String method = request.getMethod();
        if (Const.HTTP_GET.equalsIgnoreCase(method)) {
            for (String s : list) {
                String parameter = request.getParameter(s);
                if (StringUtils.isEmpty(parameter)) {
                    PageResult result = new PageResult();
                    result.setReturncode(ReturnCodeEnum.PARAMETER_MISS.getCode());
                    result.setMessage("请求参数缺失");
                    result.setResult("缺少必要的" + s + "值");
                    response.setHeader("Content-type", "application/json;charset=UTF-8");
                    response.setHeader("Access-Control-Allow-Origin", "*");//跨域
                    response.getWriter().write(result.toString());
                    return false;
                }
            }
        }else if(Const.HTTP_POST.equalsIgnoreCase(method)){
            //Post请求body的输入流只能读取一次问题
            // 此处读取了request中的inputStream，因为只能被读取一次，后面spring框架无法读取了，所以需要添加wrapper和filter解决流只能读取一次的问题
            /*
            BufferedReader reader = request.getReader();
            if (reader == null) {
                log.warn("Request for post method, body is empty, signature verification failed.");
                return false;
            }
            StringBuilder jsonParams = new StringBuilder();
            String str = "";
            while ((str=reader.readLine())!=null) {
                jsonParams.append(str);
                jsonParams.append("\n");
            }
            Map<String, String> params = JSON.parseObject(jsonParams.toString(), Map.class);
            System.out.println(params);

             */
        }
        return true;
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
            if(parameter.isAnnotationPresent(RequestParam.class)){
                RequestParam annotation = parameter.getDeclaredAnnotation(RequestParam.class);
                if(annotation == null){ return null; }
                if(annotation.required()){
                    list.add(parameter.getName());
                }
            }
        }
        return list;
    }


}
