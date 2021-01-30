package cn.zjw.jwtback.controller;

import cn.zjw.jwtback.entity.LoginEntity;
import cn.zjw.jwtback.entity.ResultEntity;
import cn.zjw.jwtback.entity.User;
import cn.zjw.jwtback.service.UserService;
import cn.zjw.jwtback.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResultEntity<String> login(@RequestBody LoginEntity loginEntity) {
        ResultEntity<String> resultEntity = new ResultEntity<>();
        User user = userService.findByUsername(loginEntity.getUsername());
        boolean isOk = user != null && user.getPassword().equals(loginEntity.getPassword());
        if (!isOk) {
            resultEntity.setErrMsg("用户名或密码错误");
            resultEntity.setStatus(false);
            return resultEntity;
        }
        String token = userService.createWebToken(user.getId(), loginEntity.getIsRemember());
        resultEntity.setData(token);
        return resultEntity;
    }

}
