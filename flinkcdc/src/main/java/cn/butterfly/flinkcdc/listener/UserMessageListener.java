package cn.butterfly.flinkcdc.listener;

import cn.butterfly.flinkcdc.annotation.FlickCdcMessageListener;
import cn.butterfly.flinkcdc.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户表消息监听器
 *
 * @author zjw
 * @date 2023-03-14
 */
@Slf4j
@Component
@FlickCdcMessageListener("user")
public class UserMessageListener extends AbstractMessageListener<User> {

    @Override
    public void create(User user) {
        log.info("新增用户: {}", user);
    }

}
