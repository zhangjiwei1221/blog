package cn.zjw.study;

import cn.zjw.study.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class TransactionalApplicationTests {

    @Autowired
    private UserService service;

    @Test
    public void dbTest() {
        service.listByPage(1, 2).forEach(System.out::println);
    }

}
