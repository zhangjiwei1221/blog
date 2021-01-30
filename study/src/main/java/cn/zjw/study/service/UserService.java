package cn.zjw.study.service;

import cn.zjw.study.dao.UserDao;
import cn.zjw.study.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserService
 *
 * @author zjw
 * @createTime 2021/1/23 21:33
 */
@Service
@Transactional
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

    public List<User> listByPage(int pageIndex, int pageCount) {
        return dao.findAll(PageRequest.of(pageIndex, pageCount)).stream().collect(Collectors.toList());
    }

    public User login(String username, String password) {
        return dao.findByUsernameAndPassword(username, password);
    }

}
