package cn.butterfly.encryption.controller;

import cn.butterfly.encryption.annotation.EncryptMethod;
import cn.butterfly.encryption.entity.User;
import cn.butterfly.encryption.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 用户控制类
 *
 * @author zjw
 * @date 2023-10-27
 */
@RestController
public class UserController {
    
    @Resource
    private IUserService userService;
    
    @PostMapping("/method1")
    public User method1(@RequestBody User user) {
        return userService.method1(user);
    }
    
    @EncryptMethod
    @PostMapping("/method2")
    public User method2(@RequestBody User user) {
        return userService.method2(user);
    }
    
    @PostMapping("/method3")
    public User method3(@RequestBody User user) {
        return userService.method3(user);
    }
    
    @PostMapping("/method4")
    public User method4(@RequestBody User user) {
        return userService.method4(user);
    }
    
    @PostMapping("/method5")
    public User method5(@RequestBody User user) {
        return userService.method5(user);
    }
    
}
