package cn.zjw.jwtback.util;


import cn.zjw.jwtback.entity.JwtEntity;
import cn.zjw.jwtback.entity.User;
import cn.zjw.jwtback.service.UserService;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class UserSecurityUtil {

    private final RedisUtil redis;
    @Value("${jwt.validate-time}")
    private long validateTime;

    @Autowired
    public UserSecurityUtil(RedisUtil redis) {
        this.redis = redis;
    }

    public boolean verifyWebToken(HttpServletRequest req, HttpServletResponse resp) {
        String token = req.getHeader("authorization");
        if (token == null) {
            return false;
        }
        DecodedJWT jwtToken = JwtUtil.decode(token);
        if (jwtToken == null) {
            return false;
        }
        long uid = Long.parseLong(jwtToken.getSubject());
        if (redis.getExpire(uid) == -2) {
            return false;
        }
        JwtEntity jwtEntity = (JwtEntity) redis.get(uid);
        try {
            JwtUtil.verifyToken(token);
        } catch (SignatureVerificationException e) {
            return false;
        } catch (TokenExpiredException e) {
            String newToken = JwtUtil.getRefreshToken(jwtToken, jwtEntity);
            if (newToken == null) {
                redis.del(uid);
                return false;
            }
            resp.setHeader("authorization", newToken);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        Instant exp = jwtEntity.getLastLoginTime().atZone(ZoneId.systemDefault()).toInstant();
        Instant now = Instant.now();
        if (now.getEpochSecond() - exp.getEpochSecond() <= validateTime) {
            token = JwtUtil.getRefreshToken(jwtToken);
        }
        resp.setHeader("authorization", token);
        return true;
    }
}
