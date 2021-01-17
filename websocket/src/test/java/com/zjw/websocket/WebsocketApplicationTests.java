package com.zjw.websocket;

import com.zjw.websocket.dao.UserDao;
import com.zjw.websocket.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebsocketApplicationTests {

    @Autowired
    private UserDao dao;

    @Test
    public void jdbcTest() {
        User user = new User();
        user.setUsername("张三");
        user.setPassword("123456");
        System.out.println(dao.isOk(user));
    }

}
