package cn.zjw.chatback;

import cn.zjw.chatback.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatBackApplicationTests {

    @Autowired
    private RedisUtil redis;

    @Test
    public void redisTest() {
        System.out.println(redis.get(1L).size());
    }

}
