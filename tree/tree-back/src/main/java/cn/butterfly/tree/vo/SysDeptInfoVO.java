package cn.butterfly.tree.vo;

import lombok.Data;

/**
 * 部门详情 vo
 *
 * @author zjw
 * @date 2021-12-26
 */
@Data
public class SysDeptInfoVO {

    /**
     * 父部门名称
     */
    private String parentDeptName;

    /**
     * 部门编码
     */
    private String code;

    /**
     * 部门名称
     */
    private String name;

}
