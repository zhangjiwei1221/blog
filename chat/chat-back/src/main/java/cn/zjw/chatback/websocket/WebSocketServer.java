package cn.zjw.chatback.websocket;

import cn.zjw.chatback.config.CustomSpringConfigurator;
import cn.zjw.chatback.entity.MessageEntity;
import cn.zjw.chatback.util.MessageEntityDecode;
import cn.zjw.chatback.util.MessageEntityEncode;
import cn.zjw.chatback.util.RedisUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Component
@ServerEndpoint(
    value = "/websocket/{id}",
    decoders = { MessageEntityDecode.class },
    encoders = { MessageEntityEncode.class },
    configurator = CustomSpringConfigurator.class
)
public class WebSocketServer {

    private Long id = 0L;
    private Session session;
    private final Gson gson;
    private final RedisUtil redis;
    private static final Map<Long, Session> WEBSOCKET_MAP = new ConcurrentHashMap<>();

    @Autowired
    public WebSocketServer(Gson gson, RedisUtil redis) {
        this.gson = gson;
        this.redis = redis;
    }

    @OnOpen
    public void onOpen(@PathParam("id") Long id, Session session) {
        this.id = id;
        this.session = session;
        WEBSOCKET_MAP.put(this.id, session);
    }

    @OnMessage
    public void onMessage(MessageEntity message) throws IOException {
        String key = LongStream.of(message.getFrom(), message.getTo())
                            .sorted()
                            .mapToObj(String::valueOf)
                            .collect(Collectors.joining("-"));
        redis.set(key, message);
        if (WEBSOCKET_MAP.get(message.getTo()) != null) {
            WEBSOCKET_MAP.get(message.getTo()).getBasicRemote().sendText(gson.toJson(message));
        }
    }

    @OnClose
    public void onClose() {
        for (Map.Entry<Long, Session> entry : WEBSOCKET_MAP.entrySet()) {
            if (this.session.getId().equals(entry.getValue().getId())) {
                WEBSOCKET_MAP.remove(entry.getKey());
                return;
            }
        }
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

}
