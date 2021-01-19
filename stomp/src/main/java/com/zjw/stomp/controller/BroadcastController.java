package com.zjw.stomp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BroadcastController {

    @MessageMapping("/sendMsg")
    @SendTo("/broadcast/greet")
    public String broadcast(String msg) {
        return msg;
    }

}
