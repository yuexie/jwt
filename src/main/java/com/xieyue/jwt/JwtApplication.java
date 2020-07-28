package com.xieyue.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@ServletComponentScan
@MapperScan("com.xieyue.jwt.dao.mapper")   //tkmybatis的注解
@SpringBootApplication  //(exclude={DataSourceAutoConfiguration.class})
public class JwtApplication {

    public static void main(String[] args) {

        /*
        SpringApplication springApplication = new SpringApplication(JwtApplication.class);
        // 设置自定义 Banner
        springApplication.setBanner(new MyBanner());
        // 启动SpringBoot
        springApplication.run(args);
         */

        SpringApplication.run(JwtApplication.class, args);
    }

}
