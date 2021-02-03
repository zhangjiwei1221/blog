package cn.zjw.chatback.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public List<Object> get(Long key) {
        return get(String.valueOf(key));
    }

    public List<Object> get(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public void set(Long key, Object value) {
        set(String.valueOf(key), value);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

}

