package cn.zjw.p2pchat.controller;

import cn.zjw.p2pchat.entity.MessageEntity;
import cn.zjw.p2pchat.service.MessageService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController2 {

    private final MessageService2 messageService;

    @Autowired
    public ChatController2(MessageService2 messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/sendMsg2")
    public void sendMsg(MessageEntity messageEntity) {
        messageService.sendToUser(messageEntity);
    }

}
