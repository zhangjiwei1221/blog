package cn.zjw.jwtback.controller;

import cn.zjw.jwtback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * LoginOutController
 *
 * @author zjw
 * @createTime 2021/1/30 20:43
 */

@RestController
public class LoginOutController {

    private final UserService userService;

    @Autowired
    public LoginOutController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/exit")
    public String login(Long uid) {
        // 用户注销时, 根据用户 id 删除 redis 中存储的信息
        // 由于前台 uid 存在 localStorage, 因此这里存在一些安全问题
        // 不过本文属于 Jwt 的入门使用, 不做进一步处理
        userService.deleteWebToken(uid);
        return "注销成功";
    }

}
