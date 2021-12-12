package cn.butterfly.tree.node;

import java.io.Serializable;
import java.util.List;

/**
 * 树形节点接口
 *
 * @author zjw
 * @date 2020-12-12
 */
public interface INode<T> extends Serializable {

	/**
	 * 获取 id
	 *
	 * @return id
	 */
	Long getId();

	/**
	 * 获取父节点 id
	 *
	 * @return 父父节点 id
	 */
	Long getParentId();

	/**
	 * 获取子节点
	 *
	 * @return 子节点列表
	 */
	List<T> getChildren();

	/**
	 * 设置子节点
	 *
	 * @param children 子节点列表
	 */
	void setChildren(List<T> children);

}
