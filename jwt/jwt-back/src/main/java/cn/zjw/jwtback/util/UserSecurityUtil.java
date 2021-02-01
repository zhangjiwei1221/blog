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
        // 获取请求头中的 authorization 信息
        String token = req.getHeader("authorization");
        // 如果为空直接返回 false
        if (token == null) {
            return false;
        }
        // 解码 Token 信息, 如果为空直接返回 false
        DecodedJWT jwtToken = JwtUtil.decode(token);
        if (jwtToken == null) {
            return false;
        }
        // 获取 Token 信息中的用户 id 信息
        // 到 redis 中判断, 如果不存在相关信息直接返回 false
        long uid = Long.parseLong(jwtToken.getSubject());
        if (redis.getExpire(uid) == -2) {
            return false;
        }
        // 根据 uid 到 redis 中获取 JwtEntity 实体信息
        JwtEntity jwtEntity = (JwtEntity) redis.get(uid);
        try {
            // 继续校验
            JwtUtil.verifyToken(token);
        } catch (SignatureVerificationException e) {
            // 出现签名校验异常直接返回 false
            return false;
        } catch (TokenExpiredException e) {
            // 如果过期, 判断是否符合获得刷新 Token 的条件
            // 如果返回为空, 说明 Token 过期, 删除 redis 中的信息, 并返回 false
            String newToken = JwtUtil.getRefreshToken(jwtToken, jwtEntity);
            if (newToken == null) {
                redis.del(uid);
                return false;
            }
            // 否则说明符合 token 刷新条件, 设置返回头部的 authorization, 并返回 true
            resp.setHeader("authorization", newToken);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        // 校验成功, 判断是否为不记住密码, 且 Token剩余有效时间小于某个特定值
        // 若小于某个特定时间, 则刷新 Token
        if (!jwtEntity.getIsRemember()) {
            Instant exp = jwtEntity.getLastLoginTime().atZone(ZoneId.systemDefault()).toInstant();
            Instant now = Instant.now();
            if (now.getEpochSecond() - exp.getEpochSecond() <= validateTime) {
                token = JwtUtil.getRefreshToken(jwtToken);
            }
        }
        // 设置返回头中的 token
        resp.setHeader("authorization", token);
        return true;
    }
}
