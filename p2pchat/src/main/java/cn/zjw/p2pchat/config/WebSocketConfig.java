package cn.zjw.p2pchat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 开启一个简单的基于内存的消息代理
        // 将消息返回到订阅了带 /chat 前缀的目的客户端
        config.enableSimpleBroker("/chat");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个 /websocket/{id} 的 WebSocket 终端
        // {id} 用于让用户连接终端时都可以有自己的路径
        // 作为 Principal 的标识，以便实现向指定用户发送信息
        registry.addEndpoint("/websocket/{id}")
                .setHandshakeHandler(new CustomHandshakeHandler());
    }

}
