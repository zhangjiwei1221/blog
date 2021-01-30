package cn.zjw.study.util;


import cn.zjw.study.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static String alg;
    private static String typ;
    private static String secret;
    private static long expiration;

    @Value("${jwt.alg}")
    public void setAlg(String alg) {
        JwtUtil.alg = alg;
    }

    @Value("${jwt.typ}")
    public void setTyp(String typ) {
        JwtUtil.typ = typ;
    }

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

    @Value("${jwt.expiration}")
    public void setExpiration(long expiration) {
        JwtUtil.expiration = expiration;
    }

    public static String createToken(User user) {
        Date expireDate = new Date(System.currentTimeMillis() + JwtUtil.expiration * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", JwtUtil.alg);
        map.put("typ", JwtUtil.typ);
        return JWT.create()
                .withHeader(map)
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withExpiresAt(expireDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(JwtUtil.secret));
    }

    public static boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JwtUtil.secret)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
