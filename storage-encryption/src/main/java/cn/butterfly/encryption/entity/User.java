package cn.butterfly.encryption.entity;

import cn.butterfly.encryption.annotation.EncryptField;
import cn.butterfly.encryption.annotation.EncryptSerializer;
import cn.butterfly.encryption.handler.EncryptTypeHandler;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户类
 *
 * @author zjw
 * @date 2023-10-27
 */
@Data
@TableName(value = "user", autoResultMap = true)
public class User {
    
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    @EncryptField
    @EncryptSerializer
    @TableField(typeHandler = EncryptTypeHandler.class)
    private String username;

    /**
     * 密码
     */
    @EncryptField
    @EncryptSerializer
    @TableField(typeHandler = EncryptTypeHandler.class)
    private String password;

}
