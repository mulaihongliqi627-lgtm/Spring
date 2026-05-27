package com.amadeus.springblogdemo;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
public class JWTTest {
    private long Expiration = 24 * 60 * 60 * 1000;  //24小时
    private String secretString = "rXRoWn8J+bCcnadUFU88AIqhK9caLJCg+6c2rP8F1EE=";
    private Key key = Keys.hmacShaKeyFor(secretString.getBytes());
    /**
     * 生成令牌
     */
    @Test
    public void genJwt(){
//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Map<String, Object> claim = new HashMap<>();
        claim.put("id", 1);
        claim.put("name", "cheems");

        String compact = Jwts.builder().setClaims(claim)
                .setIssuedAt(new Date()) //设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + Expiration))   //有效期
                .signWith(key)
                .compact();
        System.out.println(compact);
    }

    @Test
    public void genKey(){
        // 自动生成符合 HS256 算法安全强度的 SecretKey
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // 将其转化为高度不可读、不可逆向猜测的 Base64 编码字符串
        String encode = Encoders.BASE64.encode(secretKey.getEncoded());
        System.out.println(encode);
    }

    @Test
    public void parseJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiY2hlZW1zIiwiaWQiOjEsImlhdCI6MTc3OTc5NzYwMywiZXhwIjoxNzc5ODg0MDAzfQ.EDXyOGB0iY96-xuv1pJCzVri81CZzLiervjboE4G6P8";
        //校验token是否合法
        //创建解析器, 设置签名密钥
        JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder().setSigningKey(key);
        //解析token
        Claims body = jwtParserBuilder.build().parseClaimsJws(token).getBody();
        System.out.println(body);
    }
}


