package cn.zjw.jwtback.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    // 防止 LocalDateTime 在序列化中出错
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastLoginTime;
    private Boolean isRemember;

}
