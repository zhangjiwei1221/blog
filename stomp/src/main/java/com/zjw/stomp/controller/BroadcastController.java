package com.zjw.stomp.controller;

import com.zjw.stomp.entity.ClientMessage;
import com.zjw.stomp.entity.ServerMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BroadcastController {

    @MessageMapping("/sendMsg")
    @SendTo("/broadcast/greet")
    public ServerMessage broadcast(ClientMessage clientMessage) {
        String msg = clientMessage.getMsg();
        return new ServerMessage(msg);
    }

}
