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
        Instant exp = issueAt.plusSeconds(expiration);
        return createToken(uid.toString(), issueAt, exp);
    }

    public static DecodedJWT decode(String token){
        try {
            return JWT.decode(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JwtUtil.secret)).build();
        verifier.verify(token);
    }

    public static String getRefreshToken(DecodedJWT jwtToken, JwtEntity jwtEntity) {
        Instant exp = jwtEntity.getLastLoginTime().atZone(ZoneId.systemDefault()).toInstant();
        Instant now = Instant.now();
        if (!jwtEntity.getIsRemember() || (now.getEpochSecond() - exp.getEpochSecond()) > rememberTime) {
            return null;
        }
        Instant newExp = exp.plusSeconds(expiration);
        String token = createToken(jwtToken.getSubject(), now, newExp);
        LocalDateTime lastLoginTime = getLastLoginTime(newExp);
        redis.set(jwtToken.getSubject(), new JwtEntity(token, lastLoginTime, true));
        return token;
    }

    public static String getRefreshToken(DecodedJWT jwtToken) {
        Instant now = Instant.now();
        Instant newExp = now.plusSeconds(expiration);
        String token = createToken(jwtToken.getSubject(), now, newExp);
        redis.set(jwtToken.getSubject(), new JwtEntity(token, getLastLoginTime(now), false));
        return token;
    }

    private static String createToken(String sub, Instant iat, Instant exp) {
        return JWT.create()
                .withClaim("sub", sub)
                .withClaim("iat", Date.from(iat))
                .withClaim("exp", Date.from(exp))
                .sign(Algorithm.HMAC256(JwtUtil.secret));
    }

    private static LocalDateTime getLastLoginTime(Instant newExp) {
        return LocalDateTime.ofInstant(newExp, ZoneId.systemDefault());
    }

}
