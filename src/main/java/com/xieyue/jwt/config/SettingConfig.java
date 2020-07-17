package com.xieyue.jwt.config;

import com.xieyue.jwt.constants.Const;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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