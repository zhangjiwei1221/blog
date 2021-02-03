package cn.zjw.chatback.controller;

import cn.zjw.chatback.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * PullMessageController
 *
 * @author zjw
 * @createTime 2021/2/3 22:44
 */
@RestController
public class PullMessageController {

    private final RedisUtil redis;

    @Autowired
    public PullMessageController(RedisUtil redis) {
        this.redis = redis;
    }

    @PostMapping("/pullMsg")
    public List<Object> pullMsg(@RequestParam("id") Long id) {
        return redis.get(id);
    }

}
