package cn.butterfly.tree.service;

import cn.butterfly.tree.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 部门服务类
 *
 * @author zjw
 * @date 2021-12-11
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 获取部门树结构列表
     *
     * @return 部门树结构列表
     */
    List<SysDept> tree();

    /**
     * 根据部门 id 查询指定部门及以下部门树结构列表
     *
     * @param id 部门 id
     * @return 部门树结构列表
     */
    List<SysDept> deptWithChildren(Long id);

    /**
     * 新增部门
     *
     * @param sysDept 部门
     */
    void add(SysDept sysDept);

    /**
     * 更新部门信息
     *
     * @param sysDept 部门
     */
    void update(SysDept sysDept);

    /**
     * 根据 id 删除部门
     *
	 * @param id 部门 id
     */
    void delete(Long id);

}
