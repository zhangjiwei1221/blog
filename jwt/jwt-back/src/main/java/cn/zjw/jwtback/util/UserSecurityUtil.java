package cn.zjw.jwtback.util;


import cn.zjw.jwtback.entity.JwtEntity;
import cn.zjw.jwtback.entity.User;
import cn.zjw.jwtback.service.UserService;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class UserSecurityUtil {

    private final RedisUtil redis;

    @Autowired
    public UserSecurityUtil(RedisUtil redis) {
        this.redis = redis;
    }

    public boolean verifyWebToken(HttpServletRequest req, HttpServletResponse resp) {
        String token = req.getHeader("Authorization");
        if (token.isEmpty()) {
            return false;
        }
        DecodedJWT jwtToken = JwtUtil.decode(token);
        if (jwtToken == null) {
            return false;
        }
        long uid = Long.parseLong(jwtToken.getSubject());
        JwtEntity jwtEntity = (JwtEntity) redis.get(uid);
        LocalDateTime lastLoginTime = jwtEntity.getLastLoginTime();
        try {
            JwtUtil.verifyToken(token);
        } catch (SignatureVerificationException e) {
            return false;
        } catch (TokenExpiredException e) {
            String newToken = JwtUtil.getRefreshToken(jwtToken, lastLoginTime.atZone(ZoneId.systemDefault()).toInstant());
            if (newToken == null) {
                return false;
            }
            resp.setHeader("Authorization", newToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
