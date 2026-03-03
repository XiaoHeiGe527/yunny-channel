package com.yunny.channel.common.util;

import com.yunny.channel.common.tools.redis.RedisService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

/**
 * @author
 */
public class JwtUtil {

    //加密字符
    private static final String secretKey = "aoyou";
    //过期时长
    private static final Integer expireHour = 2;

    @Autowired
    private RedisService redisService;

    public static String generateJwtToken(String userNo, String userId,String salt){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR,expireHour);
        Date expirationDate = now.getTime();
        Claims claims = Jwts.claims();
        claims.put("userNo",userNo);
        claims.put("userId",userId);
        claims.put("salt",salt);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public static String generateJwtToken(String userNo, String userId,String salt,String gvdicrewId){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR,expireHour);
        Date expirationDate = now.getTime();

        Claims claims = Jwts.claims();
        claims.put("userNo",userNo);
        claims.put("userId",userId);
        claims.put("salt",salt);
        claims.put("gvdicrewId",gvdicrewId);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static Claims parseJwtToken(String token){

        try {

            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return jws.getBody();
        }catch (MalformedJwtException e){

            return null;

        }


    }


    public static void main(String[] args) {

        String token = JwtUtil.generateJwtToken("1128","1","2");
        System.out.println(token);
//        String token2 = JwtUtil.generateJwtToken("1128","2","2");
//        System.out.println(token2);
//        String token3 = JwtUtil.generateJwtToken("6562","2","2");
//        System.out.println(token3);
//        String token4 = JwtUtil.generateJwtToken("1128","1","2");
//        System.out.println(token4);
        Claims claims = JwtUtil.parseJwtToken(token);
        System.out.println("claims:"+claims);
    }
}
