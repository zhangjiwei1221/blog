package cn.butterfly.tree.vo;

import cn.butterfly.tree.constant.BaseConstants;
import cn.butterfly.tree.node.BaseTreeNode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 部门树 vo
 *
 * @author zjw
 * @date 2021-12-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptTreeVO extends BaseTreeNode<SysDeptTreeVO> {

    /**
     * 部门编码
     */
    private String code;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = BaseConstants.DB_DATE_FORMAT_PATTERN)
    private LocalDateTime gmtCreate;

}
