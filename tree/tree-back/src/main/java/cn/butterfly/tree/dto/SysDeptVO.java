package cn.butterfly.tree.dto;

import cn.butterfly.tree.node.BaseTreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门 vo
 *
 * @author zjw
 * @date 2021-12-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptVO extends BaseTreeNode<SysDeptVO> {

    /**
     * 部门编码
     */
    private String code;

    /**
     * 部门名称
     */
    private String name;

    @Override
    public String getValue() {
        return name;
    }

}
