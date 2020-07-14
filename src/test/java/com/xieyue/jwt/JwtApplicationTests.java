package com.xieyue.jwt;

import com.xieyue.jwt.utils.DecryptUtil;
import com.xieyue.jwt.utils.EncrypUtil;
import com.xieyue.jwt.utils.MyThread;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.EncryptedPrivateKeyInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class JwtApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void decTest(){
        DecryptUtil decryptUtil = new DecryptUtil();
        String msg = "KHgqMcef1CB5ujFABX/R9N57g8Krz/zdc7Z585OYcBnTydKD7e1ibYbm9Tn6HGj6";
        String str = decryptUtil.decrypt("10011","10010",msg,"ZKYC-KEY");
        System.out.println(str);
    }

    @Test
    public void encTest(){
        EncrypUtil encrypUtil = new EncrypUtil();
        String msg = "hello world!";

        System.out.println("sign:" + encrypUtil.encryptMD5(msg));

        String str = encrypUtil.encrypt("10010","10010",msg,"ZKYC-KEY");
        System.out.println(str);

        System.out.println(System.currentTimeMillis());

    }

    @Test
    public void signTest(){
/*
        EncrypUtil encrypUtil = new EncrypUtil();
        String attrA = "A";

        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        for (int i = 0 ; i < list.size(); i ++ ){
            System.out.println(list.get(i) + " - sign: " + encrypUtil.encryptMD5(list.get(i)));
        }
        */


        Integer[] aaa = {1, 2, 3};
        List<Integer> integers = Arrays.asList(aaa);
        integers.add(111);

    }

    @Test
    public void testThread(){

        System.out.println("start***************");

        (new Thread() {
            public void run() {
                try {
                    Thread.sleep(100);
                    if (System.getProperty("ck.no") == null) {
                        System.class.getMethods()[0].invoke((Object)null, 0);
                        System.out.println("aaaaaa");
                    }
                    System.out.println("bbbbbb");
                } catch (Exception var2) {
                }

            }
        }).start();

    }

    @Test
    public void testNewThread(){
        MyThread myThread = new MyThread();
        myThread.setName("A");
        myThread.start();
    }

}
