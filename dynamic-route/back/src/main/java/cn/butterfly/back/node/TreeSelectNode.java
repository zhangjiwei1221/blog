package cn.butterfly.back.node;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 下拉树结构节点类
 *
 * @author zjw
 * @date 2021-12-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TreeSelectNode extends BaseTreeNode<TreeSelectNode> {

	/**
	 * 节点值
	 */
	private String value;

	/**
	 * 父节点 id
	 */
	private Long parentId;

}
