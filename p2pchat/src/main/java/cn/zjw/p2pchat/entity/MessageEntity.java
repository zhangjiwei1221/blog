package cn.zjw.p2pchat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * MessageEntity
 *
 * @author zjw
 * @createTime 2021/2/3 20:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {

    private Long from;
    private Long to;
    private String message;
    private Date time;

}
