package cn.butterfly.tree.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 部门懒加载树 vo
 *
 * @author zjw
 * @date 2022-01-10
 */
@Data
public class SysDeptLazyTreeVO {

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 是否为叶子节点
     */
    private Boolean isLeaf;

}
