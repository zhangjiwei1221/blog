package cn.zjw.p2pchat.service;

import cn.zjw.p2pchat.entity.MessageEntity;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * MessageService
 *
 * @author zjw
 * @createTime 2021/2/4 21:52
 */
@Service
public class MessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MessageService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendToUser(String msg) {
        MessageEntity messageEntity = new Gson().fromJson(msg, MessageEntity.class);
        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(messageEntity.getTo()),
                "/chat/contact",
                "123"
        );
    }

}
