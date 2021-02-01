package cn.zjw.jwtback.util;


import cn.zjw.jwtback.entity.JwtEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author zjw
 */
@Component
public class JwtUtil {

    private static String secret;
    private static long expiration;
    private static long rememberTime;
    private static RedisUtil redis;

    @Autowired
    public JwtUtil(RedisUtil redis) {
        JwtUtil.redis = redis;
    }

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

    @Value("${jwt.expiration}")
    public void setExpiration(long expiration) {
        JwtUtil.expiration = expiration;
    }

    @Value("${jwt.remember-time}")
    public void setRememberTime(long rememberTime) {
        JwtUtil.rememberTime = rememberTime;
    }

    public static String createToken(Long uid, Instant issueAt) {
        // 生成 Token
        Instant exp = issueAt.plusSeconds(expiration);
        return createToken(uid.toString(), issueAt, exp);
    }

    public static DecodedJWT decode(String token){
        try {
            // 返回 Token 的解码信息
            return JWT.decode(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void verifyToken(String token) {
        // 校验 Token
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JwtUtil.secret)).build();
        verifier.verify(token);
    }

    public static String getRefreshToken(DecodedJWT jwtToken, JwtEntity jwtEntity) {
        // 这个刷新 Token 类用于处理 Token 失效的情况
        Instant exp = jwtEntity.getLastLoginTime().atZone(ZoneId.systemDefault()).toInstant();
        Instant now = Instant.now();
        // 如果超过记住密码的期限或者是设置未记住密码, 直接返回 null
        if (!jwtEntity.getIsRemember() || (now.getEpochSecond() - exp.getEpochSecond()) > rememberTime) {
            return null;
        }
        // 否则生成刷新的 Token, 并重新设置 redis 中存储的信息
        Instant newExp = exp.plusSeconds(expiration);
        String token = createToken(jwtToken.getSubject(), now, newExp);
        LocalDateTime lastLoginTime = getLastLoginTime(newExp);
        redis.set(jwtToken.getSubject(), new JwtEntity(token, lastLoginTime, true));
        return token;
    }

    public static String getRefreshToken(DecodedJWT jwtToken) {
        // 这个刷新 Token 类用于处理 Token 有效时间小于某个特定的值的情况
        // 生成刷新的 Token, 并重新设置 redis 中存储的信息
        Instant now = Instant.now();
        Instant newExp = now.plusSeconds(expiration);
        String token = createToken(jwtToken.getSubject(), now, newExp);
        redis.set(jwtToken.getSubject(), new JwtEntity(token, getLastLoginTime(now), false));
        return token;
    }

    private static String createToken(String sub, Instant iat, Instant exp) {
        // 生成 Token, 包括用户 uid, 生效和失效日期
        return JWT.create()
                .withClaim("sub", sub)
                .withClaim("iat", Date.from(iat))
                .withClaim("exp", Date.from(exp))
                .sign(Algorithm.HMAC256(JwtUtil.secret));
    }

    private static LocalDateTime getLastLoginTime(Instant newExp) {
        // 获取当前时间的 LocalDateTime 格式
        return LocalDateTime.ofInstant(newExp, ZoneId.systemDefault());
    }

}
