package cn.zjw.jwtback.service;

import cn.zjw.jwtback.dao.UserDao;
import cn.zjw.jwtback.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public boolean updateUsername(User user) {
        if (dao.existsById(user.getId())) {
            dao.save(user);
            return true;
        }
        return false;
    }

    public List<User> list() {
        return dao.findAll();
    }

    public User login(String username, String password) {
        return dao.findByUsernameAndPassword(username, password);
    }

}
