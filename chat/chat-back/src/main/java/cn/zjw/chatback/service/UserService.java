package cn.zjw.chatback.service;

import cn.zjw.chatback.dao.UserDao;
import cn.zjw.chatback.entity.User;
import cn.zjw.chatback.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * UserService
 *
 * @author zjw
 * @createTime 2021/1/23 21:33
 */
@Service
public class UserService {

    private final UserDao dao;

    @Autowired
    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public User findById(Long uid) {
        return dao.findById(uid).orElse(null);
    }

    public User findByUsernameAndPassword(String username, String password) {
        return dao.findByUsernameAndPassword(username, password);
    }

    public List<User> getFriends(Long uid) {
        return LongStream.of(1L, 2L, 3L, 4L)
                .filter(item -> item != uid)
                .mapToObj(this::findById)
                .collect(Collectors.toList());
    }

}
