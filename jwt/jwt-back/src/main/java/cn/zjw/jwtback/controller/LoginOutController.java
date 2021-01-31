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
        userService.deleteWebToken(uid);
        return "注销成功";
    }

}
