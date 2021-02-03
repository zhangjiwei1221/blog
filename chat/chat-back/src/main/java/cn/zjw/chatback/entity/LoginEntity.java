package cn.zjw.chatback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginEntity
 *
 * @author zjw
 * @createTime 2021/1/30 20:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginEntity {

    private String username;
    private String password;
}
