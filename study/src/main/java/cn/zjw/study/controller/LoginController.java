package cn.zjw.study.controller;

import cn.zjw.study.entity.User;
import cn.zjw.study.service.UserService;
import cn.zjw.study.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * LoginController
 *
 * @author zjw
 * @createTime 2021/1/29 14:51
 */
@RestController
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        User user = userService.login(username, password);
        return user == null ? "" : JwtUtil.createToken(user);
    }

}

