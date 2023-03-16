package cn.butterfly.flinkcdc.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 消息事件
 *
 * @author zjw
 * @date 2023-03-16
 */
@Getter
public class FlinkCdcMessageEvent extends ApplicationEvent {

    /**
     * 表名
     */
    private final String tableName;

    /**
     * 消息
     */
    private final String message;

    public FlinkCdcMessageEvent(Object source, String tableName, String message) {
        super(source);
        this.tableName = tableName;
        this.message = message;
    }

}
