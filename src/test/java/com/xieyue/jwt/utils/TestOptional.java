package com.xieyue.jwt.utils;

import com.xieyue.jwt.dao.entity.Visitor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * @ClassName :   TestOptional
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-31 22:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestOptional {


    @Test
    public void testOptionalFun(){

        String str = "helow world";
        Optional<String> optional = Optional.of(str);
        optional.ifPresent(System.out::println);

        String orElseResult = optional.orElse("dont find");
        System.out.println(orElseResult);

        String orElseGet    = optional.orElseGet(()-> "hellow get" );
        System.out.println(orElseGet);

        String orThrow      = optional.orElseThrow(()->
                new IllegalArgumentException("Argument 'str' cannot bu null"));
    }

    @Test
    public void testOptEntity(){
        Visitor visitor1 = new Visitor();
        visitor1.setVisitorName("abc");
        getVisitorInfo(visitor1);

        Visitor visitor2 = new Visitor();
        getVisitorInfo(visitor2);

        LocalDateTime dateTimeNow = LocalDateTime.now();
        System.out.println(dateTimeNow);
    }

    private void getVisitorInfo(Visitor visitor){
        Optional<Visitor> visitorOpt = Optional.ofNullable(visitor);
        String result = visitorOpt.map(v -> v.getVisitorName()).orElse("noname");
        System.out.println(result);


    }

}
