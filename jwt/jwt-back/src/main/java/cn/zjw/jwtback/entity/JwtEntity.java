package cn.zjw.jwtback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * JwtEntity
 *
 * @author zjw
 * @createTime 2021/1/30 15:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtEntity {

    private String token;
    private LocalDateTime lastLoginTime;
    private Boolean isRemember;

}
