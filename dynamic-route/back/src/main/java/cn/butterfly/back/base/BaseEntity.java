package cn.butterfly.back.base;

import cn.butterfly.back.constant.BaseConstants;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基本实体类
 *
 * @author zjw
 * @date 2021-12-11
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -4698467095812925592L;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = BaseConstants.DB_DATE_FORMAT_PATTERN)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = BaseConstants.DB_DATE_FORMAT_PATTERN)
    private LocalDateTime gmtModified;

    /**
     * 是否已删除(0: 否 1: 是)
     */
    @TableLogic
    private Integer delFlag;

}
