package com.xieyue.jwt.base;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description  非空参数注解
 *               使用说明：
 *               1.该注解用于GET请求的参数注解
 *               2.使用示例：@ParamsNotNull String id 表示id必须为非空参数
 * @author       XieYue
 * @date         2020/7/17 22:49
 * @Param
 * @return
 **/
@Target({ElementType.PARAMETER})    //参数级别
@Retention(RetentionPolicy.RUNTIME) //注解保留到运行阶段
public @interface ParamsNotNull {
}
