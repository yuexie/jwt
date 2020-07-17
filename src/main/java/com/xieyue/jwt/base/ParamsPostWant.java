package com.xieyue.jwt.base;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName :   ParamsPostWant
 * @Description : post请求的必须参数
 *                使用说明：
 *                1.该注解用于POST请求的方法注解
 *                2.请求数据格式为JSON
 *                3.使用示例：@ParamsPostWant(wantParams = {"name","age"})
 *                  表示post请求中必须有非空参数name、age
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-17 22:10
 */

@Target({ElementType.METHOD})       //参数级别
@Retention(RetentionPolicy.RUNTIME) //注解保留到运行阶段
public @interface ParamsPostWant {

    String[] wantParams() default {};
}
