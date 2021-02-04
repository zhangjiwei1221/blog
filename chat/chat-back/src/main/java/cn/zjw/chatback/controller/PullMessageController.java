package cn.zjw.chatback.controller;

import cn.zjw.chatback.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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
    public List<Object> pullMsg(@RequestParam("from") Long from, @RequestParam("to") Long to) {
        String key = LongStream.of(from, to)
                .sorted()
                .mapToObj(String::valueOf)
                .collect(Collectors.joining("-"));
        return redis.get(key);
    }

}
