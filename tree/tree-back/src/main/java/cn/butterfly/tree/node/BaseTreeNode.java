package cn.butterfly.tree.node;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * 基本树形结构节点抽象类
 *
 * @author zjw
 * @date 2021-12-12
 */
@Data
public abstract class BaseTreeNode<T> implements INode<T> {

	private static final long serialVersionUID = -3574513398762048134L;

	/**
	 * id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 子节点列表
	 */
	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	private List<T> children;

	/**
	 * 父节点 id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

}
