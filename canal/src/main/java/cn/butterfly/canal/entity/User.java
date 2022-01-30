package cn.butterfly.canal.entity;

import lombok.Data;

/**
 * 用户信息
 *
 * @author zjw
 * @date 2022-01-30
 */
@Data
public class User {

    /**
     * 用户 id
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
     * email
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

}
