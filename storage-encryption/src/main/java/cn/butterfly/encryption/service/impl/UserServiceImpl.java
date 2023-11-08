package cn.butterfly.encryption.service.impl;

import cn.butterfly.encryption.entity.User;
import cn.butterfly.encryption.mapper.UserMapper;
import cn.butterfly.encryption.service.IUserService;
import cn.butterfly.encryption.util.AESUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务接口实现类
 *
 * @author zjw
 * @date 2023-10-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User method1(User user) {
        Long userId = 1L;
        user.setId(userId);
        user.setUsername(AESUtils.encrypt(user.getUsername()));
        user.setPassword(AESUtils.encrypt(user.getPassword()));
        saveOrUpdate(user);
        User resultUser = getById(userId);
        resultUser.setUsername(AESUtils.decrypt(resultUser.getUsername()));
        resultUser.setPassword(AESUtils.decrypt(resultUser.getPassword()));
        return resultUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User method2(User user) {
        Long userId = 2L;
        user.setId(userId);
        saveOrUpdate(user);
        return getById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User method3(User user) {
        Long userId = 3L;
        user.setId(userId);
        saveOrUpdate(user);
        return getById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User method4(User user) {
        Long userId = 4L;
        user.setId(userId);
        saveOrUpdate(user);
        return getById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User method5(User user) {
        Long userId = 5L;
        user.setId(userId);
        saveOrUpdate(user);
        return getById(userId);
    }

}
