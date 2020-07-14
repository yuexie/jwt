package com.xieyue.jwt.config;

import com.xieyue.jwt.Constants.Const;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @program: jwt
 * @description:
 * @author: xieyue
 * @create: 2020-05-29 10:13
 **/


@Component
public class SettingConfig implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        //LocalDate now = LocalDate.now();
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-DD HH:mm:ss");
        //Const.APP_START_TIME = now.format(dtf);

        Const.APP_START_TIME = System.currentTimeMillis();

        System.out.println("CommandLineRunner>>>>>>>>>>>>>>>>>");
    }
}