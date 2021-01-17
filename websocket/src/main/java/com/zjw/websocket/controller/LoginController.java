package com.zjw.websocket.controller;

import com.zjw.websocket.dao.UserDao;
import com.zjw.websocket.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginController
 *
 * @author zjw
 * @createTime 2021/1/16 17:27
 */
@RestController
public class LoginController {

    private final UserDao dao;

    @Autowired
    public LoginController(UserDao dao) {
        this.dao = dao;
    }

    @PostMapping("/login")
    public boolean login(@RequestBody User user) {
        return dao.isOk(user);
    }

}
