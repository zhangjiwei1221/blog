package cn.zjw.chatback.util;

import cn.zjw.chatback.entity.MessageEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * MessageEntityEncode
 *
 * @author zjw
 * @createTime 2021/2/3 20:54
 */
public class MessageEntityEncode implements Encoder.Text<MessageEntity> {

    @Override
    public String encode(MessageEntity messageEntity) {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
                .toJson(messageEntity);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {}

    @Override
    public void destroy() {}

}
