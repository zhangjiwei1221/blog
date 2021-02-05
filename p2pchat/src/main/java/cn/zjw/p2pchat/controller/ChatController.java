package cn.zjw.p2pchat.controller;

import cn.zjw.p2pchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final MessageService messageService;

    @Autowired
    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    // 这里的 @MessageMapping 可以当成 @RequestMapping,
    // 当有信息(sendMsg 方法中的 msg 参数即为客服端发送的信息)发送到 /sendMsg 时
    // 会在这里进行处理
    @MessageMapping("/sendMsg")
    public void sendMsg(String msg) {
        messageService.sendToUser(msg);
    }

}
