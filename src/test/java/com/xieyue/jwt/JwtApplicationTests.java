package com.xieyue.jwt;

import com.xieyue.jwt.utils.DecryptUtil;
import com.xieyue.jwt.utils.EncrypUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.EncryptedPrivateKeyInfo;

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
        String str = encrypUtil.encrypt("10010","10010","hello world!","ZKYC-KEY");
        System.out.println(str);

    }

}
