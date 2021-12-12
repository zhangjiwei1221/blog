package cn.butterfly.tree.node;

import cn.butterfly.tree.constant.BaseConstants;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树节点合并工具类
 *
 * @author zjw
 * @date 2021-12-12
 */
public class TreeMerger {

	/**
	 * 合并节点列表生成树形结构列表
	 *
	 * @param nodeList 节点列表
	 * @return 树形结构列表
	 */
	public static <T extends INode<T>> List<T> mergeTreeList(List<T> nodeList) {
		if (CollectionUtils.isEmpty(nodeList)) {
			return Collections.emptyList();
		}
		Map<Long, T> nodeMap = nodeList.stream()
				.collect(Collectors.toMap(INode::getId, Function.identity()));
		Set<Long> parentIdSet = new HashSet<>(nodeList.size());
		for (T node : nodeList) {
			Long parentId = node.getParentId();
			T parentNode = nodeMap.get(parentId);
			if (parentNode == null) {
				parentIdSet.add(parentId);
			} else {
				List<T> children = Optional.ofNullable(parentNode.getChildren())
						.orElse(new ArrayList<>());
				children.add(node);
				parentNode.setChildren(children);
			}
		}
		return getRootNodeList(nodeMap.values(), parentIdSet);
	}

	/**
	 * 获取根节点列表
	 *
	 * @param nodeList 节点列表
	 * @param parentIdSet 父 id 组成的 Set
	 * @return 根节点列表
	 */
	private static <T extends INode<T>> List<T> getRootNodeList(Collection<T> nodeList, Set<Long> parentIdSet) {
		return nodeList.stream()
				.filter(node -> isRootNode(node, parentIdSet))
				.collect(Collectors.toList());
	}

	/**
	 * 判断节点是否为根节点
	 *
	 * @param node 节点
	 * @param parentIdSet 父 id 组成的 Set
	 * @return 是否为根节点
	 */
	private static <T extends INode<T>> boolean isRootNode(T node, Set<Long> parentIdSet) {
		return node.getParentId() == BaseConstants.ROOT_NODE || parentIdSet.contains(node.getId());
	}

	private TreeMerger() {}

}
