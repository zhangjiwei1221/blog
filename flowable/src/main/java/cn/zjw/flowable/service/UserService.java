package cn.zjw.flowable.service;

import cn.zjw.flowable.entity.User;
import cn.zjw.flowable.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserService
 *
 * @author zjw
 * @date 2021/3/19
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public List<User> findAll() {
        return userMapper.selectList(null);
    }
}
