package cn.zjw.jwtback.controller;

import cn.zjw.jwtback.entity.ResultEntity;
import cn.zjw.jwtback.entity.User;
import cn.zjw.jwtback.service.UserService;
import cn.zjw.jwtback.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public ResultEntity<String> login(String username, String password, Boolean isRemember) {
        ResultEntity<String> resultEntity = new ResultEntity<>();
        User user = userService.findByUsername(username);
        boolean isOk = user != null && user.getPassword().equals(password);
        if (!isOk) {
            resultEntity.setErrMsg("用户名或密码错误");
            resultEntity.setStatus(false);
            return resultEntity;
        }
        String token = userService.createWebToken(user.getId(), isRemember);
        resultEntity.setData(token);
        return resultEntity;
    }

}
