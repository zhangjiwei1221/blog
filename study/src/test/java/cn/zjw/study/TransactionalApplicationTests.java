package cn.zjw.study;

import cn.zjw.study.service.UserService;
import cn.zjw.study.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
class TransactionalApplicationTests {

    @Autowired
    private UserService service;

    @Autowired
    private RedisUtil redis;

    @Test
    public void dbTest() {
        service.listByPage(1, 2).forEach(System.out::println);
    }

    @Test
    public void redisTest() {
        System.out.println(redis.get("username"));
    }

}
