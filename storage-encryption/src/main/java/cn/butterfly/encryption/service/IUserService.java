package cn.butterfly.encryption.service;

import cn.butterfly.encryption.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户服务接口类
 *
 * @author zjw
 * @date 2023-10-27
 */
public interface IUserService extends IService<User> {

    /**
     * 加密方式一, 手动调用加密解密方法
     * 
     * @param user 用户信息
     * @return 解密后的用户信息
     */
    User method1(User user);

    /**
     * 加密方式二, 使用 aop 进行加密解密
     * 
     * @param user 用户信息
     * @return 解密后的用户信息
     */
    User method2(User user);
    
    /**
     * 加密方式三, 自定义序列化注解
     * 
     * @param user 用户信息
     * @return 解密后的用户信息
     */
    User method3(User user);

    /**
     * 加密方式四, MybatisPlus 自定义 TypeHandler
     * 
     * @param user 用户信息
     * @return 解密后的用户信息
     */
    User method4(User user);

    /**
     * 加密方式四, MybatisPlus 自定义 Interceptor
     * 
     * @param user 用户信息
     * @return 解密后的用户信息
     */
    User method5(User user);
    
}
