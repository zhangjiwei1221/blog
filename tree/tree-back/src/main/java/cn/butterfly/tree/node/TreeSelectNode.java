package cn.butterfly.tree.node;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	 * 下拉树结构列表不需要该字段, 使用 @JsonIgnore 注解忽略
	 */
	@JsonIgnore
	private Long parentId;

	@Override
	public String getValue() {
		return value;
	}

}
