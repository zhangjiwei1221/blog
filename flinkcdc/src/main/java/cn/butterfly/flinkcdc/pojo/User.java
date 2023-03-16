package cn.butterfly.flinkcdc.pojo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author zjw
 * @date 2023-03-16
 */
@Data
public class User {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
