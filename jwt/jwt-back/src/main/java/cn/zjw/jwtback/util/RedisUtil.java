package cn.zjw.jwtback.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean setExpire(int key, long time) {
        return setExpire(String.valueOf(key), time);
    }

    public boolean setExpire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getExpire(String key) {
        Long time = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return time == null ? -2 : time;
    }

    public long getExpire(int key) {
        return getExpire(String.valueOf(key));
    }

    public boolean hasKey(String key) {
        Boolean isExist = redisTemplate.hasKey(key);
        return isExist != null && !isExist.equals(false);
    }

    public boolean hasKey(int key) {
        return hasKey(String.valueOf(key));
    }

    public void del(Integer... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(String.valueOf(key[0]));
            } else {
                for (Integer s : key) {
                    redisTemplate.delete(String.valueOf(s));
                }
            }
        }
    }

    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                for (String s : key) {
                    redisTemplate.delete(s);
                }
            }
        }
    }

    public String get(int key) {
        return get(String.valueOf(key));
    }

    public String get(String key) {
        return Objects.requireNonNull(redisTemplate.opsForValue().get(key)).toString();
    }

    public boolean set(int key, Object value) {
        return set(String.valueOf(key), value);
    }

    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(int key, Object value, long time) {
        return set(String.valueOf(key), value, time);
    }

    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

