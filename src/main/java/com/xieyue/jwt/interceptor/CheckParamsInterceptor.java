package com.xieyue.jwt.interceptor;

/**
 * @description: 参数检查注解拦截器
 * @author: xieyue
 * @create: 2020-06-19 11:15
 **/

import com.alibaba.fastjson.JSON;
import com.xieyue.jwt.base.ParamsNotNull;
import com.xieyue.jwt.base.ParamsPostWant;
import com.xieyue.jwt.constants.Const;
import com.xieyue.jwt.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
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

        List<String> list;
        String method = request.getMethod();
        if (Const.HTTP_GET.equalsIgnoreCase(method)) {
            list = getParamsName((HandlerMethod) handler);
            return checkParamsGet(request,response,list);
        }else if(Const.HTTP_POST.equalsIgnoreCase(method)){
            //Post请求body的输入流只能读取一次问题
            // 此处读取了request中的inputStream，因为只能被读取一次，后面spring框架无法读取了，所以需要添加wrapper和filter解决流只能读取一次的问题
            list = getMethodWantParams((HandlerMethod) handler);
            return checkParamsPost(request,response,list);
        }
        return true;
    }

    /**
     * @description  get方法的参数效验
     * @author       XieYue
     * @date         2020/7/17 21:11 
     * @Param        
     * @return       
     **/
    private boolean checkParamsGet(HttpServletRequest request, HttpServletResponse response,List<String> listParams) throws IOException {
        for (String parameName : listParams) {
            String parameValue = request.getParameter(parameName);
            if (StringUtils.isEmpty(parameValue)) {
                return responseResult(response, parameName);
            }
        }
        return true;
    }

    /**
     * @description  post方法的参数效验
     * @author       XieYue
     * @date         2020/7/17 21:11 
     * @Param        
     * @return       
     **/
    private boolean checkParamsPost(HttpServletRequest request, HttpServletResponse response,List<String> listParams) throws IOException {
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
        for (String parame: listParams) {
            if(!params.containsKey(parame)){
                return responseResult(response, parame);
            }
            //TODO:参数其他属性的检测（pageSize必须大于0）
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
            /*
            //post的方法获取RequestBody（json数据）
            if(parameter.isAnnotationPresent(RequestBody.class)){
                RequestBody annotation = parameter.getDeclaredAnnotation(RequestBody.class);
                if(annotation == null){ return null; }
                if(annotation.required()){
                    list.add(parameter.getName());
                }
            }
             */
        }
        return list;
    }

    /**
     * @description  获取post方法上的注解参数
     * @author       XieYue
     * @date         2020/7/17 22:30 
     * @Param        
     * @return       
     **/
    private List getMethodWantParams(HandlerMethod handlerMethod){
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(ParamsPostWant.class)){
            ParamsPostWant paramsPostWant = method.getDeclaredAnnotation(ParamsPostWant.class);
            return Arrays.asList(paramsPostWant.wantParams());
        }
        return new ArrayList<>();
    }

    /**
     * @description  格式化输出参数检查结果
     * @author       XieYue
     * @date         2020/7/17 22:57
     * @Param        
     * @return       JSON
     **/
    private boolean responseResult(HttpServletResponse response, String parameName) throws IOException {
        Result result = new Result();
        result.setCode("0");
        result.setMsg("请求参数缺失");
        result.setResult("缺少必要参数[ " + parameName + " ]值");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");//跨域
        response.getWriter().write(JSON.toJSONString(result));
        return false;
    }

}
