package cn.zjw.chatback.controller;

import cn.zjw.chatback.entity.LoginEntity;
import cn.zjw.chatback.entity.ResultEntity;
import cn.zjw.chatback.entity.User;
import cn.zjw.chatback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * LoginController
 *
 * @author zjw
 * @createTime 2021/1/29 14:51
 */
@RestController
public class LoginInController {

    private final UserService userService;

    @Autowired
    public LoginInController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginEntity loginEntity) {
        return userService.findByUsernameAndPassword(loginEntity.getUsername(), loginEntity.getPassword());
    }

}
