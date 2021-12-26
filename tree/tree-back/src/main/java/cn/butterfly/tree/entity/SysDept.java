package cn.butterfly.tree.entity;

import cn.butterfly.tree.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门表
 *
 * @author zjw
 * @date 2021-12-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseEntity {

    private static final long serialVersionUID = 4118531689265448832L;

    /**
     * 部门 id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 父部门 id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 左节点
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long lft;

    /**
     * 右节点
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long rgt;

    /**
     * 部门编码
     */
    private String code;

    /**
     * 部门名称
     */
    private String name;

}
