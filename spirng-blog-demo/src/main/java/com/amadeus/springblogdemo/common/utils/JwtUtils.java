package com.amadeus.springblogdemo.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    //密钥,HS256 算法要求密钥的长度至少为 256 位（32 字节）,SECRET不能太短
    private static final String SECRET = "rXRoWn8J+bCcnadUFU88AIqhK9caLJCg+6c2rP8F1EE=";
    //过期时间
    private static final long EXPIRE_TIME = 1000L * 60 * 60 * 24;
    //安全密钥
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private JwtUtils() {
    }


    //生成JWT令牌

    public static String genJwt(Map<String, Object> claims) {
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRE_TIME))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }
    //解析JWT令牌

    public static Claims parseJwt(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}
