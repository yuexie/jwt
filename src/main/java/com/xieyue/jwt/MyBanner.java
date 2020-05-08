package com.xieyue.jwt;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * @program: jwt
 * @description:
 * @author: xieyue
 * @create: 2020-05-04 16:33
 **/

public class MyBanner implements Banner {

    private static final String BANNER =
            "  ___ ___         .__  .__          \n" +
                    " /   |   \\   ____ |  | |  |   ____  \n" +
                    "/    ~    \\_/ __ \\|  | |  |  /  _ \\ \n" +
                    "\\    Y    /\\  ___/|  |_|  |_(  <_> )\n" +
                    " \\___|_  /  \\___  >____/____/\\____/ \n" +
                    "       \\/       \\/                  ";

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println(BANNER);
        out.println();
    }
}
