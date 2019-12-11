package com.hsx.myshop.config.jwt;

import com.alibaba.fastjson.JSON;
import com.oracle.xmlns.internal.webservices.jaxws_databinding.JavaWsdlMappingType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.util.DigestUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;


public class JwtUtil {

//    public static final long EXPIRATION_TIME = 3600_000_000L; // 1000 hour 有效期
    public static final long EXPIRATION_TIME = 7*24*60*60*1000L; // 7*24 hour 有效期

    public static final String SECRET = "lambeter";//  密钥

    //解析
    public static Claims parseJWT(String jsonWebToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            throw ex;
        }
    }

    //和时间有关的token
    public static String generateToken(Integer id, Date loginTime) {
        String psw = (String) ((long)id + (loginTime == null ? 0L : Math.round(loginTime.getTime()/1000.0)) + SECRET);
        String md5Pass = DigestUtils.md5DigestAsHex(psw.getBytes());
        return md5Pass;
    }

    //生成
    public static String createJWT(String userName, String password) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //当前时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("type", "JWT")
                .claim("userName", userName)
                .claim("password", password)
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        long expMillis = nowMillis + EXPIRATION_TIME;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp).setNotBefore(now);
        //生成JWT
        String jwtTokenStr = builder.compact();
        return jwtTokenStr;
    }

}
