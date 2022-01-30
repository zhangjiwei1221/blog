package cn.butterfly.canal.listener;

import cn.butterfly.canal.entity.CanalMessage;
import cn.butterfly.canal.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 用户表 Canal 变更 RocketMQ 监听器
 *
 * @author zjw
 * @date 2022-01-30
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "canal_topic",
        consumerGroup = "canal_group"
)
public class UserCanalListener implements RocketMQListener<CanalMessage<User>> {

    @Override
    public void onMessage(CanalMessage<User> message) {
        String lineSeparator = System.lineSeparator();
        StringBuilder info = new StringBuilder(lineSeparator);
        info.append("==========数据变更信息==========").append(lineSeparator);
        info.append(String.format("数据库.表名: %s.%s%n", message.getDatabase(), message.getTable()));
        info.append(String.format("操作类型: %s%n", message.getType()));
        message.getData().forEach(user -> info.append(user).append(lineSeparator));
        log.info(info.toString());
    }

}
