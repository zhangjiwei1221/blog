package cn.zjw.jwtback.service;

import cn.zjw.jwtback.dao.UserDao;
import cn.zjw.jwtback.entity.JwtEntity;
import cn.zjw.jwtback.entity.User;
import cn.zjw.jwtback.util.JwtUtil;
import cn.zjw.jwtback.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

/**
 * UserService
 *
 * @author zjw
 * @createTime 2021/1/23 21:33
 */
@Service
public class UserService {

    private final UserDao dao;
    private final RedisUtil redis;

    @Autowired
    public UserService(UserDao dao, RedisUtil redis) {
        this.dao = dao;
        this.redis = redis;
    }

    public User findById(Long uid) {
        return dao.findById(uid).orElse(null);
    }

    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Transactional
    public String createWebToken(Long uid, Boolean isRemember) {
        Instant now = Instant.now();
        String token = JwtUtil.createToken(uid, now);
        LocalDateTime lastLoginTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        redis.set(uid, new JwtEntity(token, lastLoginTime, isRemember));
        return token;
    }

    @Transactional
    public void deleteWebToken(Long uid) {
        redis.del(uid);
    }

}
