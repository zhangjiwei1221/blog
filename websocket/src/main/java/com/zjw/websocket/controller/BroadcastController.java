package com.zjw.websocket.controller;

import com.zjw.websocket.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * BroadcastController
 *
 * @author zjw
 * @createTime 2021/1/17 14:20
 */
@RestController
public class BroadcastController {

    @PostMapping("/broadcast")
    public void broadcast(@RequestBody String msg){
        WebSocketServer.broadcast(msg);
    }
}
