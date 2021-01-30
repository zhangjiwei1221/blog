package cn.zjw.jwtback.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Date;

/**
 * @author zjw
 */
@Component
public class JwtUtil {

    private static String secret;
    private static long expiration;
    private static long rememberTime;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

    @Value("${jwt.expiration}")
    public void setExpiration(long expiration) {
        JwtUtil.expiration = expiration;
    }

    @Value("${jwt.remember-time}")
    public static void setRememberTime(long rememberTime) {
        JwtUtil.rememberTime = rememberTime;
    }

    public static String createToken(Long uid, Instant issueAt) {
        Instant exp = issueAt.plusSeconds(expiration);
        return JWT.create()
                .withClaim("sub", uid.toString())
                .withClaim("iat", Date.from(issueAt))
                .withClaim("exp", Date.from(exp))
                .sign(Algorithm.HMAC256(JwtUtil.secret));
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

    public static String getRefreshToken(DecodedJWT jwtToken, Instant exp) {
        Instant now = Instant.now();
        if ((now.getEpochSecond() - exp.getEpochSecond()) > rememberTime) {
            return null;
        }
        Instant newExp = exp.plusSeconds(expiration);
        return JWT.create()
                .withClaim("sub", jwtToken.getSubject())
                .withClaim("iat", Date.from(exp))
                .withClaim("exp", Date.from(newExp))
                .sign(Algorithm.HMAC256(JwtUtil.secret));
    }

}
