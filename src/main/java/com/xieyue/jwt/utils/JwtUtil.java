package com.xieyue.jwt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: jwt
 * @description:
 * @author: xieyue
 * @create: 2020-05-04 12:47
 **/

public class JwtUtil {

    /**
     * token 过期时间, 单位: 秒. 这个值表示 30 天
     */
    private static final long TOKEN_EXPIRED_TIME = 30 * 24 * 60 * 60;
    /**
     * jwt 加密解密密钥
     */
    private static final String JWT_SECRET = "MD54ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";


    /** 
    * @Description: 创建jwt
    * @Param:       
    * @Author:      xieyue 
    * @Date:        2020/5/4 
    */ 
    public static String creatJWT(Map<String, Object> claims){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signingKey = getSecretKey();
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                //.setPayload("this is json message")
                .setIssuer("clientId0")
                .setAudience("audienceName")
                .signWith(signingKey, signatureAlgorithm);
        //添加Token过期时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + TOKEN_EXPIRED_TIME;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp).setNotBefore(now);
        //生成JWT
        return builder.compact();

    }

    /** 
    * @Description: 验证jwt
    * @Param:       
    * @Author:      xieyue 
    * @Date:        2020/5/4 
    */ 
    public static String parseJwt(String jwsString){
        Key signingKey = getSecretKey();
        try {
            Jws<Claims> claims = Jwts.parserBuilder()           // (1)
                                .setSigningKey(signingKey)      // (2) <---- publicKey, not privateKey
                                .build()                        // (3)
                                .parseClaimsJws(jwsString);     // (4)
            if (claims == null) {
                return null;
            }

            return claims.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /** 
    * @Description: 获取加签的key
    * @Param:       
    * @Author:      xieyue 
    * @Date:        2020/5/4 
    */ 
    public static Key getSecretKey(){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET);
        byte[] apiKeySecretBytes = Base64.decodeBase64(JWT_SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    public static void main(String[] args) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("txCode",    "2001");
        claims.put("brNo",      "10001");
        claims.put("reqDate",   "20200420");
        claims.put("reqTime",   "12:04:22");

        String result = JwtUtil.creatJWT(claims);

        System.out.println(result);

        String jws = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ0b2tlblRpbWUiOiIyMDIwLTA1LTEzIDE0OjI4OjMzIiwidXNlcm5hbWUiOiJ4eSIsImlzcyI6ImNsaWVudElkMCIsImF1ZCI6ImF1ZGllbmNlTmFtZSIsImV4cCI6MTU4OTI2NzUwNSwibmJmIjoxNTg5MjY0OTEzfQ.jnFaVNy2hKcQGODm16l2gAYhVrayc8sdV0FFjxZ-Fg4";

        String parseStr = JwtUtil.parseJwt(result);
        //hCbJ3LJdHNufA88yUi3h2ZU8IPCRVk4E3J5tx9OCe4A
        //VTszXX52h2NIGqwtK13c8LxSKQDKpWQU1wd3qgMzA3M
        System.out.println(parseStr);
    }
}
