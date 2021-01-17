package com.zjw.websocket.websocket;

import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@ServerEndpoint(value = "/websocket")
public class WebSocketServer {

    private Session session;
    private static final Map<String, WebSocketServer> webSocketMap = new LinkedHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketMap.put(session.getId(), this);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable error, Session session) throws Throwable {
        throw error;
    }

    @OnClose
    public void onClose() {
        webSocketMap.remove(session.getId());
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public static void broadcast(String message) {
        webSocketMap.forEach((k, v) -> {
            try {
                v.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
